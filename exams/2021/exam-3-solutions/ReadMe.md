# Exam review

This document contains a short review of common mistakes we saw while grading Exam 3.


## Question 1

Keep in mind that Scrum is not a religion.
The goal is not to rigidly enforce exactly when coworkers are allowed to meet, only to define a “base” level of meetings necessary for the team to work.
Coworkers are absolutely allowed to talk outside of the Scrum meetings, and they should do so whenever necessary!

Also keep in mind that the sprint backlog being frozen during a sprint does not mean customer requests should be ignored entirely.
They should be forwarded to the product owner, who can then choose how to prioritize them for future sprints.


## Question 2

There was no evidence that the inter-shop discount system would ever exist, only some idea from a coworker,
thus no reason to make the code more complex by having to propagate asynchrony throughout the stack.

Similarly, there was no evidence that other payment processors were needed, so this was not a good reason to
accept a refactoring of the payment processor; instead, the reason for it was to enable _testing_, which is much harder if the code directly talks to the credit card processor.

The null product exception should be _unchecked_ because a null product represents an invalid state.
Users cannot order null products, because there is no such thing as a null product in the real world,
it’s an implementation detail of languages such as Java. Furthermore, since the code in the statement was server-side,
any request with a null product is invalid and represents a bug in the client making the request, or perhaps in the server’s request parsing code.
Recall that checked exceptions are for exceptional things that might happen even if the code is correct, such as I/O errors, while unchecked exceptions are for bugs in the code.


## Question 3

The big thing missing from the pull request was _regression testing_! This is super important to ensure a bug does not reappear.

Regarding squashing, the reason to squash is that individual commits in a pull request are incomplete
and likely “broken” because they are not complete units of work. Keeping incomplete commits in the history of the main branch makes it hard to use the history,
such as to find when a bug was introduced, because it creates “false positive” culprit commits for any bugs (since the tests will likely not pass).
One could argue that it’d have been better to squash into _two_ commits, one for the bugfix and one for the obsoletion, and indeed we gave full marks for that answer.


## Question 4

The main issue we saw was that code was overall much more complex than it needed to be, especially for the LRU cache. There is no reason to reinvent the wheel.

Another common issue was code that was not properly asynchronous, such as polling `isDone` on a future.
This is highly inefficient, you should use the proper methods on futures instead. Asynchronous code in a library should not block until a future is completed unless that is absolutely necessary.


## Question 5

The most common mistake was not mocking dependencies.
The exam statement specifically asked for tests that were not affected by the unreliability of the services,
and indeed testing the `RemoteDatabase` and the `RemoteAuthenticationService` meant tests that required long timeouts or could randomly fail.
Any time spent writing mocks was more than compensated by running tests much faster.

Many students wrote quite complex tests as they used more complex APIs than necessary.
For instance, instead of calling `get()` on a `CompletableFuture`, which throws checked exceptions, call `join()`, which has the same semantics but with unchecked exceptions.

Regarding testing the exception type, since futures wrap their exceptions, here is a way to do it:
```
  Exception ex = assertThrows(CompletionException.class, () -> {
  	someOperation().orTimeout(5, TimeUnit.SECONDS).join();
  });
  assertThat(ex.getCause(), isA(IllegalStateException.class));
```


Given how many students wrote overly complex code for the tests, we realized our Future explanations around testing could have been better, so we did not take off points for extra complexity.

We also saw too many “success” tests written as “does not fail”.
The fact that a future has not failed does not mean it did the right thing, it may not even have finished yet!
For instance, “adding did not fail” is a much weaker test than “adding means the element is in the list returned by the get list method”.

Some students used `assertThat(0, is(1))` or similar code to fail; use the `fail()` method instead, it’s cleaner.
If you want a test to pass, you do not need to add an `assertThat(0, is(0))` or anything like that, just let the test pass without assertions.
Assertions are really syntactic sugar over “if the condition does not hold, throw an `AssertionError`”, so a test that runs no assertion passes since it did not throw errors.

Also remember that `assertThat` is quite flexible thanks to the variety of matchers in Hamcrest, and you should use the right matcher for your test.
For instance, the test above could have been `assertThat(ex.getCause() instanceof IllegalStateException, is(true))`,
but this is less clear and makes the message in case of failure essentially useless (“expected true, but was false”).

In general, perhaps due to time constraints, many students used overly simple test names, such as “addWorks” or “deleteFails3”.
Such test names make it hard to tell what a test does at a glance, which is important for productivity when using an IDE with a test window that lists passing/failing tests.
As a general rule, a test name should describe what the test does and what it expects, e.g. “`itemIsInListAfterAdd`”. Numbers at the end of test names, such as “test1”, “test2”,
and so on, are a code smell.

Two other less common mistakes worth a mention:
* Do not use the Java `assert` keyword for tests, it requires the explicit enablement of asserts via a command-line parameter to the JVM, unfortunately
* `assertThat(..., is(...))` lets you compare any two objects regardless of type because in theory they could have an `equals` implementation returning true
  even if they are distinct types, which unfortunately allows the mistake `assertThat(someFutureOfT, is(someT))` which always fails.
