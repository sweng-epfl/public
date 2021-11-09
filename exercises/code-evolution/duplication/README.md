# Duplication

Duplication is probably the most common code smell there is. Although it can be hard to eliminate it completely, it is important to minimize it.

Duplication or "copypasta" code comes in various flavors:

- same class or method
- sibling class
- different class
- different algorithm

![dup.svg](dup.svg)

The duplicated code is indicated in orange. The lighter orange indicates code that isn't actual duplication but its behavior is the same as another code's behavior.

* * *

**Exercise:** We are now going to tackle each of the scenarios. There are 4 small gradle projects named `one`, `two`, `three` and `four`. For each one do the following:

- quickly go through the different classes and get familiar with them
- find the duplication and identify which type of duplication you are dealing with
- refactor the code
- say which techniques you used (extract class, pull up method, substitute algorithm, ...)
- come up with a refactoring "recipe" i.e. _"if I have this type of duplication, I can apply this/these techniques: ..."_