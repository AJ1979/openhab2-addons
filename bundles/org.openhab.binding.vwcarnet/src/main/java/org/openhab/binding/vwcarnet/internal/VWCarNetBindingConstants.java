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
package org.openhab.binding.vwcarnet.internal;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link VerisureBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author l3rum - Initial contribution
 */
@NonNullByDefault
public class VWCarNetBindingConstants {

    public static final String BINDING_ID = "vwcarnet";

    // Vehicle properties
    public static final String VIN = "vin";

    // List of Thing Type UIDs
    public static final ThingTypeUID BRIDGE_THING_TYPE = new ThingTypeUID(BINDING_ID, "vwcarnetapi");
    public static final ThingTypeUID VEHICLE_THING_TYPE = new ThingTypeUID(BINDING_ID, "vehicle");

    public static final ThingTypeUID THING_TYPE_ALARM = new ThingTypeUID(BINDING_ID, "alarm");
    public static final ThingTypeUID THING_TYPE_SMARTLOCK = new ThingTypeUID(BINDING_ID, "smartLock");

    // List of Channel ID's
    // Doors
    public static final String TRUNK = "trunk";
    public static final String RIGHT_BACK = "rightBack";
    public static final String LEFT_BACK = "leftBack";
    public static final String RIGHT_FRONT = "rightFront";
    public static final String LEFT_FRONT = "leftFront";
    public static final String HOOD = "hood";
    public static final String DOORS_LOCKED = "doorsLocked";
    public static final String TRUNK_LOCKED = "trunkLocked";

    // Windows
    public static final String RIGHT_BACK_WND = "rightBackWnd";
    public static final String LEFT_BACK_WND = "leftBackWnd";
    public static final String RIGHT_FRONT_WND = "rightFrontWnd";
    public static final String LEFT_FRONT_WND = "leftFrontWnd";

    // Fuel
    public static final String FUEL_LEVEL = "fuelLevel";
    public static final String FUEL_CONSUMPTION = "fuelConsumption";
    public static final String FUEL_ALERT = "fuelAlert";
    public static final String FUEL_RANGE = "fuelRange";

    // Compressed Natural Gas
    public static final String CNG_LEVEL = "cngLevel";
    public static final String CNG_CONSUMPTION = "cngConsumption";
    public static final String CNG_ALERT = "cngAlert";
    public static final String CNG_RANGE = "cngRange";

    // Electric
    public static final String BATTERY_LEVEL = "batteryLevel";
    public static final String ELECTRIC_CONSUMPTION = "electricConsumption";
    public static final String BATTERY_ALERT = "batteryAlert";
    public static final String BATTERY_RANGE = "batteryRange";

    // Location
    public static final String ACTUAL_LOCATION = "location";

    // Odometer
    public static final String TOTAL_AVERAGE_SPEED = "totalAverageSpeed";
    public static final String TOTAL_TRIP_DISTANCE = "totalTripDistance";
    public static final String TOTAL_TRIP_DURATION = "totalTripDuration";

    // Last Trip
    public static final String LAST_TRIP_GROUP = "lasttrip";
    public static final String AVERAGE_FUEL_CONSUMPTION = "averageFuelConsumption";
    public static final String AVERAGE_CNG_CONSUMPTION = "averageCngConsumption";
    public static final String AVERAGE_ELECTRIC_CONSUMPTION = "averageElectricConsumption";
    public static final String AVERAGE_AUXILIARY_CONSUMPTION = "averageAuxiliaryConsumption";
    public static final String AVERAGE_SPEED = "averageSpeed";
    public static final String TRIP_DISTANCE = "tripDistance";
    public static final String TRIP_DURATION = "tripDuration";
    public static final String TRIP_START_TIME = "tripStartTime";
    public static final String TRIP_END_TIME = "tripEndTime";

    // Vehicle properties
    public static final String ENGINE_START = "engineStart";
    public static final String UNLOCK = "unlock";
    public static final String UNLOCK_TIME = "unlockTimeFrame";
    public static final String LOCK = "lock";
    public static final String HONK = "honk";
    public static final String BLINK = "blink";
    public static final String HONK_BLINK = "honkAndBlink";
    public static final String HONK_AND_OR_BLINK = "honkAndOrBlink";
    public static final String REMOTE_HEATER = "remoteHeater";
    public static final String PRECLIMATIZATION = "preclimatization";
    public static final String LAST_TRIP_ID = "lastTripId";

    // Vehicle details
    public static final String NAME = "name";
    public static final String MODEL = "model";
    public static final String MODEL_CODE = "modelCode";
    public static final String MODEL_YEAR = "modelYear";
    public static final String ENROLLMENT_DATE = "enrollmentDate";
    public static final String DASHBOARD_URL = "dashboardURL";
    public static final String IMAGE_URL = "imageURL";
    public static final String ENGINE_TYPE_COMBUSTIAN = "engineTypeCombustian";
    public static final String ENGINE_TYPE_ELECTRIC = "engineTypeElectic";
    public static final String SERVICE_INSPECTION = "serviceInspectionStatus";
    public static final String OIL_INSPECTION = "oilInspectionStatus";

    public static final String LOGIN_CHECK = "-/msgc/get-new-messages";
    public static final String VEHICLE_DETAILS = "/-/mainnavigation/load-car-details/";
    public static final String VEHICLE_DETAILS_SPECIFIC = "/-/vehicle-info/get-vehicle-details";
    public static final String REQUEST_VEHICLE_STATUS_REPORT = "/-/vsr/request-vsr";
    public static final String VEHICLE_STATUS = "/-/vsr/get-vsr";
    public static final String TRIP_STATISTICS = "/-/rts/get-latest-trip-statistics";
    public static final String VEHICLE_LOCATION = "/-/cf/get-location";
    public static final String EMANAGER = "-/emanager/get-emanager";
    public static final String REQUEST_STATUS = "/-/vsr/get-request-status";

    // List of all addressable things in OH = SUPPORTED_DEVICE_THING_TYPES_UIDS + the virtual bridge
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Stream.of(BRIDGE_THING_TYPE, VEHICLE_THING_TYPE)
            .collect(Collectors.toSet());

    // List of all Channel ids
    public static final String CHANNEL_NUMERIC_STATUS = "numericStatus";
    public static final String CHANNEL_LOCATION = "homeLocation";
    public static final String CHANNEL_STATUS = "status";
    public static final String CHANNEL_CONNECTED = "connected";
    public static final String CHANNEL_STATE = "state";
    public static final String CHANNEL_LABEL = "label";
    public static final String CHANNEL_USER_NAME = "userName";
    public static final String CHANNEL_WEBACCOUNT = "webAccount";
    public static final String CHANNEL_USER_LOCATION_STATUS = "userLocationStatus";
    public static final String CHANNEL_USER_DEVICE_NAME = "userDeviceName";
    public static final String CHANNEL_SMARTLOCK_VOLUME = "smartLockVolume";
    public static final String CHANNEL_SMARTLOCK_VOICE_LEVEL = "smartLockVoiceLevel";
    public static final String CHANNEL_AUTO_RELOCK = "autoRelock";
    public static final String CHANNEL_SMARTPLUG_STATUS = "smartPlugStatus";
    public static final String CHANNEL_ALARM_STATUS = "alarmStatus";
    public static final String CHANNEL_SMARTLOCK_STATUS = "smartLockStatus";
    public static final String CHANNEL_CHANGED_BY_USER = "changedByUser";
    public static final String CHANNEL_CHANGED_VIA = "changedVia";
    public static final String CHANNEL_TIMESTAMP = "timestamp";
    public static final String CHANNEL_HAZARDOUS = "hazardous";
    public static final String CHANNEL_MOTOR_JAM = "motorJam";

    // REST URI constants
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String BASEURL = "https://mypages.verisure.com";
    public static final String LOGON_SUF = BASEURL + "/j_spring_security_check?locale=en_GB";
    public static final String SESSION_BASE = "https://www.portal.volkswagen-we.com/";
    public static final String SESSION_HEADERS = "'Accept': 'application/json, text/plain, */*', 'Content-Type': 'application/json;charset=UTF-8', 'User-Agent': 'Mozilla/5.0 (Linux; Android 6.0.1; D5803 Build/23.5.A.1.291; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36'";
    public static final String AUTH_BASE = "https://identity.vwgroup.io";
    public static final String AUTH_HEADERS = "'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3', 'Content-Type': 'application/x-www-form-urlencoded', 'User-Agent': 'Mozilla/5.0 (Linux; Android 6.0.1; D5803 Build/23.5.A.1.291; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36'";

    public static final String ALARM_COMMAND = BASEURL + "/remotecontrol/armstatechange.cmd";
    public static final String SMARTLOCK_LOCK_COMMAND = BASEURL + "/remotecontrol/lockunlock.cmd";
    public static final String SMARTLOCK_SET_COMMAND = BASEURL + "/overview/setdoorlock.cmd";
    public static final String SMARTLOCK_AUTORELOCK_COMMAND = BASEURL + "/settings/setautorelock.cmd";
    public static final String SMARTLOCK_VOLUME_COMMAND = BASEURL + "/settings/setvolume.cmd";

    public static final String SMARTPLUG_COMMAND = BASEURL + "/settings/smartplug/onoffplug.cmd";
    public static final String START_REDIRECT = "/uk/start.html";
    public static final String START_SUF = BASEURL + START_REDIRECT;

    // GraphQL constants
    public static final String STATUS = BASEURL + "/uk/status";
    public static final String SETTINGS = BASEURL + "/uk/settings.html?giid=";
    public static final String SET_INSTALLATION = BASEURL + "/setinstallation?giid=";
    public static final String BASEURL_API = "https://m-api02.verisure.com";
    public static final String START_GRAPHQL = "/graphql";
    public static final String AUTH_TOKEN = "/auth/token";
    public static final String AUTH_LOGIN = "/auth/login";

    public static final String ALARMSTATUS_PATH = "/remotecontrol";
    public static final String SMARTLOCK_PATH = "/overview/doorlock/";
    public static final String DOORWINDOW_PATH = "/settings/doorwindow";
    public static final String USERTRACKING_PATH = "/overview/usertrackingcontacts";
    public static final String CLIMATEDEVICE_PATH = "/overview/climatedevice";
    public static final String SMARTPLUG_PATH = "/settings/smartplug";
    public static final String ETHERNETSTATUS_PATH = "/overview/ethernetstatus";
    public static final String VACATIONMODE_PATH = "/overview/vacationmode";
    public static final String TEMPERATURE_CONTROL_PATH = "/overview/temperaturecontrol";
    public static final String MOUSEDETECTION_PATH = "/overview/mousedetection";
    public static final String CAMERA_PATH = "/overview/camera";
}
