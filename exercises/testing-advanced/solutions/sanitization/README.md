# Sanitization

## Introduction

Even the best programmers inevitably write code that violates some security
policy. Though the best course of action would be to manually patch existing
codebases to eliminate security violations, it is unlikely for such an outcome
to ever happen due to the sheer amount of legacy code that exists.

Sanitizers are a possible solution to this problem as they provide
an automatic way of enforcing certain security policies. Most sanitizers are implemented in two parts:

1. a compiler pass or dynamic binary translation that instruments existing code,
   and
2. runtime checks to enforce the desired security policy.

The goal of this lab is
to get familiar with 3 built-in sanitizers from the LLVM compiler
infrastructure:

- AddressSanitizer (ASan)
- MemorySanitizer (Msan)
- UndefinedBehaviorSanitizer (UBSan)

## Machine setup & methodology

We tested our 3 sanitizers on version 1.3 of the Juliet test suite, a collection
of thousands of isolated test cases in C/C++ that are representative of over
hundred programming error classes. Summary statistics are provided at the end of
each sanitizer’s execution with which one can tell whether the sanitizer was
successful in enforcing the security policy for the given CWE.

The structure of the provided test suite makes it possible to run multiple CWEs
through a given sanitizer at the same time. However, doing so would mix up the
statistics of the various CWEs and it would be difficult to assess which CWE the
sanitizer was actually able to defend against. We therefore decided to run run
each sanitizer with a single CWE in each experiment so we can have accurate
false positive / false negative numbers of the given sanitization.

Running the sanitizer on a CWE requires manual tweaks in a Makefile generator
followed by an occasional long wait while the selected test cases are compiled
and run. In the interest of time and to avoid copy-paste mistakes, we wrote a
python script that automates this procedure. The script takes as input (1) a
list of CWEs to test, and (2) the name of the sanitizer to run. It then
automatically creates a directory labelled with the given CWE number and makes
the necessary modifications to the Makefile generator before compiling and
running the test cases with the target sanitizer’s added instrumentation.
`stdout` and `stderr` were logged so we could programmatically analyze the
results afterwards.

All sanitization experiments were run in parallel on a dual-socket Intel Xeon
E5-2680 v3 (24 cores, 48 threads, 2.50GHz) and the results for our selected set
of CWEs were all available after 28 minutes.

## AddressSanitizer (ASan)

ASan’s basic working principle is to replace `malloc` and `free` with custom
versions that poison memory regions. Memory surrounding each `malloc`-ed region
is poisoned, whereas `free`-ed memory is quarantined in addition to being
poisoned. Memory accesses to poisoned regions trigger an error and cause the
program to terminate.  This means that ASan enforces temporal and spatial
safety. The documentation specifically mentions ASan’s ability to thwart the
following vulnerabilities:

- Use after free (dangling pointer dereference)
- Heap buffer overlow
- Stack buffer overflow
- Global buffer overflow
- Use after return
- Use after scope
- Initialization order bugs
- Memory leaks

Given the defenses above, we selected the following CWEs for our evaluation:

- CWE 121: Stack-based buffer overflow
- CWE 122: Heap-based buffer overflow
- CWE 401: Memory leaks
- CWE 416: Use after free
- CWE 562: Return of stack variable address
- CWE 588: Attempt to Access Child of Non Structure
- CWE 590: Free Memory not on heap
- CWE 690: NULL deref from return

We consider that use after scope and use after return attacks correspond to CWE
562. We could not find any CWEs that refer to global buffer overflows or to
initialization order bugs, so these remain untested in our evaluation.

The table below shows the summary statistics regarding ASan’s ability to flag
the tested CWEs (measured through true negative, false negative, true positive,
and false positive ratios). ASan has a relatively high true negative ratio
(above 80% except for CWE 562) and a very high true positive ratio (above 97%
except for CWE 562).

| CWE | Bad tests (TN %) | Bad tests (FN %) | Good tests (TP %) | Good tests (FP %) |
|-----|------------------|------------------|-------------------|-------------------|
| 121 | 97               | 3                | 100               | 0                 |
| 122 | 83               | 17               | 97                | 3                 |
| 401 | 80               | 20               | 100               | 0                 |
| 416 | 96               | 4                | 0                 | 100               |
| 562 | 0                | 100              | 100               | 0                 |
| 588 | 100              | 0                | 100               | 0                 |
| 590 | 100              | 0                | 100               | 0                 |
| 690 | 0                | 100              | 100               | 0                 |

The code snippet below shows the extreme false negative ratio that appeared with
CWE 562 (note that there is only 1 test case in this CWE, so the 100% false
negative ratio is a consequence of the small sample size).

```C
static char *helperBad()
{
    char charString[] = "helperBad string";

    /* FLAW: returning stack-allocated buffer */
    return charString; /* this may generate a warning -- it's on purpose */
}
```

We can understand better why this case was flagged incorrectly by ASan. Indeed,
the charString variable is not on the heap since it isn’t part of any
`malloc`-ed memory space, but rather points to a statically-allocated string. So
it is normal that ASan was not able to detect such a code segment as violating
spatial safety since no poisoned memory is accessed.

## MemorySanitizer (MSan)

MSan flags uninitialized memory reads which can occur when a stack- or a
heap-allocated memory is read before it is written (MSan has experimental
support for use-after-destruction detection, but we did not test this feature.)
It basically implements a subset of the Valgrind Memcheck tool's functionality
as it also supports bit-exact uninitialized value tracking. An important point
about MSan's instrumentation is that uninitialized value reads are only reported
when "MSan detects cases where such values affect program execution".

Given the safety policy described above, we selected the following CWEs for
our evaluation:

- CWE 457: Use of Uninitialized variable
- CWE 665: Improper initialization

The table below shows the summary statistics regarding MSan's ability to flag
the tested CWEs. MSan has perfect true positive ratios, but it is unfortunately
incapable of flagging any true negatives.

| CWE | Bad tests (TN %) | Bad tests (FN %) | Good tests (TP %) | Good tests (FP %) |
|-----|------------------|------------------|-------------------|-------------------|
| 457 |                0 |              100 |               100 |                 0 |
| 665 |                0 |              100 |               100 |                 0 |

## UndefinedBehaviorSanitizer (UBSan)

The UBSan sanitizer makes the presence of undefined program behavior explicit by
stopping programs when such cases occur. C and C++ have a very long list of
operations which are labelled as being undefined behavior and UBSan has an
impressive list of events that it can detect. We list just a few of the things
that can be flagged:

- Use of misaligned pointer / creating misaligned reference
- Out of bounds array indexing
- Enum overflows
- Division by zero
- Implicit sign changes between types
- Null pointer dereference
- Return from function without return value

We chose to test UBSan on the following CWEs:

- CWE 369: Divide by zero
- CWE 415: Double free
- CWE 416: Use after free
- CWE 440: Expected behavior violation
- CWE 475: Undefined beavior for input to API
- CWE 476: Null pointer dereference
- CWE 588: Attempt to Access Child of Non Structure
- CWE 758: Undefined behavior
- CWE 761: Free pointer not at start of buffer

We compiled our test cases with the `-fsanitize=undefined` compiler flag as it
enables all UBSan's checks other than (1) integer overflows, (2) implicit
conversions, and (3) nullability checks. We omitted these checks as most code
actually depends on such behavior. For example, reverse iterating a vector by
using indices will always trigger an integer overlow, but this is expected
behavior. We hyptothesize that the UBSan creators had similar thoughts which
explains why the `-fsanitize=undefined` flag disables integer-overflow checks.

The table below shows summary statistics for UBSan when run on the CWEs listed
above. UBSan has excellent true positive ratios (100%), but it doesn't do too
well on false negatives (most ratios are between 75-100%).

| CWE | Bad tests (TN %) | Bad tests (FN %) | Good tests (TP %) | Good tests (FP %) |
|-----|------------------|------------------|-------------------|-------------------|
| 369 |               15 |              85  |               100 |                 0 |
| 415 |               0  |              100 |               100 |                 0 |
| 416 |               0  |              100 |               100 |                 0 |
| 475 |               0  |              100 |               100 |                 0 |
| 476 |               92 |              8   |               100 |                 0 |
| 588 |               0  |              100 |               100 |                 0 |
| 758 |               25 |              75  |               100 |                 0 |
| 761 |               0  |              100 |               100 |                 0 |
