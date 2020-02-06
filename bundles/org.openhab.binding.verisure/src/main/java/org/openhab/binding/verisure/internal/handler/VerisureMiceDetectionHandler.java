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
package org.openhab.binding.verisure.internal.handler;

import static org.openhab.binding.verisure.internal.VerisureBindingConstants.*;

import java.util.Collections;
import java.util.Set;

import javax.measure.quantity.Temperature;
import javax.measure.quantity.Time;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.QuantityType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.library.unit.SIUnits;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.UnDefType;
import org.openhab.binding.verisure.internal.model.VerisureMiceDetection;
import org.openhab.binding.verisure.internal.model.VerisureMiceDetection.Detection;
import org.openhab.binding.verisure.internal.model.VerisureMiceDetection.Mouse;
import org.openhab.binding.verisure.internal.model.VerisureThing;

/**
 * Handler for the Mice Detection thing type that Verisure provides.
 *
 * @author Jan Gustafsson - Initial contribution
 *
 */
@NonNullByDefault
public class VerisureMiceDetectionHandler extends VerisureThingHandler {

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES = Collections.singleton(THING_TYPE_MICE_DETECTION);

    public VerisureMiceDetectionHandler(Thing thing) {
        super(thing);
    }

    @Override
    public synchronized void update(@Nullable VerisureThing thing) {
        logger.debug("update on thing: {}", thing);
        if (thing != null) {
            updateStatus(ThingStatus.ONLINE);
            if (getThing().getThingTypeUID().equals(THING_TYPE_MICE_DETECTION)) {
                VerisureMiceDetection obj = (VerisureMiceDetection) thing;
                updateMiceDetectionState(obj);
            } else {
                logger.warn("Can't handle this thing typeuid: {}", getThing().getThingTypeUID());
            }
        } else {
            logger.warn("Thing JSON is null: {}", getThing().getThingTypeUID());
        }
    }

    private void updateMiceDetectionState(VerisureMiceDetection miceDetectionJSON) {
        Mouse mouse = miceDetectionJSON.getData().getInstallation().getMice().get(0);
        if (mouse != null) {
            getThing().getChannels().stream().map(Channel::getUID).filter(channelUID -> isLinked(channelUID)
                    && !channelUID.getId().equals("timestamp") && !channelUID.getId().equals("temperatureTimestamp"))
                    .forEach(channelUID -> {
                        State state = getValue(channelUID.getId(), miceDetectionJSON, mouse);
                        updateState(channelUID, state);
                    });
            if (mouse.getDetections().size() != 0) {
                updateTimeStamp(mouse.getDetections().get(0).getNodeTime());
            }
            updateTimeStamp(miceDetectionJSON.getTemperatureTime(), CHANNEL_TEMPERATURE_TIMESTAMP);
            super.update(miceDetectionJSON);
        } else {
            logger.debug("Mouse is null for thing {}", thing.getUID());
        }
    }

    public State getValue(String channelId, VerisureMiceDetection miceDetectionJSON, Mouse mouse) {
        switch (channelId) {
            case CHANNEL_COUNT_LATEST_DETECTION:
                if (mouse.getDetections().size() == 0) {
                    return new DecimalType(0);
                } else {
                    return new DecimalType(mouse.getDetections().get(0).getCount());
                }
            case CHANNEL_COUNT_LAST_24_HOURS:
                if (mouse.getDetections().size() == 0) {
                    return new DecimalType(0);
                } else {
                    return new DecimalType(mouse.getDetections().stream().mapToInt(Detection::getCount).sum());
                }
            case CHANNEL_DURATION_LATEST_DETECTION:
                if (mouse.getDetections().size() == 0) {
                    return new QuantityType<Time>(0, SmartHomeUnits.SECOND);
                } else {
                    return new QuantityType<Time>(mouse.getDetections().get(0).getDuration(), SmartHomeUnits.SECOND);
                }
            case CHANNEL_DURATION_LAST_24_HOURS:
                if (mouse.getDetections().size() == 0) {
                    return new QuantityType<Time>(0, SmartHomeUnits.SECOND);
                } else {
                    return new QuantityType<Time>(mouse.getDetections().stream().mapToInt(Detection::getDuration).sum(),
                            SmartHomeUnits.SECOND);
                }
            case CHANNEL_LOCATION:
                String location = mouse.getDevice().getArea();
                return location != null ? new StringType(location) : UnDefType.NULL;
            case CHANNEL_TEMPERATURE:
                double temperature = miceDetectionJSON.getTemperatureValue();
                return temperature != VerisureMiceDetection.UNDEFINED
                        ? new QuantityType<Temperature>(temperature, SIUnits.CELSIUS)
                        : UnDefType.UNDEF;
        }
        return UnDefType.UNDEF;
    }
}