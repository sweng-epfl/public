# Bad Input Parameter Checks and Exceptions
This exercise will teach you about **bad input parameter checks and exceptions**. You will work on a tiny C implementation. Concretely, you will need to implement a partial version of the `string.h` library of **libc**. For this to be possible, you will be given a nearly-complete implementation of C-style strings. You will be using C-style strings to implement the `string.h` library. The implementation will need to be robust, make use of exceptions, and employ checks for input parameters.

This exercise illustrates basic techniques of defensive programming, in particular input parameter checks and exceptions.

Checking the values of method input parameters is essentially the same as checking data that comes from an external source, except that the data comes from another method instead of through an external interface.

Exceptions are a specific means by which code can pass along errors or exceptional events to the code that called it. One uses exceptions to notify other parts of the program about errors that should not be ignored. The difficulty lies in throwing exceptions at the right level of abstraction.

In this exercise, you will implement a partial version of the **string** library of **libc**. libc stands for *standard library for the C programming language*. You are given a nearly-functional implementation of a [C-style string type](src/ch/epfl/sweng/defensive/param/check/tinyc/type/cstring.java). C-style strings are arrays of characters where the `\0` character marks the end of the string, by convention.

Your implementation needs to be robust, i.e., check for bad input parameters and throw relevant exceptions where necessary.

### Tasks
You are given a Java project you need to complete:

1. Implement the [string library](src/ch/epfl/sweng/defensive/param/check/tinyc/libc/string.java) of the [libc](src/ch/epfl/sweng/defensive/param/check/tinyc/libc) package.
1. Check for bad input parameters in the [string library](src/ch/epfl/sweng/defensive/param/check/tinyc/libc/string.java). Throw an `IllegalArgumentException` exception where necessary.
1. Create a `SegmentationFault` exception class in a `fault` package.
1. Check for bad input parameters in the [cstring type](src/ch/epfl/sweng/defensive/param/check/tinyc/type/cstring.java).
    1. Return a random ASCII character in case of a *read* with an index that is out of bounds.
    1. Throw a `SegmentationFault` exception in case of a *write* with an index out of bounds.
1. Write a test program [Main.java](src/ch/epfl/sweng/defensive/param/check/Main.java) that checks your implementation.

### Hints
You might find the unofficial [C/C++ reference](http://www.cplusplus.com/reference/cstring/) for the *string.h* library of **libc** useful.
