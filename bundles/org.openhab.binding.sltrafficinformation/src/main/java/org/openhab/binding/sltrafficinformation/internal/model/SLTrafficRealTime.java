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
package org.openhab.binding.sltrafficinformation.internal.model;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link SLTrafficRealTime} is responsible for JSON conversion.
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class SLTrafficRealTime {

    @SerializedName("StatusCode")
    private int statusCode;
    @SerializedName("Message")
    private @Nullable Object message;
    @SerializedName("ExecutionTime")
    private int executionTime;
    @SerializedName("ResponseData")
    private @Nullable ResponseData responseData;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public @Nullable Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public @Nullable ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("statusCode", statusCode).append("message", message)
                .append("executionTime", executionTime).append("responseData", responseData).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(executionTime).append(responseData).append(message).append(statusCode)
                .toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SLTrafficRealTime)) {
            return false;
        }
        SLTrafficRealTime rhs = ((SLTrafficRealTime) other);
        return new EqualsBuilder().append(executionTime, rhs.executionTime).append(responseData, rhs.responseData)
                .append(message, rhs.message).append(statusCode, rhs.statusCode).isEquals();
    }

    @NonNullByDefault
    public class ResponseData {

        @SerializedName("LatestUpdate")
        private @Nullable String latestUpdate;
        @SerializedName("DataAge")
        private int dataAge;
        @SerializedName("Metros")
        private @Nullable List<Metro> metros;
        @SerializedName("Buses")
        private @Nullable List<Bus> buses;
        @SerializedName("Trains")
        private @Nullable List<Train> trains;
        @SerializedName("Trams")
        private @Nullable List<Tram> trams;
        @SerializedName("Ships")
        private @Nullable List<Object> ships;
        @SerializedName("StopPointDeviations")
        private @Nullable List<StopPointDeviation> stopPointDeviations;

        public @Nullable String getLatestUpdate() {
            return latestUpdate;
        }

        public void setLatestUpdate(String latestUpdate) {
            this.latestUpdate = latestUpdate;
        }

        public int getDataAge() {
            return dataAge;
        }

        public void setDataAge(int dataAge) {
            this.dataAge = dataAge;
        }

        public @Nullable List<Metro> getMetros() {
            return metros;
        }

        public void setMetros(List<Metro> metros) {
            this.metros = metros;
        }

        public @Nullable List<Bus> getBuses() {
            return buses;
        }

        public void setBuses(List<Bus> buses) {
            this.buses = buses;
        }

        public @Nullable List<Train> getTrains() {
            return trains;
        }

        public void setTrains(List<Train> trains) {
            this.trains = trains;
        }

        public @Nullable List<Tram> getTrams() {
            return trams;
        }

        public void setTrams(List<Tram> trams) {
            this.trams = trams;
        }

        public @Nullable List<Object> getShips() {
            return ships;
        }

        public void setShips(List<Object> ships) {
            this.ships = ships;
        }

        public @Nullable List<StopPointDeviation> getStopPointDeviations() {
            return stopPointDeviations;
        }

        public void setStopPointDeviations(List<StopPointDeviation> stopPointDeviations) {
            this.stopPointDeviations = stopPointDeviations;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("latestUpdate", latestUpdate).append("dataAge", dataAge)
                    .append("metros", metros).append("buses", buses).append("trains", trains).append("trams", trams)
                    .append("ships", ships).append("stopPointDeviations", stopPointDeviations).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(dataAge).append(stopPointDeviations).append(ships).append(buses)
                    .append(trains).append(trams).append(latestUpdate).append(metros).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof ResponseData)) {
                return false;
            }
            ResponseData rhs = ((ResponseData) other);
            return new EqualsBuilder().append(dataAge, rhs.dataAge).append(stopPointDeviations, rhs.stopPointDeviations)
                    .append(ships, rhs.ships).append(buses, rhs.buses).append(trains, rhs.trains)
                    .append(trams, rhs.trams).append(latestUpdate, rhs.latestUpdate).append(metros, rhs.metros)
                    .isEquals();
        }
    }

    @NonNullByDefault
    public class StopPointDeviation {

        @SerializedName("StopInfo")
        private @Nullable StopInfo stopInfo;
        @SerializedName("Deviation")
        private @Nullable Deviation_ deviation;

        public @Nullable StopInfo getStopInfo() {
            return stopInfo;
        }

        public void setStopInfo(StopInfo stopInfo) {
            this.stopInfo = stopInfo;
        }

        public @Nullable Deviation_ getDeviation() {
            return deviation;
        }

        public void setDeviation(Deviation_ deviation) {
            this.deviation = deviation;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("stopInfo", stopInfo).append("deviation", deviation).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(stopInfo).append(deviation).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof StopPointDeviation)) {
                return false;
            }
            StopPointDeviation rhs = ((StopPointDeviation) other);
            return new EqualsBuilder().append(stopInfo, rhs.stopInfo).append(deviation, rhs.deviation).isEquals();
        }

    }

    @NonNullByDefault
    public class Bus {

        @SerializedName("GroupOfLine")
        private @Nullable Object groupOfLine;
        @SerializedName("TransportMode")
        private @Nullable String transportMode;
        @SerializedName("LineNumber")
        private @Nullable String lineNumber;
        @SerializedName("Destination")
        private @Nullable String destination;
        @SerializedName("JourneyDirection")
        private int journeyDirection;
        @SerializedName("StopAreaName")
        private @Nullable String stopAreaName;
        @SerializedName("StopAreaNumber")
        private int stopAreaNumber;
        @SerializedName("StopPointNumber")
        private int stopPointNumber;
        @SerializedName("StopPointDesignation")
        private @Nullable String stopPointDesignation;
        @SerializedName("TimeTabledDateTime")
        private @Nullable String timeTabledDateTime;
        @SerializedName("ExpectedDateTime")
        private @Nullable String expectedDateTime;
        @SerializedName("DisplayTime")
        private @Nullable String displayTime;
        @SerializedName("JourneyNumber")
        private int journeyNumber;
        @SerializedName("Deviations")
        private @Nullable Object deviations;

        public @Nullable Object getGroupOfLine() {
            return groupOfLine;
        }

        public void setGroupOfLine(Object groupOfLine) {
            this.groupOfLine = groupOfLine;
        }

        public @Nullable String getTransportMode() {
            return transportMode;
        }

        public void setTransportMode(String transportMode) {
            this.transportMode = transportMode;
        }

        public @Nullable String getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }

        public @Nullable String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getJourneyDirection() {
            return journeyDirection;
        }

        public void setJourneyDirection(int journeyDirection) {
            this.journeyDirection = journeyDirection;
        }

        public @Nullable String getStopAreaName() {
            return stopAreaName;
        }

        public void setStopAreaName(String stopAreaName) {
            this.stopAreaName = stopAreaName;
        }

        public int getStopAreaNumber() {
            return stopAreaNumber;
        }

        public void setStopAreaNumber(int stopAreaNumber) {
            this.stopAreaNumber = stopAreaNumber;
        }

        public int getStopPointNumber() {
            return stopPointNumber;
        }

        public void setStopPointNumber(int stopPointNumber) {
            this.stopPointNumber = stopPointNumber;
        }

        public @Nullable String getStopPointDesignation() {
            return stopPointDesignation;
        }

        public void setStopPointDesignation(String stopPointDesignation) {
            this.stopPointDesignation = stopPointDesignation;
        }

        public @Nullable String getTimeTabledDateTime() {
            return timeTabledDateTime;
        }

        public void setTimeTabledDateTime(String timeTabledDateTime) {
            this.timeTabledDateTime = timeTabledDateTime;
        }

        public @Nullable String getExpectedDateTime() {
            return expectedDateTime;
        }

        public void setExpectedDateTime(String expectedDateTime) {
            this.expectedDateTime = expectedDateTime;
        }

        public @Nullable String getDisplayTime() {
            return displayTime;
        }

        public void setDisplayTime(String displayTime) {
            this.displayTime = displayTime;
        }

        public int getJourneyNumber() {
            return journeyNumber;
        }

        public void setJourneyNumber(int journeyNumber) {
            this.journeyNumber = journeyNumber;
        }

        public @Nullable Object getDeviations() {
            return deviations;
        }

        public void setDeviations(Object deviations) {
            this.deviations = deviations;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("groupOfLine", groupOfLine).append("transportMode", transportMode)
                    .append("lineNumber", lineNumber).append("destination", destination)
                    .append("journeyDirection", journeyDirection).append("stopAreaName", stopAreaName)
                    .append("stopAreaNumber", stopAreaNumber).append("stopPointNumber", stopPointNumber)
                    .append("stopPointDesignation", stopPointDesignation)
                    .append("timeTabledDateTime", timeTabledDateTime).append("expectedDateTime", expectedDateTime)
                    .append("displayTime", displayTime).append("journeyNumber", journeyNumber)
                    .append("deviations", deviations).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(displayTime).append(stopAreaName).append(stopPointDesignation)
                    .append(journeyNumber).append(destination).append(journeyDirection).append(expectedDateTime)
                    .append(deviations).append(groupOfLine).append(transportMode).append(timeTabledDateTime)
                    .append(lineNumber).append(stopPointNumber).append(stopAreaNumber).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Bus)) {
                return false;
            }
            Bus rhs = ((Bus) other);
            return new EqualsBuilder().append(displayTime, rhs.displayTime).append(stopAreaName, rhs.stopAreaName)
                    .append(stopPointDesignation, rhs.stopPointDesignation).append(journeyNumber, rhs.journeyNumber)
                    .append(destination, rhs.destination).append(journeyDirection, rhs.journeyDirection)
                    .append(expectedDateTime, rhs.expectedDateTime).append(deviations, rhs.deviations)
                    .append(groupOfLine, rhs.groupOfLine).append(transportMode, rhs.transportMode)
                    .append(timeTabledDateTime, rhs.timeTabledDateTime).append(lineNumber, rhs.lineNumber)
                    .append(stopPointNumber, rhs.stopPointNumber).append(stopAreaNumber, rhs.stopAreaNumber).isEquals();
        }

    }

    @NonNullByDefault
    public class Train {

        @SerializedName("SecondaryDestinationName")
        private @Nullable Object secondaryDestinationName;
        @SerializedName("GroupOfLine")
        private @Nullable String groupOfLine;
        @SerializedName("TransportMode")
        private @Nullable String transportMode;
        @SerializedName("LineNumber")
        private @Nullable String lineNumber;
        @SerializedName("Destination")
        private @Nullable String destination;
        @SerializedName("JourneyDirection")
        private int journeyDirection;
        @SerializedName("StopAreaName")
        private @Nullable String stopAreaName;
        @SerializedName("StopAreaNumber")
        private int stopAreaNumber;
        @SerializedName("StopPointNumber")
        private int stopPointNumber;
        @SerializedName("StopPointDesignation")
        private @Nullable String stopPointDesignation;
        @SerializedName("TimeTabledDateTime")
        private @Nullable String timeTabledDateTime;
        @SerializedName("ExpectedDateTime")
        private @Nullable String expectedDateTime;
        @SerializedName("DisplayTime")
        private @Nullable String displayTime;
        @SerializedName("JourneyNumber")
        private int journeyNumber;
        @SerializedName("Deviations")
        private @Nullable List<Deviation> deviations = null;

        public @Nullable Object getSecondaryDestinationName() {
            return secondaryDestinationName;
        }

        public void setSecondaryDestinationName(Object secondaryDestinationName) {
            this.secondaryDestinationName = secondaryDestinationName;
        }

        public @Nullable String getGroupOfLine() {
            return groupOfLine;
        }

        public void setGroupOfLine(String groupOfLine) {
            this.groupOfLine = groupOfLine;
        }

        public @Nullable String getTransportMode() {
            return transportMode;
        }

        public void setTransportMode(String transportMode) {
            this.transportMode = transportMode;
        }

        public @Nullable String getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }

        public @Nullable String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getJourneyDirection() {
            return journeyDirection;
        }

        public void setJourneyDirection(int journeyDirection) {
            this.journeyDirection = journeyDirection;
        }

        public @Nullable String getStopAreaName() {
            return stopAreaName;
        }

        public void setStopAreaName(String stopAreaName) {
            this.stopAreaName = stopAreaName;
        }

        public int getStopAreaNumber() {
            return stopAreaNumber;
        }

        public void setStopAreaNumber(int stopAreaNumber) {
            this.stopAreaNumber = stopAreaNumber;
        }

        public int getStopPointNumber() {
            return stopPointNumber;
        }

        public void setStopPointNumber(int stopPointNumber) {
            this.stopPointNumber = stopPointNumber;
        }

        public @Nullable String getStopPointDesignation() {
            return stopPointDesignation;
        }

        public void setStopPointDesignation(String stopPointDesignation) {
            this.stopPointDesignation = stopPointDesignation;
        }

        public @Nullable String getTimeTabledDateTime() {
            return timeTabledDateTime;
        }

        public void setTimeTabledDateTime(String timeTabledDateTime) {
            this.timeTabledDateTime = timeTabledDateTime;
        }

        public @Nullable String getExpectedDateTime() {
            return expectedDateTime;
        }

        public void setExpectedDateTime(String expectedDateTime) {
            this.expectedDateTime = expectedDateTime;
        }

        public @Nullable String getDisplayTime() {
            return displayTime;
        }

        public void setDisplayTime(String displayTime) {
            this.displayTime = displayTime;
        }

        public int getJourneyNumber() {
            return journeyNumber;
        }

        public void setJourneyNumber(int journeyNumber) {
            this.journeyNumber = journeyNumber;
        }

        public @Nullable List<Deviation> getDeviations() {
            return deviations;
        }

        public void setDeviations(List<Deviation> deviations) {
            this.deviations = deviations;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("secondaryDestinationName", secondaryDestinationName)
                    .append("groupOfLine", groupOfLine).append("transportMode", transportMode)
                    .append("lineNumber", lineNumber).append("destination", destination)
                    .append("journeyDirection", journeyDirection).append("stopAreaName", stopAreaName)
                    .append("stopAreaNumber", stopAreaNumber).append("stopPointNumber", stopPointNumber)
                    .append("stopPointDesignation", stopPointDesignation)
                    .append("timeTabledDateTime", timeTabledDateTime).append("expectedDateTime", expectedDateTime)
                    .append("displayTime", displayTime).append("journeyNumber", journeyNumber)
                    .append("deviations", deviations).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(secondaryDestinationName).append(displayTime).append(stopAreaName)
                    .append(stopPointDesignation).append(journeyNumber).append(destination).append(journeyDirection)
                    .append(expectedDateTime).append(deviations).append(groupOfLine).append(transportMode)
                    .append(timeTabledDateTime).append(lineNumber).append(stopPointNumber).append(stopAreaNumber)
                    .toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Train)) {
                return false;
            }
            Train rhs = ((Train) other);
            return new EqualsBuilder().append(secondaryDestinationName, rhs.secondaryDestinationName)
                    .append(displayTime, rhs.displayTime).append(stopAreaName, rhs.stopAreaName)
                    .append(stopPointDesignation, rhs.stopPointDesignation).append(journeyNumber, rhs.journeyNumber)
                    .append(destination, rhs.destination).append(journeyDirection, rhs.journeyDirection)
                    .append(expectedDateTime, rhs.expectedDateTime).append(deviations, rhs.deviations)
                    .append(groupOfLine, rhs.groupOfLine).append(transportMode, rhs.transportMode)
                    .append(timeTabledDateTime, rhs.timeTabledDateTime).append(lineNumber, rhs.lineNumber)
                    .append(stopPointNumber, rhs.stopPointNumber).append(stopAreaNumber, rhs.stopAreaNumber).isEquals();
        }

    }

    @NonNullByDefault
    public class Tram {

        @SerializedName("TransportMode")
        private @Nullable String transportMode;
        @SerializedName("LineNumber")
        private @Nullable String lineNumber;
        @SerializedName("Destination")
        private @Nullable String destination;
        @SerializedName("JourneyDirection")
        private int journeyDirection;
        @SerializedName("GroupOfLine")
        private @Nullable String groupOfLine;
        @SerializedName("StopAreaName")
        private @Nullable String stopAreaName;
        @SerializedName("StopAreaNumber")
        private int stopAreaNumber;
        @SerializedName("StopPointNumber")
        private int stopPointNumber;
        @SerializedName("StopPointDesignation")
        private @Nullable String stopPointDesignation;
        @SerializedName("TimeTabledDateTime")
        private @Nullable String timeTabledDateTime;
        @SerializedName("ExpectedDateTime")
        private @Nullable String expectedDateTime;
        @SerializedName("DisplayTime")
        private @Nullable String displayTime;
        @SerializedName("JourneyNumber")
        private int journeyNumber;
        @SerializedName("Deviations")
        private @Nullable Object deviations;

        public @Nullable String getTransportMode() {
            return transportMode;
        }

        public void setTransportMode(String transportMode) {
            this.transportMode = transportMode;
        }

        public @Nullable String getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }

        public @Nullable String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getJourneyDirection() {
            return journeyDirection;
        }

        public void setJourneyDirection(int journeyDirection) {
            this.journeyDirection = journeyDirection;
        }

        public @Nullable String getGroupOfLine() {
            return groupOfLine;
        }

        public void setGroupOfLine(String groupOfLine) {
            this.groupOfLine = groupOfLine;
        }

        public @Nullable String getStopAreaName() {
            return stopAreaName;
        }

        public void setStopAreaName(String stopAreaName) {
            this.stopAreaName = stopAreaName;
        }

        public int getStopAreaNumber() {
            return stopAreaNumber;
        }

        public void setStopAreaNumber(int stopAreaNumber) {
            this.stopAreaNumber = stopAreaNumber;
        }

        public int getStopPointNumber() {
            return stopPointNumber;
        }

        public void setStopPointNumber(int stopPointNumber) {
            this.stopPointNumber = stopPointNumber;
        }

        public @Nullable String getStopPointDesignation() {
            return stopPointDesignation;
        }

        public void setStopPointDesignation(String stopPointDesignation) {
            this.stopPointDesignation = stopPointDesignation;
        }

        public @Nullable String getTimeTabledDateTime() {
            return timeTabledDateTime;
        }

        public void setTimeTabledDateTime(String timeTabledDateTime) {
            this.timeTabledDateTime = timeTabledDateTime;
        }

        public @Nullable String getExpectedDateTime() {
            return expectedDateTime;
        }

        public void setExpectedDateTime(String expectedDateTime) {
            this.expectedDateTime = expectedDateTime;
        }

        public @Nullable String getDisplayTime() {
            return displayTime;
        }

        public void setDisplayTime(String displayTime) {
            this.displayTime = displayTime;
        }

        public int getJourneyNumber() {
            return journeyNumber;
        }

        public void setJourneyNumber(int journeyNumber) {
            this.journeyNumber = journeyNumber;
        }

        public @Nullable Object getDeviations() {
            return deviations;
        }

        public void setDeviations(Object deviations) {
            this.deviations = deviations;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("transportMode", transportMode).append("lineNumber", lineNumber)
                    .append("destination", destination).append("journeyDirection", journeyDirection)
                    .append("groupOfLine", groupOfLine).append("stopAreaName", stopAreaName)
                    .append("stopAreaNumber", stopAreaNumber).append("stopPointNumber", stopPointNumber)
                    .append("stopPointDesignation", stopPointDesignation)
                    .append("timeTabledDateTime", timeTabledDateTime).append("expectedDateTime", expectedDateTime)
                    .append("displayTime", displayTime).append("journeyNumber", journeyNumber)
                    .append("deviations", deviations).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(displayTime).append(stopAreaName).append(stopPointDesignation)
                    .append(journeyNumber).append(destination).append(journeyDirection).append(expectedDateTime)
                    .append(deviations).append(groupOfLine).append(transportMode).append(timeTabledDateTime)
                    .append(lineNumber).append(stopPointNumber).append(stopAreaNumber).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Tram)) {
                return false;
            }
            Tram rhs = ((Tram) other);
            return new EqualsBuilder().append(displayTime, rhs.displayTime).append(stopAreaName, rhs.stopAreaName)
                    .append(stopPointDesignation, rhs.stopPointDesignation).append(journeyNumber, rhs.journeyNumber)
                    .append(destination, rhs.destination).append(journeyDirection, rhs.journeyDirection)
                    .append(expectedDateTime, rhs.expectedDateTime).append(deviations, rhs.deviations)
                    .append(groupOfLine, rhs.groupOfLine).append(transportMode, rhs.transportMode)
                    .append(timeTabledDateTime, rhs.timeTabledDateTime).append(lineNumber, rhs.lineNumber)
                    .append(stopPointNumber, rhs.stopPointNumber).append(stopAreaNumber, rhs.stopAreaNumber).isEquals();
        }

    }

    @NonNullByDefault
    public class Metro {

        @SerializedName("GroupOfLine")
        private @Nullable String groupOfLine;
        @SerializedName("DisplayTime")
        private @Nullable String displayTime;
        @SerializedName("TransportMode")
        private @Nullable String transportMode;
        @SerializedName("LineNumber")
        private @Nullable String lineNumber;
        @SerializedName("Destination")
        private @Nullable String destination;
        @SerializedName("JourneyDirection")
        private int journeyDirection;
        @SerializedName("StopAreaName")
        private @Nullable String stopAreaName;
        @SerializedName("StopAreaNumber")
        private int stopAreaNumber;
        @SerializedName("StopPointNumber")
        private int stopPointNumber;
        @SerializedName("StopPointDesignation")
        private @Nullable String stopPointDesignation;
        @SerializedName("TimeTabledDateTime")
        private @Nullable String timeTabledDateTime;
        @SerializedName("ExpectedDateTime")
        private @Nullable String expectedDateTime;
        @SerializedName("JourneyNumber")
        private int journeyNumber;
        @SerializedName("Deviations")
        private @Nullable Object deviations;

        public @Nullable String getGroupOfLine() {
            return groupOfLine;
        }

        public void setGroupOfLine(String groupOfLine) {
            this.groupOfLine = groupOfLine;
        }

        public @Nullable String getDisplayTime() {
            return displayTime;
        }

        public void setDisplayTime(String displayTime) {
            this.displayTime = displayTime;
        }

        public @Nullable String getTransportMode() {
            return transportMode;
        }

        public void setTransportMode(String transportMode) {
            this.transportMode = transportMode;
        }

        public @Nullable String getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(String lineNumber) {
            this.lineNumber = lineNumber;
        }

        public @Nullable String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getJourneyDirection() {
            return journeyDirection;
        }

        public void setJourneyDirection(int journeyDirection) {
            this.journeyDirection = journeyDirection;
        }

        public @Nullable String getStopAreaName() {
            return stopAreaName;
        }

        public void setStopAreaName(String stopAreaName) {
            this.stopAreaName = stopAreaName;
        }

        public int getStopAreaNumber() {
            return stopAreaNumber;
        }

        public void setStopAreaNumber(int stopAreaNumber) {
            this.stopAreaNumber = stopAreaNumber;
        }

        public int getStopPointNumber() {
            return stopPointNumber;
        }

        public void setStopPointNumber(int stopPointNumber) {
            this.stopPointNumber = stopPointNumber;
        }

        public @Nullable String getStopPointDesignation() {
            return stopPointDesignation;
        }

        public void setStopPointDesignation(String stopPointDesignation) {
            this.stopPointDesignation = stopPointDesignation;
        }

        public @Nullable String getTimeTabledDateTime() {
            return timeTabledDateTime;
        }

        public void setTimeTabledDateTime(String timeTabledDateTime) {
            this.timeTabledDateTime = timeTabledDateTime;
        }

        public @Nullable String getExpectedDateTime() {
            return expectedDateTime;
        }

        public void setExpectedDateTime(String expectedDateTime) {
            this.expectedDateTime = expectedDateTime;
        }

        public int getJourneyNumber() {
            return journeyNumber;
        }

        public void setJourneyNumber(int journeyNumber) {
            this.journeyNumber = journeyNumber;
        }

        public @Nullable Object getDeviations() {
            return deviations;
        }

        public void setDeviations(Object deviations) {
            this.deviations = deviations;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("groupOfLine", groupOfLine).append("displayTime", displayTime)
                    .append("transportMode", transportMode).append("lineNumber", lineNumber)
                    .append("destination", destination).append("journeyDirection", journeyDirection)
                    .append("stopAreaName", stopAreaName).append("stopAreaNumber", stopAreaNumber)
                    .append("stopPointNumber", stopPointNumber).append("stopPointDesignation", stopPointDesignation)
                    .append("timeTabledDateTime", timeTabledDateTime).append("expectedDateTime", expectedDateTime)
                    .append("journeyNumber", journeyNumber).append("deviations", deviations).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(displayTime).append(stopAreaName).append(stopPointDesignation)
                    .append(journeyNumber).append(destination).append(journeyDirection).append(expectedDateTime)
                    .append(deviations).append(groupOfLine).append(transportMode).append(timeTabledDateTime)
                    .append(lineNumber).append(stopPointNumber).append(stopAreaNumber).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Metro)) {
                return false;
            }
            Metro rhs = ((Metro) other);
            return new EqualsBuilder().append(displayTime, rhs.displayTime).append(stopAreaName, rhs.stopAreaName)
                    .append(stopPointDesignation, rhs.stopPointDesignation).append(journeyNumber, rhs.journeyNumber)
                    .append(destination, rhs.destination).append(journeyDirection, rhs.journeyDirection)
                    .append(expectedDateTime, rhs.expectedDateTime).append(deviations, rhs.deviations)
                    .append(groupOfLine, rhs.groupOfLine).append(transportMode, rhs.transportMode)
                    .append(timeTabledDateTime, rhs.timeTabledDateTime).append(lineNumber, rhs.lineNumber)
                    .append(stopPointNumber, rhs.stopPointNumber).append(stopAreaNumber, rhs.stopAreaNumber).isEquals();
        }

    }

    @NonNullByDefault
    public class Deviation {

        @SerializedName("Text")
        private @Nullable String text;
        @SerializedName("Consequence")
        private @Nullable String consequence;
        @SerializedName("ImportanceLevel")
        private int importanceLevel;

        public @Nullable String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public @Nullable String getConsequence() {
            return consequence;
        }

        public void setConsequence(String consequence) {
            this.consequence = consequence;
        }

        public int getImportanceLevel() {
            return importanceLevel;
        }

        public void setImportanceLevel(int importanceLevel) {
            this.importanceLevel = importanceLevel;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("text", text).append("consequence", consequence)
                    .append("importanceLevel", importanceLevel).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(consequence).append(text).append(importanceLevel).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Deviation)) {
                return false;
            }
            Deviation rhs = ((Deviation) other);
            return new EqualsBuilder().append(consequence, rhs.consequence).append(text, rhs.text)
                    .append(importanceLevel, rhs.importanceLevel).isEquals();
        }

    }

    @NonNullByDefault
    public class Deviation_ {

        @SerializedName("Text")
        private @Nullable String text;
        @SerializedName("Consequence")
        private @Nullable Object consequence;
        @SerializedName("ImportanceLevel")
        private int importanceLevel;

        public @Nullable String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public @Nullable Object getConsequence() {
            return consequence;
        }

        public void setConsequence(Object consequence) {
            this.consequence = consequence;
        }

        public int getImportanceLevel() {
            return importanceLevel;
        }

        public void setImportanceLevel(int importanceLevel) {
            this.importanceLevel = importanceLevel;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("text", text).append("consequence", consequence)
                    .append("importanceLevel", importanceLevel).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(consequence).append(text).append(importanceLevel).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Deviation_)) {
                return false;
            }
            Deviation_ rhs = ((Deviation_) other);
            return new EqualsBuilder().append(consequence, rhs.consequence).append(text, rhs.text)
                    .append(importanceLevel, rhs.importanceLevel).isEquals();
        }
    }

    @NonNullByDefault
    public class StopInfo {

        @SerializedName("StopAreaNumber")
        private int stopAreaNumber;
        @SerializedName("StopAreaName")
        private @Nullable String stopAreaName;
        @SerializedName("TransportMode")
        private @Nullable String transportMode;
        @SerializedName("GroupOfLine")
        private @Nullable String groupOfLine;

        public int getStopAreaNumber() {
            return stopAreaNumber;
        }

        public void setStopAreaNumber(int stopAreaNumber) {
            this.stopAreaNumber = stopAreaNumber;
        }

        public @Nullable String getStopAreaName() {
            return stopAreaName;
        }

        public void setStopAreaName(String stopAreaName) {
            this.stopAreaName = stopAreaName;
        }

        public @Nullable String getTransportMode() {
            return transportMode;
        }

        public void setTransportMode(String transportMode) {
            this.transportMode = transportMode;
        }

        public @Nullable String getGroupOfLine() {
            return groupOfLine;
        }

        public void setGroupOfLine(String groupOfLine) {
            this.groupOfLine = groupOfLine;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("stopAreaNumber", stopAreaNumber)
                    .append("stopAreaName", stopAreaName).append("transportMode", transportMode)
                    .append("groupOfLine", groupOfLine).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(transportMode).append(groupOfLine).append(stopAreaName)
                    .append(stopAreaNumber).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof StopInfo)) {
                return false;
            }
            StopInfo rhs = ((StopInfo) other);
            return new EqualsBuilder().append(transportMode, rhs.transportMode).append(groupOfLine, rhs.groupOfLine)
                    .append(stopAreaName, rhs.stopAreaName).append(stopAreaNumber, rhs.stopAreaNumber).isEquals();
        }
    }

}
