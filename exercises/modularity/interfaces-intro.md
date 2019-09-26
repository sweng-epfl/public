# Interfaces - Introduction: In Java

Before diving into exercises about interfaces, let's recap the situation in Java.

**This situation changed in Java 8**, so if your knowledge is a bit out of date, make sure to read this.

Before Java 8, interfaces had only public abstract methods, while abstract classes could have both abstract and non-abstract methods. Classes could implement any number of interfaces and extend exactly one class.

Since Java 8, interfaces can have _default methods_, which are virtual: implementing classes can choose to override them or not, but the default implementation should make sense. This allows library developers to add features to their interfaces without breaking code that implements them. Before Java 8, adding a method to an interface was a breaking change: code that implemented the interface would not compile any more since it now lacked a method implementation.

Default methods are primarily used for performance reasons: methods that _can_ be implemented in terms of the interface's other methods, but that an interface implementation could implement much more efficiently. For instance, imagine a `FileUploader` interface with a single method `upload(File)`. With default interface methods, a new method `uploadMultiple(List<File>)` can be added. The default implementation will call `upload` for each file, but a clever implementation could upload them all at once and save on the per-request overhead.

This means that the line between interfaces and abstract classes is now rather blurry, with the main differences being that interfaces cannot have state and that a class can implement multiple interfaces but only extend a single class.

When we refer to "an interface" in program design, we mean the _public contract_ that an abstraction offers, not a specific kind of interface from a specific programming language.

