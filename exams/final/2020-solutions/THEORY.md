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

- [y] Before accepting to do the work
- [y] While doing the work
- [y] When delivering the work
- [y] After the work is delivered

> A software engineer is responsible to watch for ethical issues at all times. It is always acceptable to point these out, though earlier is better.

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

- [n] Doubling the program's default stack size via a `java -Xss...` command-line flag
- [y] Storing the result of `Math.sqrt(n)` in a variable
- [y] Replacing the `result = false` statement with `return false`
- [n] Switching the type of the parameter from `int n` to `long n`
- [n] Switching the type of the parameter from `int n` to `short n`
- [y] Replacing the loop condition with `i * i <= n`
- [y] Changing the loop so that, after the first iteration (`i`=2), it skips all even numbers

> Doubling stack size will have no impact on the performance of the program that does not run out of stack space; it might even hurt performance by blocking off memory that might otherwise be useful to the program.
> This helps avoid re-computing the square root. A super-smart compiler might be able to optimize this out on its own, but it's not easy to determine automatically whether subsequent calls to `Math.sqrt()` can be skipped, even if the argument is the same.
>  Returning as soon as a contradiction is found will avoid further iterations.
> Widening the integer will not improve performance; it might hurt it by worsening cache hit rate.
> Shortening the integer below the memory word size will not reduce it's internal representation, so this has no effect.
> Doing an integer multiplication is considerably faster than computing the square root.
> By skipping even numbers, the semantics remain unchanged, but the cost of half of the iterations is saved.

### Question 3 [4 points]

During your internship at BarBaz Corp., you develop an app with a user interface.
The Product Owner says that it must be possible for users to record their preferred color scheme.
Your app now needs to communicate with an external service `FavoriteColorService`, which keeps track of users' favorite colors.
The app generates the favorite colors as `ArrayList<Color> colors`, where `Color` is a [built-in class](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/Color.html).
However, `FavoriteColorService` accepts only raw hexadecimal values.

Which design pattern would you use to connect your app to the service? There is exactly one correct choice.

- [y] Adapter
- [n] Singleton
- [n] Decorator
- [n] Factory
- [n] Composite
- [n] Observer

> The problem (i.e., that the app generates an `ArrayList<Color>` whereas the service expects raw hexadecimal values) is a classic for the _Adapter_ pattern: we need to transform the app's output into the service's expected input.
> Said differently, an _Adapter_ wraps the service interface and transforms it into the interface that the app expects.
> Choosing `Decorator` is not a good idea, because a Decorator changes the functionality of the wrapped object or service, while typically keeping the interface unchanged, thus not solving the problem.
> The other options are completely unrelated to the problem described here.

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

- [n] `sumList` is bug-free
- [y] `sumList` has a bug
- [y] `testSumList` does not cover all relevant cases
- [y] There exists a test that evidences a bug in `sumList`
- [n] Whatever bugs `sumList` has left cannot be evidenced via a unit test

(_Relevant cases_ are test cases with "interesting" values.
As you know, it is intractable to test all the possible inputs to this method.
However, with a few carefully picked relevant cases that are more likely to trigger bugs, one has a better chance of uncovering bugs.)

> `sumList` has at leas one bug.
> Such a bug can be evidenced, e.g., by testing with the empty list, i.e., `sum(List.of())`.

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

- [n] 100% line coverage, 100% branch coverage
- [n] 100% line coverage, 50% branch coverage
- [n] 66% line coverage, 100% branch coverage
- [y] 66% line coverage, 50% branch coverage
- [n] 33% line coverage, 100% branch coverage
- [n] 33% line coverage, 50% branch coverage

> The test covers the first two lines of code, and exercises one branch of the conditional.

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

- [n] N^2/K + K^2
- [n] N^2/K + M^2
- [y] N^3 - K*M^3
- [n] N^3/K + K
- [n] N^3 + K*M^3
- [n] N^3/K + M^3

> Time to fix all bugs in a block of n LOC is _n^2_ bugs x _n_ time per bug = _n^3_.
> Time to fix all bugs without refactoring = _N^3_.
> Time to fix all bugs after refactoring = _K * M^3_.
> For refactoring to be worthwhile, it must be that _T + K * M^3 < N^3_, i.e., _T < N^3 - K * M^3_.

### Question 7 [6 points]

In class we described the evolution of version control systems over time.
Which of the following statements are true of a 3rd-generation VCS (V3) versus a 2nd-generation VCS (V2)? Make sure you think of the VCS as described in class, not of a specific product (like Git) that may be only an approximate instance of the VCS.

- [y] if you lose your network connectivity for an extended period of time, a V3 allows you to do more work during that period than a V2
- [y] controlling source code access on a per-file basis is easier with a V2 than a V3
- [y] if you want to work for the first time on a mature code repository located remotely on the Internet, a V3 requires significantly more network traffic than a V2
- [y] when you version-control large binary files using a V3, other users of that repository would get considerably more upset with you than if you were all using a V2
- [y] having a good data-backup solution for the repository is more important when using V2 than when using V3

> (1) Since code commits are local in a V3 and the entire repo resides on your machine, you can work on and complete more coding tasks than if you had to reach out to a V2 server to do your commits.
> (2) Since a V3 requires that the entire repo be local, it is not possible to limit access to parts of that repo, whereas a V2 allows this to be controlled on the server.
> (3) The first step in working with a new code base under V3 is to clone it, which consists of making a full local copy of the repo.
> A mature code base is likely to be big, so this initial clone will consume a lot of bandwidth.
> (4) Under a V3, each developer has a full copy of the repo, so every time you place a large binary (i.e., something that is not diff-able) in a repo, all other developers end up having to pull it into their local repos.
> This consumes network bandwidth and local storage.
> (5) Under a V2, the master repo resides on a server, so it needs to be backed up.
> Under a V3, each developer has a copy of the full repo, which in effect constitutes a full backup of the repo.

### Question 8 [3 points]

You're developing an app for a new film streaming service called SwEngFlix.
Which of the following are good user stories?

- [n] As a user, I want to be able to sign in hassle-free
- [n] As a Google account owner, I want to be able to sign in using my Gmail address
- [n] As a developer, I want to finalize the login screen's implementation so that users can sign in using their existing Google account
- [n] As a series-binger, I want the recommended shows to appear on the top row of my screen, along with a horizontal scrolling layout with a dark background
- [y] As an occasional user, I want to see where I last stopped, so that I can resume where I left off and keep track of my watching history

> (1) Simply being a "user" is too vague to be a persona: specifying a finer role for stories is important in order to assimilate and understand it better.
> The story itself is also too vague.
> (2) is a good story, but lacks the `reason` component of the user-story template given in the lecture notes.
> (3) The developer is not an end-user, and so the corresponding "user story" has nothing to do with users.
> (4) This is too specific: while it correctly targets a persona, it specifies implementation/design details and therefore constrains unnecessarily the developer.
> (5) This user story correctly targets a specific persona and follows the correct format and phrasing.

### Question 9 [4 points]

Which of the following actions are legal in terms of software licensing?

- [n] I take publicly available source code from GitHub, modify it, and then redistribute it unless it has a license that explicitly forbids such redistribution
- [n] Same as above, except that I redistribute the modified code via GitHub
- [y] I use GPL-licensed code in my software, and I sell the software (i.e., charge money for it)
- [n] I use GPL-licensed code in my software, but I do not distribute the source code along with the compiled software
- [y] I add MIT-licensed code to an existing GPL-licensed codebase, and then distribute the whole thing under a GPL license
- [y] Same as above, except that the added code is BSD-licensed instead of MIT-licensed
- [n] I add GPL-licensed code to an existing MIT-licensed codebase, and then distribute the whole thing under an MIT license

> (1) and (2): By default, code is the intellectual property of its author(s), regardless of how you get the code. Thus, there must be a license that explicitly allows you to redistribute that code in order for it to be legal.
> (3) and (4): Yes, the GPL license allows anyone to sell the original as well as the modified software. As Richard Stallman explains it, GPL is about being free not as in "free beer" but as in "freedom to use, study, distribute, and modify that software".
> This is why it is illegal to distribute the compiled software without the source code, but it is perfectly fine to charge money for it.
> Note that, if someone buys your program for a fee, GPL gives the the freedom to release it to the public, with or without a fee.
> (5) and (6): Both the MIT and BSD licenses are a lot less restrictive than the GPL, and they explicitly allow sublicensing, i.e., redistributing of the code under a different license.
> (7) GPL is "contagious" in that the terms of the GPL must be preserved no matter where the code ends up, so using GPL-licensed code in your codebase requires that that codebase be distributed under GPL or not at all.
> MIT and BSD licenses however do not require this.

### Question 10 [4 points]

You are a member of a software development team at FooBar Inc., working on a new app for an important client.
During lunch in the company cafeteria, a high-ranking executive of the company approaches you and asks you to squeeze in an extra feature to be done quickly, before the end of the ongoing Sprint.
Which of the following actions are compatible with the Scrum framework?

- [n] You decline outright by explaining that Scrum does not allow you to do anything about it.
- [n] You agree to do it, because it is normal for someone who is hierarchically above you and who understands the business and the market to issue such orders.
- [n] Since you already finished all your tasks for the Sprint, you agree to do it before the end of the Sprint.
- [n] Same as above, but you first add the issue to the Sprint backlog before working on it.
- [y] You tell the executive that you will give this information about customer needs to the Product Owner the next time you see her.
- [n] Same as above, but you also add the issue to the Product Backlog.
- [y] You inform your team's Scrum Master.

> Customers, managers and executives should not impede the Team's work, nor interact directly with them during a Sprint.
> The Product Owner is responsible for all features of the product, and (s)he is the only one who can decide what features go in vs. not.
> One of the Scrum Master's roles is to resolve and suppress impediments to ensure a good and productive environment, so it is perfectly legitimate to report to the Scrum Master what happened in the cafeteria, so that (s)he can take appropriate action.
> Simply declining the request is not enough: that same person might ask other team members that will find themselves in the same situation.
> Plus, there may be a valid customer reason for adding this to the product backlog, and the P.O. might not be aware of it.
