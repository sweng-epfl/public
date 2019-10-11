# Fuzzing

Fuzzing is an advanced testing technique consisting of sending random inputs to a program to find bugs.

While this may seem odd at first glance, it has shown to be very effective especially in low-level languages where fuzzing finds crashes and undefined behavior quickly.

Advanced fuzzers take this further by using heuristics to generate inputs, instead of pure randomness, and by instrumenting binaries to detect what code is executed given an input in order to distinguish categories of inputs.

Write a simple random fuzzer for the following function:

```java
  public static float mystery(int a, float b, boolean c) {
      if (b == 1) {
          throw new RuntimeException("b can't be 1! Why? Why not!");
      }
      float result;
      if (a / Math.hypot(a, b) == (c ? a : b)) {
          result = b / a;
      } else if (b + a == b) {
          result = b / b;
      } else {
          result = a / b;
      }
      if (c && Float.isNaN(result)) {
          throw new RuntimeException("Oh no, the result isn't a number!");
      }
      return result;
  }
```

How many different crashes can you find?

In Java, a crash is an unhandled exception. (You will not find segfaults and such in Java, unless you somehow managed to make the Java Virtual Machine crash, which would be a big deal)

Crashes are considered different if they do not result from the same statement/expression, e.g. finding two ways to make the denominator zero in the same division expression is only one crash.


Now that you have learned about fuzzing, download [AFL](http://lcamtuf.coredump.cx/afl/), one of the most well-known fuzzers, and find bugs in the [LAVA-M](https://sites.google.com/site/steelix2017/home/lava) corpus of buggy programs.
Or find bugs in your favorite program, and report them to the developers!
