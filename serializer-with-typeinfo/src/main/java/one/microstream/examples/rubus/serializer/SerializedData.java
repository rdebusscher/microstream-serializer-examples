package one.microstream.examples.rubus.serializer;

/*-
 * #%L
 * serializer-with-typeinfo
 * %%
 * Copyright (C) 2022 MicroStream Software
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import java.util.Map;

public class SerializedData {

    private Map<Long, Class<?>> typeInfo;
    private byte[] bytes;

    public SerializedData(Map<Long, Class<?>> typeInfo, byte[] bytes) {
        this.typeInfo = typeInfo;
        this.bytes = bytes;
    }

    public Map<Long, Class<?>> getTypeInfo() {
        return typeInfo;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
