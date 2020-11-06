# Exercise 2

## Solution 1

See [ComputerBuilder.java](ComputerBuilder.java). The key idea is to allow mutability for the sake of construction. We also use a [Director.java](Director.java) to abstract away the actual construction.

## Solution 2

`StringBuilder` decouples the construction of a String from its representation (it is an implementation of the builder design pattern for strings). In Java, constant string representations are immutable: they are actually created in a String pool, which allows 2 identical constant strings to share the same object. Hence, if you compose 2 constant strings using the `+` operator, you are in fact allocating 3 objects in the String pool. Even worse, using `new String` forces the allocation of a new, separate String object. It is hence very important to use `StringBuilder` in Java as it is much more efficient (it uses a contiguous array of chars to efficiently manipulate string construction), and it also provides a nice abstraction.