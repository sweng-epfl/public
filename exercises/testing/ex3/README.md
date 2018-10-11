Black-Box Testing and TDD
-----------------------

Tests are not just a way to check code correctness, they can also help you write the code in the first place. This is what _Test-Driven Development_ (_TDD_) is all about. The idea is simple:

- Before writing code, write tests for the simplest feature your code will have.
- Write enough code to makes the tests pass but no more
- Write tests for the next feature
- Write enough code to makes these new tests pass but no more
- ... and so on ...

This has multiple benefits:

- You are forced to think of what exactly your code should do, including edge cases, before you write the code. Thus, you cannot fall into the trap of simply writing one test per code path and forgetting about the paths that the code does not explicitly handle.
- You can get a feel for the API of the code you're writing. If writing the tests is painful, using the code outside of tests will probably be painful too.
- You work on less code at a time between testing phases, so it's easier to figure out which part of the code is responsible for test failures: the one you just wrote!
- Since you wrote the tests before the code, they are good examples of how the code should work, which can be useful to the rest of the team.

In this exercise, you will practice Test-Driven Development.

## Instructions

You can open this exercise set and start working on it using Android Studio/IntelliJ IDEA. The step to open this exercise is as follows:

1. File -> Open 
2. Point to the directory of this exercise (the parent folder of this README.md)

- [`Part 1`](src/ch/epfl/sweng/testing/part1/README.md)
- [`Part 2`](src/ch/epfl/sweng/testing/part2/README.md)
- [`Part 3`](src/ch/epfl/sweng/testing/part3/README.md)
