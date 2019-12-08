# Exercise 5

In the previous exercise, we got a glimpse of sanitization through LLVM's ASan
sanitizer. ASan's scope is limited to a subset of memory error categories. In
particular, it cannot detect uninitialized memory reads.

But fear not! LLVM has many other sanitization tools at the programmer's
disposal. The goal of this lab is to get familiar with another one of the
built-in sanitizers from the LLVM compiler infrastructure,
[MemorySanitizer](https://github.com/google/sanitizers/wiki/MemorySanitizer)
(MSan).

## MemorySanitizer (MSan)

MSan flags uninitialized memory reads which can occur when a stack- or a
heap-allocated memory is read before it is written. It basically implements a
subset of the Valgrind Memcheck tool’s functionality as it also supports
bit-exact uninitialized value tracking. An important point about MSan’s
instrumentation is that uninitialized value reads are only reported when MSan
detects cases where such values affect program execution.

You can compile your code with MSan by using the `-fsanitize=memory` flag to
the `clang` compiler. You can learn more about MSan
[here](https://github.com/google/sanitizers/wiki/MemorySanitizer).

## Task: Evaluating MSan

* What CWE classes can the sanitizer theoretically detect? Read the description
  of the sanitizers and discuss which CWE classes are covered.

* Modify the regex in `create_single_asan_Makefile.py` to compile the test
  classes you think the sanitizer should cover.

* Run the test cases and discuss what how many false positives (the sanitizer
  reports a violation even though the program is legal) and false negatives (the
  sanitizer misses a violation) you have. What could be the reason for the false
  positives or false negatives? Discuss an example of a false positive/negative.
