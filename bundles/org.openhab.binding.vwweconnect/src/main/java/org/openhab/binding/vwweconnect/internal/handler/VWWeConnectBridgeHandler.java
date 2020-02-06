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
package org.openhab.binding.vwweconnect.internal.handler;

import static org.openhab.binding.vwweconnect.internal.VWWeConnectBindingConstants.CHANNEL_STATUS;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerService;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.vwweconnect.internal.DeviceStatusListener;
import org.openhab.binding.vwweconnect.internal.VWWeConnectBridgeConfiguration;
import org.openhab.binding.vwweconnect.internal.VWWeConnectSession;
import org.openhab.binding.vwweconnect.internal.discovery.VWWeConnectDiscoveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link VWWeConnectBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class VWWeConnectBridgeHandler extends BaseBridgeHandler {

    private static final int REFRESH_DELAY_SECONDS = 30;

    private final Logger logger = LoggerFactory.getLogger(VWWeConnectBridgeHandler.class);
    private final ReentrantLock immediateRefreshJobLock = new ReentrantLock();

    private @Nullable String securePIN;
    private int refresh = 600;
    private @Nullable ScheduledFuture<?> refreshJob;
    private @Nullable ScheduledFuture<?> immediateRefreshJob;
    private @Nullable VWWeConnectSession session;
    private HttpClient httpClient;

    public VWWeConnectBridgeHandler(Bridge bridge, HttpClient httpClient) {
        super(bridge);
        this.httpClient = httpClient;
        session = new VWWeConnectSession(this.httpClient);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("VWWeConnectBridgeHandler Handle command {} on channelUID: {}", command, channelUID);
        if (command instanceof RefreshType) {
            if (channelUID.getId().equals(CHANNEL_STATUS) && channelUID.getThingUID().equals(getThing().getUID())) {
                logger.debug("Refresh command on status channel {} will trigger instant refresh", channelUID);
                scheduleImmediateRefresh(0);
            } else {
                logger.debug("Refresh command on channel {} will trigger refresh in {} seconds", channelUID,
                        REFRESH_DELAY_SECONDS);
                scheduleImmediateRefresh(REFRESH_DELAY_SECONDS);
            }
        } else {
            logger.warn("unknown command! {}", command);
        }
    }

    @Override
    protected void updateThing(Thing thing) {
        super.updateThing(thing);
    }

    @Override
    protected void updateConfiguration(Configuration configuration) {
        stopAutomaticRefresh();
        stopImmediateRefresh();
        super.updateConfiguration(configuration);
        initialize();
    }

    public @Nullable VWWeConnectSession getSession() {
        return session;
    }

    @Override
    public void initialize() {
        logger.debug("Initializing VWWeConnect Bridge");
        VWWeConnectBridgeConfiguration config = getConfigAs(VWWeConnectBridgeConfiguration.class);
        this.securePIN = config.spin;
        this.refresh = config.refresh;
        if (config.username == null || config.password == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Configuration of username and password is mandatory");
        } else {
            updateStatus(ThingStatus.UNKNOWN);

            scheduler.execute(() -> {
                if (session == null) {
                    logger.debug("Session is null, let's create a new one");
                    session = new VWWeConnectSession(this.httpClient);
                }
                if (!session.initialize(config.username, config.password, securePIN)) {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.HANDLER_REGISTERING_ERROR,
                            "Failed to login to VW We Connect portal, please check your credentials!");
                    return;
                }
                startAutomaticRefresh();
            });
        }
    }

    @Override
    public void dispose() {
        logger.debug("Handler disposed.");
        stopAutomaticRefresh();
        stopImmediateRefresh();
        if (session != null) {
            session.dispose();
            session = null;
        }
    }

    @Override
    public void channelLinked(ChannelUID channelUID) {
        // Do not do a refresh since that will refresh all things as well
        logger.debug("Channel linked {}.", channelUID);
    }

    public boolean registerObjectStatusListener(DeviceStatusListener deviceStatusListener) {
        if (session != null) {
            logger.debug("registerObjectStatusListener for listener {}", deviceStatusListener);
            return session.registerDeviceStatusListener(deviceStatusListener);
        }
        return false;
    }

    public boolean unregisterObjectStatusListener(DeviceStatusListener deviceStatusListener) {
        if (session != null) {
            logger.debug("unregisterObjectStatusListener for listener {}", deviceStatusListener);
            return session.unregisterDeviceStatusListener(deviceStatusListener);
        }
        return false;
    }

    @Override
    public void handleRemoval() {
        logger.debug("handleRemoval");
        stopAutomaticRefresh();
        stopImmediateRefresh();
        if (session != null) {
            session.dispose();
            session = null;
        }
    }

    @Override
    public Collection<Class<? extends ThingHandlerService>> getServices() {
        return Collections.singleton(VWWeConnectDiscoveryService.class);
    }

    public @Nullable String getSecurePIN() {
        return securePIN;
    }

    private void refreshAndUpdateStatus() {
        logger.debug("VWWeConnectBridgeHandler - Refresh thread is up'n running!");
        try {
            if (session != null) {
                boolean success = session.refresh();
                if (success) {
                    updateStatus(ThingStatus.ONLINE);
                    logger.debug("Refresh success!");
                } else {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
                    logger.warn("Refresh failed!");
                }
            }
        } catch (Exception e) {
            logger.debug("Exception occurred during execution: {}", e.getMessage(), e);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
        }
    }

    void scheduleImmediateRefresh(int refreshDelay) {
        logger.debug("VWWeConnectBridgeHandler - scheduleImmediateRefresh");
        immediateRefreshJobLock.lock();
        try {
            // We schedule in 10 sec, to avoid multiple updates
            if (refreshJob != null) {
                logger.debug("Current remaining delay {} for refresh job {}", refreshJob.getDelay(TimeUnit.SECONDS),
                        refreshJob);
                if (immediateRefreshJob != null) {
                    logger.debug("Current remaining delay {} for immediate refresh job {}",
                            immediateRefreshJob.getDelay(TimeUnit.SECONDS), immediateRefreshJob);
                }
                if (refreshJob.getDelay(TimeUnit.SECONDS) > refreshDelay) {
                    if (immediateRefreshJob == null || immediateRefreshJob.getDelay(TimeUnit.SECONDS) <= 0) {
                        if (immediateRefreshJob != null) {
                            logger.debug("Current remaining delay {} for immediate refresh job {}",
                                    immediateRefreshJob.getDelay(TimeUnit.SECONDS), immediateRefreshJob);
                        }
                        // Note we are using getDelay() instead of isDone() as we want to allow Things to schedule a
                        // refresh if their status is pending. As the status update happens inside the
                        // refreshAndUpdateStatus
                        // execution the isDone() will return false and would not allow the rescheduling of the task.
                        immediateRefreshJob = scheduler.schedule(this::refreshAndUpdateStatus, refreshDelay,
                                TimeUnit.SECONDS);
                        logger.debug("Scheduling new immediate refresh job {}", immediateRefreshJob);
                    }
                }
            }
        } catch (RejectedExecutionException e) {
            logger.warn("Immediate refresh job cannot be scheduled!");
        } finally {
            immediateRefreshJobLock.unlock();
        }
    }

    private void startAutomaticRefresh() {
        logger.debug("Start automatic refresh {}", refreshJob);
        if (refreshJob == null || refreshJob.isCancelled()) {
            try {
                refreshJob = scheduler.scheduleWithFixedDelay(this::refreshAndUpdateStatus, 0, refresh,
                        TimeUnit.SECONDS);
                logger.debug("Scheduling at fixed delay refreshjob {}", refreshJob);
            } catch (IllegalArgumentException e) {
                logger.warn("Refresh time value is invalid! Please change the refresh time configuration!", e);
            } catch (RejectedExecutionException e) {
                logger.warn("Automatic refresh job cannot be started!");
            }
        }
    }

    private void stopImmediateRefresh() {
        immediateRefreshJobLock.lock();
        try {
            logger.debug("Stop immediate refresh for job {}", immediateRefreshJob);
            if (immediateRefreshJob != null && !immediateRefreshJob.isCancelled()) {
                immediateRefreshJob.cancel(true);
                immediateRefreshJob = null;
            }
        } catch (RejectedExecutionException e) {
            logger.warn("Immediate refresh job cannot be scheduled!");
        } finally {
            immediateRefreshJobLock.unlock();
        }
    }

    private void stopAutomaticRefresh() {
        logger.debug("Stop automatic refresh for job {}", refreshJob);
        if (refreshJob != null && !refreshJob.isCancelled()) {
            refreshJob.cancel(true);
            refreshJob = null;
        }
    }
}
