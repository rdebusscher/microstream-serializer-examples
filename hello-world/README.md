# Hello World

The most simple example with the MicroStream `Serializer`.

It shows you the basic usage of the Serializer and important aspects of the code.

* The Maven dependency

```XML
        <dependency>
            <groupId>one.microstream</groupId>
            <artifactId>microstream-persistence-binary</artifactId>
            <version>${microstream.version}</version>
        </dependency>

```

* Create the Serializer and indicate the used class.

```Java
        SerializerFoundation<?> foundation = SerializerFoundation.New()
                .registerEntityTypes(Data.class);
        Serializer<byte[]> serializer = Serializer.Bytes(foundation);
```

* serialize and deserialize.

```Java
        byte[] data = serializer.serialize(instance);
        Data restored = serializer.deserialize(data);
 
```

## Important

The registration of the classes is required when deserializing using a different instance of the `Serializer`. Each class receives a unique OID (object ID) and to make sure the correct mapping between the OID values and the class is made, you must register them in the same order.  See also the _more-complex_ example.