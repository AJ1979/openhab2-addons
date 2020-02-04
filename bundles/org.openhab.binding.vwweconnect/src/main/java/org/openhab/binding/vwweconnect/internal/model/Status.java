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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.OpenClosedType;

import com.google.gson.annotations.SerializedName;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("errorCode", errorCode).append("vehicleStatusData", vehicleStatusData)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(errorCode).append(vehicleStatusData).toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Status)) {
            return false;
        }
        Status rhs = ((Status) other);
        return new EqualsBuilder().append(errorCode, rhs.errorCode).append(vehicleStatusData, rhs.vehicleStatusData)
                .isEquals();
    }

    @NonNullByDefault
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
        private @Nullable String adBlueLevel;
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

        public @Nullable String getAdBlueLevel() {
            return adBlueLevel;
        }

        public void setAdBlueLevel(@Nullable String adBlueLevel) {
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
                    .append("sliceRootPath", sliceRootPath).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(headerData).append(fuelLevel).append(adBlueEnabled)
                    .append(unlockDisabled).append(adBlueLevel).append(batteryRange).append(rluDisabled)
                    .append(totalRange).append(cngFuelLevel).append(sliceRootPath).append(lockData)
                    .append(hideCngFuelLevel).append(rluMibDeactivated).append(cngRange).append(fuelRange)
                    .append(primaryEngineRange).append(lockDisabled).append(windowStatusSupported).append(carRenderData)
                    .append(requestStatus).append(showAdBlueNotification).append(batteryLevel).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof VehicleStatusData)) {
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
                    .append(lockDisabled, rhs.lockDisabled).append(windowStatusSupported, rhs.windowStatusSupported)
                    .append(carRenderData, rhs.carRenderData).append(requestStatus, rhs.requestStatus)
                    .append(showAdBlueNotification, rhs.showAdBlueNotification).append(batteryLevel, rhs.batteryLevel)
                    .isEquals();
        }
    }

    @NonNullByDefault
    public class CarRenderData {

        private int parkingLights;
        private int hood;
        private @Nullable Doors doors;
        private @Nullable Windows windows;
        private int sunroof;
        private int roof;

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

        public OpenClosedType getSunroof() {
            return getDoorStatus(sunroof);
        }

        public void setSunroof(int sunroof) {
            this.sunroof = sunroof;
        }

        public OpenClosedType getRoof() {
            return getDoorStatus(roof);
        }

        public void setRoof(int roof) {
            this.roof = roof;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("parkingLights", parkingLights).append("hood", hood)
                    .append("doors", doors).append("windows", windows).append("sunroof", sunroof).append("roof", roof)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(doors).append(roof).append(sunroof).append(parkingLights).append(hood)
                    .append(windows).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof CarRenderData)) {
                return false;
            }
            CarRenderData rhs = ((CarRenderData) other);
            return new EqualsBuilder().append(doors, rhs.doors).append(roof, rhs.roof).append(sunroof, rhs.sunroof)
                    .append(parkingLights, rhs.parkingLights).append(hood, rhs.hood).append(windows, rhs.windows)
                    .isEquals();
        }
    }

    @NonNullByDefault
    public class LockData {

        @SerializedName("left_front")
        private int leftFront;
        @SerializedName("right_front")
        private int rightFront;
        @SerializedName("left_back")
        private int leftBack;
        @SerializedName("right_back")
        private int rightBack;
        private int trunk;

        public OnOffType getDoorsLocked() {
            return leftFront == 2 ? OnOffType.ON : OnOffType.OFF;
        }

        public int getLeftFront() {
            return leftFront;
        }

        public void setLeftFront(int leftFront) {
            this.leftFront = leftFront;
        }

        public int getRightFront() {
            return rightFront;
        }

        public void setRightFront(int rightFront) {
            this.rightFront = rightFront;
        }

        public int getLeftBack() {
            return leftBack;
        }

        public void setLeftBack(int leftBack) {
            this.leftBack = leftBack;
        }

        public int getRightBack() {
            return rightBack;
        }

        public void setRightBack(int rightBack) {
            this.rightBack = rightBack;
        }

        public OnOffType getTrunk() {
            return trunk == 2 ? OnOffType.ON : OnOffType.OFF;
        }

        public void setTrunk(int trunk) {
            this.trunk = trunk;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("leftFront", leftFront).append("rightFront", rightFront)
                    .append("leftBack", leftBack).append("rightBack", rightBack).append("trunk", trunk).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(leftFront).append(rightBack).append(rightFront).append(leftBack)
                    .append(trunk).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof LockData)) {
                return false;
            }
            LockData rhs = ((LockData) other);
            return new EqualsBuilder().append(leftFront, rhs.leftFront).append(rightBack, rhs.rightBack)
                    .append(rightFront, rhs.rightFront).append(leftBack, rhs.leftBack).append(trunk, rhs.trunk)
                    .isEquals();
        }

    }

    @NonNullByDefault
    public class Doors {

        @SerializedName("left_front")
        private int leftFront;
        @SerializedName("right_front")
        private int rightFront;
        @SerializedName("left_back")
        private int leftBack;
        @SerializedName("right_back")
        private int rightBack;
        private int trunk;
        @SerializedName("number_of_doors")
        private int numberOfDoors;

        public OpenClosedType getDoorStatus(int status) {
            return status == 2 ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
        }

        public OpenClosedType getLeftFront() {
            return getDoorStatus(leftFront);
        }

        public void setLeftFront(int leftFront) {
            this.leftFront = leftFront;
        }

        public OpenClosedType getRightFront() {
            return getDoorStatus(rightFront);
        }

        public void setRightFront(int rightFront) {
            this.rightFront = rightFront;
        }

        public OpenClosedType getLeftBack() {
            return getDoorStatus(leftBack);
        }

        public void setLeftBack(int leftBack) {
            this.leftBack = leftBack;
        }

        public OpenClosedType getRightBack() {
            return getDoorStatus(rightBack);
        }

        public void setRightBack(int rightBack) {
            this.rightBack = rightBack;
        }

        public OpenClosedType getTrunk() {
            return getDoorStatus(trunk);
        }

        public void setTrunk(int trunk) {
            this.trunk = trunk;
        }

        public int getNumberOfDoors() {
            return numberOfDoors;
        }

        public void setNumberOfDoors(int numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("leftFront", leftFront).append("rightFront", rightFront)
                    .append("leftBack", leftBack).append("rightBack", rightBack).append("trunk", trunk)
                    .append("numberOfDoors", numberOfDoors).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(leftFront).append(rightBack).append(rightFront).append(numberOfDoors)
                    .append(leftBack).append(trunk).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Doors)) {
                return false;
            }
            Doors rhs = ((Doors) other);
            return new EqualsBuilder().append(leftFront, rhs.leftFront).append(rightBack, rhs.rightBack)
                    .append(rightFront, rhs.rightFront).append(numberOfDoors, rhs.numberOfDoors)
                    .append(leftBack, rhs.leftBack).append(trunk, rhs.trunk).isEquals();
        }
    }

    @NonNullByDefault
    public class Windows {

        @SerializedName("left_front")
        private int leftFront;
        @SerializedName("right_front")
        private int rightFront;
        @SerializedName("left_back")
        private int leftBack;
        @SerializedName("right_back")
        private int rightBack;

        public OpenClosedType getWindowStatus(int status) {
            return status == 2 ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
        }

        public OpenClosedType getLeftFront() {
            return getWindowStatus(leftFront);
        }

        public void setLeftFront(int leftFront) {
            this.leftFront = leftFront;
        }

        public OpenClosedType getRightFront() {
            return getWindowStatus(rightFront);
        }

        public void setRightFront(int rightFront) {
            this.rightFront = rightFront;
        }

        public OpenClosedType getLeftBack() {
            return getWindowStatus(leftBack);
        }

        public void setLeftBack(int leftBack) {
            this.leftBack = leftBack;
        }

        public OpenClosedType getRightBack() {
            return getWindowStatus(rightBack);
        }

        public void setRightBack(int rightBack) {
            this.rightBack = rightBack;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("leftFront", leftFront).append("rightFront", rightFront)
                    .append("leftBack", leftBack).append("rightBack", rightBack).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(leftFront).append(leftBack).append(rightBack).append(rightFront)
                    .toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Windows)) {
                return false;
            }
            Windows rhs = ((Windows) other);
            return new EqualsBuilder().append(leftFront, rhs.leftFront).append(leftBack, rhs.leftBack)
                    .append(rightBack, rhs.rightBack).append(rightFront, rhs.rightFront).isEquals();
        }

    }
}
