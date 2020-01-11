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
package org.openhab.binding.vwcarnet.internal.model;

import java.math.BigDecimal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A base Vehicle.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class BaseVehicle {
    public static final int UNDEFINED = -1;

    protected String deviceId = "";
    protected @Nullable String name;
    protected @Nullable String homeLocation;
    protected @Nullable String status;
    protected @Nullable String siteName;
    protected @Nullable BigDecimal siteId;

    public BaseVehicle() {
        super();
    }

    /**
     *
     * @return
     *         The status
     */
    public @Nullable String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *            The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the name
     */
    public @Nullable String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(@Nullable String deviceId) {
        // Make sure device id is normalized, i.e. replace all non character/digits with empty string
        this.deviceId = deviceId.replaceAll("[^a-zA-Z0-9]+", "");
    }

    /**
     * @return the homeLocation
     */
    public @Nullable String getLocation() {
        return homeLocation;
    }

    /**
     * @param homeLocation the homeLocation to set
     */
    public void setLocation(@Nullable String location) {
        this.homeLocation = location;
    }

    public @Nullable String getSiteName() {
        return siteName;
    }

    public void setSiteName(@Nullable String siteName) {
        this.siteName = siteName;
    }

    public @Nullable BigDecimal getSiteId() {
        return siteId;
    }

    public void setSiteId(@Nullable BigDecimal siteId) {
        this.siteId = siteId;
    }

    @SuppressWarnings("null")
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
        result = prime * result + ((homeLocation == null) ? 0 : homeLocation.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((siteName == null) ? 0 : siteName.hashCode());
        result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BaseVehicle)) {
            return false;
        }

        BaseVehicle other = (BaseVehicle) obj;
        if (deviceId == null) {
            if (other.deviceId != null) {
                return false;
            }
        } else if (deviceId != null && !deviceId.equals(other.deviceId)) {
            return false;
        }

        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (name != null && !name.equals(other.name)) {
            return false;
        }

        if (homeLocation == null) {
            if (other.homeLocation != null) {
                return false;
            }
        } else if (homeLocation != null && !homeLocation.equals(other.homeLocation)) {
            return false;
        }

        if (status == null) {
            if (other.status != null) {
                return false;
            }
        } else if (status != null && !status.equals(other.status)) {
            return false;
        }

        if (siteName == null) {
            if (other.siteName != null) {
                return false;
            }
        } else if (siteName != null && !siteName.equals(other.siteName)) {
            return false;
        }

        if (siteId != other.siteId) {
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VerisureBaseThingJSON [");
        if (name != null) {
            builder.append("name=");
            builder.append(name);
            builder.append(", ");
        }
        if (deviceId != null) {
            builder.append(", deviceId=");
            builder.append(deviceId);
        }
        if (homeLocation != null) {
            builder.append(", homeLocation=");
            builder.append(homeLocation);
        }
        if (status != null) {
            builder.append(", status=");
            builder.append(status);
        }
        if (siteName != null) {
            builder.append(", siteName=");
            builder.append(siteName);
        }
        builder.append(", siteId=");
        builder.append(siteId);
        builder.append("]");
        return builder.toString();
    }
}
