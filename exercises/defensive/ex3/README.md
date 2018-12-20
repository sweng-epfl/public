# Exercise 3: Error-processing routine
This exercise introduces the defensive programming concept of "error-processing routines," using the code pattern called "callback."

A callback is a piece of executable code that is passed as an argument to other code, which is expected to call back (execute) the argument when needed. The invocation may be immediate (as in a synchronous callback) or might happen at a later time (as in an asynchronous callback).

The concept of callback is much broader than just error handling. There are error-handling callbacks, asynchronous result callbacks, or a mix of both. The former is the one we refer to in this exercise. 

The interface to a callback can be as follows:

1. The first argument of the callback is reserved for an error object. If an error occurred, it will be passed to the callback as the first `err` argument.
1. The second argument of the callback is reserved for any successful response data. If no error occurred, `err` will be set to null, and any successful data will be returned in the second argument.

In an object-oriented paradigm, a callback would be an abstract class that provides two methods:  one for any successful response data, and one for an error object.

Here is an example of a generic `Callback` interface in Java:

```java
interface Callback<T> {
  void onSuccess(T value);
  void onError(Exception e);
}
```

In this exercise, you will modify a program that makes use of exceptions as a defense mechanism, and you will replace exceptions with callbacks.

Callbacks are used for _delivering an error asynchronously_. The programmer passes a function or object—the callback—to a procedure. The latter invokes the callback sometime later when the asynchronous operation completes. Exceptions on the other hand are used for _delivering an error synchronously_. The programmer catches an error when some code throws an exception.

### Tasks
You are given a Java project that you need to modify:

1. Define a **Callback** interface in a **callback** package.
1. Modify the [random](src/ch/epfl/sweng/defensive/error/processing/routine/repository/JokeRepository.java#L12) method of [JokeRepository.java](src/ch/epfl/sweng/defensive/error/processing/routine/repository/JokeRepository.java):
    1. Pass a **Callback** argument
    1. Return a random joke with the callback
    1. Handle the **JokeException** locally to the method
1. Modify the [infinite loop](ch/epfl/sweng/defensive/error/processing/routine/Main.java#L13) of [Main.java](src/ch/epfl/sweng/defensive/error/processing/routine/Main.java):
    1. Remove the `try-catch` construct
    1. Retrieve a random joke with an anonymous callback
1. Test the program to validate your changes.

### References
The one-liner jokes were taken from  GitHub user [Daronspence](https://github.com/Daronspence/One-Liners/blob/master/jokes.txt).
