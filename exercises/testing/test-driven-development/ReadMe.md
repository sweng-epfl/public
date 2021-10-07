# Test-Driven Development

In this exercise, you will first practice writing a specification. You will then apply _Test-Driven Development_, TDD for short, in which tests help you write the code instead of only being a way to check code for correctness. The idea is simple:
- Before writing code, write tests for the simplest feature your code will have.
- Write code to make the tests pass
- Write tests for the next feature
- Write code to make these new tests pass
- ... and so on ...

This has multiple benefits:
- You are forced to think of what exactly your code should do, including edge cases, before you write the code. Thus, you cannot fall into the trap of simply writing one test per code path and forgetting about the paths that the code does not explicitly handle.
- You can get a feel for the API of the code you're writing. If writing the tests is painful, using the code outside of tests will probably be painful too.
- You work on less code at a time between testing phases, so it's easier to figure out which part of the code is responsible for test failures: the one you just wrote!
- Since you wrote the tests before the code, they are good examples of how the code should work, which can be useful to the rest of the team.


You are a new developer working on a 3D game engine *Anarchy3D™*. You are tasked with writing the new 3D vector class.

- In the class' documentation, write the specification for the `Vector3` class in informal language. This class should have at least the following capabilities:
  * You should be able to check if the vector is the zero vector
  * You should be able to compute the dot product with another vector
  * You should be able to compute the length of the vector
  * You should be able to compute the cross product with another vector
  * You should be able to normalize the vector
  * You should be able to scale the vector by a scalar
  You are free to add any other functionality that you see fit. You can also use formal language, i.e., mathematical formulas, if required. Think about the edge cases when designing the specification and how they should be handled.

Then, implement the `Vector3` class using TDD. You are free to add any methods/classes that you like.
