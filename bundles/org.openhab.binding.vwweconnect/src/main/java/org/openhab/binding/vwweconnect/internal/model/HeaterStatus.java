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
package org.openhab.binding.vwweconnect.internal.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The Vehicle heating status representation.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class HeaterStatus {

    private @Nullable String errorCode;
    private int timerCount;
    private @Nullable RemoteAuxiliaryHeating remoteAuxiliaryHeating;
    private @Nullable Map<String, Object> additionalProperties;

    /**
     * No args constructor for use in serialization
     *
     */
    public HeaterStatus() {
    }

    public @Nullable String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getTimerCount() {
        return timerCount;
    }

    public void setTimerCount(int timerCount) {
        this.timerCount = timerCount;
    }

    public @Nullable RemoteAuxiliaryHeating getRemoteAuxiliaryHeating() {
        return remoteAuxiliaryHeating;
    }

    public void setRemoteAuxiliaryHeating(@Nullable RemoteAuxiliaryHeating remoteAuxiliaryHeating) {
        this.remoteAuxiliaryHeating = remoteAuxiliaryHeating;
    }

    public @Nullable Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("errorCode", errorCode).append("timerCount", timerCount)
                .append("remoteAuxiliaryHeating", remoteAuxiliaryHeating)
                .append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(errorCode).append(timerCount).append(additionalProperties)
                .append(remoteAuxiliaryHeating).toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HeaterStatus) == false) {
            return false;
        }
        HeaterStatus rhs = ((HeaterStatus) other);
        return new EqualsBuilder().append(errorCode, rhs.errorCode).append(timerCount, rhs.timerCount)
                .append(additionalProperties, rhs.additionalProperties)
                .append(remoteAuxiliaryHeating, rhs.remoteAuxiliaryHeating).isEquals();
    }

    @NonNullByDefault
    public class RemoteAuxiliaryHeating {

        private @Nullable Status status;
        private @Nullable List<Timer> timers;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public RemoteAuxiliaryHeating() {
        }

        public @Nullable Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public @Nullable List<Timer> getTimers() {
            return timers;
        }

        public void setTimers(@Nullable List<Timer> timers) {
            this.timers = timers;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("status", status).append("timers", timers)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(timers).append(additionalProperties).append(status).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof RemoteAuxiliaryHeating) == false) {
                return false;
            }
            RemoteAuxiliaryHeating rhs = ((RemoteAuxiliaryHeating) other);
            return new EqualsBuilder().append(timers, rhs.timers).append(additionalProperties, rhs.additionalProperties)
                    .append(status, rhs.status).isEquals();
        }
    }

    @NonNullByDefault
    public class Status {

        private boolean active;
        private @Nullable String operationMode;
        private @Nullable String outdoorTemp;
        private double temperature = BaseVehicle.UNDEFINED;
        private int remainingTime;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public Status() {
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public @Nullable String getOperationMode() {
            return operationMode;
        }

        public void setOperationMode(String operationMode) {
            this.operationMode = operationMode;
        }

        public void setOutdoorTemp(String outdoorTemp) {
            this.outdoorTemp = outdoorTemp;
        }

        public @Nullable String getOutdoorTemp() {
            return outdoorTemp;
        }

        public double getTemperature() {
            if (outdoorTemp != null) {
                String[] tempArray = outdoorTemp.split(" ");
                double temp = Double.parseDouble(tempArray[0].replace(',', '.'));
                return temp;
            }
            return temperature;
        }

        public int getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(int remainingTime) {
            this.remainingTime = remainingTime;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("active", active).append("operationMode", operationMode)
                    .append("outdoorTemp", outdoorTemp).append("remainingTime", remainingTime)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(active).append(operationMode).append(additionalProperties)
                    .append(outdoorTemp).append(remainingTime).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Status) == false) {
                return false;
            }
            Status rhs = ((Status) other);
            return new EqualsBuilder().append(active, rhs.active).append(operationMode, rhs.operationMode)
                    .append(additionalProperties, rhs.additionalProperties).append(outdoorTemp, rhs.outdoorTemp)
                    .append(remainingTime, rhs.remainingTime).isEquals();
        }
    }

    @NonNullByDefault
    public class Time {

        private int hours;
        private int minutes;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public Time() {
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("hours", hours).append("minutes", minutes)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(hours).append(additionalProperties).append(minutes).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Time) == false) {
                return false;
            }
            Time rhs = ((Time) other);
            return new EqualsBuilder().append(hours, rhs.hours).append(additionalProperties, rhs.additionalProperties)
                    .append(minutes, rhs.minutes).isEquals();
        }
    }

    @NonNullByDefault
    public class Timer {

        private int timerId;
        private boolean active;
        private int weekDay;
        private @Nullable Time time;
        private @Nullable String nextActivationDate;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public Timer() {
        }

        public int getTimerId() {
            return timerId;
        }

        public void setTimerId(int timerId) {
            this.timerId = timerId;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public int getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(int weekDay) {
            this.weekDay = weekDay;
        }

        public @Nullable Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public @Nullable String getNextActivationDate() {
            return nextActivationDate;
        }

        public void setNextActivationDate(String nextActivationDate) {
            this.nextActivationDate = nextActivationDate;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("timerId", timerId).append("active", active)
                    .append("weekDay", weekDay).append("time", time).append("nextActivationDate", nextActivationDate)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(weekDay).append(nextActivationDate).append(active).append(time)
                    .append(additionalProperties).append(timerId).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Timer) == false) {
                return false;
            }
            Timer rhs = ((Timer) other);
            return new EqualsBuilder().append(weekDay, rhs.weekDay).append(nextActivationDate, rhs.nextActivationDate)
                    .append(active, rhs.active).append(time, rhs.time)
                    .append(additionalProperties, rhs.additionalProperties).append(timerId, rhs.timerId).isEquals();
        }
    }
}
