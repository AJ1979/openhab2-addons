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
package org.openhab.binding.srvrecycling.internal;

import static org.openhab.binding.srvrecycling.internal.SrvRecyclingBindingConstants.CHANNEL_1;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.eclipse.smarthome.io.net.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link SrvRecyclingHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class SrvRecyclingHandler extends BaseThingHandler {

    private static final int REQUEST_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(20);
    private final Logger logger = LoggerFactory.getLogger(SrvRecyclingHandler.class);
    private final Properties httpHeader = new Properties();

    private @Nullable SrvRecyclingConfiguration config;
    private final static String SRV_URL = "https://www.srvatervinning.se/Privat/Din-sophamtning/Nar-hamtar-vi-dina-sopor/";
    private final static String COOKIE_DOMAIN = "www.srvatervinning.se";
    private final static String COOKIE_NAME = "cc_aceptSRV";
    private final static String COOKIE_VALUE = "1";
    private int refresh = 600;
    private @Nullable ScheduledFuture<?> refreshJob;
    private @NonNullByDefault({}) HttpClient httpClient;

    public SrvRecyclingHandler(Thing thing, HttpClient httpClient) {
        super(thing);
        this.httpClient = httpClient;

        httpHeader.put("content-type", "application/x-www-form-urlencoded");
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (CHANNEL_1.equals(channelUID.getId())) {
            if (command instanceof RefreshType) {
                // TODO: handle data refresh
            }

            // TODO: handle command

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information:
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
        }
    }

    @Override
    public void initialize() {
        // logger.debug("Start initializing!");
        config = getConfigAs(SrvRecyclingConfiguration.class);

        // TODO: Initialize the handler.
        // The framework requires you to return from this method quickly. Also, before leaving this method a thing
        // status from one of ONLINE, OFFLINE or UNKNOWN must be set. This might already be the real thing status in
        // case you can decide it directly.
        // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
        // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
        // background.

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
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
            startAutomaticRefresh();
        });

        // logger.debug("Finished initializing!");

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    private void startAutomaticRefresh() {
        logger.warn("Start automatic refresh {}", refreshJob);
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

    private void refreshAndUpdateStatus() {
        logger.debug("SrvRecyclingHandler - Refresh thread is up'n running!");
        String body = "__VIEWSTATE=%2FwEPDwUKMTExNDg5MDkxNmQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgIFF2N0bDAwJEhlYWRlcjEkYnRuU2VhcmNoBStjdGwwMCRDb250ZW50UmVnaW9uJE1haW5SZWdpb24kc2VhcmNoQnV0dG9uzOo6lzZamuo%2FxzTZqyyJeqf2uuAkW1%2FyJhUA1txgWI8%3D&__EVENTVALIDATION=%2FwEdAAWEon0nvZp9yjglBOOi%2Bj2Ck8cg7oqO6kilKCnfaEUUkxxcQNtO2VOAhaW%2FdNQyOIMXkWXPWWQEyRsmQUC2h4ZTnsm%2FDy81eWU7hK9r9inyaRadyWsTYWFNoc9rOdVnoXHszMBfG7yEp%2BpT1fNunty9&ctl00%24ContentRegion%24MainRegion%24txtSearch=";
        String searchString = "Champinjonvägen 44, Huddinge";
        body = body + searchString;
        try {
            InputStream inputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
            String jsonString = HttpUtil.executeUrl("POST", SRV_URL, httpHeader, inputStream, null, REQUEST_TIMEOUT);
            logger.warn("Post URL: {} Attributes {} Content {}", SRV_URL, httpHeader);
            logger.warn("Result: {}", jsonString);

        } catch (Exception e) {
            logger.warn("Failed to poll SRV {}", e.getMessage(), e);
        }
    }

}
