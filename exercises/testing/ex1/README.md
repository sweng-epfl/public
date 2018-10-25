Unit and End-to-End Testing
===========================

In this section, we'll talk about the two basic kinds of tests:

- Unit tests, which test a single, small, self-contained feature
- End-to-end tests, which test an entire component

You will learn how to write both kinds of tests, how to choose between the two when writing tests, and optionally how to write Android end-to-end tests by testing the user interface. Let's go!


Unit testing
------------

Unit testing is the simplest kind of automated testing: tests for small pieces of code with clear boundaries and a clear specification.

**Exercise**: We provide a [`Calculator`](./Calculator.java) class with methods `add` and `divide`, which do what you expect them to. Write tests for this class, including interesting cases (negative/positive numbers) and the divide-by-zero edge case.

_Hint 1_: `Calculator.add` returns a `long` to avoid overflows; in your tests, you need to use long literals for the return value, e.g. `3L` instead of just `3`.

_Hint 2_: To write a test for an exception, you can surround the call to `divide` with a try-catch, and use JUnit's `fail` method to fail the test if no exception is thrown.

Done? Good. That was fairly simple, right? It may have taken you some time to write, but now you can be confident that if you or somebody else modifies the `Calculator`, there's a safety net to catch buggy code.


End-to-end testing
------------------

End-to-end testing is a different kind of tests: instead of writing a test for a specific self-contained feature, you test an entire component as a "black box", and make sure that the component does what it says.

**Exercise**: We provide a [`CalculatorProgram`](./CalculatorProgram.java) class with a `main` method, intended to be run from the command-line. It internally calls `Calculator`. The `main` method takes three arguments: a number, an operator that can be `+` or `/`, and another number. It prints the result, or `Error!` in case of a division by zero. Test the `main` method.

_Hint_: In order to test what is printed on `System.out`, use the `System.setOut` method to replace standard output with your own stream. You can then use, e.g., a `ByteArrayOutputStream` to store all printed data. See [this](https://stackoverflow.com/a/1119559/3311770) for more details.

Done? OK, that was probably a bit painful. But notice one thing: your tests do not depend at all on the `Calculator` class. Now you could refactor `CalculatorMain` to use any other way of computing the result (such as computing it directly and not calling `Calculator` at all) and still be confident that, if the tests pass, your program is correct.


Choosing what tests to write
----------------------------

Now that you are familiar with both kinds of tests, you may wonder which kind to write when. Of course, if you had infinite time, you'd write unit tests for everything, and end-to-end tests for everything, but you probably don't have infinite time. (If you do, please send the staff an email, we're very interested!)

A good rule of thumb is to write mostly unit tests, with a few end-to-end tests to make sure the component composes its sub-components correctly. Unit tests are easy to write, so you can afford to write a lot of them, but having only unit tests means that the overall program might still be incorrect, for instance because it uses the components in a way they do not support. End-to-end tests are harder to write, but provide stronger guarantees that the program indeed works as intended.


End-to-end testing on Android
----------------------------------------

If you write an Android app, or an app in general, end-to-end testing means you will test your program's user interface. For instance, you can fill in a text box, click a button, and make sure that the app behaves in the way you expect it to.

To do this on Android, you can follow [Google's official guide](https://developer.android.com/training/testing/ui-testing/espresso-testing).
