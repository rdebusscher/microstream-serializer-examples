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

import one.microstream.examples.rubus.serializer.model.Employee;
import one.microstream.persistence.binary.util.Serializer;
import one.microstream.persistence.binary.util.SerializerFoundation;

public class TestCircular {

    public static void main(String[] args) {

        SerializerFoundation<?> foundation = SerializerFoundation.New()
                .registerEntityTypes(Employee.class);
        Serializer<byte[]> serializer = Serializer.Bytes(foundation);

        Employee theBoss = createTheEmployeeHierarchy();  // Create an object with circular reference!
        byte[] data = serializer.serialize(theBoss);

        System.out.println("reconstructed");
        Employee reconstructed = serializer.deserialize(data);
        printHierarchy(reconstructed);
    }

    private static Employee createTheEmployeeHierarchy() {
        Employee theBoss = new Employee(1L, "The boss");

        Employee employee1 = new Employee(2L, "Person X");
        Employee employee2 = new Employee(3L, "Person Y");
        Employee employee3 = new Employee(4L, "Person Z");

        employee3.setManager(employee2);

        employee1.setManager(theBoss);
        employee2.setManager(theBoss);

        printHierarchy(theBoss);
        return theBoss;
    }

    private static void printHierarchy(Employee manager) {
        System.out.println(manager);
        manager.getEmployees().forEach(TestCircular::printHierarchy);
    }
}
