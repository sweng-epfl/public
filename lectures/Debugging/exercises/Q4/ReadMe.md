# Debugging and writing regression tests

This exercise will teach you how to use a debugger to find and fix bugs in your code. To this end,
you will be given a program that contains multiple bugs. Therefore, you will need to use a debugger
to investigate, reproduce, and fix the bugs.

A particular emphasis should be placed on writing regression tests for the bugs you find. Regression
tests are tests that are designed to fail when a bug is present, and pass when the bug is fixed.
You are expected to write regression tests for the bugs you find in this exercise.

## Task : Breakpoints

In this exercise, you're working on a program that sorts a list of numbers. Unfortunately, this
functionality is currently broken.

Your task is to investigate the issues with the sorting algorithm. There are 3 bugs in
the [Quicksort](src/main/java/ch/epfl/sweng/Quicksort.java) class. You have to:

1. Identify the bugs in the code;
2. Write a regression test for the bugs you find
   in [QuicksortTest.java](src/test/java/ch/epfl/sweng/QuicksortTest.java). A regression test is a
   test that will fail when the bug is present, and pass when the bug is fixed; and
3. Actually fix the bugs.

The implemented sorting algorithm is called [Quicksort](https://en.wikipedia.org/wiki/Quicksort).
Quicksort is a divide-and-conquer algorithm that sorts a list of elements by recursively
partitioning the list into two sublists, sorting the sublists, and then combining the two sublists
into a single sorted list.

In order to efficiently identify the bugs, here are a few tips:

- Use breakpoints to stop the execution of the program at a particular point in the code. Your IDE
  probably has some tools to set breakpoints. For example, you can set breakpoints in IntelliJ by
  clicking on the left margin of the code editor.
- Use the debugger to inspect the state of the program at the point where the execution is
  stopped. You can step through the code line by line, inspect the values of variables and arrays,
  and evaluate expressions.
