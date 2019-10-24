# Callback-based error-processing routines
This exercise introduces the defensive programming concept of "error-processing routines" through callback functions.

A callback is a piece of executable code that is passed as an argument to other code, which can then execute the code passed in arguments by "calling it back" after completion. The invocation may be immediate (synchronous callback) or might happen at a later time (asynchronous callback).

In this exercise we restrict ourselves to using callbacks for error handling, but the concept can be applied to many other scenarios (such as asynchronous result delivery). An example of asynchronous callbacks is network-handling code in Androi apps: user actions which require communication over the network are often done asynchronously in the background instead of on the UI thread to avoid locking up the application while the user waits for a result from the network.

Conceptually, the interface to a callback generally looks like a function `callback(err, res)`:

1. The first argument of the callback is reserved for an error object. If an error occurred, it will be passed to the callback as the first `err` argument.
1. The second argument of the callback is reserved for any successful response data. If no error occurred, `err` will be set to null, and any successful data will be returned in the `res` argument.

In an object-oriented paradigm, a callback would be represented as an abstract class that provides two methods: one for any successful response data, and one for an error object.

Here is an example of a generic `Callback` interface in Java:

```java
interface Callback<T> {
  void onSuccess(T value);
  void onError(Exception e);
}
```

In this exercise, you are given a program that makes use of exceptions as its defense mechanism and your task will be to replace all exceptions with callbacks.

Callbacks are used for _delivering an error asynchronously_. The programmer passes a function or object—the callback—to a procedure. The latter invokes the callback sometime later when the asynchronous operation completes. Exceptions on the other hand are used for _delivering an error synchronously_. The programmer catches an error when some code throws an exception.

### Tasks
You are given a Java project that you need to modify:

1. Define a **Callback** interface in a **callback** package.
1. Modify the [random](src/main/java/ch/epfl/sweng/defensive/error/processing/routine/repository/JokeRepository.java#L12) method of [JokeRepository.java](src/main/java/ch/epfl/sweng/defensive/error/processing/routine/repository/JokeRepository.java):
    1. Pass a **Callback** argument
    1. Return a random joke with the callback
    1. Handle the **JokeException** locally to the method
1. Modify the [infinite loop](src/main/java/ch/epfl/sweng/defensive/error/processing/routine/Main.java#L13) of [Main.java](src/main/java/ch/epfl/sweng/defensive/error/processing/routine/Main.java):
    1. Remove the `try-catch` construct
    1. Retrieve a random joke with an anonymous callback
1. Test the program to validate your changes.

### References
The one-liner jokes were taken from  GitHub user [Daronspence](https://github.com/Daronspence/One-Liners/blob/master/jokes.txt).
