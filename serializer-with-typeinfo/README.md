# Serializer with TypeInfo

When you use the `Serializer`, it is important that you register the classes in the same order. Within the serialised content, there are only ids present, not class names and no byte code. So it is important that the mapping between id and classname is identical when you deserialize it then when you created it.

The `SerializerWithTypeInfo` can help you in this case.  It returns an object that contain the byte array, but also a list of classes and the order in which you need to register them when you want to deserialize the bytes.

The `SerializerWithTypeInfo` class also implements a `deserialize()` method that reconstructs the object.


> &#9888; You should use the [ObjectCopier](https://docs.microstream.one/manual/storage/storing-data/deep-copy.html#_deep_copy_utility_objectcopier) when you want to make a deep copy of an instance and not the  `SerializerWithTypeInfo` solution.

Tests also showed that the current implementation of the Serializer is not yet fast enough the use it as a replacement for JSON serialization between data endpoints for example.