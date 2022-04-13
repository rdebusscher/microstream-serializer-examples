package one.microstream.examples.rubus.serializer.model;

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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Called Gadget as within Java Serialization Security Vulnerability it's called Gadget Chain when a combination of classes is
 * found that can be used to execute arbitrary code during deserialization.
 */
public class Gadget implements Serializable {

    private final Runnable command;

    public Gadget(Command command) {
        this.command = command;
    }

    /**
     * From the moment there is a `readObject` created you have introduced the possibility of a security vulnerability
     * through Java deserialization.
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private final void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        command.run();
    }
}
