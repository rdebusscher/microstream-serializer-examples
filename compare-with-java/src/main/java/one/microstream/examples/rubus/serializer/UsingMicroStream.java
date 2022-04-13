package one.microstream.examples.rubus.serializer;

/*-
 * #%L
 * compare-with-java
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


import one.microstream.examples.rubus.serializer.model.Address;
import one.microstream.examples.rubus.serializer.model.Person;
import one.microstream.persistence.binary.util.Serializer;
import one.microstream.persistence.binary.util.SerializerFoundation;

import java.util.ArrayList;
import java.util.List;

public class UsingMicroStream {

    public static void main(String[] args) {

        // Configure Serializer
        SerializerFoundation<?> foundation = SerializerFoundation.New()
                .registerEntityTypes(Person.class, Address.class);
        Serializer<byte[]> serializer = Serializer.Bytes(foundation);

        List<Person> people = PersonUtil.newPersons();
        // Perform serialization
        byte[] data = serializer.serialize(people);
        System.out.printf("Serialized data length : %s%n", data.length);

        List<Person> reconstructed = serializer.deserialize(data);
        System.out.println("Reconstructed");
        System.out.println(reconstructed);

        Person john = reconstructed.stream().filter(p -> p.getId() == 11).findAny().orElseThrow();
        Person jane = reconstructed.stream().filter(p -> p.getId() == 13).findAny().orElseThrow();

        System.out.printf("Does Address instance is still shared : %s", john.getAddress() == jane.getAddress());
    }



}
