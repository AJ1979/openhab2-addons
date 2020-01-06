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
import org.openhab.binding.vwcarnet.internal.VWCarNetSession;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetBridgeHandler;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetAlarmsJSON;
import org.openhab.binding.vwcarnet.internal.model.BaseVehicle;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetSmartLocksJSON;
import org.openhab.binding.vwcarnet.internal.model.Vehicle;
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
        super(SUPPORTED_THING_TYPES_UIDS, SEARCH_TIME_SECONDS);

        this.vwcarnetBridgeHandler = bridgeHandler;

    }

    @Override
    public void startScan() {
        removeOlderResults(getTimestampOfLastScan());
        logger.debug("VWCarNetThingDiscoveryService:startScan");

        if (vwcarnetBridgeHandler != null) {
            VWCarNetSession session = vwcarnetBridgeHandler.getSession();
            if (session != null) {
                HashMap<String, BaseVehicle> vwcarnetThings = session.getVWCarNetThings();
                for (Map.Entry<String, BaseVehicle> entry : vwcarnetThings.entrySet()) {
                    BaseVehicle thing = entry.getValue();
                    if (thing != null) {
                        logger.info("Thing: {}", thing);
                        onThingAddedInternal(thing);
                    }
                }
            }
        }
    }

    private void onThingAddedInternal(BaseVehicle thing) {
        logger.debug("VWCarNetThingDiscoveryService:OnThingAddedInternal");
        if (thing instanceof Vehicle) {
            Vehicle theVehicle = (Vehicle) thing;
            String vin = theVehicle.getCompleteVehicleJson().getVin();
            if (vin != null) {
                ThingUID thingUID = new ThingUID(VEHICLE_THING_TYPE, vin);
                ThingUID bridgeUID = vwcarnetBridgeHandler.getThing().getUID();
                String label = theVehicle.getCompleteVehicleJson().getName();

                DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID).withLabel(label)
                        .withBridge(bridgeUID).withProperty(VIN, vin).withRepresentationProperty(vin).build();
                logger.debug("Discovered thing: thinguid: {}, bridge {}, label {}", thingUID.toString(), bridgeUID,
                        label);
                thingDiscovered(discoveryResult);
            } else {
                logger.debug("VIN is null for thing '{}'", thing);
            }

        } else {
            logger.debug("Discovered unsupported thing of type '{}'", thing.getClass());
        }

    }

    private @Nullable ThingUID getThingUID(BaseVehicle thing) {
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
                } else {
                    logger.warn("Unsupported JSON! thing {}", thing.toString());
                }
            }
        }
        return thingUID;
    }
}
