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

import static org.eclipse.smarthome.core.library.unit.MetricPrefix.KILO;
import static org.openhab.binding.vwcarnet.internal.VWCarNetBindingConstants.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;

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
import org.openhab.binding.vwcarnet.internal.model.Location.Position;
import org.openhab.binding.vwcarnet.internal.model.Status.VehicleStatusData;
import org.openhab.binding.vwcarnet.internal.model.Trips;
import org.openhab.binding.vwcarnet.internal.model.Trips.TripStatistic;
import org.openhab.binding.vwcarnet.internal.model.Trips.TripStatisticDetail;
import org.openhab.binding.vwcarnet.internal.model.Vehicle;
import org.openhab.binding.vwcarnet.internal.model.Vehicle.CompleteVehicleJson;
import org.openhab.binding.vwcarnet.internal.wrapper.VehiclePositionWrapper;

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

        // for (int i = 0; i < getThing().getChannels().size(); i++) {
        // logger.warn("Channel Group: {}", getThing().getChannels().get(i).getUID().getGroupId().toString());
        // logger.warn("Channel: {}", getThing().getChannels().get(i).getChannelTypeUID().getAsString());
        // logger.warn("Channel isLinked: {}", isLinked(getThing().getChannels().get(i).getUID()));
        //
        // }

        if (vehicle != null && vehicleDetails != null && vehicleStatus != null && trips != null
                && vehicleLocation != null) {
            getThing().getChannels().stream().map(Channel::getUID)
                    .filter(channelUID -> !LAST_TRIP_GROUP.equals(channelUID.getGroupId())).forEach(channelUID -> {
                        State state = getValue(channelUID.getIdWithoutGroup(), vehicle, vehicleDetails, vehicleStatus,
                                trips, vehicleLocation);
                        updateState(channelUID, state);
                    });
            updateLastTrip(trips);
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
                ZonedDateTime enrollmentStartDate = vehicle.getEnrollmentStartDate();
                return enrollmentStartDate != null ? new DateTimeType(enrollmentStartDate) : UnDefType.UNDEF;
            case DASHBOARD_URL:
                return new StringType(vehicle.getDashboardUrl());
            case IMAGE_URL:
                return new StringType(vehicle.getImageUrl());
            case ENGINE_TYPE_COMBUSTIAN:
                return vehicle.getEngineTypeCombustian() ? OnOffType.ON : OnOffType.OFF;
            case ENGINE_TYPE_ELECTRIC:
                return vehicle.getEngineTypeElectric() ? OnOffType.ON : OnOffType.OFF;
            case FUEL_LEVEL:
                return vehicleStatus.getFuelLevel() != BaseVehicle.UNDEFINED
                        ? new QuantityType<>(vehicleStatus.getFuelLevel(), SmartHomeUnits.PERCENT)
                        : UnDefType.UNDEF;
            case FUEL_CONSUMPTION:
                return trips.getRtsViewModel().getLongTermData().getAverageFuelConsumption() != BaseVehicle.UNDEFINED
                        ? new DecimalType(trips.getRtsViewModel().getLongTermData().getAverageFuelConsumption() / 10)
                        : UnDefType.UNDEF;
            case FUEL_RANGE:
                return vehicleStatus.getFuelRange() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Length>(vehicleStatus.getFuelRange(), KILO(SIUnits.METRE))
                        : UnDefType.UNDEF;
            case FUEL_ALERT:
                return vehicleStatus.getFuelRange() < 100 ? OnOffType.ON : OnOffType.OFF;
            case CNG_LEVEL:
                return vehicleStatus.getCngFuelLevel() != BaseVehicle.UNDEFINED
                        ? new QuantityType<>(vehicleStatus.getCngFuelLevel(), SmartHomeUnits.PERCENT)
                        : UnDefType.UNDEF;
            case CNG_CONSUMPTION:
                return trips.getRtsViewModel().getLongTermData().getAverageCngConsumption() != BaseVehicle.UNDEFINED
                        ? new DecimalType(trips.getRtsViewModel().getLongTermData().getAverageCngConsumption() / 10)
                        : UnDefType.UNDEF;
            case CNG_RANGE:
                return vehicleStatus.getCngRange() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Length>(vehicleStatus.getCngRange(), KILO(SIUnits.METRE))
                        : UnDefType.UNDEF;
            case CNG_ALERT:
                return vehicleStatus.getCngRange() < 100 ? OnOffType.ON : OnOffType.OFF;
            case BATTERY_LEVEL:
                return vehicleStatus.getBatteryLevel() != BaseVehicle.UNDEFINED
                        ? new QuantityType<>(vehicleStatus.getBatteryLevel(), SmartHomeUnits.PERCENT)
                        : UnDefType.UNDEF;
            case ELECTRIC_CONSUMPTION:
                return trips.getRtsViewModel().getLongTermData()
                        .getAverageElectricConsumption() != BaseVehicle.UNDEFINED
                                ? new DecimalType(
                                        trips.getRtsViewModel().getLongTermData().getAverageElectricConsumption() / 10)
                                : UnDefType.UNDEF;
            case BATTERY_RANGE:
                return vehicleStatus.getBatteryRange() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Length>(vehicleStatus.getBatteryRange(), KILO(SIUnits.METRE))
                        : UnDefType.UNDEF;
            case BATTERY_ALERT:
                return vehicleStatus.getBatteryRange() < 100 ? OnOffType.ON : OnOffType.OFF;
            case TOTAL_TRIP_DISTANCE:
                return trips.getRtsViewModel().getLongTermData().getTripLength() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Length>(trips.getRtsViewModel().getLongTermData().getTripLength(),
                                KILO(SIUnits.METRE))
                        : UnDefType.UNDEF;
            case TOTAL_TRIP_DURATION:
                return trips.getRtsViewModel().getLongTermData().getTripDuration() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Time>(trips.getRtsViewModel().getLongTermData().getTripDuration(),
                                SmartHomeUnits.MINUTE)
                        : UnDefType.UNDEF;
            case TOTAL_AVERAGE_SPEED:
                return trips.getRtsViewModel().getLongTermData().getAverageSpeed() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Speed>(trips.getRtsViewModel().getLongTermData().getAverageSpeed(),
                                SIUnits.KILOMETRE_PER_HOUR)
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
            case ACTUAL_LOCATION:
                Position localPosition = vehicleLocation.getPosition();
                return localPosition != null ? new VehiclePositionWrapper(localPosition).getPosition() : UnDefType.NULL;
        }

        return UnDefType.NULL;
    }

    public void updateLastTrip(Trips trips) {
        List<TripStatistic> tripsStat = trips.getRtsViewModel().getTripStatistics();
        Collections.reverse(tripsStat);

        Optional<TripStatistic> lastTrip = tripsStat.stream()
                .filter(aggregatedStatistics -> aggregatedStatistics != null).findFirst();
        int tripId = lastTrip.get().getAggregatedStatistics().getTripId();
        Optional<TripStatisticDetail> lastTripStats = lastTrip.get().getTripStatistics().stream()
                .filter(t -> t.getTripId() == tripId).findFirst();

        // for (int i = 0; i < getThing().getChannels().size(); i++) {
        // logger.warn("Channel: {}", getThing().getChannels().get(i).getChannelTypeUID().getAsString());
        // }

        getThing().getChannels().stream().map(Channel::getUID)
                .filter(channelUID -> LAST_TRIP_GROUP.equals(channelUID.getGroupId())).forEach(channelUID -> {
                    State state = getTripValue(channelUID.getIdWithoutGroup(), lastTripStats.get());
                    updateState(channelUID, state);
                });

    }

    public State getTripValue(String channelId, TripStatisticDetail trip) {
        switch (channelId) {
            case AVERAGE_FUEL_CONSUMPTION:
                return trip.getAverageFuelConsumption() != BaseVehicle.UNDEFINED
                        ? new DecimalType(trip.getAverageFuelConsumption() / 10)
                        : UnDefType.UNDEF;
            case AVERAGE_CNG_CONSUMPTION:
                return trip.getAverageCngConsumption() != BaseVehicle.UNDEFINED
                        ? new DecimalType(trip.getAverageCngConsumption() / 10)
                        : UnDefType.UNDEF;
            case AVERAGE_ELECTRIC_CONSUMPTION:
                return trip.getAverageElectricConsumption() != BaseVehicle.UNDEFINED
                        ? new DecimalType(trip.getAverageElectricConsumption())
                        : UnDefType.UNDEF;
            case AVERAGE_AUXILIARY_CONSUMPTION:
                return trip.getAverageAuxiliaryConsumption() != BaseVehicle.UNDEFINED
                        ? new DecimalType(trip.getAverageAuxiliaryConsumption())
                        : UnDefType.UNDEF;
            case AVERAGE_SPEED:
                return trip.getAverageSpeed() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Speed>(trip.getAverageSpeed(), SIUnits.KILOMETRE_PER_HOUR)
                        : UnDefType.UNDEF;
            case TRIP_DISTANCE:
                return trip.getTripLength() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Length>(trip.getTripLength(), KILO(SIUnits.METRE))
                        : UnDefType.UNDEF;
            case TRIP_START_TIME:
                ZonedDateTime localStartTimestamp = trip.getStartTimestamp();
                return localStartTimestamp != null ? new DateTimeType(localStartTimestamp) : UnDefType.UNDEF;
            case TRIP_END_TIME:
                ZonedDateTime localEndTimestamp = trip.getEndTimestamp();
                return localEndTimestamp != null ? new DateTimeType(localEndTimestamp) : UnDefType.UNDEF;
            case TRIP_DURATION:
                return trip.getTripDuration() != BaseVehicle.UNDEFINED
                        ? new QuantityType<Time>(trip.getTripDuration(), SmartHomeUnits.MINUTE)
                        : UnDefType.UNDEF;
        }

        return UnDefType.NULL;
    }

}
