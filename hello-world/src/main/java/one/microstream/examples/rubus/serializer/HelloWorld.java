package one.microstream.examples.rubus.serializer;

/*-
 * #%L
 * hello-world
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

import java.util.Date;

public class HelloWorld {

    public static void main(String[] args) throws InterruptedException {
        SerializerFoundation<?> foundation = SerializerFoundation.New()
                .registerEntityTypes(Data.class);
        Serializer<byte[]> serializer = Serializer.Bytes(foundation);

        Data instance = new Data("Hello MicroStream", new Date());
        System.out.printf("Object created as : %s%n", instance);


        byte[] data = serializer.serialize(instance);
        System.out.printf("Serialized length : %s%n", data.length);

        Thread.sleep(2000);  // So that it is clear that we deserialise 'old' data


        Data restored = serializer.deserialize(data);
        System.out.printf("Current timestamp : %s%n", new Date());
        System.out.printf("Instance restored : %s%n", restored);
    }
}
