/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.supla.internal.api;

import org.openhab.binding.supla.internal.supla.entities.SuplaServerInfo;

import java.util.Optional;

public interface ServerInfoManager {
    Optional<SuplaServerInfo> obtainServerInfo();
}
