# Solution: Modularity - Basics

### Part 1 : rewriting long code

There are five main tasks which are being done:

-   Loading the numbers
-   Computing the mean
-   Computing the standard deviation
-   Normalizing the values
-   Writing the results

We can split this behavior into separate functions, making it much easier to test and develop. It also has the benefit of making it significantly easier to understand.

See [src/main/java/App.java](src/main/java/App.java) for an example solution.

### Part 2 : testing

See [src/test/java/AppTest.java](src/test/java/AppTest.java) for tests. Note that your tests may be different depending on how you modularized your code.
