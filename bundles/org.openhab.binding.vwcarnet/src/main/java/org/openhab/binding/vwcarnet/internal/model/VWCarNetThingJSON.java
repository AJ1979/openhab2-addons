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
 * The base identifer of all Verisure response objects.
 *
 * @author Jarle Hjortland - Initial contribution
 *
 */
@NonNullByDefault
public interface VWCarNetThingJSON {

    String getDeviceId();

    void setDeviceId(@Nullable String deviceId);

    @Nullable
    String getLocation();

    void setSiteName(@Nullable String siteName);

    @Nullable
    String getSiteName();

    void setSiteId(@Nullable BigDecimal siteId);

    @Nullable
    BigDecimal getSiteId();
}
