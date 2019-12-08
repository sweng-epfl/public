# Find the bugs!

In these exercises, you will write tests to uncover bugs in code we provide.

This is a form of _regression testing_: adding tests that show the existence of bugs before you fix the bugs. The workflow is:

-   Somebody reports a bug. This can be a developer, a customer, or anyone else
-   You write a test that fails, showing the bug exists
-   You fix the bug
-   Your test now passes
-   The bug doesn't happen in any future version of the product, because there is now a test to catch it.

You may be thinking, _can't I just fix the bug first and then write a test that passes?_ The issue with doing that is that if you write the test afterwards, you can't be sure that it really catches the bug. You know that the test passes, but maybe it would have on the buggy code before you fixed it!


## People and their age

Let's start with a simple [`Person` class](src/main/java/Person.java) that implements the basics of a person: first name, last name, and age.

`Person` has a method `isMinor()` that is supposed to return whether the person is under 18. However, the method has a bug. Write a test to demonstrate the bug, then fix the bug, then check that your test passes.


## The address book

The [`AddressBook` class](src/main/java/AddressBook.java) maps people to their address, with methods to add, update and delete a person-to-address mapping, as well as to pretty-print the entire book.

However, there is a bug somewhere in the codebase that will manifest itself when you write tests for `AddressBook`. Find it, fix the bug, then check that your tests pass.


## The work log

People sometimes need to track how many hours they've worked. The [`WorkLog` class](src/main/java/WorkLog.java) takes care of this: a person can tell the log they've started working, then later tell the log they've stopped working, and the log remembers how long they worked.

There is an important but subtle bug in this class, that would only appear very rarely in production.
This time, you're allowed to change the `WorkLog` private members and constructor (but not other methods) in order to write proper tests. Find the bug, fix it, then check that your tests pass.
