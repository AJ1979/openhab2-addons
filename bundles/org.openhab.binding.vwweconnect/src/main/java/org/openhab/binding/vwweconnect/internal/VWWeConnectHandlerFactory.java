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
package org.openhab.binding.vwweconnect.internal;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

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
import org.openhab.binding.vwweconnect.internal.discovery.VWWeConnectDiscoveryService;
import org.openhab.binding.vwweconnect.internal.handler.VWWeConnectBridgeHandler;
import org.openhab.binding.vwweconnect.internal.handler.VehicleHandler;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link VWWeConnectHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.vwweconnect")
public class VWWeConnectHandlerFactory extends BaseThingHandlerFactory {

    private final Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(VWWeConnectHandlerFactory.class);
    private static final boolean DEBUG = true;

    private @NonNullByDefault({}) HttpClient httpClient;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return VWWeConnectBindingConstants.SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    public VWWeConnectHandlerFactory() {
        super();
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        logger.debug("createHandler this: {}", thing);
        final ThingHandler thingHandler;
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (VWWeConnectBindingConstants.BRIDGE_THING_TYPE.equals(thing.getThingTypeUID())) {
            logger.debug("Create VWWeConnectBridgeHandler");
            thingHandler = new VWWeConnectBridgeHandler((Bridge) thing, httpClient);
            registerObjectDiscoveryService((VWWeConnectBridgeHandler) thingHandler);
        } else if (VWWeConnectBindingConstants.VEHICLE_THING_TYPE.equals(thing.getThingTypeUID())) {
            logger.debug("Create VehicleHandler {}", thing.getThingTypeUID());
            thingHandler = new VehicleHandler(thing);
        } else {
            logger.debug("Not possible to create thing handler for thing {}", thing);
            thingHandler = null;
        }
        return thingHandler;
    }

    private synchronized void registerObjectDiscoveryService(VWWeConnectBridgeHandler bridgeHandler) {
        VWWeConnectDiscoveryService discoveryService = new VWWeConnectDiscoveryService(bridgeHandler);
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