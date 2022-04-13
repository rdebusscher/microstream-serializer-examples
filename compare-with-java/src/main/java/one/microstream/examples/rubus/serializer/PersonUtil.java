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

import java.util.ArrayList;
import java.util.List;

public final class PersonUtil {

    private PersonUtil() {
    }

    static List<Person> newPersons() {
        List<Person> result = new ArrayList<>();
        Address address = new Address(111, "Road to nowhere - 1", "somewhere", "1234");
        result.add(new Person(11, "John Doe", 42, address));
        result.add(new Person(13, "Jane Doe", 36, address));
        result.add(new Person(12, "X", 38, new Address(112, "unknown", "to be determined", "???")));

        return result;
    }

}
