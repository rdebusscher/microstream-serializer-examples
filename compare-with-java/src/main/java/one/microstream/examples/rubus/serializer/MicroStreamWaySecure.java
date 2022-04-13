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
import one.microstream.persistence.binary.util.Serializer;
import one.microstream.persistence.binary.util.SerializerFoundation;

import java.util.List;

public class MicroStreamWaySecure {

    public static void main(String[] args) {
        String command;
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            command = "dir";
        } else {
            command = "ls -l";

        }

        SerializerFoundation<?> foundation = SerializerFoundation.New()
                .registerEntityTypes(Gadget.class, Command.class);
        Serializer<byte[]> serializer = Serializer.Bytes(foundation);

        Gadget securityVulnerable = new Gadget(new Command(command));

        byte[] data = serializer.serialize(securityVulnerable);

        // No execution of any method before cast! (which fails obviously)
        List<String> reconstructed = serializer.deserialize(data);

        // But the deserialization itself will fail since the Object Ids in the byte array does not match those
        // that are expected by the Serializer.
    }


}
