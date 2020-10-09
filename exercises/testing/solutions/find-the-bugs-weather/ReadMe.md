# Find the bugs - advanced!

In these exercises, you will write tests to uncover bugs in code we provide.

This is a form of _regression testing_: adding tests that show the existence of bugs before you fix the bugs. The workflow is:

-   Somebody reports a bug. This can be a developer, a customer, or anyone else
-   You write a test that fails, showing the bug exists
-   You fix the bug
-   Your test now passes
-   The bug doesn't happen in any future version of the product, because there is now a test to catch it.

You may be thinking, _can't I just fix the bug first and then write a test that passes?_ The issue with doing that is that if you write the test afterwards, you can't be sure that it really catches the bug. You know that the test passes, but maybe it would have on the buggy code before you fixed it!

This time you will work on an Android application (it should remind you something 8-) ). We do not tell you where to search the bug, we instead provide you a bug report which ressembles more an user provided one.

## Bug 1
"The application does not crash but the weather information seems wrong: the temperature information seems not right."

## Bug 2
