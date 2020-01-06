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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The Vehicle details representation.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class Details {

    private @Nullable VehicleDetails vehicleDetails;
    private @Nullable String errorCode;
    private @Nullable Map<String, Object> additionalProperties;

    /**
     * No args constructor for use in serialization
     *
     */
    public Details() {
    }

    /**
     *
     * @param vehicleDetails
     * @param errorCode
     */
    public Details(VehicleDetails vehicleDetails, String errorCode) {
        super();
        this.vehicleDetails = vehicleDetails;
        this.errorCode = errorCode;
    }

    public @Nullable VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public @Nullable String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public @Nullable Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("vehicleDetails", vehicleDetails).append("errorCode", errorCode)
                .append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(errorCode).append(additionalProperties).append(vehicleDetails).toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Details) == false) {
            return false;
        }
        Details rhs = ((Details) other);
        return new EqualsBuilder().append(errorCode, rhs.errorCode)
                .append(additionalProperties, rhs.additionalProperties).append(vehicleDetails, rhs.vehicleDetails)
                .isEquals();
    }

    public class VehicleDetails {

        private @Nullable List<String> lastConnectionTimeStamp;
        private @Nullable String distanceCovered;
        private int range = BaseVehicle.UNDEFINED;
        private @Nullable String serviceInspectionData;
        private @Nullable String oilInspectionData;
        private @Nullable Boolean showOil;
        private @Nullable Boolean showService;
        private @Nullable Boolean flightMode;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public VehicleDetails() {
        }

        public @Nullable List<String> getLastConnectionTimeStamp() {
            return lastConnectionTimeStamp;
        }

        public void setLastConnectionTimeStamp(List<String> lastConnectionTimeStamp) {
            this.lastConnectionTimeStamp = lastConnectionTimeStamp;
        }

        public @Nullable String getDistanceCovered() {
            return distanceCovered;
        }

        public void setDistanceCovered(String distanceCovered) {
            this.distanceCovered = distanceCovered;
        }

        public int getRange() {
            return range;
        }

        public void setRange(int range) {
            this.range = range;
        }

        public @Nullable String getServiceInspectionData() {
            return serviceInspectionData;
        }

        public void setServiceInspectionData(String serviceInspectionData) {
            this.serviceInspectionData = serviceInspectionData;
        }

        public @Nullable String getOilInspectionData() {
            return oilInspectionData;
        }

        public void setOilInspectionData(String oilInspectionData) {
            this.oilInspectionData = oilInspectionData;
        }

        public @Nullable Boolean getShowOil() {
            return showOil;
        }

        public void setShowOil(Boolean showOil) {
            this.showOil = showOil;
        }

        public @Nullable Boolean getShowService() {
            return showService;
        }

        public void setShowService(Boolean showService) {
            this.showService = showService;
        }

        public @Nullable Boolean getFlightMode() {
            return flightMode;
        }

        public void setFlightMode(Boolean flightMode) {
            this.flightMode = flightMode;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("lastConnectionTimeStamp", lastConnectionTimeStamp)
                    .append("distanceCovered", distanceCovered).append("range", range)
                    .append("serviceInspectionData", serviceInspectionData)
                    .append("oilInspectionData", oilInspectionData).append("showOil", showOil)
                    .append("showService", showService).append("flightMode", flightMode)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(oilInspectionData).append(distanceCovered).append(range)
                    .append(serviceInspectionData).append(lastConnectionTimeStamp).append(additionalProperties)
                    .append(showOil).append(flightMode).append(showService).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof VehicleDetails) == false) {
                return false;
            }
            VehicleDetails rhs = ((VehicleDetails) other);
            return new EqualsBuilder().append(oilInspectionData, rhs.oilInspectionData)
                    .append(distanceCovered, rhs.distanceCovered).append(range, rhs.range)
                    .append(serviceInspectionData, rhs.serviceInspectionData)
                    .append(lastConnectionTimeStamp, rhs.lastConnectionTimeStamp)
                    .append(additionalProperties, rhs.additionalProperties).append(showOil, rhs.showOil)
                    .append(flightMode, rhs.flightMode).append(showService, rhs.showService).isEquals();
        }
    }
}
