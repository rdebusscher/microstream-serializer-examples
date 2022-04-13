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

import one.microstream.examples.rubus.serializer.model.Command;
import one.microstream.examples.rubus.serializer.model.Gadget;

import java.io.*;
import java.util.List;

public class JavaWaySecure {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String command;
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            command = "dir";
        } else {
            command = "ls -l";
        }

        Gadget securityVulnerable = new Gadget(new Command(command));
        // The idea is that someone replaced the original serialized content with this Gadget.

        ByteArrayOutputStream storage = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(storage);

        out.writeObject(securityVulnerable);
        out.close();

        byte[] data = storage.toByteArray();

        InputStream dataBytes = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(dataBytes);

        // Due to the readObject method, it shows the directory listing!
        // And only afterwards a class cast exception because of the malicious content change.
        List<String> reconstructed = (List<String>) in.readObject();

        in.close();

    }
}
