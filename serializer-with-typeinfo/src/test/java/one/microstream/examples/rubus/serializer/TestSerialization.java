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

import one.microstream.examples.rubus.serializer.model.Address;
import one.microstream.examples.rubus.serializer.model.Employee;
import one.microstream.examples.rubus.serializer.model.Person;
import one.microstream.examples.rubus.serializer.model.SimpleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestSerialization {

    private static final String THE_BOSS = "The boss";
    private static final String PERSON_X = "Person X";
    private static final String PERSON_Y = "Person Y";
    private static final String PERSON_Z = "Person Z";

    @Test
    void simpleData() throws Exception {
        SimpleData data = new SimpleData("MicroStream", new Date());
        SerializedData serializedData = SerializerWithTypeInfo.serialize(data);

        SimpleData reconstructed = SerializerWithTypeInfo.deserialize(serializedData);

        Assertions.assertEquals(data.getName(), reconstructed.getName());
        Assertions.assertEquals(data.getTimestamp(), reconstructed.getTimestamp());
    }

    @Test
    void listAndReferences() throws Exception {
        List<Person> data = newPersons();
        SerializedData serializedData = SerializerWithTypeInfo.serialize(data);

        List<Person> reconstructed = SerializerWithTypeInfo.deserialize(serializedData);

        Assertions.assertEquals(data.size(), reconstructed.size());
        for (int i = 0; i < 2; i++) {

            Assertions.assertEquals(data.get(i), reconstructed.get(i));
            Assertions.assertEquals(data.get(i).getAge(), reconstructed.get(i).getAge());
            Assertions.assertEquals(data.get(i).getAddress(), reconstructed.get(i).getAddress());
        }

        // Test to see if address instance of John and Jane is the same.
        Assertions.assertSame(reconstructed.get(0).getAddress(), reconstructed.get(1).getAddress());
        // To be on the safe side, test if it is John and Jane.
        Assertions.assertEquals("John Doe", reconstructed.get(0).getName());
        Assertions.assertEquals("Jane Doe", reconstructed.get(1).getName());

        Assertions.assertEquals(2, serializedData.getTypeInfo().size());
    }

    @Test
    void circular() throws Exception {
        Employee theBoss = createTheEmployeeHierarchy();

        SerializedData serializedData = SerializerWithTypeInfo.serialize(theBoss);

        Employee reconstructed = SerializerWithTypeInfo.deserialize(serializedData);

        Assertions.assertEquals(THE_BOSS, reconstructed.getName());
        List<String> names = reconstructed.getEmployees().stream().map(Employee::getName).collect(Collectors.toList());
        Assertions.assertEquals(List.of(PERSON_X, PERSON_Y), names);

        // Manager is the boss
        Assertions.assertEquals(reconstructed, reconstructed.getEmployees().get(0).getManager());
        Assertions.assertEquals(reconstructed, reconstructed.getEmployees().get(1).getManager());
    }

    private static List<Person> newPersons() {
        List<Person> result = new ArrayList<>();
        Address address = new Address(111, "Road to nowhere - 1", "somewhere", "1234");
        result.add(new Person(11, "John Doe", 42, address));
        result.add(new Person(13, "Jane Doe", 36, address));
        result.add(new Person(12, "X", 38, new Address(112, "unknown", "to be determined", "???")));

        return result;
    }

    private static Employee createTheEmployeeHierarchy() {
        Employee theBoss = new Employee(1L, THE_BOSS);

        Employee employee1 = new Employee(2L, PERSON_X);
        Employee employee2 = new Employee(3L, PERSON_Y);
        Employee employee3 = new Employee(4L, PERSON_Z);

        employee3.setManager(employee2);

        employee1.setManager(theBoss);
        employee2.setManager(theBoss);

        return theBoss;
    }
}
