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
package org.openhab.binding.vwcarnet.internal.handler;

import static org.openhab.binding.vwcarnet.internal.VWCarNetBindingConstants.CHANNEL_TIMESTAMP;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.ThingStatusInfo;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.BridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.vwcarnet.internal.DeviceStatusListener;
import org.openhab.binding.vwcarnet.internal.VWCarNetSession;
import org.openhab.binding.vwcarnet.internal.VehicleConfiguration;
import org.openhab.binding.vwcarnet.internal.model.BaseVehicle;
import org.openhab.binding.vwcarnet.internal.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class and handler for some of the different thing types that VWCarNet provides.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class VWCarNetHandler extends BaseThingHandler implements DeviceStatusListener {

    protected final Logger logger = LoggerFactory.getLogger(VWCarNetHandler.class);

    protected @Nullable VWCarNetSession session;

    protected @NonNullByDefault({}) VehicleConfiguration config;

    public VWCarNetHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("VWCarNetHandler handleCommand, channel: {}, command: {}", channelUID, command);
        if (command instanceof RefreshType) {
            Bridge bridge = getBridge();
            if (bridge != null) {
                BridgeHandler bridgeHandler = bridge.getHandler();
                if (bridgeHandler != null) {
                    bridgeHandler.handleCommand(channelUID, command);
                }
            }
            String vin = config.vin;
            if (session != null && vin != null) {
                BaseVehicle thing = session.getVWCarNetThing(vin);
                update(thing);
            }
        } else {
            logger.warn("Unknown command! {}", command);
        }
    }

    protected void scheduleImmediateRefresh(int refreshDelay) {
        logger.debug("scheduleImmediateRefresh on thing: {}", thing);
        Bridge bridge = getBridge();
        if (bridge != null && bridge.getHandler() != null) {
            VWCarNetBridgeHandler vbh = (VWCarNetBridgeHandler) bridge.getHandler();
            if (vbh != null) {
                vbh.scheduleImmediateRefresh(refreshDelay);
            }
        }
    }

    protected void updateTimeStamp(@Nullable String lastUpdatedTimeStamp) {
        if (lastUpdatedTimeStamp != null) {
            try {
                logger.debug("Parsing date {}", lastUpdatedTimeStamp);
                ZonedDateTime zdt = ZonedDateTime.parse(lastUpdatedTimeStamp);
                ZonedDateTime zdtLocal = zdt.withZoneSameInstant(ZoneId.systemDefault());

                logger.trace("Parsing datetime successful. Using date. {}", new DateTimeType(zdtLocal));
                ChannelUID cuid = new ChannelUID(getThing().getUID(), CHANNEL_TIMESTAMP);
                updateState(cuid, new DateTimeType(zdtLocal));
            } catch (IllegalArgumentException e) {
                logger.warn("Parsing date failed: {}.", e);
            }
        } else {
            logger.debug("Timestamp is null!");
        }
    }

    @Override
    public void initialize() {
        logger.debug("initialize on thing: {}", thing);
        // Do not go online
        config = getConfigAs(VehicleConfiguration.class);
        if (config.vin == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "Vehicle is missing VIN");
        }
        Bridge bridge = getBridge();
        if (bridge != null) {
            this.bridgeStatusChanged(bridge.getStatusInfo());
        }

    }

    @Override
    public void dispose() {
        logger.debug("dispose on thing: {}", thing);
        Bridge bridge = getBridge();
        if (bridge != null) {
            VWCarNetBridgeHandler vbh = (VWCarNetBridgeHandler) bridge.getHandler();
            if (vbh != null) {
                session = vbh.getSession();
                if (session != null) {
                    session.unregisterDeviceStatusListener(this);
                }
            }
        }
    }

    @Override
    public void bridgeStatusChanged(ThingStatusInfo bridgeStatusInfo) {
        logger.debug("bridgeStatusChanged bridgeStatusInfo: {}", bridgeStatusInfo);
        if (bridgeStatusInfo.getStatus() == ThingStatus.ONLINE) {
            Bridge bridge = getBridge();
            if (bridge != null) {
                VWCarNetBridgeHandler vbh = (VWCarNetBridgeHandler) bridge.getHandler();
                if (vbh != null) {
                    session = vbh.getSession();
                    String vin = config.vin;
                    if (session != null && vin != null) {
                        update(session.getVWCarNetThing(vin));
                        session.registerDeviceStatusListener(this);
                    }
                }
            }
        }
        super.bridgeStatusChanged(bridgeStatusInfo);
    }

    @Override
    public void onDeviceStateChanged(@Nullable BaseVehicle vehicle) {
        logger.trace("onDeviceStateChanged on vehicle: {}", vehicle);
        if (vehicle != null) {
            if (vehicle instanceof Vehicle) {
                Vehicle theVehicle = (Vehicle) vehicle;
                if (config.vin.equals((theVehicle.getCompleteVehicleJson().getVin()))) {
                    update(theVehicle);
                }
            } else {
                logger.debug("Update failed, unknown vehicle type.");
            }
        }
    }

    public synchronized void update(@Nullable BaseVehicle thing) {
        logger.debug("Update on base class. {}", thing);
    }

    @Override
    public void onDeviceRemoved(@Nullable BaseVehicle thing) {
        logger.trace("onDeviceRemoved on thing: {}", thing);
    }

    @Override
    public void onDeviceAdded(@Nullable BaseVehicle thing) {
        logger.trace("onDeviceAdded on thing: {}", thing);
    }
}
