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

import java.time.LocalDate;
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

import com.google.gson.annotations.Expose;

/**
 * The Vehicle representation.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class Vehicle extends BaseVehicle {

    private @Nullable String errorCode;
    private @Nullable CompleteVehicleJson completeVehicleJson;
    @Expose(serialize = false, deserialize = false)
    private @Nullable Details vehicleDetails;
    @Expose(serialize = false, deserialize = false)
    private @Nullable Status vehicleStatus;
    @Expose(serialize = false, deserialize = false)
    private @Nullable Trips trips;
    @Expose(serialize = false, deserialize = false)
    private @Nullable Location vehicleLocation;

    /**
     * No args constructor for use in serialization
     *
     */
    public Vehicle() {
    }

    public @Nullable String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(@Nullable String errorCode) {
        this.errorCode = errorCode;
    }

    public @Nullable CompleteVehicleJson getCompleteVehicleJson() {
        return completeVehicleJson;
    }

    public void setCompleteVehicleJson(CompleteVehicleJson completeVehicleJson) {
        this.completeVehicleJson = completeVehicleJson;
    }

    public @Nullable Details getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(Details vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public @Nullable Status getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(Status vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public @Nullable Trips getTrips() {
        return trips;
    }

    public void setTrips(Trips trips) {
        this.trips = trips;
    }

    public @Nullable Location getVehicleLocation() {
        return vehicleLocation;
    }

    public void setVehicleLocation(Location vehicleLocation) {
        this.vehicleLocation = vehicleLocation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("errorCode", errorCode)
                .append("completeVehicleJson", completeVehicleJson).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(errorCode).append(completeVehicleJson).toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Vehicle) == false) {
            return false;
        }
        Vehicle rhs = ((Vehicle) other);
        return new EqualsBuilder().append(errorCode, rhs.errorCode).append(completeVehicleJson, rhs.completeVehicleJson)
                .append(vehicleDetails, rhs.vehicleDetails).append(vehicleStatus, rhs.vehicleStatus)
                .append(vehicleLocation, rhs.vehicleLocation).isEquals();
    }

    public class CompleteVehicleJson {

        private @Nullable String vin;
        private @Nullable String name;
        private @Nullable Boolean expired;
        private @Nullable String model;
        private @Nullable String modelCode;
        private @Nullable String modelYear;
        private @Nullable String imageUrl;

        private @Nullable Object vehicleSpecificFallbackImageUrl;
        private @Nullable Object modelSpecificFallbackImageUrl;
        private @Nullable String defaultImageUrl;
        private @Nullable String vehicleBrand;
        private @Nullable String enrollmentDate;
        private @Nullable Boolean deviceOCU1;
        private @Nullable Boolean deviceOCU2;
        private @Nullable Boolean deviceMIB;
        private @Nullable Boolean engineTypeCombustian;
        private @Nullable Boolean engineTypeHybridOCU1;
        private @Nullable Boolean engineTypeHybridOCU2;
        private @Nullable Boolean engineTypeElectric;
        private @Nullable Boolean engineTypeCNG;
        private @Nullable Boolean engineTypeDefault;
        private @Nullable String stpStatus;
        private @Nullable Boolean windowstateSupported;
        private @Nullable String dashboardUrl;
        private @Nullable Boolean vhrRequested;
        private @Nullable Boolean vsrRequested;
        private @Nullable Boolean vhrConfigAvailable;
        private @Nullable Boolean verifiedByDealer;
        private @Nullable Boolean vhr2;
        private @Nullable Boolean roleEnabled;
        private @Nullable Boolean isEL2Vehicle;
        private @Nullable Boolean workshopMode;
        private @Nullable Boolean hiddenUserProfiles;
        private @Nullable Object mobileKeyActivated;
        private @Nullable String enrollmentType;
        private @Nullable Boolean ocu3Low;
        private @Nullable List<PackageService> packageServices = null;
        private @Nullable Boolean fullyEnrolled;
        private @Nullable Boolean secondaryUser;
        private @Nullable Boolean fleet;
        private @Nullable Boolean touareg;
        private @Nullable Boolean iceSupported;
        private @Nullable Boolean flightMode;
        private @Nullable Boolean esimCompatible;
        private @Nullable Boolean dkyenabled;
        private @Nullable Object smartCardKeyActivated;
        private @Nullable Boolean selected;
        private @Nullable Boolean defaultCar;
        private @Nullable Boolean vwConnectPowerLayerAvailable;

        /**
         * No args constructor for use in serialization
         *
         */
        public CompleteVehicleJson() {
        }

        public @Nullable String getVin() {
            return vin;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public @Nullable String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public @Nullable Boolean getExpired() {
            return expired;
        }

        public void setExpired(Boolean expired) {
            this.expired = expired;
        }

        public @Nullable String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public @Nullable String getModelCode() {
            return modelCode;
        }

        public void setModelCode(String modelCode) {
            this.modelCode = modelCode;
        }

        public @Nullable String getModelYear() {
            return modelYear;
        }

        public void setModelYear(String modelYear) {
            this.modelYear = modelYear;
        }

        public @Nullable String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public @Nullable Object getVehicleSpecificFallbackImageUrl() {
            return vehicleSpecificFallbackImageUrl;
        }

        public void setVehicleSpecificFallbackImageUrl(Object vehicleSpecificFallbackImageUrl) {
            this.vehicleSpecificFallbackImageUrl = vehicleSpecificFallbackImageUrl;
        }

        public @Nullable Object getModelSpecificFallbackImageUrl() {
            return modelSpecificFallbackImageUrl;
        }

        public void setModelSpecificFallbackImageUrl(Object modelSpecificFallbackImageUrl) {
            this.modelSpecificFallbackImageUrl = modelSpecificFallbackImageUrl;
        }

        public @Nullable String getDefaultImageUrl() {
            return defaultImageUrl;
        }

        public void setDefaultImageUrl(String defaultImageUrl) {
            this.defaultImageUrl = defaultImageUrl;
        }

        public @Nullable String getVehicleBrand() {
            return vehicleBrand;
        }

        public void setVehicleBrand(String vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
        }

        public @Nullable ZonedDateTime getEnrollmentStartDate() {
            String formattedTime = enrollmentDate;
            if (formattedTime != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.getDefault());
                LocalDate date = LocalDate.parse(formattedTime, formatter);
                ZonedDateTime zdt = date.atStartOfDay(ZoneId.systemDefault());

                return zdt;
            }
            return null;
        }

        public @Nullable String getEnrollmentDate() {
            return enrollmentDate;
        }

        public void setEnrollmentDate(String enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
        }

        public @Nullable Boolean getDeviceOCU1() {
            return deviceOCU1;
        }

        public void setDeviceOCU1(Boolean deviceOCU1) {
            this.deviceOCU1 = deviceOCU1;
        }

        public @Nullable Boolean getDeviceOCU2() {
            return deviceOCU2;
        }

        public void setDeviceOCU2(Boolean deviceOCU2) {
            this.deviceOCU2 = deviceOCU2;
        }

        public @Nullable Boolean getDeviceMIB() {
            return deviceMIB;
        }

        public void setDeviceMIB(Boolean deviceMIB) {
            this.deviceMIB = deviceMIB;
        }

        public @Nullable Boolean getEngineTypeCombustian() {
            return engineTypeCombustian;
        }

        public void setEngineTypeCombustian(Boolean engineTypeCombustian) {
            this.engineTypeCombustian = engineTypeCombustian;
        }

        public @Nullable Boolean getEngineTypeHybridOCU1() {
            return engineTypeHybridOCU1;
        }

        public void setEngineTypeHybridOCU1(Boolean engineTypeHybridOCU1) {
            this.engineTypeHybridOCU1 = engineTypeHybridOCU1;
        }

        public @Nullable Boolean getEngineTypeHybridOCU2() {
            return engineTypeHybridOCU2;
        }

        public void setEngineTypeHybridOCU2(Boolean engineTypeHybridOCU2) {
            this.engineTypeHybridOCU2 = engineTypeHybridOCU2;
        }

        public @Nullable Boolean getEngineTypeElectric() {
            return engineTypeElectric;
        }

        public void setEngineTypeElectric(Boolean engineTypeElectric) {
            this.engineTypeElectric = engineTypeElectric;
        }

        public @Nullable Boolean getEngineTypeCNG() {
            return engineTypeCNG;
        }

        public void setEngineTypeCNG(Boolean engineTypeCNG) {
            this.engineTypeCNG = engineTypeCNG;
        }

        public @Nullable Boolean getEngineTypeDefault() {
            return engineTypeDefault;
        }

        public void setEngineTypeDefault(Boolean engineTypeDefault) {
            this.engineTypeDefault = engineTypeDefault;
        }

        public @Nullable String getStpStatus() {
            return stpStatus;
        }

        public void setStpStatus(String stpStatus) {
            this.stpStatus = stpStatus;
        }

        public @Nullable Boolean getWindowstateSupported() {
            return windowstateSupported;
        }

        public void setWindowstateSupported(Boolean windowstateSupported) {
            this.windowstateSupported = windowstateSupported;
        }

        public @Nullable String getDashboardUrl() {
            return dashboardUrl;
        }

        public void setDashboardUrl(String dashboardUrl) {
            this.dashboardUrl = dashboardUrl;
        }

        public @Nullable Boolean getVhrRequested() {
            return vhrRequested;
        }

        public void setVhrRequested(Boolean vhrRequested) {
            this.vhrRequested = vhrRequested;
        }

        public @Nullable Boolean getVsrRequested() {
            return vsrRequested;
        }

        public void setVsrRequested(Boolean vsrRequested) {
            this.vsrRequested = vsrRequested;
        }

        public @Nullable Boolean getVhrConfigAvailable() {
            return vhrConfigAvailable;
        }

        public void setVhrConfigAvailable(Boolean vhrConfigAvailable) {
            this.vhrConfigAvailable = vhrConfigAvailable;
        }

        public @Nullable Boolean getVerifiedByDealer() {
            return verifiedByDealer;
        }

        public void setVerifiedByDealer(Boolean verifiedByDealer) {
            this.verifiedByDealer = verifiedByDealer;
        }

        public @Nullable Boolean getVhr2() {
            return vhr2;
        }

        public void setVhr2(Boolean vhr2) {
            this.vhr2 = vhr2;
        }

        public @Nullable Boolean getRoleEnabled() {
            return roleEnabled;
        }

        public void setRoleEnabled(Boolean roleEnabled) {
            this.roleEnabled = roleEnabled;
        }

        public @Nullable Boolean getIsEL2Vehicle() {
            return isEL2Vehicle;
        }

        public void setIsEL2Vehicle(Boolean isEL2Vehicle) {
            this.isEL2Vehicle = isEL2Vehicle;
        }

        public @Nullable Boolean getWorkshopMode() {
            return workshopMode;
        }

        public void setWorkshopMode(Boolean workshopMode) {
            this.workshopMode = workshopMode;
        }

        public @Nullable Boolean getHiddenUserProfiles() {
            return hiddenUserProfiles;
        }

        public void setHiddenUserProfiles(Boolean hiddenUserProfiles) {
            this.hiddenUserProfiles = hiddenUserProfiles;
        }

        public @Nullable Object getMobileKeyActivated() {
            return mobileKeyActivated;
        }

        public void setMobileKeyActivated(Object mobileKeyActivated) {
            this.mobileKeyActivated = mobileKeyActivated;
        }

        public @Nullable String getEnrollmentType() {
            return enrollmentType;
        }

        public void setEnrollmentType(String enrollmentType) {
            this.enrollmentType = enrollmentType;
        }

        public @Nullable Boolean getOcu3Low() {
            return ocu3Low;
        }

        public void setOcu3Low(Boolean ocu3Low) {
            this.ocu3Low = ocu3Low;
        }

        public @Nullable List<PackageService> getPackageServices() {
            return packageServices;
        }

        public void setPackageServices(List<PackageService> packageServices) {
            this.packageServices = packageServices;
        }

        public @Nullable Boolean getFullyEnrolled() {
            return fullyEnrolled;
        }

        public void setFullyEnrolled(Boolean fullyEnrolled) {
            this.fullyEnrolled = fullyEnrolled;
        }

        public @Nullable Boolean getSecondaryUser() {
            return secondaryUser;
        }

        public void setSecondaryUser(Boolean secondaryUser) {
            this.secondaryUser = secondaryUser;
        }

        public @Nullable Boolean getFleet() {
            return fleet;
        }

        public void setFleet(Boolean fleet) {
            this.fleet = fleet;
        }

        public @Nullable Boolean getTouareg() {
            return touareg;
        }

        public void setTouareg(Boolean touareg) {
            this.touareg = touareg;
        }

        public @Nullable Boolean getIceSupported() {
            return iceSupported;
        }

        public void setIceSupported(Boolean iceSupported) {
            this.iceSupported = iceSupported;
        }

        public @Nullable Boolean getFlightMode() {
            return flightMode;
        }

        public void setFlightMode(Boolean flightMode) {
            this.flightMode = flightMode;
        }

        public @Nullable Boolean getEsimCompatible() {
            return esimCompatible;
        }

        public void setEsimCompatible(Boolean esimCompatible) {
            this.esimCompatible = esimCompatible;
        }

        public @Nullable Boolean getDkyenabled() {
            return dkyenabled;
        }

        public void setDkyenabled(Boolean dkyenabled) {
            this.dkyenabled = dkyenabled;
        }

        public @Nullable Object getSmartCardKeyActivated() {
            return smartCardKeyActivated;
        }

        public void setSmartCardKeyActivated(Object smartCardKeyActivated) {
            this.smartCardKeyActivated = smartCardKeyActivated;
        }

        public @Nullable Boolean getSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
        }

        public @Nullable Boolean getDefaultCar() {
            return defaultCar;
        }

        public void setDefaultCar(Boolean defaultCar) {
            this.defaultCar = defaultCar;
        }

        public @Nullable Boolean getVwConnectPowerLayerAvailable() {
            return vwConnectPowerLayerAvailable;
        }

        public void setVwConnectPowerLayerAvailable(Boolean vwConnectPowerLayerAvailable) {
            this.vwConnectPowerLayerAvailable = vwConnectPowerLayerAvailable;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("vin", vin).append("name", name).append("expired", expired)
                    .append("model", model).append("modelCode", modelCode).append("modelYear", modelYear)
                    .append("imageUrl", imageUrl)
                    .append("vehicleSpecificFallbackImageUrl", vehicleSpecificFallbackImageUrl)
                    .append("modelSpecificFallbackImageUrl", modelSpecificFallbackImageUrl)
                    .append("defaultImageUrl", defaultImageUrl).append("vehicleBrand", vehicleBrand)
                    .append("enrollmentDate", enrollmentDate).append("deviceOCU1", deviceOCU1)
                    .append("deviceOCU2", deviceOCU2).append("deviceMIB", deviceMIB)
                    .append("engineTypeCombustian", engineTypeCombustian)
                    .append("engineTypeHybridOCU1", engineTypeHybridOCU1)
                    .append("engineTypeHybridOCU2", engineTypeHybridOCU2)
                    .append("engineTypeElectric", engineTypeElectric).append("engineTypeCNG", engineTypeCNG)
                    .append("engineTypeDefault", engineTypeDefault).append("stpStatus", stpStatus)
                    .append("windowstateSupported", windowstateSupported).append("dashboardUrl", dashboardUrl)
                    .append("vhrRequested", vhrRequested).append("vsrRequested", vsrRequested)
                    .append("vhrConfigAvailable", vhrConfigAvailable).append("verifiedByDealer", verifiedByDealer)
                    .append("vhr2", vhr2).append("roleEnabled", roleEnabled).append("isEL2Vehicle", isEL2Vehicle)
                    .append("workshopMode", workshopMode).append("hiddenUserProfiles", hiddenUserProfiles)
                    .append("mobileKeyActivated", mobileKeyActivated).append("enrollmentType", enrollmentType)
                    .append("ocu3Low", ocu3Low).append("packageServices", packageServices)
                    .append("fullyEnrolled", fullyEnrolled).append("secondaryUser", secondaryUser)
                    .append("fleet", fleet).append("touareg", touareg).append("iceSupported", iceSupported)
                    .append("flightMode", flightMode).append("esimCompatible", esimCompatible)
                    .append("dkyenabled", dkyenabled).append("smartCardKeyActivated", smartCardKeyActivated)
                    .append("selected", selected).append("defaultCar", defaultCar)
                    .append("vwConnectPowerLayerAvailable", vwConnectPowerLayerAvailable).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(fleet).append(vhrRequested).append(vhr2).append(fullyEnrolled)
                    .append(verifiedByDealer).append(enrollmentDate).append(mobileKeyActivated).append(ocu3Low)
                    .append(deviceMIB).append(engineTypeCombustian).append(modelCode).append(vin).append(model)
                    .append(selected).append(windowstateSupported).append(defaultCar).append(isEL2Vehicle)
                    .append(deviceOCU1).append(hiddenUserProfiles).append(deviceOCU2).append(modelYear)
                    .append(engineTypeDefault).append(flightMode).append(defaultImageUrl).append(stpStatus)
                    .append(touareg).append(smartCardKeyActivated).append(name).append(vsrRequested)
                    .append(enrollmentType).append(workshopMode).append(roleEnabled).append(engineTypeElectric)
                    .append(modelSpecificFallbackImageUrl).append(vehicleBrand).append(expired)
                    .append(engineTypeHybridOCU2).append(vwConnectPowerLayerAvailable).append(imageUrl)
                    .append(engineTypeHybridOCU1).append(dashboardUrl).append(engineTypeCNG).append(dkyenabled)
                    .append(iceSupported).append(vhrConfigAvailable).append(secondaryUser).append(packageServices)
                    .append(esimCompatible).append(vehicleSpecificFallbackImageUrl).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof CompleteVehicleJson) == false) {
                return false;
            }
            CompleteVehicleJson rhs = ((CompleteVehicleJson) other);
            return new EqualsBuilder().append(fleet, rhs.fleet).append(vhrRequested, rhs.vhrRequested)
                    .append(vhr2, rhs.vhr2).append(fullyEnrolled, rhs.fullyEnrolled)
                    .append(verifiedByDealer, rhs.verifiedByDealer).append(enrollmentDate, rhs.enrollmentDate)
                    .append(mobileKeyActivated, rhs.mobileKeyActivated).append(ocu3Low, rhs.ocu3Low)
                    .append(deviceMIB, rhs.deviceMIB).append(engineTypeCombustian, rhs.engineTypeCombustian)
                    .append(modelCode, rhs.modelCode).append(vin, rhs.vin).append(model, rhs.model)
                    .append(selected, rhs.selected).append(windowstateSupported, rhs.windowstateSupported)
                    .append(defaultCar, rhs.defaultCar).append(isEL2Vehicle, rhs.isEL2Vehicle)
                    .append(deviceOCU1, rhs.deviceOCU1).append(hiddenUserProfiles, rhs.hiddenUserProfiles)
                    .append(deviceOCU2, rhs.deviceOCU2).append(modelYear, rhs.modelYear)
                    .append(engineTypeDefault, rhs.engineTypeDefault).append(flightMode, rhs.flightMode)
                    .append(defaultImageUrl, rhs.defaultImageUrl).append(stpStatus, rhs.stpStatus)
                    .append(touareg, rhs.touareg).append(smartCardKeyActivated, rhs.smartCardKeyActivated)
                    .append(name, rhs.name).append(vsrRequested, rhs.vsrRequested)
                    .append(enrollmentType, rhs.enrollmentType).append(workshopMode, rhs.workshopMode)
                    .append(roleEnabled, rhs.roleEnabled).append(engineTypeElectric, rhs.engineTypeElectric)
                    .append(modelSpecificFallbackImageUrl, rhs.modelSpecificFallbackImageUrl)
                    .append(vehicleBrand, rhs.vehicleBrand).append(expired, rhs.expired)
                    .append(engineTypeHybridOCU2, rhs.engineTypeHybridOCU2)
                    .append(vwConnectPowerLayerAvailable, rhs.vwConnectPowerLayerAvailable)
                    .append(imageUrl, rhs.imageUrl).append(engineTypeHybridOCU1, rhs.engineTypeHybridOCU1)
                    .append(dashboardUrl, rhs.dashboardUrl).append(engineTypeCNG, rhs.engineTypeCNG)
                    .append(dkyenabled, rhs.dkyenabled).append(iceSupported, rhs.iceSupported)
                    .append(vhrConfigAvailable, rhs.vhrConfigAvailable).append(secondaryUser, rhs.secondaryUser)
                    .append(packageServices, rhs.packageServices).append(esimCompatible, rhs.esimCompatible)
                    .append(vehicleSpecificFallbackImageUrl, rhs.vehicleSpecificFallbackImageUrl).isEquals();
        }
    }

    public class PackageService {

        private @Nullable String packageServiceId;
        private @Nullable String propertyKeyReference;
        private @Nullable String packageServiceName;
        private @Nullable String trackingName;
        private @Nullable String activationDate;
        private @Nullable String expirationDate;
        private @Nullable Boolean expired;
        private @Nullable Boolean expireInAMonth;
        private @Nullable String packageType;
        private @Nullable String enrollmentPackageType;

        /**
         * No args constructor for use in serialization
         *
         */
        public PackageService() {
        }

        public @Nullable String getPackageServiceId() {
            return packageServiceId;
        }

        public void setPackageServiceId(String packageServiceId) {
            this.packageServiceId = packageServiceId;
        }

        public @Nullable String getPropertyKeyReference() {
            return propertyKeyReference;
        }

        public void setPropertyKeyReference(String propertyKeyReference) {
            this.propertyKeyReference = propertyKeyReference;
        }

        public @Nullable String getPackageServiceName() {
            return packageServiceName;
        }

        public void setPackageServiceName(String packageServiceName) {
            this.packageServiceName = packageServiceName;
        }

        public @Nullable String getTrackingName() {
            return trackingName;
        }

        public void setTrackingName(String trackingName) {
            this.trackingName = trackingName;
        }

        public @Nullable String getActivationDate() {
            return activationDate;
        }

        public void setActivationDate(String activationDate) {
            this.activationDate = activationDate;
        }

        public @Nullable String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public @Nullable Boolean getExpired() {
            return expired;
        }

        public void setExpired(Boolean expired) {
            this.expired = expired;
        }

        public @Nullable Boolean getExpireInAMonth() {
            return expireInAMonth;
        }

        public void setExpireInAMonth(Boolean expireInAMonth) {
            this.expireInAMonth = expireInAMonth;
        }

        public @Nullable String getPackageType() {
            return packageType;
        }

        public void setPackageType(String packageType) {
            this.packageType = packageType;
        }

        public @Nullable String getEnrollmentPackageType() {
            return enrollmentPackageType;
        }

        public void setEnrollmentPackageType(String enrollmentPackageType) {
            this.enrollmentPackageType = enrollmentPackageType;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("packageServiceId", packageServiceId)
                    .append("propertyKeyReference", propertyKeyReference)
                    .append("packageServiceName", packageServiceName).append("trackingName", trackingName)
                    .append("activationDate", activationDate).append("expirationDate", expirationDate)
                    .append("expired", expired).append("expireInAMonth", expireInAMonth)
                    .append("packageType", packageType).append("enrollmentPackageType", enrollmentPackageType)
                    .toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(trackingName).append(packageServiceName).append(packageServiceId)
                    .append(expired).append(expireInAMonth).append(propertyKeyReference).append(enrollmentPackageType)
                    .append(activationDate).append(packageType).append(expirationDate).toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof PackageService) == false) {
                return false;
            }
            PackageService rhs = ((PackageService) other);
            return new EqualsBuilder().append(trackingName, rhs.trackingName)
                    .append(packageServiceName, rhs.packageServiceName).append(packageServiceId, rhs.packageServiceId)
                    .append(expired, rhs.expired).append(expireInAMonth, rhs.expireInAMonth)
                    .append(propertyKeyReference, rhs.propertyKeyReference)
                    .append(enrollmentPackageType, rhs.enrollmentPackageType).append(activationDate, rhs.activationDate)
                    .append(packageType, rhs.packageType).append(expirationDate, rhs.expirationDate).isEquals();
        }
    }

    public class VehicleDetails {

        private @Nullable List<String> lastConnectionTimeStamp;
        private @Nullable String distanceCovered;
        private @Nullable String range;
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

        /**
         *
         * @param oilInspectionData
         * @param distanceCovered
         * @param range
         * @param serviceInspectionData
         * @param lastConnectionTimeStamp
         * @param showOil
         * @param flightMode
         * @param showService
         */
        public VehicleDetails(List<String> lastConnectionTimeStamp, String distanceCovered, String range,
                String serviceInspectionData, String oilInspectionData, Boolean showOil, Boolean showService,
                Boolean flightMode) {
            super();
            this.lastConnectionTimeStamp = lastConnectionTimeStamp;
            this.distanceCovered = distanceCovered;
            this.range = range;
            this.serviceInspectionData = serviceInspectionData;
            this.oilInspectionData = oilInspectionData;
            this.showOil = showOil;
            this.showService = showService;
            this.flightMode = flightMode;
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

        public @Nullable String getRange() {
            return range;
        }

        public void setRange(String range) {
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
