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
package org.openhab.binding.vwweconnect.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.vwweconnect.internal.handler.VWWeConnectBridgeHandler;

/**
 * Configuration class for {@link VWWeConnectBridgeHandler} bridge used to connect to the
 * Verisure MyPage.
 *
 * @author Jarle Hjortland - Initial contribution
 */
@NonNullByDefault
public class VWWeConnectBridgeConfiguration {
    public @Nullable String username;
    public @Nullable String password;
    public @Nullable String pin;
    public int refresh;
}
