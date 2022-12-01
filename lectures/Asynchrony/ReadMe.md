# Asynchrony

You're writing an app and want it to download data and process it, so you write some code:
```java
data = download();
process(data);
```
This works fine if both of these operations finish in the blink of an eye... but if they take more than that, since these operations are all your code is doing,
your user interface will be frozen, and soon your users will see the dreaded "application is not responding" dialog from the operating system.

Even if your app has no user interface and works in the background, consider the following:
```java
firstResult = computeFirst();
secondResult = computeSecond();
compare(firstResult, secondResult);
```
If `computeFirst` and `computeSecond` are independent, the code as written is likely a waste of resources, since modern machines have multiple
CPU cores and thus your app could have computed them in parallel before executing the comparison on both results.

Even if you have a single CPU core, there are other resources, such as the disk:
```java
for (int i = 0; i < 100; i++) {
    chunk = readFromDisk();
    process(chunk);
}    
```
As written, this code will spend some of its time reading from the disk, and some of its time processing the data that was read,
but never both at the same time, which is a waste of resources and time.

These examples illustrate the need for _asynchronous code_.
For instance, an app can start a download in the background, show a progress bar while the download is ongoing, and process the data once the download has finished.
Multiple computations can be executed concurrently.
A disk-heavy program can read data from the disk while it is processing data already read.


## Objectives

After this lecture, you should be able to:
- Understand _asynchrony_ in practice
- Build async code with _futures_
- Write _tests_ for async code
- _Design_ async software components


## What are async operations?

Asynchronous code is all about starting an operation without waiting for it to complete. Instead, one schedules what to do after the completion.
Asynchronous code is _concurrent_, and if resources are available it can also be _parallel_, though this is not required.

There are many low-level primitives you could use to implement asynchrony: threads, processes, fibers, and so on.
You could then use low-level interactions between those primitives, such as shared memory, locks, mutexes, and so on.
You could do all of that, but low-level concurrency is _very hard_.
Even experts who have spent years understanding hardware details regularly make mistakes.
This gets even worse with multiple kinds of hardware that provide different guarantees, such as whether a variable write is immediately visible to other threads or not.

Instead, we want a high-level goal: do stuff concurrently.
An asynchronous function is similar to a normal function, except that it has two points of return: synchronously, to let you know the operation has started,
and asynchronously, to let you know the operation has finished.
Just like a synchronous function can fail, an asynchronous function can fail, but again at two points: synchronously, to let you know the operation cannot even start,
perhaps because you gave an invalid argument, and asynchronously, to let you know the operation has failed, perhaps because there was no Internet connection even after retrying in the background.

We would like our asynchronous operations to satisfy three goals: they should be composable, they should be completion-based, and they should minimize shared state.
Let's see each of these three.

### Composition

When baking a cake, the recipe might tell you "when the melted butter is cold _and_ the yeast has proofed, _then_ mix them to form the dough".
This is a kind of composition: the recipe isn't telling you to stand in front of the melted butter and watch it cool, rather it lets you know what operations must finish for the next step and what that next step is.
You are free to implement the two operations however you like; you could stand in front of the butter, or you could go do something else and come back later.

Composition is recursive: once you have a dough, the next step might be "when the dough has risen _and_ the filling is cold, _then_ fill the dough".
Again, this is a composition of asynchronous operations into one big operation representing making a filled dough.

The two previous examples were _and_, but _or_ is also a kind of composition. The recipe might tell you "bake for 30 minutes, _or_ until golden brown".
If the cake looks golden brown after 25 minutes, you take it out of the oven, and forget about the 30 minutes.

One important property of composition is that errors must implicitly compose, too.
If the ripe strawberries you wanted to use for the filling splash on the floor, your overall "bake a cake" operation has failed.
The recipe doesn't explicitly tell you that, because it's a baking convention that if any step fails, you should stop and declare the whole operation a failure, unless you can re-do the failed operation from scratch.
Maybe you have other strawberries you can use instead.

### Completion

If you have a cake you'd like to bake, you could ask a baker "please let me know when you've finished baking the cake", which is _completion-based_.

Alternatively, you could ask them "please let me know when the oven is free" and then bake it yourself, which is a bit lower level and gives you more control.
This is _readiness-based_, and some older APIs, such as those found on Unix, were designed that way.

The problem with readiness-based operations is that they lead to inefficiencies due to concurrent requests.
The baker just told you the oven was ready, but unbeknownst to you, another person has put their cake in the oven before you've had time to get there.
Now your "put the cake in the oven" operation will fail, and you have to ask the baker to let you know when the oven is free again, at which point the same problem could occur.

### Minimizing shared state

Multiple asynchronous operations might need to read and write to the same shared state.
For instance, the task of counting all elements in a large collection that satisfy some property could be parallelized by dividing the collection into chunks and processing multiple chunks in parallel.
Each sub-task could then increment a global counter after every matching element it sees.

However, shared state introduces an exponential explosion in the number of paths through a piece of software.
There is one path in which the code of sub-task 1 accesses the shared state first, and one in which sub-task 2 accesses it first, and so on for each sub-task and for each shared state access.
Even with a simple counter, one already needs to worry about atomic updates.
With more complex shared state, one needs to worry about potential bugs that only happen with specific "interleavings" of sub-task executions.
For instance, there may be a crash only if sub-task 3 accessed shared state first, followed by 2, followed by 3 again, followed by 1.
This may only happen to a small fraction of users.
Then one day, a developer speeds up the code of sub-task 3, and now the bug happens more frequently because the problematic interleaving is more common.
The next day, a user gets an operating system update which slightly changes the scheduling policy of threads in the kernel. Now the bug happens all the time for this user,
because the operating system happens to choose an order of execution that exposes the bug on that user's machine.

Asynchronous operations must minimize shared state, for instance by computing a local result, then merging all local results into a single global result at the end.

One extreme form of minimizing state is _message passing_: operations send each other messages rather than directly accessing the same state.
For instance, operations handling parts of a large collection could do their work and then send their local result as a message to another "monitor" operation which is responsible for merging these results.
Message passing is the standard way to communicate for operations across machines already, so it can make sense to do it even on a single machine.
One interesting use of this is the [Multikernel](https://www.sigops.org/s/conferences/sosp/2009/papers/baumann-sosp09.pdf), an operating system that can run on multiple local CPUs each of a different architecture,
because it uses only message passing and never shared memory, thus what each local thread runs on is irrelevant.


## How can we write maintainable async code?

A simple and naïve way to write asynchronous code is with _callbacks_.
Instead of returning a value, functions take an additional callback argument that is called with the value once it's ready:
```java
void send(String text, Consumer<String> callback);

send("hello", reply -> {
    System.out.println(reply);
});
```

One can call an asynchronous function within a callback, leading to nested callbacks:
```java
send("hello", reply -> {
    send("login", reply2 -> {
        // ...
    });
});
```

But this becomes rather messy.
And we haven't even discussed errors yet, which need another callback:
```java
void send(
    String text,
    Consumer<String> callback,
    Consumer<Exception> errorCallback
);
```

Code that uses callbacks frequently ends in "callback hell":
```java
send("hello", reply -> {
    send("login", reply2 -> {
        send("join", reply3 -> {
            send("msg", reply4 -> {
                send("msg", reply5 -> {
                    send("logout", reply6 -> {
                        // ...
                    });
                });
            });
        });
    });
});
```
This code is hard to read and to maintain, because callbacks are _too_ simple and low-level.
They do not provide easy ways to be composed, especially when dealing with errors as well.
The resulting code is poorly structured. Just because something is simple does not mean it is good!

Instead of overly-simple callbacks, modern code uses _futures_.
A future is an object that represents an operation in one of three states: in progress, finished successfully, or failed.

In Java, futures are represented with the `CompletableFuture<T>` class, where T is the type of the result.
Since `void` is not a type, Java has the type `Void` with a capital `V` to indicate no result, thus an asynchronous operation that would return `void` if it was synchronous instead returns a `CompletableFuture<Void>`.
`CompletableFuture`s can be composed with synchronous operations and with asynchronous operations using various methods.

### Composing `CompletableFuture`s

The `thenAccept` method creates a future that composes the current one with a synchronous operation.
If the current future fails, so does the overall future; but if it succeeds, the overall future represents applying the operation to the result.
And if the operation fails, the overall future has failed:
```java
CompletableFuture<String> future = // ...
return future.thenAccept(System.out::println);
```

Sometimes a failure can be replaced by some kind of "backup" value, such as printing the error message if there is one instead of not printing anything.
The `exceptionally` method returns a future that either returns the result of the current future if there is one, or transforms the future's exception into a result:
```java
return future.exceptionally(e -> e.getMessage())
             .thenAccept(System.out::println);
```

One may wish to not potentially wait forever, such as when making a network request if the server is very slow
or the network connectivity is just good enough to connect but not good enough to transmit data at a reasonable rate.
Implementing timeouts properly is difficult, but thankfully the Java developers provided a method to do it easily:
```java
return future.orTimeout(5, TimeUnit.SECONDS)
             .exceptionally(e -> e.getMessage())
             .thenAccept(System.out::println);
```
The returned combined future represents:
- Printing the result of the original future, if it completes successfully within 5s
- Printing the failure of the original future, if it fails within 5s
- Printing the message from the timeout error, if the original future takes more than 5s

Futures can be composed with asynchronous operations too, such as with `thenCompose`:
```java
CompletableFuture<String> getMessage();
CompletableFuture<Void> log(String text);

return getMessage().thenCompose(log);
```
The returned future represents first getting the message, then logging it, or failing if either of these operations fail.

Composing futures in parallel and doing something with both results can be done with the `thenCombine` method:
```java
CompletableFuture<String> computeFirst();
CompletableFuture<String> computeSecond();

return computeFirst().thenCombine(computeSecond(), (a, b) -> a + b);
```
The resulting future in this example represents doing both operations concurrently and returning the concatenation of their results, or failing if either future fails or if the combination operation fails.

One can compose more than two futures into one that executes them all concurrently with a method such as allOf:
```
CompletableFuture<Void> uploads = ...;
for (int n = 0; n < uploads.length; n++) {
    uploads[n] = ...
}
return CompletableFuture.allOf(uploads);
```
In the loop, each upload will start, and by the time the loop has ended, some of the uploads may have finished while others may still be in progress.
The resulting future represents the combined operation.
One can also use `anyOf` to represent the operation of waiting for any one of the futures to finish and ignoring the others.
Refer to the [Javadoc](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/CompletableFuture.html) for the exact semantics of these operations on failures.

_A quick warning about rate limits._
We have just seen code that allows one to easily upload a ton of data at the same time.
Similar code can be written with an operation to download data, or to call some API that triggers an operation.
However, in practice, many websites will rate limit users to avoid one person using too many resources.
For instance, at the time of this writing, GitHub allows 5000 requests per hour.
Doing too many operations in a short time may get you banned from an API, so look at the documentation of any API you're using carefully before starting hundreds of futures representing API calls.

### Creating `CompletableFuture`s

One can create a future and complete it after some work in a background thread like so:
```java
var future = new CompletableFuture<String>();

new Thread(() -> {
    // ... do some work ...
    future.complete("hello");
}).start();

return future;
```
Instead of `complete`, one can use `completeExceptionally` to fail the future instead, giving the exception that caused the failure as an argument instead of the result.
This code forks execution into two logical threads: one that creates `future`, creates and starts the threads, and returns `future`; and one that contains the code in the `Thread` constructor.
By the time `return future` executes, the future may already have been completed, or it may not.
The beauty of futures is that the code that obtains the returned future doesn't have to care.
Instead, it will use composition operations, which will do the right thing whether the future is already completed, still in progress, or will fail.

Creating a Future representing a background operation is common enough that there is a helper method for it:
```java
return CompletableFuture.supplyAsync(() -> {
    // ... do some work ...
    return "hello"; // or throw an exception to fail the Future
});
```

Sometimes one wants to create a future representing an operation that is already finished, perhaps because the result was cached. There is a method for that too:
```java
return CompletableFuture.completedFuture("hello");
```

However, creating futures is a low-level operation.
Most code composes futures created by lower-level layers.
For instance, a TCP/IP stack might create futures.
Another reason to create futures is when adapting old asynchronous code that uses other patterns, such as callbacks.

---
#### Exercise
Take a look at the four methods in [`Basics.java`](exercises/lecture/src/main/java/Basics.java), and complete them one by one, based on the knowledge you've just acquired.
You can run `App.java` to see if you got it right.

<details>
<summary>Suggested solution (click to expand)</summary>
<p>

- Printing today's weather is done by composing `Weather.today` with `System.out.println` using `thenAccept`
- Uploading today's weather is done by composing `Weather.today` with `Server.upload` using `thenCompose`
- Printing either weather is done using `acceptEither` on two individual futures, both of which use `thenApply` to prefix their results, with `System.out.println` as the composition operation
- Finally, `Weather.all` should be composed with `orTimeout` and `exceptionallyCompose` to use `Weather.today` as a backup

We provide a [solution example](exercises/solutions/lecture/src/main/java/Basics.java).

</p>
</details>
---

### Sync over async

Doing the exercise above, you've noticed `App.java` uses `join()` to block until a future finishes and either return the result or throw the error represented by the future.
There are other such "sync over async" operations, such as `isDone()` and `isCompletedExceptionallly()` to check a future's status.

"Sync over async" operations are useful specifically to use asynchronous operations in a context that must be synchronous,
typically because you are working with a framework that expects a synchronous operation.
Java's `main()` method must be synchronous, for instance.
JUnit also expects `@Test`s to be synchronous.
This is not true of all frameworks, e.g., C# can have asynchronous main methods, and most C# testing frameworks support running asynchronous test methods.

You might also need to use methods such as `isDone()` if you are implementing your own low-level infrastructure code for asynchronous functions, though that is a rare scenario.

In general, everyday async functions must _not_ use `join()` or any other method that attempts to synchronously wait for or interact with an asynchronous operation.
If you call any of these methods on a future outside of a top-level method such as `main` or a unit test, you are most likely doing something wrong.
For instance, if in a button click handler you call `join()` on a `CompletableFuture` representing the download of a picture, you will freeze the entire app until the picture is downloaded.
Instead, the button click handler should compose that future with the operation of processing the picture, and the app will continue working while the download and processing happen in the background.

### Sync errors

Another form of synchrony in asynchronous operations is synchronous errors, i.e., signalling that an asynchronous operation could not even be created.
Unlike asynchronous errors contained in Futures, which in Java are handled with methods such as `exceptionally(...)`, synchronous errors are handled with the good old `try { ... } catch { ... }` statement.
Thus, if you call a method that could fail synchronously or asynchronously and you want to handle both cases, you must write the error handling code twice.

Only use sync errors for bugs, i.e., errors that are not recoverable and indicate something has gone wrong such as an `IllegalArgumentException`:
```java
CompletableFuture<Void> send(String s) {
    if (s == null) {
        // pre-condition violated, the calling code has a bug
        throw new IllegalArgumentException(...);
    }
    ...
}
```
You will thus not have to duplicate error handling logic, because you will not handle unrecoverable errors.

**Never** do something like this in Java:
```java
CompletableFuture<Void> send(String s) {
    if (internetUnavailable) {
        // oh well, we already know it'll fail, we can fail synchronously...?
        throw new IOError(...);
    }
    ...
}
```
This forces all of your callers to handle both synchronous and asynchronous exceptions.
Instead, return a `CompletableFuture` that has failed already:
```java
    if (internetUnavailable) {
        return CompletableFuture.failedFuture(new IOError(...));
    }
```
This enables your callers to handle that exception just like any other asynchronous failure: with future composition.

Another thing you should **never** do is return a `null` future:
```java
    if (s.equals("")) {
        // nothing to do, might as well do nothing...?
        return null;
    }
```
While Java allows any value of a non-primitive type to be `null`, ideally `CompletableFuture` would disallow this entirely, as once again this forces all of your callers to explicitly handle the `null` case.
Instead, return a `CompletableFuture` that is already finished:
```java
    if (s.equals("")) {
        return CompletableFuture.completedFuture(null);
    }
```

### Cancellation

One form of failure that is expected is _cancellation_: a future might fail because it has been explicitly canceled.
Canceling futures is a common operation to avoid wasting resources.
For instance, if a user has navigated away from a page which was still loading pictures, there is no point in finishing the download of these pictures,
decoding them from raw bytes, and displaying them on a view that is no longer on screen.
Cancellation is not only about ignoring a future's result, but actually stopping whatever operation was still ongoing.

One may be tempted to use a method such as `Thread::stop()` in Java, but this method is obsolete for a good reason: forceful cancellation is dangerous.
The thread could for instance be holding a lock when it is forcefully stopped, preventing any other thread from ever entering the lock.
(`CompletableFuture` has a `cancel` method with a `mayInterruptIfRunning` parameter, but this parameter only exists because the method implements the more general interface `CompletionStage`;
as the documentation explains, the parameter is ignored because forcefully interrupting a computation is not a good idea in general)

Instead, cancellation should be _cooperative_: the operation you want to cancel must be aware that cancellation is a possibility, and provide a way to cancel at well-defined points in the operation.

Some frameworks have built-in support for cancellation, but in Java you have to do it yourself.
One way to do it is to pass around an `AtomicBoolean` value, which serves as a "box" to pass by reference a flag indicating whether a future should be canceled.
Because this is shared state, you must be disciplined in its use: only write to it outside of the future, and only read from it inside of the future.
The computation in the future should periodically check whether it should be canceled, and if so throw a cancellation exception instead of continuing:
```java
for (int step = 0; step < 100; step++) {
    if (cancelFlag.get()) {
        throw new CancellationException();
    }
    ...
}
```

### Progress

If you've implemented cancellation and let your users cancel ongoing tasks, they might ask themselves "should I cancel this or not?" after a task has been running for a while.
Indeed, if a task has been running for 10 minutes, one may want to cancel it if it's only 10% done, whereas if it's 97% done it's probably be better to wait.

This is where _progress_ indication comes in.
You can indicate progress in units that make sense for a given operation, such as "files copied", "bytes downloaded", or "emails sent".

Some frameworks have built-in support for progress, but in Java you have to do it yourself.
One way to do it is to pass around an `AtomicInteger` value, which serves as a "box" to pass by reference a counter indicating the progress of the operation relative to some maximum.
Again, because this is shared state, you must be disciplined in its use, this time the other way around: only read from it outside of the future, and only write to it from inside of the future.
The computation in the future should periodically update the progress:
```java
for (int step = 0; step < 100; step++) {
    progress.set(step);
    ...
}
progress.set(100);
```

It is tempting to define progress in terms of time, such as "2 minutes remaining".
However, in practice, the duration of most operations cannot be easily predicted.
The first 10 parts of a 100-part operation might take 10 seconds, and then the 11th takes 20 seconds on its own; how long will the remaining 89 take?
Nobody knows. This leads to poor estimates.

One way to show something to users without committing to an actual estimate is to use an "indeterminate" progress bar, such as a bar that continuously moves from left to right.
This lets users know something is happening, meaning the app has not crashed, and is typically enough if the operation is expected to be short so users won't get frustrated waiting.

---
#### Exercise
Take a look at the three methods in [`Advanced.java`](exercises/lecture/src/main/java/Advanced.java), and complete them one by one, based on the knowledge you've just acquired.
Again, you can run `App.java` to see if you got it right.

<details>
<summary>Suggested solution (click to expand)</summary>
<p>

- `Server.uploadBatch` should be modified to support cancellation as indicated above, and then used in a way that sets the cancel flag if the operation times out using `orTimeout`
- Converting `OldServer.download` can be done by creating a `CompletableFuture<String>` and passing its `complete` and `completeExceptionally` methods as callbacks
- Reliable downloads can be implemented in the equivalent of a recursive function: `download` composed with `reliableDownload` if it fails, using `exceptionallyCompose`

We provide a [solution example](exercises/solutions/lecture/src/main/java/Advanced.java).

</p>
</details>
---

### In other languages

We have seen futures in Java as `CompletableFuture`, but other languages have roughly the same concept with different names such as `Promise` in JavaScript,
`Task<T>` in C#, and `Future` in Rust. Importantly, operations on these types don't always have the exact same semantics, so be sure to read their documentation before using them.

Some languages have built-in support for asynchronous operations.
Consider the following C# code:
```csharp
Task<string> GetAsync(string url);

async Task WriteAsync()
{
    var text = await GetAsync("epfl.ch");
    Console.WriteLine(text);
}
```
`Task<T>` is C#'s future equivalent, so `GetAsync` is an asynchronous operation that takes in a string and returns a string.
`WriteAsync` is marked `async`, which means it is an asynchronous operation.
When it calls `GetAsync`, it does so with the `await` operator, which is a way to compose futures while writing code that looks and feels synchronous.
To be clear, `WriteAsync` is an asynchronous operation and does not block. It is equivalent to explicitly returning `GetAsync("epfl.ch")` composed with `Console.WriteLine`, C#'s equivalent of `System.out.println`.
But because the language supports it, the compiler does all of the future composition, an engineer can write straightforward asynchronous code without having to explicitly reason about future composition.
It is still possible to explicitly compose futures, such as with `Task.WhenAll` to compose many futures into one.
Handling failures is also easier:
```csharp
try
{
    var text = await GetAsync("epfl.ch");
    Console.WriteLine(text);
}
catch (Exception) { ... }
```
Whether `GetAsync` fails synchronously or asynchronously, the handling is in the `catch` block, and the compiler takes care of transforming this code into code that composes futures in the expected way.

Similarly, Kotlin uses "coroutines", a form of lightweight threads, which can be suspended by `suspend` functions:
```kotlin
suspend fun getAsync(url: String): String

suspend fun writeAsync() {
    val content = getAsync("epfl.ch")
    println(content)
}
```
Once again, this code is fully asynchronous: when `writeAsync` calls `getAsync`, it can get _suspended_, and the language runtime will then schedule another coroutine that is ready to run,
such as one that updates the user interface with a progress bar.
Kotlin can also handle both kinds of failures with a `catch` block.

### Futures as functions

Now that we've seen different kinds of futures, let's take a step back and think of what a future is at a high level.
There are two fundamental operations for futures: (1) turning a value, or an error, to a future; and
(2) combining a future with a function handling its result into a new future.

The first operation, creation, is two separate methods in Java's `CompletableFuture`: `completedFuture` and `failedFuture`.
The second operation, transformation, is the `thenCompose` method in Java, which takes a future and a function from the result to another future and returns the aggregate future.

You may have already seen this pattern before.
Consider Java's `Optional<T>`. One can create a full or empty optional with the `of` and `empty` methods, and transform it with the `flatMap` method.

In the general case, there are two operations on a "value of T" type: create a `Value<T>` from a `T`, and map a `Value<T>` with a `T -> Value<U>` function to a `Value<U>`.
Mathematically-inclined folks use different names for these. "Create" is called "Return", "Map" is called "Bind", and the type is called a _monad_. 

What's the use of knowing about monads?
You can think of monads as a kind of design pattern, a "shape" for abstractions.
If you need to represent some kind of "box" that can contain values, such as an optional, a future, or a lists, you already know many useful methods you should provide:
the ones you can find on optionals, futures, and lists.

Consider the following example from the exercises:
```java
return Weather.today()
    .thenApply(w -> "Today: " + w)
    .acceptEither(
         Weather.yesterday()
                .thenApply(w->"Yesterday: " + w),
         System.out::println
     );
```
You know this code operates on futures because you've worked with the codebase and you know what `Weather.today()` and `Weather.yesterday()` return.
But what if this was a different codebase, one where these methods return optionals instead?
The code still makes sense: if there is a weather for today, transform it and print it, otherwise get the weather for yesterday, transform it and print it.
Or maybe they return lists?
Transform all elements of today's prediction, and if there are none, use the elements of yesterday's predictions instead, transformed; then print all these elements.


## How should we test async code?

Consider the following method:
```java
void printWeather() {
    getString(...)
        .thenApply(parseWeather)
        .thenAccept(System.out::println);
}
```
How would you test it?

The problem is that immediately after calling the method, the `getString` future may not have finished yet, so one cannot test its side effect yet.
It might even never finish if there is an infinite loop in the code, or if it expects a response from a server that is down.

One commonly used option is to sleep via, e.g., `Thread.sleep`, and then assume that the operation must have finished.
**Never, ever, ever, ever sleep in tests for async code**.
It slows tests down immensely since one must sleep the whole duration regardless of how long the operation actually takes,
it is brittle since a future version of the code may be slower,
and most importantly it's unreliable since the code could sometimes take more time due to factors out of your control,
such as continuous integration having fewer resources because many pull requests are open and being tested.

The fundamental problem with the method above is its return value, or rather the lack of a return value.
Tests cannot access the future, they cannot even name it, because the method does not expose it.
To be testable, the method must be refactored to expose the future:
```java
CompletableFuture<Void> printWeather() {
    return getString(...)
          .thenApply(parseWeather)
          .thenAccept(System.out::println);
}
```
It is now possible to test the method using the future it returns.
One option to do so is this:
```java
printWeather().thenApply(r -> {
    // ... test? ...
});
```
However, this is a bad idea because the lambda will not run if the future fails or if it never finishes, so the test will pass even though the future has not succeeded.
In fact, the test does not even wait for the future to finish, so the test may pass even if the result is wrong because the lambda will execute too late!
To avoid these problems, one could write this instead:
```java
printAnswer().join();
// ... test ...
```
But this leaves one problem: if the code is buggy and the future never completes, `join()` will block forever and the tests will never complete.
To avoid this problem, one can use the timeout method we saw earlier:
```java
printWeather()
    .orTimeout(5, TimeUnit.SECONDS)
    .join();
// ... test ...
```
There is of course still the issue unrelated to asynchrony that the method has an implicit dependency on the standard output stream, which makes testing difficult.
You can remove this dependency by returning the `CompletableFuture<String>` directly instead of printing it, which makes tests much simpler:
```java
String weather = getWeather()
    .orTimeout(5, TimeUnit.SECONDS)
    .join();
// ... test weather ...
```

Exposing the underlying future is the ideal way to test asynchronous operations.
However, this is not always feasible. Consider an app for which we want to test a button click "end-to-end", i.e., by making a fake button click and testing the resulting changes in the user interface.
The button click handler must typically have a specific interface, including a `void` return type:
```java
@Override
void onClick(View v) { ... }
```
One cannot return the future here, since the `void` return type is required to comply with the button click handler interface.
Instead, one can add an explicit callback for tests:
```java
@Override
void onClick(View v) {
    // ...
    callback.run();
}

public void setCallback(...) { ... }
```
Tests can then set a callback and proceed as usual:
```java
setCallback(() -> {
    // ... can we test here?
});
// ... click the button ...
```
However, this now raises another problem: how to properly test callback-based methods?
One way would be to use low-level classes such as latches or barriers to wait for the callback to fire.
But this is a reimplementation of the low-level code the authors of `CompletableFuture` have already written for us, so we should be reusing that instead!
```java
var future = new CompletableFuture<Void>();
setCallback(() -> future.complete(null));
// ... click the button ...
future.orTimeout(5, TimeUnit.SECONDS)
      .join();
```
We have reduced the problem of testing callbacks to a known one, testing futures, which can then be handled as usual.

---
#### Exercise        		
Take a look at the three tests in [`WeatherTests.java`](exercises/lecture/src/test/java/WeatherTests.java), and complete them one by one, based on the knowledge you've just acquired.

<details>
<summary>Suggested solution (click to expand)</summary>
<p>

- As we did above, call `orTimeout(...)` then `join()` on `Weather.today()`, then assert that its result is `"Sunny"`
- Create a `CompletableFuture<Void>`, call its `complete` method in the callback of `WeatherView`, then wait for it with a timeout after executing `WeatherView.clickButton()`, and test the value of `WeatherView.weather()`
- There are multiple ways to do the last exercise; one way is to return the combination of the two futures with `allOf` in `printWeather`, and replace `System.out::println` with a `Consumer<String>` parameter,
  then test it with a consumer that adds values to a string; another would be a more thorough refactoring of `printWeather` that directly returns a `CompletableFuture<List<String>>`

We provide a [solution example](exercises/solutions/lecture/src/test/java/WeatherTests.java).

</p>
</details>

---

## How does asynchrony interact with software design?

Which operations should be sync and which should be async, and why?
This is a question you will encounter over and over again when engineering software.

One naïve option is to make everything asynchronous. Even `1 + 1` could be `addAsync(1, 1)`! This is clearly going too far.

It's important to remember that _asynchrony is viral_: if a method is async, then the methods that call it must also be async, though it can itself call sync methods.
A sync method that calls an async method is poor design outside of the very edges of your system, typically tests and the main method,
because it will need to block until the async method is done, which can introduce all kinds of issues such as UI freezes or deadlocks.

This also applies to inheritance: if a method might be implemented in an asynchronous way, but also has other synchronous implementations, it must be async.
For instance, while fetching the picture for an album in a music player may be done from a local cache in a synchronous way, it will also sometimes be done by downloading the image from the Internet.
Thus, you should make operations async if they are expected to be implemented in an asynchronous way, typically I/O operations.

One policy example is the one in the [Windows Runtime APIs](https://learn.microsoft.com/en-us/windows/uwp/cpp-and-winrt-apis/concurrency#asynchronous-operations-and-windows-runtime-async-functions),
which Microsoft introduced with Windows 8.
Any operation that has the potential to take more than 50 milliseconds to complete returns a future instead of a synchronous result.
50ms may not be the optimal threshold for everything, but it is a reasonable and clear position to take that enables engineers to decide whether any given operation should be asynchronous.

Remember the "YAGNI" principle: "_You Aren't Gonna Need It".
Don't make operations async because they might perhaps one day possibly maybe need to be async.
Only do so if there is a clear reason to. Think of how painful, or painless, it would be to change from sync to async if you need it later.

One important principle is to be consistent.
Perhaps you are using an OS that gives you asynchronous primitives for reading and writing to files, but only a synchronous primitive to delete it.
You could expose an interface like this:
```java
class File {
    CompletableFuture<String> read();
    CompletableFuture<Void> write(String text);
    void delete();
}
```
However, this is inconsistent and surprising to developers using the interface.
Instead, make everything async, even if deletion has to be implemented by returning an already-completed future after synchronously deleting the file:
```java
class File {
    CompletableFuture<String> read();
    CompletableFuture<Void> write(String text);
    CompletableFuture<Void> delete();
}
```
This offers a predictable and clear experience to developers using the interface.

What if you need to asynchronously return a sequence of results?
For instance, what should the return type of a `downloadImagesAsync` method be?
It could be `CompletableFuture<List<Image>>`, if you want to batch the results.
Or it could be `List<CompletableFuture<Image>>`, if you want to parallelize the downloads.
But if you'd like to return "a sequence of asynchronous operations", what should you return?

Enter "reactive" programming, in which you react to events such as downloads, clicks, or requests, each of which is an asynchronous event.
For instance, if you have a "flow" of requests, you could `map` it to a flow of users and their data, then `filter` it to remove requests from users without the proper access right, and so on.
In other words, it's a monad! Thus, you already mostly know what operations exist and how to use it.
Check out [ReactiveX.io](https://reactivex.io) if you're interested, with libraries such as [RxJava](https://github.com/ReactiveX/RxJava).


## Summary

In this lecture, you learned:
- Asynchrony: what it is, how to use it, and when to use it
- Maintainable async code by creating and combining futures
- Testable async code with reliable and useful tests

You can now check out the [exercises](exercises/)!
