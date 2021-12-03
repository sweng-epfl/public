## Exercise 1 (Thread/FutureTask/CompletableFuture)

The goal of this exercise is to familiarize yourself with asynchrony in Java. To do so, you will implement the `execute` method of the following classes of the `threads` package:
- `ThreadExecutor`
- `FutureTaskExecutor`
- `CompletableFutureExecutor`

As depicted in the TODO, you will have to make an async call to the `compute` method of `HeavyComputation` and wait for the result to return it. Making an async call on the heavy computation will actually let the main thread continue and in a real scenario, you would be able to continue using the app without blocking on the heavy computation.
