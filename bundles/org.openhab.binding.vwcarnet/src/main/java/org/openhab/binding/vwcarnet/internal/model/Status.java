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

import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;

/**
 * The Vehicle status representation.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class Status {

    private @Nullable String errorCode;
    private @Nullable VehicleStatusData vehicleStatusData;
    private @Nullable Map<String, Object> additionalProperties;

    /**
     * No args constructor for use in serialization
     *
     */
    public Status() {
    }

    public @Nullable String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public @Nullable VehicleStatusData getVehicleStatusData() {
        return vehicleStatusData;
    }

    public void setVehicleStatusData(VehicleStatusData vehicleStatusData) {
        this.vehicleStatusData = vehicleStatusData;
    }

    public @Nullable Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("errorCode", errorCode).append("vehicleStatusData", vehicleStatusData)
                .append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(errorCode).append(additionalProperties).append(vehicleStatusData)
                .toHashCode();
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
        return new EqualsBuilder().append(errorCode, rhs.errorCode)
                .append(additionalProperties, rhs.additionalProperties).append(vehicleStatusData, rhs.vehicleStatusData)
                .isEquals();
    }

    public class VehicleStatusData {

        private @Nullable Boolean windowStatusSupported;
        private @Nullable CarRenderData carRenderData;
        private @Nullable LockData lockData;
        private @Nullable Object headerData;
        private @Nullable String requestStatus;
        private @Nullable Boolean lockDisabled;
        private @Nullable Boolean unlockDisabled;
        private @Nullable Boolean rluDisabled;
        private @Nullable Boolean hideCngFuelLevel;
        private @Nullable Boolean adBlueEnabled;
        private int adBlueLevel = BaseVehicle.UNDEFINED;
        private @Nullable Boolean showAdBlueNotification;
        private @Nullable Boolean rluMibDeactivated;
        private int totalRange = BaseVehicle.UNDEFINED;
        private int primaryEngineRange = BaseVehicle.UNDEFINED;
        private int fuelRange = BaseVehicle.UNDEFINED;
        private int cngRange = BaseVehicle.UNDEFINED;
        private int batteryRange = BaseVehicle.UNDEFINED;
        private int fuelLevel = BaseVehicle.UNDEFINED;
        private int cngFuelLevel = BaseVehicle.UNDEFINED;
        private int batteryLevel = BaseVehicle.UNDEFINED;
        private @Nullable String sliceRootPath;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public VehicleStatusData() {
        }

        public @Nullable Boolean getWindowStatusSupported() {
            return windowStatusSupported;
        }

        public void setWindowStatusSupported(Boolean windowStatusSupported) {
            this.windowStatusSupported = windowStatusSupported;
        }

        public @Nullable CarRenderData getCarRenderData() {
            return carRenderData;
        }

        public void setCarRenderData(CarRenderData carRenderData) {
            this.carRenderData = carRenderData;
        }

        public @Nullable LockData getLockData() {
            return lockData;
        }

        public void setLockData(LockData lockData) {
            this.lockData = lockData;
        }

        public @Nullable Object getHeaderData() {
            return headerData;
        }

        public void setHeaderData(Object headerData) {
            this.headerData = headerData;
        }

        public @Nullable String getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(String requestStatus) {
            this.requestStatus = requestStatus;
        }

        public @Nullable Boolean getLockDisabled() {
            return lockDisabled;
        }

        public void setLockDisabled(Boolean lockDisabled) {
            this.lockDisabled = lockDisabled;
        }

        public @Nullable Boolean getUnlockDisabled() {
            return unlockDisabled;
        }

        public void setUnlockDisabled(Boolean unlockDisabled) {
            this.unlockDisabled = unlockDisabled;
        }

        public @Nullable Boolean getRluDisabled() {
            return rluDisabled;
        }

        public void setRluDisabled(Boolean rluDisabled) {
            this.rluDisabled = rluDisabled;
        }

        public @Nullable Boolean getHideCngFuelLevel() {
            return hideCngFuelLevel;
        }

        public void setHideCngFuelLevel(Boolean hideCngFuelLevel) {
            this.hideCngFuelLevel = hideCngFuelLevel;
        }

        public @Nullable Boolean getAdBlueEnabled() {
            return adBlueEnabled;
        }

        public void setAdBlueEnabled(Boolean adBlueEnabled) {
            this.adBlueEnabled = adBlueEnabled;
        }

        public int getAdBlueLevel() {
            return adBlueLevel;
        }

        public void setAdBlueLevel(int adBlueLevel) {
            this.adBlueLevel = adBlueLevel;
        }

        public @Nullable Boolean getShowAdBlueNotification() {
            return showAdBlueNotification;
        }

        public void setShowAdBlueNotification(Boolean showAdBlueNotification) {
            this.showAdBlueNotification = showAdBlueNotification;
        }

        public @Nullable Boolean getRluMibDeactivated() {
            return rluMibDeactivated;
        }

        public void setRluMibDeactivated(Boolean rluMibDeactivated) {
            this.rluMibDeactivated = rluMibDeactivated;
        }

        public int getTotalRange() {
            return totalRange;
        }

        public void setTotalRange(int totalRange) {
            this.totalRange = totalRange;
        }

        public int getPrimaryEngineRange() {
            return primaryEngineRange;
        }

        public void setPrimaryEngineRange(int primaryEngineRange) {
            this.primaryEngineRange = primaryEngineRange;
        }

        public int getFuelRange() {
            return fuelRange;
        }

        public void setFuelRange(int fuelRange) {
            this.fuelRange = fuelRange;
        }

        public int getCngRange() {
            return cngRange;
        }

        public void setCngRange(int cngRange) {
            this.cngRange = cngRange;
        }

        public int getBatteryRange() {
            return batteryRange;
        }

        public void setBatteryRange(int batteryRange) {
            this.batteryRange = batteryRange;
        }

        public int getFuelLevel() {
            return fuelLevel;
        }

        public void setFuelLevel(int fuelLevel) {
            this.fuelLevel = fuelLevel;
        }

        public int getCngFuelLevel() {
            return cngFuelLevel;
        }

        public void setCngFuelLevel(int cngFuelLevel) {
            this.cngFuelLevel = cngFuelLevel;
        }

        public int getBatteryLevel() {
            return batteryLevel;
        }

        public void setBatteryLevel(int batteryLevel) {
            this.batteryLevel = batteryLevel;
        }

        public @Nullable String getSliceRootPath() {
            return sliceRootPath;
        }

        public void setSliceRootPath(String sliceRootPath) {
            this.sliceRootPath = sliceRootPath;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("windowStatusSupported", windowStatusSupported)
                    .append("carRenderData", carRenderData).append("lockData", lockData)
                    .append("headerData", headerData).append("requestStatus", requestStatus)
                    .append("lockDisabled", lockDisabled).append("unlockDisabled", unlockDisabled)
                    .append("rluDisabled", rluDisabled).append("hideCngFuelLevel", hideCngFuelLevel)
                    .append("adBlueEnabled", adBlueEnabled).append("adBlueLevel", adBlueLevel)
                    .append("showAdBlueNotification", showAdBlueNotification)
                    .append("rluMibDeactivated", rluMibDeactivated).append("totalRange", totalRange)
                    .append("primaryEngineRange", primaryEngineRange).append("fuelRange", fuelRange)
                    .append("cngRange", cngRange).append("batteryRange", batteryRange).append("fuelLevel", fuelLevel)
                    .append("cngFuelLevel", cngFuelLevel).append("batteryLevel", batteryLevel)
                    .append("sliceRootPath", sliceRootPath).append("additionalProperties", additionalProperties)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(headerData).append(fuelLevel).append(adBlueEnabled)
                    .append(unlockDisabled).append(adBlueLevel).append(batteryRange).append(rluDisabled)
                    .append(totalRange).append(cngFuelLevel).append(sliceRootPath).append(lockData)
                    .append(hideCngFuelLevel).append(rluMibDeactivated).append(cngRange).append(fuelRange)
                    .append(primaryEngineRange).append(lockDisabled).append(additionalProperties)
                    .append(windowStatusSupported).append(carRenderData).append(requestStatus)
                    .append(showAdBlueNotification).append(batteryLevel).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof VehicleStatusData) == false) {
                return false;
            }
            VehicleStatusData rhs = ((VehicleStatusData) other);
            return new EqualsBuilder().append(headerData, rhs.headerData).append(fuelLevel, rhs.fuelLevel)
                    .append(adBlueEnabled, rhs.adBlueEnabled).append(unlockDisabled, rhs.unlockDisabled)
                    .append(adBlueLevel, rhs.adBlueLevel).append(batteryRange, rhs.batteryRange)
                    .append(rluDisabled, rhs.rluDisabled).append(totalRange, rhs.totalRange)
                    .append(cngFuelLevel, rhs.cngFuelLevel).append(sliceRootPath, rhs.sliceRootPath)
                    .append(lockData, rhs.lockData).append(hideCngFuelLevel, rhs.hideCngFuelLevel)
                    .append(rluMibDeactivated, rhs.rluMibDeactivated).append(cngRange, rhs.cngRange)
                    .append(fuelRange, rhs.fuelRange).append(primaryEngineRange, rhs.primaryEngineRange)
                    .append(lockDisabled, rhs.lockDisabled).append(additionalProperties, rhs.additionalProperties)
                    .append(windowStatusSupported, rhs.windowStatusSupported).append(carRenderData, rhs.carRenderData)
                    .append(requestStatus, rhs.requestStatus).append(showAdBlueNotification, rhs.showAdBlueNotification)
                    .append(batteryLevel, rhs.batteryLevel).isEquals();
        }
    }

    public class CarRenderData {

        private int parkingLights;
        private int hood;
        private @Nullable Doors doors;
        private @Nullable Windows windows;
        private int sunroof;
        private int roof;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public CarRenderData() {
        }

        public OpenClosedType getDoorStatus(int status) {
            return status == 2 ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
        }

        public int getParkingLights() {
            return parkingLights;
        }

        public void setParkingLights(int parkingLights) {
            this.parkingLights = parkingLights;
        }

        public OpenClosedType getHood() {
            return getDoorStatus(hood);
        }

        public void setHood(int hood) {
            this.hood = hood;
        }

        public @Nullable Doors getDoors() {
            return doors;
        }

        public void setDoors(Doors doors) {
            this.doors = doors;
        }

        public @Nullable Windows getWindows() {
            return windows;
        }

        public void setWindows(Windows windows) {
            this.windows = windows;
        }

        public int getSunroof() {
            return sunroof;
        }

        public void setSunroof(int sunroof) {
            this.sunroof = sunroof;
        }

        public int getRoof() {
            return roof;
        }

        public void setRoof(int roof) {
            this.roof = roof;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("parkingLights", parkingLights).append("hood", hood)
                    .append("doors", doors).append("windows", windows).append("sunroof", sunroof).append("roof", roof)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(doors).append(roof).append(sunroof).append(additionalProperties)
                    .append(parkingLights).append(hood).append(windows).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof CarRenderData) == false) {
                return false;
            }
            CarRenderData rhs = ((CarRenderData) other);
            return new EqualsBuilder().append(doors, rhs.doors).append(roof, rhs.roof).append(sunroof, rhs.sunroof)
                    .append(additionalProperties, rhs.additionalProperties).append(parkingLights, rhs.parkingLights)
                    .append(hood, rhs.hood).append(windows, rhs.windows).isEquals();
        }
    }

    public class LockData {

        private int left_front;
        private int right_front;
        private int left_back;
        private int right_back;
        private int trunk;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public LockData() {
        }

        public OnOffType getDoorsLocked() {
            return left_front == 2 ? OnOffType.ON : OnOffType.OFF;
        }

        public int getLeftFront() {
            return left_front;
        }

        public void setLeftFront(int leftFront) {
            this.left_front = leftFront;
        }

        public int getRightFront() {
            return right_front;
        }

        public void setRightFront(int rightFront) {
            this.right_front = rightFront;
        }

        public int getLeftBack() {
            return left_back;
        }

        public void setLeftBack(int leftBack) {
            this.left_back = leftBack;
        }

        public int getRightBack() {
            return right_back;
        }

        public void setRightBack(int rightBack) {
            this.right_back = rightBack;
        }

        public OnOffType getTrunk() {
            return trunk == 2 ? OnOffType.ON : OnOffType.OFF;
        }

        public void setTrunk(int trunk) {
            this.trunk = trunk;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("left_front", left_front).append("right_front", right_front)
                    .append("left_back", left_back).append("right_back", right_back).append("trunk", trunk)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(left_front).append(right_back).append(right_front).append(left_back)
                    .append(additionalProperties).append(trunk).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof LockData) == false) {
                return false;
            }
            LockData rhs = ((LockData) other);
            return new EqualsBuilder().append(left_front, rhs.left_front).append(right_back, rhs.right_back)
                    .append(right_front, rhs.right_front).append(left_back, rhs.left_back)
                    .append(additionalProperties, rhs.additionalProperties).append(trunk, rhs.trunk).isEquals();
        }

    }

    public class Doors {

        private int left_front;
        private int right_front;
        private int left_back;
        private int right_back;
        private int trunk;
        private int number_of_doors;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public Doors() {
        }

        public OpenClosedType getDoorStatus(int status) {
            return status == 2 ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
        }

        public OpenClosedType getLeftFront() {
            return getDoorStatus(left_front);
        }

        public void setLeftFront(int leftFront) {
            this.left_front = leftFront;
        }

        public OpenClosedType getRightFront() {
            return getDoorStatus(right_front);
        }

        public void setRightFront(int rightFront) {
            this.right_front = rightFront;
        }

        public OpenClosedType getLeftBack() {
            return getDoorStatus(left_back);
        }

        public void setLeftBack(int leftBack) {
            this.left_back = leftBack;
        }

        public OpenClosedType getRightBack() {
            return getDoorStatus(right_back);
        }

        public void setRightBack(int rightBack) {
            this.right_back = rightBack;
        }

        public OpenClosedType getTrunk() {
            return getDoorStatus(trunk);
        }

        public void setTrunk(int trunk) {
            this.trunk = trunk;
        }

        public int getNumberOfDoors() {
            return number_of_doors;
        }

        public void setNumberOfDoors(int numberOfDoors) {
            this.number_of_doors = numberOfDoors;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("left_front", left_front).append("right_front", right_front)
                    .append("left_back", left_back).append("right_back", right_back).append("trunk", trunk)
                    .append("number_of_doors", number_of_doors).append("additionalProperties", additionalProperties)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(left_front).append(right_back).append(right_front)
                    .append(number_of_doors).append(left_back).append(additionalProperties).append(trunk).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Doors) == false) {
                return false;
            }
            Doors rhs = ((Doors) other);
            return new EqualsBuilder().append(left_front, rhs.left_front).append(right_back, rhs.right_back)
                    .append(right_front, rhs.right_front).append(number_of_doors, rhs.number_of_doors)
                    .append(left_back, rhs.left_back).append(additionalProperties, rhs.additionalProperties)
                    .append(trunk, rhs.trunk).isEquals();
        }
    }

    public class Windows {

        private int left_front;
        private int right_front;
        private int left_back;
        private int right_back;
        private @Nullable Map<String, Object> additionalProperties;

        /**
         * No args constructor for use in serialization
         *
         */
        public Windows() {
        }

        public OpenClosedType getWindowStatus(int status) {
            return status == 2 ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
        }

        public OpenClosedType getLeftFront() {
            return getWindowStatus(left_front);
        }

        public void setLeftFront(int leftFront) {
            this.left_front = leftFront;
        }

        public OpenClosedType getRightFront() {
            return getWindowStatus(right_front);
        }

        public void setRightFront(int rightFront) {
            this.right_front = rightFront;
        }

        public OpenClosedType getLeftBack() {
            return getWindowStatus(left_back);
        }

        public void setLeftBack(int leftBack) {
            this.left_back = leftBack;
        }

        public OpenClosedType getRightBack() {
            return getWindowStatus(right_back);
        }

        public void setRightBack(int rightBack) {
            this.right_back = rightBack;
        }

        public @Nullable Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("left_front", left_front).append("right_front", right_front)
                    .append("left_back", left_back).append("right_back", right_back)
                    .append("additionalProperties", additionalProperties).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(left_front).append(left_back).append(right_back)
                    .append(additionalProperties).append(right_front).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Windows) == false) {
                return false;
            }
            Windows rhs = ((Windows) other);
            return new EqualsBuilder().append(left_front, rhs.left_front).append(left_back, rhs.left_back)
                    .append(right_back, rhs.right_back).append(additionalProperties, rhs.additionalProperties)
                    .append(right_front, rhs.right_front).isEquals();
        }

    }
}
