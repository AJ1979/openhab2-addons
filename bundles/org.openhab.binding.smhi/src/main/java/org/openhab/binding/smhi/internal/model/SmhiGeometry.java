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
 * The {@link smhiGeometry} is the Java class used to map the JSON response to an SMHI
 * request.
 *
 * @author Michael Parment - Initial contribution
 */
@NonNullByDefault
public class SmhiGeometry {
    private @Nullable String type;

    private String @Nullable [][] coordinates;

    public @Nullable String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    public String @Nullable [][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@Nullable String[][] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "ClassPojo [type = " + type + ", coordinates = " + coordinates + "]";
    }
}
