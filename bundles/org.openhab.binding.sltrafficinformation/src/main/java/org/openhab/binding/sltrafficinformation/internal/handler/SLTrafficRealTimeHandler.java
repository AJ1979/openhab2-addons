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
package org.openhab.binding.sltrafficinformation.internal.handler;

import static org.openhab.binding.sltrafficinformation.internal.SLTrafficInformationBindingConstants.CHANNEL_REAL_TIME_INFO;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.UnDefType;
import org.eclipse.smarthome.io.net.http.HttpUtil;
import org.openhab.binding.sltrafficinformation.internal.SLTrafficInformationConfiguration;
import org.openhab.binding.sltrafficinformation.internal.model.SLTrafficRealTime;
import org.openhab.binding.sltrafficinformation.internal.model.SLTrafficRealTime.TrafficType;
import org.openhab.binding.sltrafficinformation.internal.util.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The {@link SLTrafficRealTimeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class SLTrafficRealTimeHandler extends BaseThingHandler {
    private static final int REQUEST_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(20);
    private final Logger logger = LoggerFactory.getLogger(SLTrafficRealTimeHandler.class);

    private @Nullable SLTrafficInformationConfiguration config;
    private int refresh = 600;
    private @Nullable ScheduledFuture<?> refreshJob;

    private static final String SL_REAL_TIME_INFO_URL = "https://api.sl.se/api2/realtimedeparturesV4.json";

    private Gson gson = new GsonBuilder().create();

    public SLTrafficRealTimeHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            refreshAndUpdateStatus();
        }
    }

    @Override
    public void dispose() {
        logger.debug("Handler is disposed.");
        stopAutomaticRefresh();
    }

    @Override
    public void initialize() {
        // logger.debug("Start initializing!");
        config = getConfigAs(SLTrafficInformationConfiguration.class);

        // set the thing status to UNKNOWN temporarily and let the background task decide for the real status.
        // the framework is then able to reuse the resources from the thing handler initialization.
        // we set this upfront to reliably check status updates in unit tests.
        updateStatus(ThingStatus.UNKNOWN);

        // Example for background initialization:
        scheduler.execute(() -> {
            boolean thingReachable = true; // <background task with long running initialization here>
            // when done do:
            if (thingReachable) {
                updateStatus(ThingStatus.ONLINE);
                startAutomaticRefresh();
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
        });

        logger.debug("Finished initializing!");

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
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

    private void stopAutomaticRefresh() {
        logger.debug("Stop automatic refresh for job {}", refreshJob);
        if (refreshJob != null && !refreshJob.isCancelled()) {
            refreshJob.cancel(true);
            refreshJob = null;
        }
    }

    private void refreshAndUpdateStatus() {
        logger.debug("SLTrafficInformationHandler - Refresh thread is up'n running!");

        String url = SL_REAL_TIME_INFO_URL + "?key=" + config.apiKeyRealTime + "&siteid=" + config.siteId
                + "&timewindow=" + config.timeWindow;
        SLTrafficRealTime realTimeInfo;
        try {
            String htmlJSON = HttpUtil.executeUrl("POST", url, null, null, null, REQUEST_TIMEOUT);
            logger.debug("Result: {}", htmlJSON);
            realTimeInfo = gson.fromJson(htmlJSON, SLTrafficRealTime.class);

            if (realTimeInfo != null) {
                getThing().getChannels().stream().map(Channel::getUID).filter(channelUID -> isLinked(channelUID))
                        .forEach(channelUID -> {
                            State state = getValue(channelUID.getId(), realTimeInfo);
                            updateState(channelUID, state);
                        });
            } else {
                logger.warn("Update real time status failes!");
            }
        } catch (IOException e) {
            logger.warn("Exception caught: {}", e.getMessage(), e);
        }
    }

    private TrafficType mapHandleTraffic(TrafficType traffic) {
        if (traffic != null) {
            logger.debug("bus: {}", traffic.getDestination());
        } else {
            logger.debug("traffic is null");
        }
        return traffic;
    }

    private Boolean filterHandleTraffic(Either e) {
        if (e.isLeft()) {
            logger.debug("Exception caught: {}", e.getLeft());
            return false;
        } else {
            Optional<TrafficType> o = e.getRight();
            TrafficType traffic = o.get();
            String destinations = config.destinations;
            return destinations.toLowerCase().contains(traffic.getDestination().toLowerCase());
        }
    }

    private void forHandleTraffic(Either e, StringBuffer result) {
        if (e.isLeft()) {
            logger.debug("Exception caught: {}", e.getLeft());
        } else {
            Optional<TrafficType> o = e.getRight();
            TrafficType traffic = o.get();
            logger.debug("b: {}", traffic);
            result.append(traffic.getGroupOfLine() != null ? traffic.getGroupOfLine() : "Buss");
            result.append(" ");
            result.append(traffic.getLineNumber());
            result.append(" till ");
            result.append(traffic.getDestination());
            if (traffic.getDisplayTime().contains(":")) {
                result.append(" går klockan ");
            } else {
                result.append(" går om ");
            }
            result.append(traffic.getDisplayTime());
            result.append(". ");
        }
    }

    public State getValue(String channelId, SLTrafficRealTime realTime) {
        switch (channelId) {
            case CHANNEL_REAL_TIME_INFO:
                StringBuffer result = new StringBuffer();

                // Handle buses
                realTime.getResponseData().getBuses().stream().map(Either.liftWithValue(this::mapHandleTraffic))
                        .filter(this::filterHandleTraffic).forEach(eitherBus -> forHandleTraffic(eitherBus, result));

                // Handle trains
                realTime.getResponseData().getTrains().stream().map(Either.liftWithValue(this::mapHandleTraffic))
                        .filter(this::filterHandleTraffic).forEach(eitherBus -> forHandleTraffic(eitherBus, result));

                // Handle trams
                realTime.getResponseData().getTrams().stream().map(Either.liftWithValue(this::mapHandleTraffic))
                        .filter(this::filterHandleTraffic).forEach(eitherBus -> forHandleTraffic(eitherBus, result));

                // Handle metros
                realTime.getResponseData().getMetros().stream().map(Either.liftWithValue(this::mapHandleTraffic))
                        .filter(this::filterHandleTraffic).forEach(eitherBus -> forHandleTraffic(eitherBus, result));

                return new StringType(result.toString());
        }
        return UnDefType.UNDEF;
    }

}
