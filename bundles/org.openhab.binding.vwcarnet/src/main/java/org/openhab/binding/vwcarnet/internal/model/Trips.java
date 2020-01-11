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
package org.openhab.binding.vwcarnet.internal.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The Trips status representation.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class Trips {

    private @Nullable String errorCode;
    private @Nullable RtsViewModel rtsViewModel;
    private @Nullable Map<String, Object> additionalProperties;

    /**
     * No args constructor for use in serialization
     *
     */
    public Trips() {
    }

    public @Nullable String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public @Nullable RtsViewModel getRtsViewModel() {
        return rtsViewModel;
    }

    public void setRtsViewModel(RtsViewModel rtsViewModel) {
        this.rtsViewModel = rtsViewModel;
    }

    public @Nullable Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("errorCode", errorCode).append("rtsViewModel", rtsViewModel)
                .append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(errorCode).append(rtsViewModel).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Trips) == false) {
            return false;
        }
        Trips rhs = ((Trips) other);
        return new EqualsBuilder().append(errorCode, rhs.errorCode).append(rtsViewModel, rhs.rtsViewModel)
                .append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    public class RtsViewModel {

        private int daysInMonth;
        private int firstWeekday;
        private int month;
        private int year;
        private int firstTripYear;
        private @Nullable List<TripStatistic> tripStatistics = null;
        private @Nullable LongTermData longTermData;
        private @Nullable Object cyclicData;
        private @Nullable ServiceConfiguration serviceConfiguration;
        private boolean tripFromLastRefuelAvailable;

        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public RtsViewModel() {
        }

        public int getDaysInMonth() {
            return daysInMonth;
        }

        public void setDaysInMonth(int daysInMonth) {
            this.daysInMonth = daysInMonth;
        }

        public int getFirstWeekday() {
            return firstWeekday;
        }

        public void setFirstWeekday(int firstWeekday) {
            this.firstWeekday = firstWeekday;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getFirstTripYear() {
            return firstTripYear;
        }

        public void setFirstTripYear(int firstTripYear) {
            this.firstTripYear = firstTripYear;
        }

        public @Nullable List<TripStatistic> getTripStatistics() {
            return tripStatistics;
        }

        public void setTripStatistics(List<TripStatistic> tripStatistics) {
            this.tripStatistics = tripStatistics;
        }

        public @Nullable LongTermData getLongTermData() {
            return longTermData;
        }

        public void setLongTermData(LongTermData longTermData) {
            this.longTermData = longTermData;
        }

        public @Nullable Object getCyclicData() {
            return cyclicData;
        }

        public void setCyclicData(Object cyclicData) {
            this.cyclicData = cyclicData;
        }

        public @Nullable ServiceConfiguration getServiceConfiguration() {
            return serviceConfiguration;
        }

        public void setServiceConfiguration(ServiceConfiguration serviceConfiguration) {
            this.serviceConfiguration = serviceConfiguration;
        }

        public boolean isTripFromLastRefuelAvailable() {
            return tripFromLastRefuelAvailable;
        }

        public void setTripFromLastRefuelAvailable(boolean tripFromLastRefuelAvailable) {
            this.tripFromLastRefuelAvailable = tripFromLastRefuelAvailable;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("daysInMonth", daysInMonth).append("firstWeekday", firstWeekday)
                    .append("month", month).append("year", year).append("firstTripYear", firstTripYear)
                    .append("tripStatistics", tripStatistics).append("longTermData", longTermData)
                    .append("cyclicData", cyclicData).append("serviceConfiguration", serviceConfiguration)
                    .append("tripFromLastRefuelAvailable", tripFromLastRefuelAvailable)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(firstWeekday).append(firstTripYear).append(longTermData)
                    .append(serviceConfiguration).append(daysInMonth).append(month).append(year).append(cyclicData)
                    .append(tripFromLastRefuelAvailable).append(tripStatistics).append(additionalProperties)
                    .toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof RtsViewModel) == false) {
                return false;
            }
            RtsViewModel rhs = ((RtsViewModel) other);
            return new EqualsBuilder().append(firstWeekday, rhs.firstWeekday).append(firstTripYear, rhs.firstTripYear)
                    .append(longTermData, rhs.longTermData).append(serviceConfiguration, rhs.serviceConfiguration)
                    .append(daysInMonth, rhs.daysInMonth).append(month, rhs.month).append(year, rhs.year)
                    .append(cyclicData, rhs.cyclicData)
                    .append(tripFromLastRefuelAvailable, rhs.tripFromLastRefuelAvailable)
                    .append(tripStatistics, rhs.tripStatistics).append(additionalProperties, rhs.additionalProperties)
                    .isEquals();
        }
    }

    public class LongTermData {

        private int tripId;
        private double averageElectricConsumption = BaseVehicle.UNDEFINED;;
        private double averageFuelConsumption = BaseVehicle.UNDEFINED;;
        private double averageCngConsumption = BaseVehicle.UNDEFINED;;
        private double averageSpeed = BaseVehicle.UNDEFINED;;
        private int tripDuration = BaseVehicle.UNDEFINED;;
        private double tripLength = BaseVehicle.UNDEFINED;;
        private @Nullable String timestamp;
        private @Nullable String tripDurationFormatted;
        private @Nullable Object recuperation;
        private double averageAuxiliaryConsumption = BaseVehicle.UNDEFINED;;
        private double totalElectricConsumption = BaseVehicle.UNDEFINED;;
        private @Nullable String longFormattedTimestamp;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public LongTermData() {
        }

        public int getTripId() {
            return tripId;
        }

        public void setTripId(int tripId) {
            this.tripId = tripId;
        }

        public double getAverageElectricConsumption() {
            return averageElectricConsumption;
        }

        public void setAverageElectricConsumption(double averageElectricConsumption) {
            this.averageElectricConsumption = averageElectricConsumption;
        }

        public double getAverageFuelConsumption() {
            return averageFuelConsumption;
        }

        public void setAverageFuelConsumption(double averageFuelConsumption) {
            this.averageFuelConsumption = averageFuelConsumption;
        }

        public double getAverageCngConsumption() {
            return averageCngConsumption;
        }

        public void setAverageCngConsumption(double averageCngConsumption) {
            this.averageCngConsumption = averageCngConsumption;
        }

        public double getAverageSpeed() {
            return averageSpeed;
        }

        public void setAverageSpeed(double averageSpeed) {
            this.averageSpeed = averageSpeed;
        }

        public int getTripDuration() {
            return tripDuration;
        }

        public void setTripDuration(int tripDuration) {
            this.tripDuration = tripDuration;
        }

        public double getTripLength() {
            return tripLength;
        }

        public void setTripLength(double tripLength) {
            this.tripLength = tripLength;
        }

        public @Nullable String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public @Nullable String getTripDurationFormatted() {
            return tripDurationFormatted;
        }

        public void setTripDurationFormatted(String tripDurationFormatted) {
            this.tripDurationFormatted = tripDurationFormatted;
        }

        public @Nullable Object getRecuperation() {
            return recuperation;
        }

        public void setRecuperation(Object recuperation) {
            this.recuperation = recuperation;
        }

        public double getAverageAuxiliaryConsumption() {
            return averageAuxiliaryConsumption;
        }

        public void setAverageAuxiliaryConsumption(double averageAuxiliaryConsumption) {
            this.averageAuxiliaryConsumption = averageAuxiliaryConsumption;
        }

        public double getTotalElectricConsumption() {
            return totalElectricConsumption;
        }

        public void setTotalElectricConsumption(double totalElectricConsumption) {
            this.totalElectricConsumption = totalElectricConsumption;
        }

        public @Nullable String getLongFormattedTimestamp() {
            return longFormattedTimestamp;
        }

        public void setLongFormattedTimestamp(String longFormattedTimestamp) {
            this.longFormattedTimestamp = longFormattedTimestamp;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("tripId", tripId)
                    .append("averageElectricConsumption", averageElectricConsumption)
                    .append("averageFuelConsumption", averageFuelConsumption)
                    .append("averageCngConsumption", averageCngConsumption).append("averageSpeed", averageSpeed)
                    .append("tripDuration", tripDuration).append("tripLength", tripLength)
                    .append("timestamp", timestamp).append("tripDurationFormatted", tripDurationFormatted)
                    .append("recuperation", recuperation)
                    .append("averageAuxiliaryConsumption", averageAuxiliaryConsumption)
                    .append("totalElectricConsumption", totalElectricConsumption)
                    .append("longFormattedTimestamp", longFormattedTimestamp)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(averageElectricConsumption).append(tripLength)
                    .append(averageFuelConsumption).append(averageAuxiliaryConsumption).append(tripId)
                    .append(averageSpeed).append(recuperation).append(totalElectricConsumption)
                    .append(averageCngConsumption).append(longFormattedTimestamp).append(additionalProperties)
                    .append(tripDuration).append(tripDurationFormatted).append(timestamp).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof LongTermData) == false) {
                return false;
            }
            LongTermData rhs = ((LongTermData) other);
            return new EqualsBuilder().append(averageElectricConsumption, rhs.averageElectricConsumption)
                    .append(tripLength, rhs.tripLength).append(averageFuelConsumption, rhs.averageFuelConsumption)
                    .append(averageAuxiliaryConsumption, rhs.averageAuxiliaryConsumption).append(tripId, rhs.tripId)
                    .append(averageSpeed, rhs.averageSpeed).append(recuperation, rhs.recuperation)
                    .append(totalElectricConsumption, rhs.totalElectricConsumption)
                    .append(averageCngConsumption, rhs.averageCngConsumption)
                    .append(longFormattedTimestamp, rhs.longFormattedTimestamp)
                    .append(additionalProperties, rhs.additionalProperties).append(tripDuration, rhs.tripDuration)
                    .append(tripDurationFormatted, rhs.tripDurationFormatted).append(timestamp, rhs.timestamp)
                    .isEquals();
        }
    }

    public class AggregatedStatistics {

        private int tripId;
        private double averageElectricConsumption;
        private double averageFuelConsumption;
        private double averageCngConsumption;
        private double averageSpeed;
        private int tripDuration;
        private double tripLength;
        private @Nullable String timestamp;
        private @Nullable String tripDurationFormatted;
        private @Nullable Object recuperation;
        private double averageAuxiliaryConsumption;
        private double totalElectricConsumption;
        private @Nullable Object longFormattedTimestamp;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public AggregatedStatistics() {
        }

        public int getTripId() {
            return tripId;
        }

        public void setTripId(int tripId) {
            this.tripId = tripId;
        }

        public double getAverageElectricConsumption() {
            return averageElectricConsumption;
        }

        public void setAverageElectricConsumption(double averageElectricConsumption) {
            this.averageElectricConsumption = averageElectricConsumption;
        }

        public double getAverageFuelConsumption() {
            return averageFuelConsumption;
        }

        public void setAverageFuelConsumption(double averageFuelConsumption) {
            this.averageFuelConsumption = averageFuelConsumption;
        }

        public double getAverageCngConsumption() {
            return averageCngConsumption;
        }

        public void setAverageCngConsumption(double averageCngConsumption) {
            this.averageCngConsumption = averageCngConsumption;
        }

        public double getAverageSpeed() {
            return averageSpeed;
        }

        public void setAverageSpeed(double averageSpeed) {
            this.averageSpeed = averageSpeed;
        }

        public int getTripDuration() {
            return tripDuration;
        }

        public void setTripDuration(int tripDuration) {
            this.tripDuration = tripDuration;
        }

        public double getTripLength() {
            return tripLength;
        }

        public void setTripLength(double tripLength) {
            this.tripLength = tripLength;
        }

        public @Nullable String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public @Nullable String getTripDurationFormatted() {
            return tripDurationFormatted;
        }

        public void setTripDurationFormatted(String tripDurationFormatted) {
            this.tripDurationFormatted = tripDurationFormatted;
        }

        public @Nullable Object getRecuperation() {
            return recuperation;
        }

        public void setRecuperation(Object recuperation) {
            this.recuperation = recuperation;
        }

        public double getAverageAuxiliaryConsumption() {
            return averageAuxiliaryConsumption;
        }

        public void setAverageAuxiliaryConsumption(double averageAuxiliaryConsumption) {
            this.averageAuxiliaryConsumption = averageAuxiliaryConsumption;
        }

        public double getTotalElectricConsumption() {
            return totalElectricConsumption;
        }

        public void setTotalElectricConsumption(double totalElectricConsumption) {
            this.totalElectricConsumption = totalElectricConsumption;
        }

        public @Nullable Object getLongFormattedTimestamp() {
            return longFormattedTimestamp;
        }

        public void setLongFormattedTimestamp(Object longFormattedTimestamp) {
            this.longFormattedTimestamp = longFormattedTimestamp;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("tripId", tripId)
                    .append("averageElectricConsumption", averageElectricConsumption)
                    .append("averageFuelConsumption", averageFuelConsumption)
                    .append("averageCngConsumption", averageCngConsumption).append("averageSpeed", averageSpeed)
                    .append("tripDuration", tripDuration).append("tripLength", tripLength)
                    .append("timestamp", timestamp).append("tripDurationFormatted", tripDurationFormatted)
                    .append("recuperation", recuperation)
                    .append("averageAuxiliaryConsumption", averageAuxiliaryConsumption)
                    .append("totalElectricConsumption", totalElectricConsumption)
                    .append("longFormattedTimestamp", longFormattedTimestamp)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(averageElectricConsumption).append(tripLength)
                    .append(averageFuelConsumption).append(averageAuxiliaryConsumption).append(tripId)
                    .append(averageSpeed).append(recuperation).append(totalElectricConsumption)
                    .append(averageCngConsumption).append(longFormattedTimestamp).append(additionalProperties)
                    .append(tripDuration).append(tripDurationFormatted).append(timestamp).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof AggregatedStatistics) == false) {
                return false;
            }
            AggregatedStatistics rhs = ((AggregatedStatistics) other);
            return new EqualsBuilder().append(averageElectricConsumption, rhs.averageElectricConsumption)
                    .append(tripLength, rhs.tripLength).append(averageFuelConsumption, rhs.averageFuelConsumption)
                    .append(averageAuxiliaryConsumption, rhs.averageAuxiliaryConsumption).append(tripId, rhs.tripId)
                    .append(averageSpeed, rhs.averageSpeed).append(recuperation, rhs.recuperation)
                    .append(totalElectricConsumption, rhs.totalElectricConsumption)
                    .append(averageCngConsumption, rhs.averageCngConsumption)
                    .append(longFormattedTimestamp, rhs.longFormattedTimestamp)
                    .append(additionalProperties, rhs.additionalProperties).append(tripDuration, rhs.tripDuration)
                    .append(tripDurationFormatted, rhs.tripDurationFormatted).append(timestamp, rhs.timestamp)
                    .isEquals();
        }
    }

    public class ServiceConfiguration {

        private boolean electricConsumption;
        private boolean triptypeShort;
        private boolean auxiliaryConsumption;
        private boolean fuelOverallConsumption;
        private boolean triptypeCyclic;
        private boolean electricOverallConsumption;
        private boolean triptypeLong;
        private boolean cngOverallConsumption;
        private boolean recuperation;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public ServiceConfiguration() {
        }

        public boolean isElectricConsumption() {
            return electricConsumption;
        }

        public void setElectricConsumption(boolean electricConsumption) {
            this.electricConsumption = electricConsumption;
        }

        public boolean isTriptypeShort() {
            return triptypeShort;
        }

        public void setTriptypeShort(boolean triptypeShort) {
            this.triptypeShort = triptypeShort;
        }

        public boolean isAuxiliaryConsumption() {
            return auxiliaryConsumption;
        }

        public void setAuxiliaryConsumption(boolean auxiliaryConsumption) {
            this.auxiliaryConsumption = auxiliaryConsumption;
        }

        public boolean isFuelOverallConsumption() {
            return fuelOverallConsumption;
        }

        public void setFuelOverallConsumption(boolean fuelOverallConsumption) {
            this.fuelOverallConsumption = fuelOverallConsumption;
        }

        public boolean isTriptypeCyclic() {
            return triptypeCyclic;
        }

        public void setTriptypeCyclic(boolean triptypeCyclic) {
            this.triptypeCyclic = triptypeCyclic;
        }

        public boolean isElectricOverallConsumption() {
            return electricOverallConsumption;
        }

        public void setElectricOverallConsumption(boolean electricOverallConsumption) {
            this.electricOverallConsumption = electricOverallConsumption;
        }

        public boolean isTriptypeLong() {
            return triptypeLong;
        }

        public void setTriptypeLong(boolean triptypeLong) {
            this.triptypeLong = triptypeLong;
        }

        public boolean isCngOverallConsumption() {
            return cngOverallConsumption;
        }

        public void setCngOverallConsumption(boolean cngOverallConsumption) {
            this.cngOverallConsumption = cngOverallConsumption;
        }

        public boolean isRecuperation() {
            return recuperation;
        }

        public void setRecuperation(boolean recuperation) {
            this.recuperation = recuperation;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("electricConsumption", electricConsumption)
                    .append("triptypeShort", triptypeShort).append("auxiliaryConsumption", auxiliaryConsumption)
                    .append("fuelOverallConsumption", fuelOverallConsumption).append("triptypeCyclic", triptypeCyclic)
                    .append("electricOverallConsumption", electricOverallConsumption)
                    .append("triptypeLong", triptypeLong).append("cngOverallConsumption", cngOverallConsumption)
                    .append("recuperation", recuperation).append("additionalProperties", additionalProperties)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(triptypeCyclic).append(triptypeShort).append(electricConsumption)
                    .append(cngOverallConsumption).append(triptypeLong).append(auxiliaryConsumption)
                    .append(additionalProperties).append(electricOverallConsumption).append(fuelOverallConsumption)
                    .append(recuperation).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof ServiceConfiguration) == false) {
                return false;
            }
            ServiceConfiguration rhs = ((ServiceConfiguration) other);
            return new EqualsBuilder().append(triptypeCyclic, rhs.triptypeCyclic)
                    .append(triptypeShort, rhs.triptypeShort).append(electricConsumption, rhs.electricConsumption)
                    .append(cngOverallConsumption, rhs.cngOverallConsumption).append(triptypeLong, rhs.triptypeLong)
                    .append(auxiliaryConsumption, rhs.auxiliaryConsumption)
                    .append(additionalProperties, rhs.additionalProperties)
                    .append(electricOverallConsumption, rhs.electricOverallConsumption)
                    .append(fuelOverallConsumption, rhs.fuelOverallConsumption).append(recuperation, rhs.recuperation)
                    .isEquals();
        }
    }

    public class TripStatistic {

        private @Nullable AggregatedStatistics aggregatedStatistics;
        private @Nullable List<TripStatistic_> tripStatistics;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public TripStatistic() {
        }

        public @Nullable AggregatedStatistics getAggregatedStatistics() {
            return aggregatedStatistics;
        }

        public void setAggregatedStatistics(AggregatedStatistics aggregatedStatistics) {
            this.aggregatedStatistics = aggregatedStatistics;
        }

        public @Nullable List<TripStatistic_> getTripStatistics() {
            return tripStatistics;
        }

        public void setTripStatistics(List<TripStatistic_> tripStatistics) {
            this.tripStatistics = tripStatistics;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("aggregatedStatistics", aggregatedStatistics)
                    .append("tripStatistics", tripStatistics).append("additionalProperties", additionalProperties)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(tripStatistics).append(additionalProperties)
                    .append(aggregatedStatistics).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof TripStatistic) == false) {
                return false;
            }
            TripStatistic rhs = ((TripStatistic) other);
            return new EqualsBuilder().append(tripStatistics, rhs.tripStatistics)
                    .append(additionalProperties, rhs.additionalProperties)
                    .append(aggregatedStatistics, rhs.aggregatedStatistics).isEquals();
        }
    }

    public class TripStatistic_ {

        private int tripId;
        private double averageElectricConsumption = BaseVehicle.UNDEFINED;
        private double averageFuelConsumption = BaseVehicle.UNDEFINED;
        private double averageCngConsumption = BaseVehicle.UNDEFINED;
        private double averageSpeed = BaseVehicle.UNDEFINED;
        private int tripDuration = BaseVehicle.UNDEFINED;
        private double tripLength = BaseVehicle.UNDEFINED;
        private @Nullable String timestamp;
        private @Nullable String tripDurationFormatted;
        private @Nullable Object recuperation;
        private int averageAuxiliaryConsumption = BaseVehicle.UNDEFINED;;
        private int totalElectricConsumption = BaseVehicle.UNDEFINED;;
        private @Nullable String longFormattedTimestamp;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public TripStatistic_() {
        }

        public int getTripId() {
            return tripId;
        }

        public void setTripId(int tripId) {
            this.tripId = tripId;
        }

        public double getAverageElectricConsumption() {
            return averageElectricConsumption;
        }

        public void setAverageElectricConsumption(double averageElectricConsumption) {
            this.averageElectricConsumption = averageElectricConsumption;
        }

        public double getAverageFuelConsumption() {
            return averageFuelConsumption;
        }

        public void setAverageFuelConsumption(double averageFuelConsumption) {
            this.averageFuelConsumption = averageFuelConsumption;
        }

        public double getAverageCngConsumption() {
            return averageCngConsumption;
        }

        public void setAverageCngConsumption(double averageCngConsumption) {
            this.averageCngConsumption = averageCngConsumption;
        }

        public double getAverageSpeed() {
            return averageSpeed;
        }

        public void setAverageSpeed(double averageSpeed) {
            this.averageSpeed = averageSpeed;
        }

        public int getTripDuration() {
            return tripDuration;
        }

        public void setTripDuration(int tripDuration) {
            this.tripDuration = tripDuration;
        }

        public double getTripLength() {
            return tripLength;
        }

        public void setTripLength(double tripLength) {
            this.tripLength = tripLength;
        }

        public @Nullable String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public @Nullable String getTripDurationFormatted() {
            return tripDurationFormatted;
        }

        public void setTripDurationFormatted(String tripDurationFormatted) {
            this.tripDurationFormatted = tripDurationFormatted;
        }

        public @Nullable Object getRecuperation() {
            return recuperation;
        }

        public void setRecuperation(Object recuperation) {
            this.recuperation = recuperation;
        }

        public int getAverageAuxiliaryConsumption() {
            return averageAuxiliaryConsumption;
        }

        public void setAverageAuxiliaryConsumption(int averageAuxiliaryConsumption) {
            this.averageAuxiliaryConsumption = averageAuxiliaryConsumption;
        }

        public int getTotalElectricConsumption() {
            return totalElectricConsumption;
        }

        public void setTotalElectricConsumption(int totalElectricConsumption) {
            this.totalElectricConsumption = totalElectricConsumption;
        }

        public @Nullable String getLongFormattedTimestamp() {
            return longFormattedTimestamp;
        }

        public void setLongFormattedTimestamp(String longFormattedTimestamp) {
            this.longFormattedTimestamp = longFormattedTimestamp;
        }

        public @Nullable ZonedDateTime getStartTimestamp() {
            String formattedTime = longFormattedTimestamp;
            if (formattedTime != null) {
                String[] splitStrings = formattedTime.split(",");
                String dateString = splitStrings[1].trim() + ", " + splitStrings[2].trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy, HH:mm", Locale.getDefault());
                LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault());

                return zdt.minusMinutes(tripDuration);
            }
            return null;
        }

        public @Nullable ZonedDateTime getEndTimestamp() {
            String formattedTime = longFormattedTimestamp;
            if (formattedTime != null) {
                String[] splitStrings = formattedTime.split(",");
                String dateString = splitStrings[1].trim() + ", " + splitStrings[2].trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy, HH:mm", Locale.getDefault());
                LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                ZonedDateTime zdt = dateTime.atZone(ZoneId.systemDefault());

                return zdt;
            }
            return null;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("tripId", tripId)
                    .append("averageElectricConsumption", averageElectricConsumption)
                    .append("averageFuelConsumption", averageFuelConsumption)
                    .append("averageCngConsumption", averageCngConsumption).append("averageSpeed", averageSpeed)
                    .append("tripDuration", tripDuration).append("tripLength", tripLength)
                    .append("timestamp", timestamp).append("tripDurationFormatted", tripDurationFormatted)
                    .append("recuperation", recuperation)
                    .append("averageAuxiliaryConsumption", averageAuxiliaryConsumption)
                    .append("totalElectricConsumption", totalElectricConsumption)
                    .append("longFormattedTimestamp", longFormattedTimestamp)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(averageElectricConsumption).append(tripLength)
                    .append(averageFuelConsumption).append(averageAuxiliaryConsumption).append(tripId)
                    .append(averageSpeed).append(recuperation).append(totalElectricConsumption)
                    .append(averageCngConsumption).append(longFormattedTimestamp).append(additionalProperties)
                    .append(tripDuration).append(tripDurationFormatted).append(timestamp).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof TripStatistic_) == false) {
                return false;
            }
            TripStatistic_ rhs = ((TripStatistic_) other);
            return new EqualsBuilder().append(averageElectricConsumption, rhs.averageElectricConsumption)
                    .append(tripLength, rhs.tripLength).append(averageFuelConsumption, rhs.averageFuelConsumption)
                    .append(averageAuxiliaryConsumption, rhs.averageAuxiliaryConsumption).append(tripId, rhs.tripId)
                    .append(averageSpeed, rhs.averageSpeed).append(recuperation, rhs.recuperation)
                    .append(totalElectricConsumption, rhs.totalElectricConsumption)
                    .append(averageCngConsumption, rhs.averageCngConsumption)
                    .append(longFormattedTimestamp, rhs.longFormattedTimestamp)
                    .append(additionalProperties, rhs.additionalProperties).append(tripDuration, rhs.tripDuration)
                    .append(tripDurationFormatted, rhs.tripDurationFormatted).append(timestamp, rhs.timestamp)
                    .isEquals();
        }

    }
}
