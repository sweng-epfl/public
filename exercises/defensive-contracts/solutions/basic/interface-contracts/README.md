# Solution - Interface Contracts

### Exercise 1. Fibonacci and pre-conditions
The bug was in handling the base case (see the code). `apply` called itself recursively until it would reach a negative argument, at which point the precondition thrown an exception.

### Exercise 2. Dynamic Vector
The first bug was that we stored a `0` instead of an `element` in the array.
The second bug was in the `expandArray` method.
Additional postcondition for this method allowed us to locate the bug quickly, limiting the scope by just one method body, instead of the whole class.

### Exercise 3. Fraction
See [Fraction](src/main/java/ch/epfl/sweng/contracts/Fraction.java) and [FractionTests](src/test/java/ch/epfl/sweng/contracts/FractionTests.java).

### Exercise 4. Stack
See [SwengStack](src/main/java/ch/epfl/sweng/contracts/SwengStack.java) and [SwengStackTests](src/test/java/ch/epfl/sweng/contracts/SwengStackTests.java)
