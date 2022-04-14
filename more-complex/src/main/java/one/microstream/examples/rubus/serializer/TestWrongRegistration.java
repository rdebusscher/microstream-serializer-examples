package one.microstream.examples.rubus.serializer;

/*-
 * #%L
 * more-complex
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

public class TestWrongRegistration {

    public static void main(String[] args) throws Exception {
        List<Person> people = newPersons();

        SerializerFoundation<?> foundation = SerializerFoundation.New()
                .registerEntityTypes(Person.class, Address.class);
        byte[] data;
        try (Serializer<byte[]> serializer = Serializer.Bytes(foundation)) {

            data = serializer.serialize(people);
        }

        System.out.println("Testing deserializer with same registered entities in the same order");
        Serializer<byte[]> serializer2 = null;
        try {
            SerializerFoundation<?> foundation2 = SerializerFoundation.New()
                    .registerEntityTypes(Person.class, Address.class);
            serializer2 = Serializer.Bytes(foundation2);

            serializer2.deserialize(data);
            System.out.printf("Success%n%n%n");
        } catch (Exception e) {
            System.out.printf("Failed with %s%n%n%n", getExceptionMessage(e));
        } finally {
            serializer2.close();

        }

        System.out.println("Testing deserializer with same registered entities in the different order");
        Serializer<byte[]> serializer3 = null;
        try {

            SerializerFoundation<?> foundation3 = SerializerFoundation.New()
                    .registerEntityTypes(Address.class, Person.class);

            serializer3 = Serializer.Bytes(foundation3);

            serializer3.deserialize(data);
            System.out.printf("Success%n%n%n");
        } catch (Exception e) {
            System.out.printf("Failed with %s%n%n%n", getExceptionMessage(e));
        } finally {
            serializer3.close();

        }
    }

    private static List<Person> newPersons() {
        List<Person> result = new ArrayList<>();
        Address address = new Address(111, "Road to nowhere - 1", "somewhere", "1234");
        result.add(new Person(11, "John Doe", 42, address));
        result.add(new Person(13, "Jane Doe", 36, address));
        result.add(new Person(12, "X", 38, new Address(112, "unknown", "to be determined", "???")));

        return result;
    }

    private static String getExceptionMessage(Exception e) {
        return "\nException = " + e.getClass().getName() +
                "\nmessage = " + e.getMessage() +
                "\nat = " + e.getStackTrace()[0].toString();
    }
}
