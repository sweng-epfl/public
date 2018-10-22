Regression testing
==================

Now that you know how to write tests, let's look at a practical use of tests during development. 

In general, you will want to write some tests before or after writing a piece of code to make sure it behaves as you expect. If you are a test-driven-development practitioner, you will write tests while writing the code itself. But what if you didn't think of an edge case, and your program has a bug that tests did not catch? Or if you are working on legacy code that does not have any tests, and you are tasked with fixing a bug reported by a customer?

This is where _regression testing_ comes in: adding tests that show the existence of bugs before you fix the bugs. The workflow is:

- Somebody reports a bug. This can be a developer, a customer if the product is released, or anyone else.
- You write a test that fails, showing the bug exists
- You fix the bug
- Your test now passes
- The bug doesn't happen any more in any future version of the product, because there is now a test to catch it.

You may be thinking, _can't I just fix the bug first and then write a test that passes?_ The issue with doing that is that if you write the test afterwards, you can't be sure that it really catches the bug. You know that the test passes, but maybe it would have passed before too, on the buggy code before you fixed it!

**Exercise**: We provide a [`Person`](./Person.java) class, with various methods and some tests. `Person` has a method `isMinor()` that is supposed to return whether the person is under 18. However, the method has a bug. Write a test to demonstrate the bug, then go ahead and fix the bug, and then check that your test passes.

**Exercise**: The [`Person`](./Person.java) class has another bug. Find it, write a test to demonstrate its existence, fix it, and make sure the test passes.
