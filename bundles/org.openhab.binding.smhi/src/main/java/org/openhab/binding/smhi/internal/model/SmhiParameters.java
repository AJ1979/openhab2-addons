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
package org.openhab.binding.smhi.internal.model;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The {@link SmhiParameters} is the Java class used to map the JSON response to an SMHI
 * request.
 *
 * @author Michael Parment - Initial contribution
 */
@NonNullByDefault
public class SmhiParameters {
    private @Nullable String unit;

    private @Nullable String levelType;

    public Double @Nullable [] values;

    private @Nullable String level;

    public @Nullable String name;

    public @Nullable String getUnit() {
        return unit;
    }

    public @Nullable String getLevelType() {
        return levelType;
    }

    public Double @Nullable [] getValues() {
        return values;
    }

    public @Nullable String getLevel() {
        return level;
    }

    public @Nullable String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ClassPojo [unit = " + unit + ", levelType = " + levelType + ", values = " + values + ", level = "
                + level + ", name = " + name + "]";
    }
}
