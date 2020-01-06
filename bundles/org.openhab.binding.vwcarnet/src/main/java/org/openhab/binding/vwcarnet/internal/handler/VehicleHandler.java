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

import static org.eclipse.smarthome.core.library.unit.MetricPrefix.KILO;
import static org.openhab.binding.vwcarnet.internal.VWCarNetBindingConstants.*;

import javax.measure.quantity.Length;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.QuantityType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.library.unit.SIUnits;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.UnDefType;
import org.openhab.binding.vwcarnet.internal.model.BaseVehicle;
import org.openhab.binding.vwcarnet.internal.model.Details.VehicleDetails;
import org.openhab.binding.vwcarnet.internal.model.Location;
import org.openhab.binding.vwcarnet.internal.model.Status.VehicleStatusData;
import org.openhab.binding.vwcarnet.internal.model.Trips;
import org.openhab.binding.vwcarnet.internal.model.Vehicle;
import org.openhab.binding.vwcarnet.internal.model.Vehicle.CompleteVehicleJson;

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
    public synchronized void update(@Nullable BaseVehicle vehicle) {
        logger.debug("update on thing: {}", vehicle);
        if (vehicle != null) {
            if (getThing().getThingTypeUID().equals(VEHICLE_THING_TYPE)) {
                Vehicle obj = (Vehicle) vehicle;
                updateVehicleStatus(obj);
                updateStatus(ThingStatus.ONLINE);
            } else {
                logger.warn("Can't handle this thing typeuid: {}", getThing().getThingTypeUID());
            }
        } else {
            logger.warn("Thing JSON is null: {}", getThing().getThingTypeUID());
        }
    }

    private void updateVehicleStatus(Vehicle vehicleJSON) {
        CompleteVehicleJson vehicle = vehicleJSON.getCompleteVehicleJson();
        VehicleDetails vehicleDetails = vehicleJSON.getVehicleDetails().getVehicleDetails();
        VehicleStatusData vehicleStatus = vehicleJSON.getVehicleStatus().getVehicleStatusData();
        Trips trips = vehicleJSON.getTrips();
        Location vehicleLocation = vehicleJSON.getVehicleLocation();

        if (vehicle != null && vehicleDetails != null && vehicleStatus != null && trips != null
                && vehicleLocation != null) {
            getThing().getChannels().stream().map(Channel::getUID)
                    .filter(channelUID -> isLinked(channelUID) && !LAST_TRIP_GROUP.equals(channelUID.getGroupId()))
                    .forEach(channelUID -> {
                        State state = getValue(channelUID.getIdWithoutGroup(), vehicle, vehicleDetails, vehicleStatus,
                                trips, vehicleLocation);
                        updateState(channelUID, state);
                    });
        } else {
            logger.warn("Update vehicle status failed vehicle: {}, details: {}, status: {}", vehicle, vehicleDetails,
                    vehicleStatus);
        }
    }

    public State getValue(String channelId, CompleteVehicleJson vehicle, VehicleDetails vehicleDetails,
            VehicleStatusData vehicleStatus, Trips trips, Location vehicleLocation) {
        switch (channelId) {
            case MODEL:
                return new StringType(vehicle.getModel());
            case NAME:
                return new StringType(vehicle.getName());
            case MODEL_CODE:
                return new StringType(vehicle.getModelCode());
            case MODEL_YEAR:
                return new StringType(vehicle.getModelYear());
            case ENROLLMENT_DATE:
                String localEnrollmentDate = vehicle.getEnrollmentDate();
                return localEnrollmentDate != null ? new DateTimeType(localEnrollmentDate) : UnDefType.NULL;
            case DASHBOARD_URL:
                return new StringType(vehicle.getDashboardUrl());
            case IMAGE_URL:
                return new StringType(vehicle.getImageUrl());
            case ENGINE_TYPE_COMBUSTIAN:
                return vehicle.getEngineTypeCombustian() ? OnOffType.ON : OnOffType.OFF;
            case ENGINE_TYPE_ELECTRIC:
                return vehicle.getEngineTypeElectric() ? OnOffType.ON : OnOffType.OFF;
            case FUEL_RANGE:
                return vehicleDetails.getRange() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Length>(vehicleDetails.getRange(), KILO(SIUnits.METRE))
                        : UnDefType.UNDEF;
            case FUEL_LEVEL:
                return vehicleStatus.getFuelLevel() != BaseVehicle.UNDEFINED
                        ? new QuantityType<>(vehicleStatus.getFuelLevel(), SmartHomeUnits.PERCENT)
                        : UnDefType.UNDEF;
            case FUEL_CONSUMPTION:
                return trips.getRtsViewModel().getLongTermData().getAverageFuelConsumption() != BaseVehicle.UNDEFINED
                        ? new DecimalType(trips.getRtsViewModel().getLongTermData().getAverageFuelConsumption() / 10)
                        : UnDefType.UNDEF;
            case ODOMETER:
                return vehicleDetails.getDistanceCovered() != null
                        ? new QuantityType<Length>(Double.parseDouble(vehicleDetails.getDistanceCovered()) * 1000,
                                KILO(SIUnits.METRE))
                        : UnDefType.UNDEF;
            case SERVICE_INSPECTION:
                return new StringType(vehicleDetails.getServiceInspectionData());
            case OIL_INSPECTION:
                return new StringType(vehicleDetails.getOilInspectionData());
            case TRUNK:
                return vehicleStatus.getCarRenderData().getDoors().getTrunk() != null
                        ? vehicleStatus.getCarRenderData().getDoors().getTrunk()
                        : UnDefType.NULL;
            case RIGHT_BACK:
                return vehicleStatus.getCarRenderData().getDoors().getRightBack() != null
                        ? vehicleStatus.getCarRenderData().getDoors().getRightBack()
                        : UnDefType.NULL;
            case LEFT_BACK:
                return vehicleStatus.getCarRenderData().getDoors().getLeftBack() != null
                        ? vehicleStatus.getCarRenderData().getDoors().getLeftBack()
                        : UnDefType.NULL;
            case RIGHT_FRONT:
                return vehicleStatus.getCarRenderData().getDoors().getRightFront() != null
                        ? vehicleStatus.getCarRenderData().getDoors().getRightFront()
                        : UnDefType.NULL;
            case LEFT_FRONT:
                return vehicleStatus.getCarRenderData().getDoors().getLeftFront() != null
                        ? vehicleStatus.getCarRenderData().getDoors().getLeftFront()
                        : UnDefType.NULL;
            case HOOD:
                return vehicleStatus.getCarRenderData().getHood() != null ? vehicleStatus.getCarRenderData().getHood()
                        : UnDefType.NULL;
            case DOORS_LOCKED:
                return vehicleStatus.getLockData().getDoorsLocked() != null
                        ? vehicleStatus.getLockData().getDoorsLocked()
                        : UnDefType.NULL;
            case TRUNK_LOCKED:
                return vehicleStatus.getLockData().getTrunk() != null ? vehicleStatus.getLockData().getTrunk()
                        : UnDefType.NULL;
            case RIGHT_BACK_WND:
                return vehicleStatus.getCarRenderData().getWindows().getRightBack() != null
                        ? vehicleStatus.getCarRenderData().getWindows().getRightBack()
                        : UnDefType.NULL;
            case LEFT_BACK_WND:
                return vehicleStatus.getCarRenderData().getWindows().getLeftBack() != null
                        ? vehicleStatus.getCarRenderData().getWindows().getLeftBack()
                        : UnDefType.NULL;
            case RIGHT_FRONT_WND:
                return vehicleStatus.getCarRenderData().getWindows().getRightFront() != null
                        ? vehicleStatus.getCarRenderData().getWindows().getRightFront()
                        : UnDefType.NULL;
            case LEFT_FRONT_WND:
                return vehicleStatus.getCarRenderData().getWindows().getLeftFront() != null
                        ? vehicleStatus.getCarRenderData().getWindows().getLeftFront()
                        : UnDefType.NULL;
            // case TRIPMETER1:
            // return status.tripMeter1 != Status.UNDEFINED
            // ? new QuantityType<Length>((double) status.tripMeter1 / 1000, KILO(SIUnits.METRE))
            // : UnDefType.UNDEF;
            // case TRIPMETER2:
            // return status.tripMeter2 != Status.UNDEFINED
            // ? new QuantityType<Length>((double) status.tripMeter2 / 1000, KILO(SIUnits.METRE))
            // : UnDefType.UNDEF;
            // case ACTUAL_LOCATION:
            // return position.getPosition();
            // case CALCULATED_LOCATION:
            // return position.isCalculated();
            // case HEADING:
            // return position.isHeading();
            // case LOCATION_TIMESTAMP:
            // return position.getTimestamp();
            // case CAR_LOCKED:
            // return status.carLocked;
            // case ENGINE_RUNNING:
            // return status.engineRunning;
            // case WASHER_FLUID:
            // return new StringType(status.washerFluidLevel);
            // case SERVICE_WARNING:
            // return new StringType(status.serviceWarningStatus);
            // case FUEL_ALERT:
            // return status.distanceToEmpty < 100 ? OnOffType.ON : OnOffType.OFF;
        }

        return UnDefType.NULL;
    }

}
