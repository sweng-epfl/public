# Modularity

Consider the problem of normalizing a list of doubles (as you might have done in statistics) using the following
formula:

![](latex_normalized.png)

Functions are the most basic unit of modularity. They allow us to express computations as a black box that takes an
input and transforms it into an output. However, if the body of a function becomes too long, it becomes difficult to
encapsulate its whole behavior into its name which may lead to misunderstanding and bugs when used by another developer.

Take a look at [src/main/java/App.java](src/main/java/App.java), you will see that the developer who wrote
the `compute()` function actually fit the whole program into it and that I/O, computations and error handlers are all
over the place. Your task is to refactor `compute()` into smaller functions such that it reads like a recipe, i.e. a
list of logical, decoupled smaller steps; you should not change the signatures of the current code.