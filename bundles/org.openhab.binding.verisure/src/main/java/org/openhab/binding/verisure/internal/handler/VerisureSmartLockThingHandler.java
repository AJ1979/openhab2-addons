/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.verisure.internal.handler;

import static org.openhab.binding.verisure.internal.VerisureBindingConstants.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.UnDefType;
import org.openhab.binding.verisure.internal.model.VerisureSmartLock;
import org.openhab.binding.verisure.internal.model.VerisureSmartLock.DoorLockVolumeSettings;
import org.openhab.binding.verisure.internal.model.VerisureSmartLocks;
import org.openhab.binding.verisure.internal.model.VerisureSmartLocks.Doorlock;
import org.openhab.binding.verisure.internal.model.VerisureThing;

/**
 * Handler for the Smart Lock Device thing type that Verisure provides.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class VerisureSmartLockThingHandler extends VerisureThingHandler {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES = Collections.singleton(THING_TYPE_SMARTLOCK);

    private static final int REFRESH_DELAY_SECONDS = 10;

    public VerisureSmartLockThingHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("handleCommand, channel: {}, command: {}", channelUID, command);
        if (command instanceof RefreshType) {
            super.handleCommand(channelUID, command);
            return;
        } else if (channelUID.getId().equals(CHANNEL_SMARTLOCK_STATUS)) {
            handleSmartLockState(command);
        } else if (channelUID.getId().equals(CHANNEL_AUTO_RELOCK)) {
            handleAutoRelock(command);
        } else if (channelUID.getId().equals(CHANNEL_SMARTLOCK_VOLUME)) {
            handleSmartLockVolumeAndVoiceLevel(command, true);
        } else if (channelUID.getId().equals(CHANNEL_SMARTLOCK_VOICE_LEVEL)) {
            handleSmartLockVolumeAndVoiceLevel(command, false);
        } else {
            logger.warn("Unknown command! {}", command);
            return;
        }
        scheduleImmediateRefresh(REFRESH_DELAY_SECONDS);
    }

    private void handleSmartLockState(Command command) {
        String deviceId = config.getDeviceId();
        if (session != null && deviceId != null) {
            VerisureSmartLocks smartLock = (VerisureSmartLocks) session.getVerisureThing(deviceId);
            if (smartLock != null) {
                BigDecimal installationId = smartLock.getSiteId();
                String pinCode = session.getPinCode(installationId);
                if (deviceId != null && pinCode != null && installationId != null) {
                    StringBuilder sb = new StringBuilder(deviceId);
                    sb.insert(4, " ");
                    String url = START_GRAPHQL;
                    String operation;
                    if (command == OnOffType.OFF) {
                        operation = "DoorUnlock";
                    } else if (command == OnOffType.ON) {
                        operation = "DoorLock";
                    } else {
                        logger.debug("Unknown command! {}", command);
                        return;
                    }
                    String queryQLSmartLockSetState = "[{\"operationName\":\"" + operation
                            + "\",\"variables\":{\"giid\":\"" + installationId + "\",\"deviceLabel\":\"" + deviceId
                            + "\",\"input\":{\"code\":\"" + pinCode + "\"}},\"query\":\"mutation " + operation
                            + "($giid: String!, $deviceLabel: String!, $input: LockDoorInput!) {\\n  " + operation
                            + "(giid: $giid, deviceLabel: $deviceLabel, input: $input)\\n}\\n\"}]";
                    logger.debug("Trying to set SmartLock state to {} with URL {} and data {}", operation, url,
                            queryQLSmartLockSetState);
                    int httpResultCode = session.sendCommand(url, queryQLSmartLockSetState, installationId);
                    if (httpResultCode == HttpStatus.OK_200) {
                        logger.debug("SmartLock status successfully changed!");
                    } else {
                        logger.warn("Failed to send command, HTTP result code {}", httpResultCode);
                    }
                } else if (pinCode == null) {
                    logger.warn("PIN code is not configured! It is mandatory to control SmartLock!");
                }
            }
        }
    }

    private void handeAutoRelockResult(String url, String data, BigDecimal installationId, Command command) {
        logger.debug("Trying to set Auto Relock {} with URL {} and data {}", command.toString(), url, data);
        int httpResultCode = session.sendCommand(url, data, installationId);
        if (httpResultCode == HttpStatus.OK_200) {
            logger.debug("AutoRelock sucessfully changed to {}", command.toString());
        } else {
            logger.warn("Failed to send command, HTTP result code {}", httpResultCode);
        }
    }

    private void handleAutoRelock(Command command) {
        String deviceId = config.getDeviceId();
        if (session != null && deviceId != null) {
            VerisureSmartLocks smartLock = (VerisureSmartLocks) session.getVerisureThing(deviceId);
            if (smartLock != null) {
                BigDecimal installationId = smartLock.getSiteId();
                if (installationId != null && deviceId != null) {
                    String csrf = session.getCsrfToken(installationId);
                    StringBuilder sb = new StringBuilder(deviceId);
                    sb.insert(4, "+");
                    String data;
                    String url = SMARTLOCK_AUTORELOCK_COMMAND;
                    if (command == OnOffType.ON) {
                        data = "enabledDoorLocks=" + sb.toString()
                                + "&doorLockDevices%5B0%5D.autoRelockEnabled=true&_doorLockDevices%5B0%5D.autoRelockEnabled=on&_csrf="
                                + csrf;
                        handeAutoRelockResult(url, data, installationId, command);
                    } else if (command == OnOffType.OFF) {
                        data = "enabledDoorLocks=&doorLockDevices%5B0%5D.autoRelockEnabled=true&_doorLockDevices%5B0%5D.autoRelockEnabled=on&_csrf="
                                + csrf;
                        handeAutoRelockResult(url, data, installationId, command);
                    } else {
                        logger.debug("Unknown command! {}", command);
                    }
                }
            }
        }
    }

    private boolean isSettingAllowed(List<String> allowedSettings, String setting) {
        return allowedSettings.contains(setting);
    }

    private void handleSmartLockVolumeAndVoiceLevel(Command command, boolean setVolume) {
        String deviceId = config.getDeviceId();
        if (session != null && deviceId != null) {
            VerisureSmartLocks smartLock = (VerisureSmartLocks) session.getVerisureThing(deviceId);
            if (smartLock != null) {
                DoorLockVolumeSettings volumeSettings = smartLock.getSmartLockJSON().getDoorLockVolumeSettings();
                if (volumeSettings != null) {
                    String volume;
                    String voiceLevel;
                    if (setVolume) {
                        List<String> availableVolumes = volumeSettings.getAvailableVolumes();
                        if (availableVolumes != null && isSettingAllowed(availableVolumes, command.toString())) {
                            volume = command.toString();
                            voiceLevel = volumeSettings.getVoiceLevel();
                        } else {
                            logger.debug("Failed to change volume, setting not allowed {}", command.toString());
                            return;
                        }
                    } else {
                        List<String> availableVoiceLevels = volumeSettings.getAvailableVoiceLevels();
                        if (availableVoiceLevels != null
                                && isSettingAllowed(availableVoiceLevels, command.toString())) {
                            volume = volumeSettings.getVolume();
                            voiceLevel = command.toString();
                        } else {
                            logger.debug("Failed to change voice level, setting not allowed {}", command.toString());
                            return;
                        }
                        BigDecimal installationId = smartLock.getSiteId();
                        String csrf = session.getCsrfToken(installationId);
                        if (installationId != null && csrf != null) {
                            String url = SMARTLOCK_VOLUME_COMMAND;
                            String data = "keypad.volume=MEDIUM&keypad.beepOnKeypress=true&_keypad.beepOnKeypress=on&siren.volume=MEDIUM&voiceDevice.volume=MEDIUM&doorLock.volume="
                                    + volume + "&doorLock.voiceLevel=" + voiceLevel
                                    + "&_devices%5B0%5D.on=on&devices%5B1%5D.on=true&_devices%5B1%5D.on=on&devices%5B2%5D.on=true&_devices%5B2%5D.on=on&_devices%5B3%5D.on=on&_keypad.keypadsPlayChime=on&_siren.sirensPlayChime=on&_csrf="
                                    + csrf;
                            logger.debug("Trying to set SmartLock volume with URL {} and data {}", url, data);
                            int httpResultCode = session.sendCommand(url, data, installationId);
                            if (httpResultCode == HttpStatus.OK_200) {
                                logger.debug("SmartLock volume sucessfully changed!");
                            } else {
                                logger.warn("Failed to send command, HTTP result code {}", httpResultCode);
                            }
                        } else {
                            logger.debug("Unknown command! {}", command);
                        }
                    }
                }
            }
        }
    }

    @Override
    public synchronized void update(@Nullable VerisureThing thing) {
        logger.debug("update on thing: {}", thing);
        updateStatus(ThingStatus.ONLINE);
        if (getThing().getThingTypeUID().equals(THING_TYPE_SMARTLOCK)) {
            VerisureSmartLocks obj = (VerisureSmartLocks) thing;
            if (obj != null) {
                updateSmartLockState(obj);
            }
        } else {
            logger.warn("Can't handle this thing typeuid: {}", getThing().getThingTypeUID());
        }
    }

    private void updateSmartLockState(VerisureSmartLocks smartLocksJSON) {
        Doorlock doorlock = smartLocksJSON.getData().getInstallation().getDoorlocks().get(0);
        String smartLockStatus = doorlock.getCurrentLockState();
        VerisureSmartLock smartLockJSON = smartLocksJSON.getSmartLockJSON();
        if (smartLockStatus != null && smartLockJSON != null) {
            getThing().getChannels().stream().map(Channel::getUID)
                    .filter(channelUID -> isLinked(channelUID) && !channelUID.getId().equals("timestamp"))
                    .forEach(channelUID -> {
                        State state = getValue(channelUID.getId(), doorlock, smartLockStatus, smartLockJSON);
                        updateState(channelUID, state);
                    });
            super.update(smartLocksJSON);
            updateTimeStamp(doorlock.getEventTime());
        } else {
            logger.debug("Smart lock status {} or smartLockJSON {} is null!", smartLockStatus, smartLockJSON);
        }
    }

    public State getValue(String channelId, Doorlock doorlock, String smartLockStatus,
            VerisureSmartLock smartLockJSON) {
        switch (channelId) {
            case CHANNEL_SMARTLOCK_STATUS:
                if ("LOCKED".equals(smartLockStatus)) {
                    return OnOffType.ON;
                } else if ("UNLOCKED".equals(smartLockStatus)) {
                    return OnOffType.OFF;
                } else if ("PENDING".equals(smartLockStatus)) {
                    // Schedule another refresh.
                    logger.debug("Issuing another immediate refresh since status is still PENDING ...");
                    this.scheduleImmediateRefresh(REFRESH_DELAY_SECONDS);
                }
                break;
            case CHANNEL_CHANGED_BY_USER:
                String user = doorlock.getUserString();
                return user != null ? new StringType(user) : UnDefType.NULL;
            case CHANNEL_CHANGED_VIA:
                String method = doorlock.getMethod();
                return method != null ? new StringType(method) : UnDefType.NULL;
            case CHANNEL_MOTOR_JAM:
                String motorJam = doorlock.isMotorJam().toString();
                return motorJam != null ? new StringType(motorJam) : UnDefType.NULL;
            case CHANNEL_LOCATION:
                String location = doorlock.getDevice().getArea();
                return location != null ? new StringType(location) : UnDefType.NULL;
            case CHANNEL_AUTO_RELOCK:
                Boolean autoRelock = smartLockJSON.getAutoRelockEnabled();
                return autoRelock ? OnOffType.ON : OnOffType.OFF;
            case CHANNEL_SMARTLOCK_VOLUME:
                return smartLockJSON.getDoorLockVolumeSettings() != null
                        ? new StringType(smartLockJSON.getDoorLockVolumeSettings().getVolume())
                        : UnDefType.NULL;
            case CHANNEL_SMARTLOCK_VOICE_LEVEL:
                return smartLockJSON.getDoorLockVolumeSettings() != null
                        ? new StringType(smartLockJSON.getDoorLockVolumeSettings().getVoiceLevel())
                        : UnDefType.NULL;
        }
        return UnDefType.UNDEF;
    }
}