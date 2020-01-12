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
package org.openhab.binding.vwcarnet.internal.action;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.binding.ThingActions;
import org.eclipse.smarthome.core.thing.binding.ThingActionsScope;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.vwcarnet.internal.handler.VehicleHandler;
import org.openhab.core.automation.annotation.ActionInput;
import org.openhab.core.automation.annotation.RuleAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@VehicleAction } class is responsible to call corresponding
 * action on Vehicle Handler
 *
 * @author Jan Gustafsson - Initial contribution
 */
@ThingActionsScope(name = "vwcarnet")
@NonNullByDefault
public class VWCarNetActions implements ThingActions {

    private final static Logger logger = LoggerFactory.getLogger(VWCarNetActions.class);

    private @Nullable VehicleHandler handler;

    public VWCarNetActions() {
        logger.info("VW Car Net actions service instanciated");
    }

    @Override
    public void setThingHandler(@Nullable ThingHandler handler) {
        if (handler instanceof VehicleHandler) {
            this.handler = (VehicleHandler) handler;
        }
    }

    @Override
    public @Nullable ThingHandler getThingHandler() {
        return this.handler;
    }

    @RuleAction(label = "VW Car Net : Heater Start", description = "Starts car heater")
    public void heaterStartCommand() {
        logger.debug("heaterStartCommand called");
        if (handler != null) {
            handler.actionHeater(true);
        } else {
            logger.warn("VWCarNet Action service ThingHandler is null!");
        }
    }

    public static void heaterStartCommand(@Nullable ThingActions actions) {
        if (actions instanceof VWCarNetActions) {
            ((VWCarNetActions) actions).heaterStartCommand();
        } else {
            throw new IllegalArgumentException("Instance is not an VWCarNetThingActionsService class.");
        }
    }

    @RuleAction(label = "VW Car Net : Preclimatization Start", description = "Starts car heater")
    public void preclimatizationStartCommand() {
        logger.debug("preclimatizationStartCommand called");
        if (handler != null) {
            handler.actionPreclimatization(true);
        } else {
            logger.warn("VWCarNet Action service ThingHandler is null!");
        }
    }

    public static void preclimatizationStartCommand(@Nullable ThingActions actions) {
        if (actions instanceof VWCarNetActions) {
            ((VWCarNetActions) actions).preclimatizationStartCommand();
        } else {
            throw new IllegalArgumentException("Instance is not an VWCarNetThingActionsService class.");
        }
    }

    @RuleAction(label = "VW Car Net : Heater Stop", description = "Stops car heater")
    public void heaterStopCommand() {
        logger.debug("heaterStopCommand called");
        if (handler != null) {
            handler.actionHeater(false);
        } else {
            logger.warn("VWCarNet Action service ThingHandler is null!");
        }
    }

    public static void heaterStopCommand(@Nullable ThingActions actions) {
        if (actions instanceof VWCarNetActions) {
            ((VWCarNetActions) actions).heaterStopCommand();
        } else {
            throw new IllegalArgumentException("Instance is not an VWCarNetThingActionsService class.");
        }
    }

    @RuleAction(label = "VW Car Net : Preclimatization Stop", description = "Stops car heater")
    public void preclimatizationStopCommand() {
        logger.debug("preclimatizationStopCommand called");
        if (handler != null) {
            handler.actionPreclimatization(false);
        } else {
            logger.warn("VWCarNet Action service ThingHandler is null!");
        }
    }

    public static void preclimatizationStopCommand(@Nullable ThingActions actions) {
        if (actions instanceof VWCarNetActions) {
            ((VWCarNetActions) actions).preclimatizationStopCommand();
        } else {
            throw new IllegalArgumentException("Instance is not an VWCarNetThingActionsService class.");
        }
    }

    @RuleAction(label = "VW Car Net : Honk-blink", description = "Activates the horn and or lights of the car")
    public void honkBlinkCommand(@ActionInput(name = "honk", label = "Honk") Boolean honk,
            @ActionInput(name = "blink", label = "Blink") Boolean blink) {
        logger.debug("honkBlinkCommand called");
        if (handler != null) {
            handler.actionHonkBlink(honk, blink);
        } else {
            logger.warn("VWCarNet Action service ThingHandler is null!");
        }
    }

    public static void honkBlinkCommand(@Nullable ThingActions actions, Boolean honk, Boolean blink) {
        if (actions instanceof VWCarNetActions) {
            ((VWCarNetActions) actions).honkBlinkCommand(honk, blink);
        } else {
            throw new IllegalArgumentException("Instance is not an VWCarNetThingActionsService class.");
        }
    }
}
