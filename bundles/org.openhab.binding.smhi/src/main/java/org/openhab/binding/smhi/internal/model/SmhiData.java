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

import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * The {@link SmhiData} is the Java class used to map the JSON response to an SMHI
 * request.
 *
 * @author Michael Parment - Initial contribution
 */
@NonNullByDefault
public class SmhiData {
    private @Nullable List<SmhiTimeSeries> timeSeries;

    private @Nullable String referenceTime;

    private @Nullable String approvedTime;

    private @Nullable SmhiGeometry geometry;

    public @Nullable List<SmhiTimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public @Nullable String getReferenceTime() {
        return referenceTime;
    }

    public @Nullable String getApprovedTime() {
        return approvedTime;
    }

    public @Nullable SmhiGeometry getGeometry() {
        return geometry;
    }

    @Override
    public String toString() {
        return "ClassPojo [timeSeries = " + timeSeries + ", referenceTime = " + referenceTime + ", approvedTime = "
                + approvedTime + ", geometry = " + geometry + "]";
    }
}
