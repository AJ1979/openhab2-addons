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
package org.openhab.binding.vwcarnet.internal.handler;

import static org.openhab.binding.vwcarnet.internal.VWCarNetBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetBaseVehicle;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetVehicle;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetVehicle.CompleteVehicleJson;

//import com.google.common.collect.Sets;

/**
 * Handler for the Smart Lock Device thing type that VWCarNet provides.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class VehicleHandler extends VWCarNetHandler {

    public VehicleHandler(Thing thing) {
        super(thing);
    }

    @Override
    public synchronized void update(@Nullable VWCarNetBaseVehicle vehicle) {
        logger.debug("update on thing: {}", vehicle);
        if (vehicle != null) {
            if (getThing().getThingTypeUID().equals(VEHICLE_THING_TYPE)) {
                VWCarNetVehicle obj = (VWCarNetVehicle) vehicle;
                updateVehicleStatus(obj);
                updateStatus(ThingStatus.ONLINE);
            } else {
                logger.warn("Can't handle this thing typeuid: {}", getThing().getThingTypeUID());
            }
        } else {
            logger.warn("Thing JSON is null: {}", getThing().getThingTypeUID());
        }
    }

    private void updateVehicleStatus(VWCarNetVehicle vehicleJSON) {
        CompleteVehicleJson vehicle = vehicleJSON.getCompleteVehicleJson();

        if (vehicle != null) {
            ChannelUID cuid = new ChannelUID(getThing().getUID(), MODEL);
            updateState(cuid, new StringType(vehicle.getModel()));
            cuid = new ChannelUID(getThing().getUID(), MODEL_CODE);
            updateState(cuid, new StringType(vehicle.getModelCode()));
            cuid = new ChannelUID(getThing().getUID(), MODEL_YEAR);
            updateState(cuid, new StringType(vehicle.getModelYear()));
            cuid = new ChannelUID(getThing().getUID(), MODEL);
            updateState(cuid, new StringType(vehicle.getModel()));
            cuid = new ChannelUID(getThing().getUID(), ENROLLMENT_DATE);
            String enrollmentDate = vehicle.getEnrollmentDate();
            if (enrollmentDate != null) {
                updateState(cuid, new DateTimeType(enrollmentDate));
            }
            cuid = new ChannelUID(getThing().getUID(), DASHBOARD_URL);
            updateState(cuid, new StringType(vehicle.getDashboardUrl()));
            cuid = new ChannelUID(getThing().getUID(), IMAGE_URL);
            updateState(cuid, new StringType(vehicle.getImageUrl()));
            cuid = new ChannelUID(getThing().getUID(), ENGINE_TYPE_COMBUSTIAN);
            updateState(cuid, vehicle.getEngineTypeCombustian() ? OnOffType.ON : OnOffType.OFF);
            cuid = new ChannelUID(getThing().getUID(), ENGINE_TYPE_ELECTRIC);
            updateState(cuid, vehicle.getEngineTypeElectric() ? OnOffType.ON : OnOffType.OFF);
        }

        super.update(vehicleJSON);
    }

}
