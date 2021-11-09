# Duplication - Solutions

## one

The type of duplication was **sibling class**. We directly see that the `doExercise()` method is common to all classes, so we _pull up_ the method into the `Exercise` superclass. We notice that there are still some similarities. For example there is a common `weight` attribute in the `Benchpress` and `Deadlift` classes, and their `requiresWeight()` and `getWeight()` methods are also identical. These can be again _pulled up_ into a new superclass that we name `WeightedExercise`. Similarly, we create a `UnweightedExercise` class that can be extended by `Pushup` and `Squat`.

## two

The type of duplication was **same class**. First of all, we see that a new instance of `Scanner` is created in each method. We can _extract_ it into a static field of the class and make it available to all methods. Next, we notice that the methods have a very similar structure e.g. ask a question, get the answer and print the greeting message. We can _extract_ this behavior into separate methods. Finally, we can also make the questions we ask into constants, as they are reused ("_Please enter your first name_" is used in 3 different methods). Note that Willy Wonka probably isn't 23 years old, that was only an example.

## three

The type of duplication was **different class**. Here we notice that both classes have very similar methods: one for getting a sorted subset of a list and one for printing the result. We decide to _extract_ a new, separate class (in our case `TopNPrinter`) and _move_ the methods there. Each class can now have an instance of `TopNPrinter` and rely on it's methods.

A typical example of how different class duplication is handled are projects that work extensively with matrices (array of arrays in java). Usually, you will have a separate `Matrix` class that defines many useful methods such as `transpose`, `multiply`, `invert`, ...

## four

The type of duplication was **different algorithm**. We see that the class uses 3 different ways of computing the approximation of PI, however the result is (more or less) the same each time. We choose the simplest of the 3 algorithms and _substitute_ the other two by the one we chose. Then, we can _extract_ the approximation of PI into a separate method.

Some notes:
- Instead of having a method that does the computation each time, a better idea would be to store it in a constant
- In a real project one should just use the value `Math.PI` which is much more precise and doesn't require any computation whatsoever

## Recap

The refactoring "recipes" can be summarized as follows:

| Duplication type | "Recipe" |
| --- | --- |
| Same class or method | Extract method / constant |
| Sibling class | Pull up method / field |
| Different class | Extract class + move method |
| Different algorithm | Substitute algorithm + extract method |

![dup-sol.svg](dup-sol.svg)