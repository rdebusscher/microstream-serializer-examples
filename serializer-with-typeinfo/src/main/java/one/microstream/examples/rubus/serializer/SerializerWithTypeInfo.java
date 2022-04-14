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

import one.microstream.persistence.binary.util.Serializer;
import one.microstream.persistence.binary.util.SerializerFoundation;
import one.microstream.persistence.types.PersistenceTypeRegistry;

import java.util.Map;
import java.util.TreeMap;

public final class SerializerWithTypeInfo {

    private SerializerWithTypeInfo() {
    }

    public static SerializedData serialize(Object instance) throws Exception {
        SerializerFoundation<?> foundation = SerializerFoundation.New();
        long currentTypeId;
        byte[] data;
        try (Serializer<byte[]> serializer = Serializer.Bytes(foundation)) {

            // So that all default classes are in the TypeRegistry, including the ones that are added during the Lazy init.
            serializer.serialize(new Object());

            currentTypeId = foundation.getTypeIdProvider().currentTypeId();

            data = serializer.serialize(instance);
        }

        Map<Long, Class<?>> types = new TreeMap<>();
        foundation.getTypeRegistry().iteratePerIds(types::put);

        types.entrySet().removeIf(entry -> entry.getKey() <= currentTypeId);

        return new SerializedData(types, data);
    }

    public static <T> T deserialize(SerializedData data) throws Exception {
        SerializerFoundation<?> foundation = SerializerFoundation.New();
        PersistenceTypeRegistry typeRegistry = foundation.getTypeRegistry();
        data.getTypeInfo().forEach(typeRegistry::registerType);
        data.getTypeInfo().values().forEach(foundation::registerEntityType);

        try (Serializer<byte[]> serializer = Serializer.Bytes(foundation)) {

            return serializer.deserialize(data.getBytes());
        }
    }
}
