# Defensive Programming
In this exercise set, you will learn about defensive programming techniques to deliver high quality software by making the software behave in a predictable manner despite unexpected input or user actions.

### Exercises

- [The quiz](quiz/) introduces the key topics of defensive programming.

- [Exercise 1](ex1/) will teach you about **garbage-in ⇏ garbage out**. You will use techniques to protect against invalid inputs from external sources. To this end, you will be given a CSV file. This file is malformed, i.e. lines do not enforce a homogenous format for their fields. Therefore, you will need to parse the CSV file, ignore invalid lines, and produce a valid version of the file.

- [Exercise 2](ex2/) will teach you about **the interaction between defensive programming and code coverage**. This exercise will take the form of a case study. You will be given some bad, fragile code, i.e., code that breaks easily. This code will be subject to several refinements. It will evolve from being *brittle* to being *robust* while achieving *no decrease in code coverage*. This exercise will take you from the imperative to the functional paradigm while introducing Java 8's [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) container object.

- [Exercise 3](ex3/) will teach you about **error-processing routines**. You will be given a Java program that makes use of exceptions as a defense mechanism. You will need to replace exceptions with callbacks.

- [Exercise 4](ex4/) will teach you about returning **neutral values**. You will be given a Java program that uses `null` values as neutral values. You will need to modify the program so that it adopts a more objet-oriented approach of dealing with neutral values. To achieve that, you will make use of Java 8's [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html), introduced earlier.

- [Exercise 5](ex5/) will teach you about **bad input parameter checks and exceptions**. You will work on a tiny C implementation. Concretely, you will need to implement a partial version of the `string.h` library of **libc**. For this to be possible, you will be given a nearly-complete implementation of C-style strings. You will be using C-style strings to implement the `string.h` library. The implementation will need to be robust, make use of exceptions, and employ checks for input parameters.

- [Exercise 6](ex6/) will teach you about **code invariants**, in particular loop invariants. To this end, you will be asked to implement a given algorithm. You will need to prove the correctness of your implementation. For that, you will develop a loop invariant utility. You will use **assertions** and the loop invariant utility to verify the correctness of the algorithm implementation.

### Solutions

You can find the solutions [here](solutions/), but please try to solve the exercises by yourself before peeking at the solutions.
