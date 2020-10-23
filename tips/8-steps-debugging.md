# How to debug (small) programs<sup>1</sup>

## Disciplined approach

__Symptom__: Program behaves in an unexpected way – it hangs, outputs the wrong thing, etc.
For quick & dirty ways to debug, check out the student-contributed ideas further down. For techniques specific to Android Studio, check out [this tutorial](https://docs.google.com/document/d/1XjJeJ4codngKfGoIG-hYT4sGCxxFReAkbw4IV77Tcqw/edit). 

__Step 1__: Turn on all warnings. This means all Java compiler warnings, as well as [Android Lint](http://ericlippert.com/2014/03/05/how-to-debug-small-programs/). There is rarely a good reason for your program to produce even a single warning. It's basically saying that your code compiles, but may not do what you think it does. Read every warning carefully; if you don't understand it, read up or ask around. If unsuccessful, move to Step 2.

__Step 2__: The Rubber Duck<sup>2</sup>. Get a rubber duck, place it next to you, and explain to the duck why each line of each method in your program is correct (preferably not in a busy public place). At some point you may be unable to do so, either because you don’t understand the method you wrote, or because it’s wrong, or both. Concentrate your efforts on that method; that’s probably where the bug is. In describing what the code is supposed to do and observing what it actually does, things will become clearer. The benefit of a rubber duck is that you don't bother anyone else, and the duck won't judge you if you made a stupid mistake. If your program compiles cleanly and the duck doesn’t raise any major objections, yet there is still a problem, move to Step 3.

__Step 3__: Break program up into smaller methods, each of which does exactly one logical operation. Smaller methods are easier to understand and therefore easier (for both you and the duck) to see the bugs. For each small method, write in a comment what the method does, what constitutes a legal input, what the expected outputs look like, what error cases arise and what are the corresponding exceptions, etc. Let the rubber duck follow along.

__Step 4__: Pre- and postconditions. Annotate the comments you wrote above with all the preconditions and postconditions of every method. A precondition is a thing that has to be true to enable the method body to run correctly (e.g., "list L is sorted in ascending order", or "value of V is no greater than 100"). A postcondition is a thing that must be true after the method body has run (e.g., "list L is sorted in descending order") . As you state the preconditions and postconditions, think whether they are indeed always true.

__Step 5__: Assertions. Use `assert` statements to verify your preconditions and postconditions. Enable them by passing `-ae` to the compiler, or setting the `debug.assert` property to 1 (in config file on the phone or using adb). Put the precondition assertions at the top of the method body and the postconditions before the method returns. Rerun the program; if an assertion fires, you will have located the problem.

__Step 6__: Whitebox tests cases. Write test cases for each method to verify that it is behaving correctly. Test each method in isolation until you are comfortable that it is correct. Focus on corner cases (e.g., if your method sorts lists, try the empty list, a list with one item, two items, many identical items, all items in backward order).

__Step 7__: Line-by-line debugging. For each line of code, write a comment describing what you expect that line to do in the particular scenario in which your program is broken (i.e., the symptom at the top of this doc). Use a [debugger](https://developer.android.com/studio/debug/) to step through every line of code; at each step, examine program variables to verify that the code indeed does what you state in the comment. Look not only for things it does differently from what you wrote in the comment but also for things it does in addition to the comment (i.e., the comment didn't capture the full behavior). When you find a discrepancy, determine whether it is the comment or the code that is wrong. Think very carefully about side effects.

__Step 8__: Ask a buddy. If you still haven't figured out the problem, ask for help.

The next time you code, consider doing some of these steps (e.g., pre- and postconditions) before even writing the code itself; you are much less likely to introduce bugs this way.

---

<sup>1</sup> Paraphrased from [http://ericlippert.com/2014/03/05/how-to-debug-small-programs/](http://ericlippert.com/2014/03/05/how-to-debug-small-programs/)

<sup>2</sup> Based on "The Pragmatic Programmer: From Journeyman to Master" by Andrew Hunt and David Thomas

---

## Quick & dirty approach

_(Student contribution)_

These techniques can be faster than the more disciplined approach to finding a particular bug, but it does not improve the overall code quality, or prevent you from introducing other bugs. In the normal development cycle, you should always stick to the disciplined approach.

__Prologue: Test case__: First of all you need a (preferably automatic) scenario that triggers the bug. You are going to try a multitude of variations of your source code, so you need a clear input sequence and a strict criterion that would judge each time, whether the bug is still there or not.

__Step 1__: VCS dichotomy. Modern version control systems offer a great debug utility, called dichotomy (or bisection). The dichotomy finds the exact commit that introduced the bug. Here it is helpful to have an automated test. However, if you can notmake the test automated, you can still run dichotomy and manually test each version chosen by VCS. Unfortunately the dichotomy power is limited by the granularity of commits. Other limiting factors are lack of necessary features in early commits, radical change in the project run environment and broken builds! When you reach the commit that introduced the bug, if you still can not see the mistake, continue to the next step. E.g. [`git bisect`](http://webchick.net/node/99), [`svn bisect`](https://github.com/Infinoid/svn-bisect).

__Step 2__: Divide and conquer. Here we need to minimize to total amount of suspected code. Chop off chunks of the code, that are not relevant to the bug. Suppose you have game which consists of a menu screen and an action screen. And some unit on the action screen does not draw as expected. Delete completely all the code related to the menu, and the menu screen itself. Provide hardcoded values for the parameters that the menu might have passed to the action screen. Then delete the code that draws all the other units on the screen. Then delete all the game mechanics, leaving only the unit-drawing part.

Keep cutting the code. On each step, identify a piece of code that is probably not related to the bug, and delete it, replacing it with something trivial, but sufficient to keep the rest of the code running.

If the bug is still there, commit the reduced version in your VCS.

Every step will decrease the total suspected code size. As this can not continue infinitely, sooner or later you will have one of two possible outcomes:

- At some step, the bug disappears. Hooray! You just identified the bad code that was causing the bug. Now try to remove other parts, preserving the bug.
- You reach an irreducible code. Any other piece that you remove either prevents the program to run, or removes the bug, but you still do not understand why. At this point you should have a tiny code fragment, so called [Minimal Working Example](https://en.wikipedia.org/wiki/Minimal_Working_Example) that demonstrates your problem. With this example you can go and ask around your colleagues, StackOverflow, TAs, etc.