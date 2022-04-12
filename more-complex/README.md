# More complex examples

These examples continue on the _hello-world_ example. For the configuration and basic aspects, have a look at that example.

The `TestObjectGraph` example uses multiple objects that are serialized.  It also shows that initial links are preserved. There are 2 persons (John and Jane Doe) that have a link, to the same `Address` instance. After deserialization, this is still the case.

The `TestCircular` example shows that MicroStream can also handle circular dependencies between the instances that are serialized and deserialized.

The `TestWrongRegistration` shows the importance of the correct registration of the classes with the `SerializerFoundation`.  When not registered in the same order (or some missing classes) the deregistration will fail with an Exception or a message that as ObjectID (OID) can not be mapped to a Class.

A more self-contained serialization is demonstrated in the project ??? (TODO - not pushed yet) but also remember that the usage of this `Serializer` should only be used in the situations where you can't use the preferred `StorageManager` solution. 