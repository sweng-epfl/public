# Exercise 2

If the source code is not available, then AFL can use a binary translation
engine (QEMU) to dynamically insert coverage tracking into the executed binary.
Repeat the same fuzzing campaign from Exercise 1 by adding "`-Q`" to the fuzzer
invocation.

* What is the performance difference between running an instrumented binary and
  an uninstrumented binary?

* What is the source of this overhead?