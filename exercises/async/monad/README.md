## Exercise 5 (Monad)

> :warning: This exercise is quite complex and require knowledges that go way beyond the scope of this course

In this exercise, you will implement a promise monad. First have a look at the skeleton of `Promise` and `Async` in the `monad` package and the main method in `App`. The goal of this promise monad is to be able to chain async call and use the return value of the previous element. This is similar to `CompletableFuture`'s `thenApplyAsync`.

Advice:
- `Promise` should probably implement `Future` in some way
- This exercise is based on multiple implementation of `Promise` if you are stuck, have a look at: 
  - https://github.com/shelajev/promises
  - https://github.com/playframework/play1/blob/master/framework/src/play/libs/F.java
  - https://www.slideshare.net/JavaDayUA/unlocking-the-magic-of-monads-with-java-8
- Also have a look at the solution [here](../solutions/monad)
