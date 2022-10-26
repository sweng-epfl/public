# Design and refactoring

This exercise will teach you how to extract abstractions from existing code. To this end, you will
be given a program that contains multiple common bad design practices. You will need to refactor the
code to make it more readable and maintainable.

## Task

In this exercise, you're working on a program that lets you manage the balance of a bank account.
The balance of the account can be increased or decreased by arbitrary amounts of money in various
currency units. The program also lets you convert the balance to a different currency unit.

However, this program suffers from multiple bad design practices. Your task is to refactor the code
to eliminate the code smells present in [src/main/java/App.java](src/main/java/App.java). In
particular, you should:

1. Identify repeating patterns in the code; and
2. Carefully refactor the code to introduce better abstractions.
