# Solution - Bad Input Parameter Checks and Exceptions

### Implementing the string library

The unofficial [C/C++ reference](http://www.cplusplus.com/reference/cstring/) for the string.h library of libc useful provides enough information to implement the required functions. Nevertheless, [here](https://clc-wiki.net/wiki/C_standard_library:string.h) are implementation examples of those functions written with the C programming language.

### Checking for bad input parameters in the string library

Most of the functions take two input parameters of type `cstring`. According to the defensive programming principles, we need to check for bad input parameters.

In the particular case of the required functions, we check for null parameters. To this end, we throw an `IllegalArgumentException` exception whenever an input parameter is null along with a meaningful cause.

Here is an example of bad input parameters checks for the `strcpy` function:

```java
public static cstring strcpy(cstring destination, cstring source) {
  if (destination == null) {
    throw new IllegalArgumentException("null destination");
  }
  if (source == null) {
    throw new IllegalArgumentException("null source");
  }
  // ...
}
```

One could argue that we do not need to check for those null parameters since a `NullPointerException` will be thrown anyway. There are two ways to look at this:

1. The code should throw an exception that is descriptive of the problem it encountered, i.e., an exception that informs the caller what the problem is. Consider the case where destination is null. If the code simply throws `NullPointerException`, then the caller will only know that there was some null pointer dereference somewhere.
By looking at the stack trace, the developer might be able to identify the code line where that happened. Next step is to figure out whether it was destination or source that was null; if source is obtained through some complicated process, then the developer will likely waste a lot of time.
However, if the method throws `IllegalArgumentException` with the "null destination" message, then it's clear to the developer that the destination argument passed in was null. This is more informative and makes debugging easier.
2. The statement of the exercise explicitly says to throw an `IllegalArgumentException` when bad input parameters are provided (including null references), so from an "exam taking technique" point of view, answering in a way that precisely matches the exercise instructions is ideal.

### Creating a SegmentationFault exception class

The creation of a `SegmentationFault` exception class is rather straightforward. We define a class with such a name and arbitrary make it inherit Java's root exception class, namely `Exception`.

```java
package ch.epfl.sweng.defensive.param.check.tinyc.fault;

public class SegmentationFault extends Exception {
  public SegmentationFault() {
    super();
  }
}
```

### Checking for bad input parameters in the cstring type

This task consisted in reproducing the behavior that a C programmer generally encounters when reading from or writing to a C-style string.

In the case of reading a character from a C-style string with an index out of bounds, a possible behavior is that we get an unpredictable character. The reason is that we read some random value from the memory. That's basically what we simulate here; whenever we want to access a character out of the bounds of a `cstring` instance, we return a random ASCII character:

```java
public char get(int index) {
  if (index < 0 || index >= chars.length) {
    return ascii.charAt(random.nextInt(ascii.length()));
  }
  return chars[index];
}
```

Very often, when one tries to write something with the C programming language at some unwanted location, a segmentation fault generally occurs. For this reason, we decide to trigger a segmentation fault, and as such, we throw a `SegmentationFault` exception:

```java
public void set(int index, char value) throws SegmentationFault {
  if (index < 0 || index >= chars.length) {
    throw new SegmentationFault();
  }
  chars[index] = value;
}
```

Given the fact that most functions of the string library call the `set` method at some point, we need to update the signature of those functions so that it takes into account the fact that a segmentation fault may occur. This basically means adding `throws SegmentationFault` to every method prototype of the `string` class.
