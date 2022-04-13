# Compare with Java Serialization

This project compares the standard Java Serialization (class `UsingJava`) with the MicroStream way (class `UsingMicroStream` )

Main differences

* MicroStream does not require that the instances implement the `java.io.Serializable` interface.
* Same behaviour for `transient`behaviour.
* Serialization with MicroStream doesn't perform any method call during deserialization. This means no Security vulnerability possible.
* The `Serializer` uses the underlying codebase of the Storage Manager of MicroStream and thus has the same functionality such as Legacy Type Mappers, Field Evaluator to determine what is persisted, etc ...


The differences regarding security vulnerability is compared in `JavaWaySecure` and `MicroStreamWaySecure`.

Because there is no method execution performed when deserializing the content, no uncontrolled execution of code can happen. In fact, when the byte content doesn't match, the deserializing attempt will already fail (before instance is created) because MicroStream only stores Object Ids, not class names.  And the recognized classes are defined by the developer with `.registerEntityTypes()`.