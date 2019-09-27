# Solutions: Abstractions - Intermediate

## `gets`

The `gets` function does not ask for how long the buffer is. In C, arrays and strings have no associated length, thus one must always know until which point a buffer is valid. Since `gets` does not do that, and since it writes characters into the buffer until the computer's user decides to stop, it is unsafe by design: there is no way to prevent an user from writing too much data in the buffer, no matter how big it is.

In other words, `gets` tries to abstract away the low-level details of C, but fails.

You may also have noticed that `gets` returns a `char*`. This value is either the `str` argument, or `NULL` if an error occurs. This is also an example of a poor abstraction: it models something that both edits the thing you gave it and also separately gives you back that same edited thing, unless there is an error in which case you get a magic value. This "model" most likely does not match how a developer thinks of user input. It would have been much clearer to return an error code instead, or even just a Boolean since that is what `gets` is currently capable of expressing.

Instead of attempting to abstract away something that can't be, `gets` should ask the developer for the length of the buffer, and not write any more than that. This is what functions such as [`snprintf`](https://linux.die.net/man/3/snprintf) do.


## `java.lang.Cloneable`

Think of how you would model "something that can be cloned": it's a thing that you can ask for a clone.

But that's not what `java.lang.Cloneable` does. It doesn't even have any methods! Instead, it's a special interface you must implement if you want to be able to call `super.clone()` in your class without receiving an exception.

This is the opposite of an abstraction; it's an implementation detail of the Java standard library.

A better `Cloneable` interface could look like:

```java
interface Cloneable<T> {
  T clone();
}
```

You can read more about this in the [Josh Bloch on Design](https://www.artima.com/intv/bloch13.html) interview.


## The TCP header

The issue is in the checksum: not only does it check the header and the payload, but it also checks a "pseudo-header" consisting among other things of the source and destination IPs.

This is a clear violation of the overall networking abstractions: TCP should not know that it is contained in IP packets!

Furthermore, adding the pseudo-header to the checksum does not help much, since one can still find ways to corrupt both the checksum and a field.

The reason why the checksum was designed this way is that TCP and IP were designed as a single protocol, and only split in two later. This is an unfortunate remnant of the old design.


## IEEE floating-point

Because it is not an exact format, computations sometimes have small errors, e.g. 1 + 100000000000000000000 results in 100000000000000000000, as if the 1 had disappeared.

This is clearly not a good abstraction if you need exact numbers, e.g. for a bank account. However, it is perfectly fine if your numbers have errors to start with, such as in physical experiments where every number has an error margin. In that case, the errors introduced by the number format do not matter since the measurements have bigger error margins.

Unfortunately, the only exact real number class in Java is `BigDecimal`, which is designed to have infinite precision and thus sacrifices poor performance. Some languages have a built-in type with specific precision that does not lose information in a given range, e.g. C#'s `decimal`.

