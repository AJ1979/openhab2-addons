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
package org.openhab.binding.vwcarnet.internal.handler;

import static org.openhab.binding.vwcarnet.internal.VWCarNetBindingConstants.*;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetThingJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetUserPresencesJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetUserPresencesJSON.UserTracking;

/**
 * Handler for the User Presence Device thing type that VWCarNet provides.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class VWCarNetUserPresenceThingHandler extends VWCarNetThingHandler {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES = Collections.singleton(THING_TYPE_USERPRESENCE);

    public VWCarNetUserPresenceThingHandler(Thing thing) {
        super(thing);
    }

    @Override
    public synchronized void update(@Nullable VWCarNetThingJSON thing) {
        logger.debug("update on thing: {}", thing);
        updateStatus(ThingStatus.ONLINE);
        if (getThing().getThingTypeUID().equals(THING_TYPE_USERPRESENCE)) {
            VWCarNetUserPresencesJSON obj = (VWCarNetUserPresencesJSON) thing;
            if (obj != null) {
                updateUserPresenceState(obj);
            }
        } else {
            logger.warn("Can't handle this thing typeuid: {}", getThing().getThingTypeUID());
        }
    }

    private void updateUserPresenceState(VWCarNetUserPresencesJSON userPresenceJSON) {
        ChannelUID cuid = new ChannelUID(getThing().getUID(), CHANNEL_USER_NAME);
        UserTracking userTracking = userPresenceJSON.getData().getInstallation().getUserTrackings().get(0);
        updateState(cuid, new StringType(userTracking.getName()));
        cuid = new ChannelUID(getThing().getUID(), CHANNEL_USER_LOCATION_STATUS);
        if (userTracking.getCurrentLocationName() == null) {
            updateState(cuid, new StringType(userTracking.getCurrentLocationId()));
        } else {
            updateState(cuid, new StringType(userTracking.getCurrentLocationName()));
        }
        cuid = new ChannelUID(getThing().getUID(), CHANNEL_STATUS);
        updateState(cuid, new StringType(userTracking.getStatus()));
        cuid = new ChannelUID(getThing().getUID(), CHANNEL_WEBACCOUNT);
        updateState(cuid, new StringType(userTracking.getWebAccount()));
        updateTimeStamp(userTracking.getCurrentLocationTimestamp());
        cuid = new ChannelUID(getThing().getUID(), CHANNEL_USER_DEVICE_NAME);
        updateState(cuid, new StringType(userTracking.getDeviceName()));
        super.update(userPresenceJSON);
    }
}
