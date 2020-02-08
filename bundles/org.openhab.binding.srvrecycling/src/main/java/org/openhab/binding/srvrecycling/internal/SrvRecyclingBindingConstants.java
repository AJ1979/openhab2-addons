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
package org.openhab.binding.srvrecycling.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link SrvRecyclingBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class SrvRecyclingBindingConstants {

    private static final String BINDING_ID = "srvrecycling";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_PICKUPS = new ThingTypeUID(BINDING_ID, "pickups");

    // List of all Channel ids
    public static final String CHANNEL_1 = "channel1";
}
