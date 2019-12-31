/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
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
package org.openhab.binding.vwcarnet.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.eclipse.smarthome.io.net.http.HttpClientFactory;
import org.openhab.binding.vwcarnet.internal.discovery.VWCarNetThingDiscoveryService;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetAlarmThingHandler;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetBridgeHandler;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetBroadbandConnectionThingHandler;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetClimateDeviceThingHandler;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetDoorWindowThingHandler;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetSmartLockThingHandler;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetSmartPlugThingHandler;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetUserPresenceThingHandler;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link VWCarNetHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Jarle Hjortland - Initial contribution
 */
@NonNullByDefault
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.vwcarnet")
public class VWCarNetHandlerFactory extends BaseThingHandlerFactory {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES = new HashSet<ThingTypeUID>();
    static {
        SUPPORTED_THING_TYPES.addAll(VWCarNetBridgeHandler.SUPPORTED_THING_TYPES);
        SUPPORTED_THING_TYPES.addAll(VWCarNetAlarmThingHandler.SUPPORTED_THING_TYPES);
        SUPPORTED_THING_TYPES.addAll(VWCarNetSmartLockThingHandler.SUPPORTED_THING_TYPES);
        SUPPORTED_THING_TYPES.addAll(VWCarNetSmartPlugThingHandler.SUPPORTED_THING_TYPES);
        SUPPORTED_THING_TYPES.addAll(VWCarNetClimateDeviceThingHandler.SUPPORTED_THING_TYPES);
        SUPPORTED_THING_TYPES.addAll(VWCarNetBroadbandConnectionThingHandler.SUPPORTED_THING_TYPES);
        SUPPORTED_THING_TYPES.addAll(VWCarNetDoorWindowThingHandler.SUPPORTED_THING_TYPES);
        SUPPORTED_THING_TYPES.addAll(VWCarNetUserPresenceThingHandler.SUPPORTED_THING_TYPES);
    }

    private final Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(VWCarNetHandlerFactory.class);
    private static final boolean DEBUG = true;

    private @NonNullByDefault({}) HttpClient httpClient;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES.contains(thingTypeUID);
    }

    public VWCarNetHandlerFactory() {
        super();
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        logger.debug("createHandler this: {}", thing);
        final ThingHandler thingHandler;
        if (VWCarNetBridgeHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetBridgeHandler");
            thingHandler = new VWCarNetBridgeHandler((Bridge) thing, httpClient);
            registerObjectDiscoveryService((VWCarNetBridgeHandler) thingHandler);
        } else if (VWCarNetAlarmThingHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetAlarmThingHandler {}", thing.getThingTypeUID());
            thingHandler = new VWCarNetAlarmThingHandler(thing);
        } else if (VWCarNetSmartLockThingHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetSmartLockThingHandler {}", thing.getThingTypeUID());
            thingHandler = new VWCarNetSmartLockThingHandler(thing);
        } else if (VWCarNetSmartPlugThingHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetSmartPlugThingHandler {}", thing.getThingTypeUID());
            thingHandler = new VWCarNetSmartPlugThingHandler(thing);
        } else if (VWCarNetClimateDeviceThingHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetClimateDeviceThingHandler {}", thing.getThingTypeUID());
            thingHandler = new VWCarNetClimateDeviceThingHandler(thing);
        } else if (VWCarNetBroadbandConnectionThingHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetBroadbandConnectionThingHandler {}", thing.getThingTypeUID());
            thingHandler = new VWCarNetBroadbandConnectionThingHandler(thing);
        } else if (VWCarNetDoorWindowThingHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetDoorWindowThingHandler {}", thing.getThingTypeUID());
            thingHandler = new VWCarNetDoorWindowThingHandler(thing);
        } else if (VWCarNetUserPresenceThingHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
            logger.debug("Create VWCarNetUserPresenceThingHandler {}", thing.getThingTypeUID());
            thingHandler = new VWCarNetUserPresenceThingHandler(thing);
        } else {
            logger.debug("Not possible to create thing handler for thing {}", thing);
            thingHandler = null;
        }
        return thingHandler;
    }

    private synchronized void registerObjectDiscoveryService(VWCarNetBridgeHandler bridgeHandler) {
        VWCarNetThingDiscoveryService discoveryService = new VWCarNetThingDiscoveryService(bridgeHandler);
        this.discoveryServiceRegs.put(bridgeHandler.getThing().getUID(), bundleContext
                .registerService(DiscoveryService.class.getName(), discoveryService, new Hashtable<String, Object>()));
    }

    @Reference
    protected void setHttpClientFactory(HttpClientFactory httpClientFactory) {
        logger.debug("setHttpClientFactory this: {}", this);
        this.httpClient = httpClientFactory.getCommonHttpClient();
        if (DEBUG) {
            SslContextFactory sslFactory = new SslContextFactory(true);
            // sslFactory.setExcludeProtocols("TLSv1.3");
            this.httpClient = new HttpClient(sslFactory);
            this.httpClient.setFollowRedirects(false);
            try {
                this.httpClient.start();
            } catch (Exception e) {
                logger.error("Exception: {}", e.getMessage());
            }
        }
    }

    protected void unsetHttpClientFactory(HttpClientFactory httpClientFactory) {
        logger.debug("unsetHttpClientFactory this: {}", this);
        this.httpClient = null;
    }

}
