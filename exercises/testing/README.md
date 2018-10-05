Testing
=======

In this set of exercises, you will learn how to write tests for your code, and thus increase your confidence that the code is correct. There are multiple modules:

- Introduction (this file)
- [Unit and end-to-end testing](./ex1)
- [Regression testing](./ex2)
- [Black-box testing and TDD](./ex3)
- [Testing using dependency injection and mocks](./ex4)
- [Testing using advanced infrastructure](./ex5)

All exercises are accompanied by their solutions, in a `solutions` folder, but you should first solve the exercises on your own before looking at the solutions.


Introduction
------------

Writing code is hard. Sometimes it's not clear what exactly the output of a function should be, and there are often many edge-cases you have to think about. Could you prove the program correct using formal logic? Maybe, but unfortunately, formal verification requires specific skills and takes a lot of time; making it scale to large programs is still a research topic.

The simplest way to gain _some_ confidence in your code is _manual testing_: run your code yourself and look at what it does. If it feels OK, good, otherwise, go and fix it. But this does not scale to large code bases: there are too many things that the code does to remember them all, and you may forget to check some parts.

This series of exercises introduces _automated testing_: write the tests once, and automatically run them every time you want, with a clear report of which tests passed and which tests failed. No more need to remember all the edge cases yourself, or to waste time manually entering all kinds of inputs!

We will use two Java libraries: JUnit is the base testing library, and Hamcrest adds useful methods for testing. JUnit is supported by virtually all Java IDEs, which means that you can automatically run tests from an IDE and see the passed and failed tests; you can also do this from the command-line.

To add JUnit and Hamcrest to a Gradle project (e.g., for developing an Android app), use the following dependencies:
- `testImplementation 'junit:junit:4.12'`
- `testImplementation 'org.hamcrest:hamcrest-library:1.3'`

You can then import the following:

```java
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
```

And use them like this:

```java
public class MyTest {
    // Tests are methods annotated with the @Test annotation
    @Test
    public void onePlusOneIsTwo() {
        assertThat(1 + 1, is(2));
    }
}
```

Hamcrest also contains a lot of other "matchers" for testing, such as the following (if your IDE doesn't auto-suggest the import, you will have to `import static org.hamcrest.Matchers.<matcher>` for each `<matcher>` you're interested in):

```java
assertThat(1 + 1, is(not(5)));
assertThat(1 + 1, anyOf(is(2), is(5)));
assertThat("example", instanceOf(String.class));
assertThat(Arrays.asList(1, 2, 3), hasSize(3));
assertThat(Arrays.asList(1, 2, 3), containsInAnyOrder(2, 3, 1));
assertThat(Arrays.asList(1, 2, 3), everyItem(greaterThan(0)));
```

You can view all of them in the [Hamcrest documentation](http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html).

> As an aside, if you've used JUnit before, you may be wondering why not to use the built-in JUnit methods such as `assertEquals` or `assertTrue`. First, these methods are often confusing because of the order of arguments; should you write `assertEquals("expected value", actualValue)` or `assertEquals(actualValue, "expected value")`? Second, the error messages they produce when the test fails is not always sufficiently descriptive; for instance, `assertTrue(myValue instanceOf String)` will fail with an `AssertionError` and no useful message if the value is not a `String`, whereas `assertThat(myValue, instanceOf(String.class))` will helpfully display `Expected: an instance of java.lang.String, but: <1> is a java.lang.Integer`.

Before we continue, here are some simple guidelines for writing tests:

- Always give tests clear names. `bannedUserCannotJoinGroup` is a descriptive name, while `groupBehavesProperly` is not (what is proper behavior?).
- Each test should test a single concept. Because tests will stop running at the first failed assertion, if a test contains assertions on many different concepts, it's hard to get an accurate picture of what is broken. For instance, if you want to test 10 features but pack all tests into one test method, even if 9/10 features are working correctly, 0% of your tests will pass.
- Tests should be independent of each other. Tests can be run in any order, so they shouldn't require some other test to have to run first. If you need to share code between tests, use the [Before](https://junit.org/junit4/javadoc/4.12/org/junit/Before.html) and [After](https://junit.org/junit4/javadoc/4.12/index.html?org/junit/After.html) annotations of JUnit.

Now let's dive right into it with [unit and end-to-end testing](./ex1).
