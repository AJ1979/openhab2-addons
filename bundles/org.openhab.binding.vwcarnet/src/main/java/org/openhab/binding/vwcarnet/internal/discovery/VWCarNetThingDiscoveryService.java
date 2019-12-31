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
package org.openhab.binding.vwcarnet.internal.discovery;

import static org.openhab.binding.vwcarnet.internal.VWCarNetBindingConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.vwcarnet.internal.VWCarNetHandlerFactory;
import org.openhab.binding.vwcarnet.internal.VWCarNetSession;
import org.openhab.binding.vwcarnet.internal.VWCarNetThingConfiguration;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetBridgeHandler;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetAlarmsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetBroadbandConnectionsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetClimatesJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetDoorWindowsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetSmartLocksJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetSmartPlugsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetThingJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetUserPresencesJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The discovery service, notified by a listener on the VWCarNetSession.
 *
 * @author Jarle Hjortland - Initial contribution
 *
 */
@NonNullByDefault
public class VWCarNetThingDiscoveryService extends AbstractDiscoveryService {

    private static final int SEARCH_TIME_SECONDS = 60;
    private final Logger logger = LoggerFactory.getLogger(VWCarNetThingDiscoveryService.class);

    private @Nullable VWCarNetBridgeHandler vwcarnetBridgeHandler;

    public VWCarNetThingDiscoveryService(VWCarNetBridgeHandler bridgeHandler) throws IllegalArgumentException {
        super(VWCarNetHandlerFactory.SUPPORTED_THING_TYPES, SEARCH_TIME_SECONDS);

        this.vwcarnetBridgeHandler = bridgeHandler;

    }

    @Override
    public void startScan() {
        removeOlderResults(getTimestampOfLastScan());
        logger.debug("VWCarNetThingDiscoveryService:startScan");

        if (vwcarnetBridgeHandler != null) {
            VWCarNetSession session = vwcarnetBridgeHandler.getSession();
            if (session != null) {
                HashMap<String, VWCarNetThingJSON> vwcarnetThings = session.getVWCarNetThings();
                for (Map.Entry<String, VWCarNetThingJSON> entry : vwcarnetThings.entrySet()) {
                    VWCarNetThingJSON thing = entry.getValue();
                    if (thing != null) {
                        logger.info("Thing: {}", thing);
                        onThingAddedInternal(thing);
                    }
                }
            }
        }
    }

    private void onThingAddedInternal(VWCarNetThingJSON thing) {
        logger.debug("VWCarNetThingDiscoveryService:OnThingAddedInternal");
        ThingUID thingUID = getThingUID(thing);
        String deviceId = thing.getDeviceId();
        if (thingUID != null && deviceId != null) {
            if (vwcarnetBridgeHandler != null) {
                ThingUID bridgeUID = vwcarnetBridgeHandler.getThing().getUID();
                String label = "Device Id: " + deviceId;
                if (thing.getLocation() != null) {
                    label += ", Location: " + thing.getLocation();
                }
                if (thing.getSiteName() != null) {
                    label += ", Site name: " + thing.getSiteName();
                }
                DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID).withBridge(bridgeUID)
                        .withLabel(label).withProperty(VWCarNetThingConfiguration.DEVICE_ID_LABEL, deviceId).build();
                logger.debug("thinguid: {}, bridge {}, label {}", thingUID.toString(), bridgeUID, thing.getDeviceId());
                thingDiscovered(discoveryResult);
            }
        } else {
            logger.debug("Discovered unsupported thing of type '{}' with deviceId {}", thing.getClass(),
                    thing.getDeviceId());
        }

    }

    private @Nullable ThingUID getThingUID(VWCarNetThingJSON thing) {
        ThingUID thingUID = null;
        if (vwcarnetBridgeHandler != null) {
            ThingUID bridgeUID = vwcarnetBridgeHandler.getThing().getUID();
            String deviceId = thing.getDeviceId();
            if (deviceId != null) {
                // Make sure device id is normalized, i.e. replace all non character/digits with empty string
                deviceId.replaceAll("[^a-zA-Z0-9]+", "");
                if (thing instanceof VWCarNetAlarmsJSON) {
                    thingUID = new ThingUID(THING_TYPE_ALARM, bridgeUID, deviceId);
                } else if (thing instanceof VWCarNetSmartLocksJSON) {
                    thingUID = new ThingUID(THING_TYPE_SMARTLOCK, bridgeUID, deviceId);
                } else if (thing instanceof VWCarNetUserPresencesJSON) {
                    thingUID = new ThingUID(THING_TYPE_USERPRESENCE, bridgeUID, deviceId);
                } else if (thing instanceof VWCarNetDoorWindowsJSON) {
                    thingUID = new ThingUID(THING_TYPE_DOORWINDOW, bridgeUID, deviceId);
                } else if (thing instanceof VWCarNetSmartPlugsJSON) {
                    thingUID = new ThingUID(THING_TYPE_SMARTPLUG, bridgeUID, deviceId);
                } else if (thing instanceof VWCarNetClimatesJSON) {
                    String type = ((VWCarNetClimatesJSON) thing).getData().getInstallation().getClimates().get(0)
                            .getDevice().getGui().getLabel();
                    if ("SMOKE".equals(type)) {
                        thingUID = new ThingUID(THING_TYPE_SMOKEDETECTOR, bridgeUID, deviceId);
                    } else if ("WATER".equals(type)) {
                        thingUID = new ThingUID(THING_TYPE_WATERDETECTOR, bridgeUID, deviceId);
                    } else if ("HOMEPAD".equals(type)) {
                        thingUID = new ThingUID(THING_TYPE_NIGHT_CONTROL, bridgeUID, deviceId);
                    } else if ("SIREN".equals(type)) {
                        thingUID = new ThingUID(THING_TYPE_SIREN, bridgeUID, deviceId);
                    } else {
                        logger.warn("Unknown climate device {}.", type);
                    }
                } else if (thing instanceof VWCarNetBroadbandConnectionsJSON) {
                    thingUID = new ThingUID(THING_TYPE_BROADBAND_CONNECTION, bridgeUID, deviceId);
                } else {
                    logger.warn("Unsupported JSON! thing {}", thing.toString());
                }
            }
        }
        return thingUID;
    }
}
