# Exercise 6

## UndefinedBehaviorSanitizer (UBSan)

The [UBSan](https://clang.llvm.org/docs/UndefinedBehaviorSanitizer.html)
sanitizer makes the presence of undefined behavior explicit by stopping programs
when such cases occur. C and C++ have a very long list of operations which are
labelled as undefined behavior and UBSan has an impressive list of events that
it can detect. We list just a few of the things that can be flagged:

- Use of misaligned pointer / creating misaligned reference
- Out of bounds array indexing
- Enum overflows
- Division by zero
- Implicit sign changes between types
- Null pointer dereference
- Return from function without return value

You can compile your code with UBSan by using the `-fsanitize=undefined` flag to
the `clang` compiler. You can learn more about UBSan
[here](https://clang.llvm.org/docs/UndefinedBehaviorSanitizer.html).

## Task: Evaluating UBSan

* What CWE classes can the sanitizer theoretically detect? Read the description
  of the sanitizers and discuss which CWE classes are covered.

* Modify the regex in `create_single_asan_Makefile.py` to compile the test
  classes you think the sanitizer should cover.

* Run the test cases and discuss what how many false positives (the sanitizer
  reports a violation even though the program is legal) and false negatives (the
  sanitizer misses a violation) you have. What could be the reason for the false
  positives or false negatives? Discuss an example of a false positive/negative.
