# Software Engineering - Final Exam

## Part 1: Theory [40 points]

This part of the exam has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`.

Provide the answers in this file and commit the file to the `master` branch of this `student-GASPAR` repo.
Do not change the name of the file, and do not change the existing text.
We will only grade the `master` branch.
Do not introduce extraneous spaces or stray characters, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.
You must change each one to either `[y]` for a yes answer or to `[n]` for a no answer.
This requires that you replace the space between all the brackets with either `y` or `n`.
Nothing else will be accepted.
Answers such as `[o]`, `[N]` (uppercase n), `[x]`, or variations thereof will earn you 0 points.
Unless otherwise specified, questions may have zero, one, or more correct answer choices.
If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

Note that, in grading, choices that are more difficult to get right receive a higher fraction of the points allotted to that question than other choices.
As a result, you must be careful if you choose to make educated guesses.

Every time you push a version of this file to the `master` branch, GitHub Actions will do a simple syntactic check.
Please check the `Actions` tab in the web interface to your repo (the workflow step called _Build and run checks_) to make sure it's OK.
If the file does not pass the syntactic check there, it will not be graded by our scripts, and you will likely lose a lot or all of the points for this part of the exam.

### Question 1 [2 points]

As a software engineer, you have a responsibility to watch for ethical issues that your work might bring about.
At which stage is it acceptable for you to point out these ethical issues?

- [ ] Before accepting to do the work
- [ ] While doing the work
- [ ] When delivering the work
- [ ] After the work is delivered


### Question 2 [4 points]

Consider the following code that tests whether a number is prime:

```java
boolean isPrime(int n) {
  if (n < 2)
    return false;
  boolean result = true;
  for (int i = 2; i <= Math.sqrt(n); ++i) {
    if (n % i == 0) {
      result = false;
    }
  }
  return result;
}
```

Which of the following are good performance optimizations to apply in this case (i.e., they are likely to improve the performance of `isPrime` without changing its semantics)? If you think there are both circumstances under which performance could improve and circumstances under which performance would be unaffected, then answer `y`.

- [ ] Doubling the program's default stack size via a `java -Xss...` command-line flag
- [ ] Storing the result of `Math.sqrt(n)` in a variable
- [ ] Replacing the `result = false` statement with `return false`
- [ ] Switching the type of the parameter from `int n` to `long n`
- [ ] Switching the type of the parameter from `int n` to `short n`
- [ ] Replacing the loop condition with `i * i <= n`
- [ ] Changing the loop so that, after the first iteration (`i`=2), it skips all even numbers


### Question 3 [4 points]

During your internship at BarBaz Corp., you develop an app with a user interface.
The Product Owner says that it must be possible for users to record their preferred color scheme.
Your app now needs to communicate with an external service `FavoriteColorService`, which keeps track of users' favorite colors.
The app generates the favorite colors as `ArrayList<Color> colors`, where `Color` is a [built-in class](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/Color.html).
However, `FavoriteColorService` accepts only raw hexadecimal values.

Which design pattern would you use to connect your app to the service? There is exactly one correct choice.

- [ ] Adapter
- [ ] Singleton
- [ ] Decorator
- [ ] Factory
- [ ] Composite
- [ ] Observer


### Question 4 [3 points]

Consider the following complicated way to compute the sum of the numbers in a list:

```java
public static double sumList(List<Double> numbers) {
  if (numbers.size() <= 1) {
    return numbers.get(0);
  }

  return numbers.stream().mapToDouble(a -> a).sum();
}
```

The following test passes:

```java
@Test
public void testSumList() {
  assertEquals(sumList(List.of(5D)), 5D);
  assertEquals(sumList(List.of(5D, 10D)), 15D);
}
```

By looking at the code and the test, which of the following statements do you conclude to be correct?

- [ ] `sumList` is bug-free
- [ ] `sumList` has a bug
- [ ] `testSumList` does not cover all relevant cases
- [ ] There exists a test that evidences a bug in `sumList`
- [ ] Whatever bugs `sumList` has left cannot be evidenced via a unit test

(_Relevant cases_ are test cases with "interesting" values.
As you know, it is intractable to test all the possible inputs to this method.
However, with a few carefully picked relevant cases that are more likely to trigger bugs, one has a better chance of uncovering bugs.)


### Question 5 [5 points]

Consider the function `abs` and the `testAbs` test below it:

```java
public static int abs(int x) {
  if (x < 0) {
    return -x;
  }
  return x;
}

@Test
public void testAbs() {
  assertEquals(abs(-5), 5);
}
```

We ask you to compute line coverage.
The method signature, including the first `{`, is not counted as a line.
Lines that only have a brace don't count (i.e., `{` or `}`).
All the other lines of the method are counted.
So, the `abs` method has 3 lines.

What coverage does this test achieve on this method? (For simplicity, assume that 1/3=0.33 and 2/3=0.66)

- [ ] 100% line coverage, 100% branch coverage
- [ ] 100% line coverage, 50% branch coverage
- [ ] 66% line coverage, 100% branch coverage
- [ ] 66% line coverage, 50% branch coverage
- [ ] 33% line coverage, 100% branch coverage
- [ ] 33% line coverage, 50% branch coverage


### Question 6 [5 points]

You are put in charge of fixing all the bugs in a program that has _N_ lines of code (LOC).
You wonder whether to start fixing the bugs one by one, or to first refactor the program into _K_ independent modules of _M_ LOC each, and only afterward start fixing the bugs in the modules.
Assume the following idealized properties hold:

- a block of code with _n_ lines has _n^2_ bugs before you start debugging it, regardless of refactoring
- fixing a specific bug in a block of _n_ LOC takes _n_ minutes
- fixing a bug does not change the number of LOC
- fixing a bug does not introduce a new bug or make some other bug go away
- if a group of modules are independent, then no bugs can result from the interactions between these modules

Your goal is to finish fixing all the bugs as quickly as possible, i.e., you're trying to achieve the shortest total time to fix all bugs.
What is the _tightest upper bound_ on your _refactoring time_ that would make it worthwhile to first refactor before starting to fix the bugs?

- [ ] N^2/K + K^2
- [ ] N^2/K + M^2
- [ ] N^3 - K*M^3
- [ ] N^3/K + K
- [ ] N^3 + K*M^3
- [ ] N^3/K + M^3


### Question 7 [6 points]

In class we described the evolution of version control systems over time.
Which of the following statements are true of a 3rd-generation VCS (V3) versus a 2nd-generation VCS (V2)? Make sure you think of the VCS as described in class, not of a specific product (like Git) that may be only an approximate instance of the VCS.

- [ ] if you lose your network connectivity for an extended period of time, a V3 allows you to do more work during that period than a V2
- [ ] controlling source code access on a per-file basis is easier with a V2 than a V3
- [ ] if you want to work for the first time on a mature code repository located remotely on the Internet, a V3 requires significantly more network traffic than a V2
- [ ] when you version-control large binary files using a V3, other users of that repository would get considerably more upset with you than if you were all using a V2
- [ ] having a good data-backup solution for the repository is more important when using V2 than when using V3


### Question 8 [3 points]

You're developing an app for a new film streaming service called SwEngFlix.
Which of the following are good user stories?

- [ ] As a user, I want to be able to sign in hassle-free
- [ ] As a Google account owner, I want to be able to sign in using my Gmail address
- [ ] As a developer, I want to finalize the login screen's implementation so that users can sign in using their existing Google account
- [ ] As a series-binger, I want the recommended shows to appear on the top row of my screen, along with a horizontal scrolling layout with a dark background
- [ ] As an occasional user, I want to see where I last stopped, so that I can resume where I left off and keep track of my watching history


### Question 9 [4 points]

Which of the following actions are legal in terms of software licensing?

- [ ] I take publicly available source code from GitHub, modify it, and then redistribute it unless it has a license that explicitly forbids such redistribution
- [ ] Same as above, except that I redistribute the modified code via GitHub
- [ ] I use GPL-licensed code in my software, and I sell the software (i.e., charge money for it)
- [ ] I use GPL-licensed code in my software, but I do not distribute the source code along with the compiled software
- [ ] I add MIT-licensed code to an existing GPL-licensed codebase, and then distribute the whole thing under a GPL license
- [ ] Same as above, except that the added code is BSD-licensed instead of MIT-licensed
- [ ] I add GPL-licensed code to an existing MIT-licensed codebase, and then distribute the whole thing under an MIT license


### Question 10 [4 points]

You are a member of a software development team at FooBar Inc., working on a new app for an important client.
During lunch in the company cafeteria, a high-ranking executive of the company approaches you and asks you to squeeze in an extra feature to be done quickly, before the end of the ongoing Sprint.
Which of the following actions are compatible with the Scrum framework?

- [ ] You decline outright by explaining that Scrum does not allow you to do anything about it.
- [ ] You agree to do it, because it is normal for someone who is hierarchically above you and who understands the business and the market to issue such orders.
- [ ] Since you already finished all your tasks for the Sprint, you agree to do it before the end of the Sprint.
- [ ] Same as above, but you first add the issue to the Sprint backlog before working on it.
- [ ] You tell the executive that you will give this information about customer needs to the Product Owner the next time you see her.
- [ ] Same as above, but you also add the issue to the Product Backlog.
- [ ] You inform your team's Scrum Master.

