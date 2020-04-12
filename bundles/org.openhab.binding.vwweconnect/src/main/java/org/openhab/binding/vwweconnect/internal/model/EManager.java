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
package org.openhab.binding.vwweconnect.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * The Emanager representation.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class EManager {
    private @Nullable String errorCode;
    @SerializedName("EManager")
    private EManagerModel eManager = new EManagerModel();

    public @Nullable String getErrorCode() {
        return errorCode;
    }

    public EManagerModel getEManager() {
        return eManager;
    }

    @NonNullByDefault
    public class EManagerModel {
        private Rbc rbc = new Rbc();
        private Rpc rpc = new Rpc();
        private Rdt rdt = new Rdt();
        private boolean actionPending;
        private boolean rdtAvailable;

        public Rbc getRbc() {
            return rbc;
        }

        public Rpc getRpc() {
            return rpc;
        }

        public Rdt getRdt() {
            return rdt;
        }

        public boolean isActionPending() {
            return actionPending;
        }

        public boolean isRdtAvailable() {
            return rdtAvailable;
        }

        @NonNullByDefault
        public class Profile {
            private int profileId;
            private @Nullable String profileName;
            private @Nullable String timeStamp;
            private boolean charging;
            private boolean climatisation;
            private int targetChargeLevel;
            private boolean nightRateActive;
            private @Nullable String nightRateTimeStart;
            private @Nullable String nightRateTimeEnd;
            private int chargeMaxCurrent;
            private @Nullable String heaterSource;

            public int getProfileId() {
                return profileId;
            }

            public @Nullable String getProfileName() {
                return profileName;
            }

            public @Nullable String getTimeStamp() {
                return timeStamp;
            }

            public boolean isCharging() {
                return charging;
            }

            public boolean isClimatisation() {
                return climatisation;
            }

            public int getTargetChargeLevel() {
                return targetChargeLevel;
            }

            public boolean isNightRateActive() {
                return nightRateActive;
            }

            public @Nullable String getNightRateTimeStart() {
                return nightRateTimeStart;
            }

            public @Nullable String getNightRateTimeEnd() {
                return nightRateTimeEnd;
            }

            public int getChargeMaxCurrent() {
                return chargeMaxCurrent;
            }

            public @Nullable String getHeaterSource() {
                return heaterSource;
            }
        }

        @NonNullByDefault
        public class Rbc {

            private Status status = new Status();
            private Settings settings = new Settings();

            public Status getStatus() {
                return status;
            }

            public Settings getSettings() {
                return settings;
            }
        }

        @NonNullByDefault
        public class Rdt {
            private RdtStatus status = new RdtStatus();
            private RdtSettings settings = new RdtSettings();
            private boolean auxHeatingAllowed;
            private boolean auxHeatingEnabled;

            public RdtStatus getStatus() {
                return status;
            }

            public RdtSettings getSettings() {
                return settings;
            }

            public boolean isAuxHeatingAllowed() {
                return auxHeatingAllowed;
            }

            public boolean isAuxHeatingEnabled() {
                return auxHeatingEnabled;
            }
        }

        @NonNullByDefault
        public class Rpc {
            private RpcStatus status = new RpcStatus();
            private RpcSettings settings = new RpcSettings();
            private @Nullable String climaterActionState;
            private boolean auAvailable;

            public RpcStatus getStatus() {
                return status;
            }

            public RpcSettings getSettings() {
                return settings;
            }

            public @Nullable String getClimaterActionState() {
                return climaterActionState;
            }

            public boolean isAuAvailable() {
                return auAvailable;
            }
        }

        @NonNullByDefault
        public class Schedule {
            private int type;
            private Start start = new Start();
            private End end = new End();
            private @Nullable Object index;
            private List<String> daypicker = new ArrayList<>();
            private @Nullable String startDateActive;
            private @Nullable String endDateActive;

            public int getType() {
                return type;
            }

            public Start getStart() {
                return start;
            }

            public End getEnd() {
                return end;
            }

            public @Nullable Object getIndex() {
                return index;
            }

            public List<String> getDaypicker() {
                return daypicker;
            }

            public @Nullable String getStartDateActive() {
                return startDateActive;
            }

            public @Nullable String getEndDateActive() {
                return endDateActive;
            }
        }

        @NonNullByDefault
        public class Settings {
            private int chargerMaxCurrent;
            private int maxAmpere;
            private boolean maxCurrentReduced;

            public int getChargerMaxCurrent() {
                return chargerMaxCurrent;
            }

            public int getMaxAmpere() {
                return maxAmpere;
            }

            public boolean isMaxCurrentReduced() {
                return maxCurrentReduced;
            }
        }

        @NonNullByDefault
        public class RpcSettings {
            private @Nullable String targetTemperature;
            private boolean climatisationWithoutHVPower;
            private boolean electric;

            public @Nullable String getTargetTemperature() {
                return targetTemperature;
            }

            public boolean isClimatisationWithoutHVPower() {
                return climatisationWithoutHVPower;
            }

            public boolean isElectric() {
                return electric;
            }
        }

        @NonNullByDefault
        public class RdtSettings {

            private int minChargeLimit;
            private int lowerLimitMax;

            public int getMinChargeLimit() {
                return minChargeLimit;
            }

            public int getLowerLimitMax() {
                return lowerLimitMax;
            }
        }

        @NonNullByDefault
        public class Start {
            private int hours;
            private int minutes;

            public int getHours() {
                return hours;
            }

            public int getMinutes() {
                return minutes;
            }
        }

        @NonNullByDefault
        public class End {
            private int hours;
            private int minutes;

            public int getHours() {
                return hours;
            }

            public int getMinutes() {
                return minutes;
            }
        }

        @NonNullByDefault
        public class Status {
            private int batteryPercentage;
            private @Nullable String chargingState;
            private @Nullable String chargingRemaningHour;
            private @Nullable String chargingRemaningMinute;
            private @Nullable String chargingReason;
            private @Nullable String pluginState;
            private @Nullable String lockState;
            private @Nullable String extPowerSupplyState;
            private double range = BaseVehicle.UNDEFINED;
            private double electricRange = BaseVehicle.UNDEFINED;
            private double combustionRange = BaseVehicle.UNDEFINED;
            private double combinedRange = BaseVehicle.UNDEFINED;
            private boolean rlzeUp;

            public int getBatteryPercentage() {
                return batteryPercentage;
            }

            public @Nullable String getChargingState() {
                return chargingState;
            }

            public @Nullable String getChargingRemaningHour() {
                return chargingRemaningHour;
            }

            public @Nullable String getChargingRemaningMinute() {
                return chargingRemaningMinute;
            }

            public @Nullable String getChargingReason() {
                return chargingReason;
            }

            public @Nullable String getPluginState() {
                return pluginState;
            }

            public @Nullable String getLockState() {
                return lockState;
            }

            public @Nullable String getExtPowerSupplyState() {
                return extPowerSupplyState;
            }

            public double getRange() {
                return range;
            }

            public double getElectricRange() {
                return electricRange;
            }

            public double getCombustionRange() {
                return combustionRange;
            }

            public double getCombinedRange() {
                return combinedRange;
            }

            public boolean isRlzeUp() {
                return rlzeUp;
            }
        }

        @NonNullByDefault
        public class RpcStatus {
            private @Nullable String climatisationState;
            private int climatisationRemaningTime;
            private @Nullable String windowHeatingStateFront;
            private @Nullable String windowHeatingStateRear;
            private @Nullable String climatisationReason;
            private boolean windowHeatingAvailable;

            public @Nullable String getClimatisationState() {
                return climatisationState;
            }

            public int getClimatisationRemaningTime() {
                return climatisationRemaningTime;
            }

            public @Nullable String getWindowHeatingStateFront() {
                return windowHeatingStateFront;
            }

            public @Nullable String getWindowHeatingStateRear() {
                return windowHeatingStateRear;
            }

            public @Nullable String getClimatisationReason() {
                return climatisationReason;
            }

            public boolean isWindowHeatingAvailable() {
                return windowHeatingAvailable;
            }
        }

        @NonNullByDefault
        public class RdtStatus {
            private List<Timer> timers = new ArrayList<>();
            private List<Profile> profiles = new ArrayList<>();

            public List<Timer> getTimers() {
                return timers;
            }

            public List<Profile> getProfiles() {
                return profiles;
            }
        }

        @NonNullByDefault
        public class Timer {
            private int timerId;
            private int timerProfileId;
            private @Nullable String timerStatus;
            private @Nullable String timerChargeScheduleStatus;
            private @Nullable String timerClimateScheduleStatus;
            private @Nullable String timerExpStatusTimestamp;
            private @Nullable String timerProgrammedStatus;
            private Schedule schedule = new Schedule();
            private @Nullable String startDateActive;
            private @Nullable String timeRangeActive;

            public int getTimerId() {
                return timerId;
            }

            public int getTimerProfileId() {
                return timerProfileId;
            }

            public @Nullable String getTimerStatus() {
                return timerStatus;
            }

            public @Nullable String getTimerChargeScheduleStatus() {
                return timerChargeScheduleStatus;
            }

            public @Nullable String getTimerClimateScheduleStatus() {
                return timerClimateScheduleStatus;
            }

            public @Nullable String getTimerExpStatusTimestamp() {
                return timerExpStatusTimestamp;
            }

            public @Nullable String getTimerProgrammedStatus() {
                return timerProgrammedStatus;
            }

            public @Nullable Schedule getSchedule() {
                return schedule;
            }

            public @Nullable String getStartDateActive() {
                return startDateActive;
            }

            public @Nullable String getTimeRangeActive() {
                return timeRangeActive;
            }
        }
    }
}
