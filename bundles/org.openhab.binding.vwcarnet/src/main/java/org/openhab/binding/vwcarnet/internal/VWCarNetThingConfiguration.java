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
package org.openhab.binding.vwcarnet.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.vwcarnet.internal.handler.VWCarNetThingHandler;

/**
 * Configuration class for {@link VWCarNetThingHandler}.
 *
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class VWCarNetThingConfiguration {
    public static final String DEVICE_ID_LABEL = "deviceId";

    private @Nullable String deviceId;

    public @Nullable String getDeviceId() {
        // Make sure device id is normalized, i.e. replace all non character/digits with empty string
        return deviceId.replaceAll("[^a-zA-Z0-9]+", "");
    }
}
