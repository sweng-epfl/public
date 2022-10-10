# Debugging and writing regression tests

This exercise will teach you how to use a debugger to inspect the state of a program. To this end,
you will be given a program that contains a bug. Your task is to find the bug and fix it.

In this exercise, you're working on a program that manages a list of Computer Science students.
The program simulates the workday of a bunch of students who study in buildings at EPFL.

Unfortunately, this functionality is currently broken. Your task is to fix it. The
code for the feature can be found in [App.java](src/main/java/ch/epfl/sweng/App.java).

## Task : Inspecting the memory

Your task is to investigate the issues with the simulation functionality. The simulation
is defined in [App.java](src/main/java/ch/epfl/sweng/App.java) in the `simulate()` method. While the
simulation seems to run properly, it is actually broken, since some rooms are still occupied after
the simulation is over. Your task is to:

1. Identify the bug in the code;
2. Write a regression test for the bug you found; and
3. Actually fix the bug.

Here are a few tips specific to this task:

- Your IDE probably has some tools to explore the state of the program at runtime. For example,
  IntelliJ has a "Memory View" that allows you to inspect the state of the memory at runtime. You
  can access it by clicking on the "Debug" menu when the program is paused with a breakpoint.
- Can you identify something that students who fail to leave have in common?
