# Exam review

This document is a review of Exam 1, including common errors.


## Question 1

A correct answer needed the three elements without extra details, e.g., “as a blind person, I want to be able to hear the jokes read out loud, in order to make my friends laugh“.

Common errors:
* “As someone who cannot read” has a very different meaning than being blind, and would lead to different user stories and products
* “As a user with a disability” is too generic, e.g., a broken leg is a disability
* “so that I can use the app” is too generic, as it could apply to any app in any story
* Detail based on the “a friend of mine told me…” part should not be included, as it was speculation from the user and should not be in the story
* Developer-oriented stories, such as “I want the app to provide text, so that the phone can read it”: this is not something a user directly cares about


## Question 2


### Branching

The flaw was committing once per day.

A good alternative is to commit when a self-contained change, such as a feature or a bug fix, is done. Alternatively, committing often as a backup and squashing commits is acceptable.

We also asked for a good explanation, which could be about making the git history easier to read and to use for reverts and bisects, or about the problems induced by large half-finished commits.

Common errors:
* “Commit often” or “Commit as much as you can” are not good alternatives without squashing, as it is good for backup but makes the git history complicated.
* “Having one branch per feature is too much”, no, it is actually a good idea to do so!


### Testing

The flaw was running tests locally and sending a screenshot of the results.

A good alternative: set up Continuous Integration to run tests on every push.

Since this question was worth more points we expected a more complete explanation than before. A good explanation should explain why testing only locally is bad, because it may run locally but not in a production environment. And it should show why CI is more practical, because it can run automatically on every push, produce a report that everyone can see, and simulate any environment we want.

Common errors:
* TDD: while TDD is a nice workflow, it is absolutely fine to not use it.
* “Tests should be part of the commit” or “tests should be specific to the feature”, this is a misunderstanding of the question.
* “Others should review the code before doing a PR”, a pull request is a request to merge into another branch when the code is ready, therefore it is after the PR is open that others should review.
* “Others should pull and run the code”, using CI is the same but simpler and easier.


### Merging

The flaw: Merging without any check/approval is a bad idea.

A good alternative: Merge only if N colleagues have reviewed the code.

A good explanation should mention why reviewing is better (enforce code standard, spot certain bugs, check the requirements, etc.).

Common errors:
* “We should merge immediately”, please don’t.
* “We should set up a CI, and once all tests pass the PR can be automatically merged.” While good, this is not enough, here we suppose tests were run in the previous question, code should be reviewed by a human before getting merged into a production branch.


## Question 3


### Debug prints

Debug prints are not a good idea, and using a debugger is a better alternative, for the reasons we saw in class: one can change what to print where and when at runtime.

Common mistakes:
* Using debugging: debugging is the goal, be careful not to confuse “debugging” with “debugger”
* The debugger is better because it does more: what exactly? Give concrete examples. Since we ask for “a better way to do it”, you should show at least state that the debugger can do better prints
* Print is fine for small code: Almost all of us do or did print to debug code. However, that does not mean it is generally a good idea, even for small code.
  It is best to have good habits now, you won’t always have a “hello world” code to debug ;) 
* Write tests instead: Testing is not the same as debugging. Tests are about checking the behavior of the code, debugging is about finding the root cause of errors


### Good names

The names are too long and verbose. It makes the code hard to read and maintain.

Common mistakes:
* Only giving examples: Even if examples can help your explanation, it should not be the base of your answer unless stated in the question.
  You should answer generally, then give an example if you think it is important.


### Comments

No, they are not used correctly. Comments are made to explain “why” and not “how”.

Common mistakes:
* There are too many comments: The amount of the comments is not directly the problem. Code should have as many comments as it needs,
  so just stating that the number is the problem is not a valid explanation.
* Comment should explain the function, arguments, return values: code comments and code documentation are not the same. Here there is simply no documentation.


### Commented-out code

Use a VCS, to be able to go back to an earlier version of the code to retrieve the case once the class CommandInterpreter is functional for that case.

Common mistakes:
* Use TDD/use tests: TDD is short for Test Driven Development. This is not a question on how to test a piece of code, but what to do with one that serves no purpose yet.
* Return a value: The purpose of the case is to export. If you return a bogus value just for the sake of having a “clean” code, then it is a terrible idea.
  You transform the problem from “dead code” to “wrong code”.
* Throwing an error: With this solution, you won’t achieve the same result as skipping the case, but you change the behavior and add extra work to then handle the error. 
* Using dependency injection: This question has nothing to do with dependency injection.


## Question 4

This question focused on testing, and required tests of the same kind we saw in class.

Common mistakes:
* Testing multiple logical behaviors per test. This is bad practice because it reduces the usefulness of the test suite, since a failed test can mean more than one problem.
* A lot of names were either too verbose or too vague w.r.t illustrating required semantics. Test names should cover (1) the behavior to test, and (2) the expected outcome of said behavior,
  in a way that is unambiguous. Good names should not be unreasonably long by excluding unnecessary grammar used in everyday language.


## Question 5

A correct refactoring required extracting the three dependencies on the environment: the calls on _System.in_ to get the list of roles,
the _URL.openStream()_ call to obtain an _InputStream_ and the calls on _System.out_ to print the list of users.

Common mistakes:
* Not preserving the behavior of the program. This includes not providing implementations for the injected dependencies.
* Writing _if (debug) { … } else { … }_ -like code or duplicating logic.
* Extracting the parsing logic for the set of roles, and not only the calls to _Scanner.hasNextLine()_ and _Scanner.nextLine()_.
  If you extract the whole while loop, then you must test its logic in end-to-end tests instead of in simpler tests, which partly defeats the point of injecting dependencies.
* Passing the set of roles in the constructor. The _JaasClient.run_ method should be able to obtain a different set of roles each time it is executed.
* Writing tests that only verify failure scenarios or make no meaningful assertions when _JaasClient.run_ is executed and succeeds.
