# Software Engineering - Midterm Exam

## Part 1: Theory [30 points]

This part of the midterm exam has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

---

### Question 1 [1 points]

In Scrum, the team gathers every day at a fixed time for the daily standup meeting. What is true about this meeting? 

- [n] The Product Owner attends daily standups
- [y] Daily standups allow each member of the team to summarize their progress
- [n] Daily standups are used to discuss the Backlog of the next Sprint
- [n] Daily standups usually last 1 hour

> Daily Standups are meetings held every day during which the members of the team can summarize their progress.
> See [https://www.scrumguides.org/scrum-guide.html#events-daily](https://www.scrumguides.org/scrum-guide.html#events-daily) for details.

---

### Question 2 [2 points]
 
Say you are a developer on a team that uses Scrum. In the middle of a Sprint, you discover a serious, critical bug. What do you do?

- [y] I add the bug to the Product Backlog
- [n] I start fixing it right away
- [n] I file a bug report and assign it to the Scrum Master
- [n] I file a bug report and assign it to the Product owner

---

### Question 3 [4 points]
 
What is true of the following abstraction?

```java
interface Pipe {
  /** Waits for a byte to be available to a pipe and return it 
    * @throws InterruptedException if the current thread was interrupted
    */
  byte nextByte() throws InterruptedException;

  void write(byte b);
}

class WriteOnlyPipe implements Pipe {
  @Override byte nextByte() throws InterruptedException {
    throw new InterruptedException("Read from empty pipe");
  }

  @Override void write(byte b) { /* implementation */ }
}
``` 

- [y] `WriteOnlyPipe` is poorly implemented because `InterruptedException` is not the right exception to throw in `nextByte()`
- [n] `Pipe` is a good abstraction, and `WriteOnlyPipe` is a good implementation
- [y] Implementing `WriteOnlyPipe.nextByte()` as an infinite loop would be more consistent with the `Pipe` interface than throwing an exception
- [y] If we wanted pipes that can only be written to, then the right thing to do is not have a `Pipe` interface but define two interfaces, `ReadablePipe` and `WriteablePipe`

---

### Question 4 [3 points]

Consider the following abstraction:

```java

public interface WeatherService {
  Weather getWeatherInCity(City city, HttpMethod method, HttpHeader headers) throws HttpException;
}
```

What is true of this abstraction?

- [n] It is a good abstraction
- [y] It is not a good abstraction because it exposes implementation details
- [n] It is not a good abstraction because it is missing necessary methods
- [n] It is not a good abstraction because the method takes too many parameters

> `HttpMethod`, `HttpHeader` and `HttpException` are specific to HTTP, they should not be involved in a generic WeatherService.

---

### Question 5 [2 points]

Which of the messages below are acceptable commit messages?

- [n] Fix several bugs
- [n] Couldn't have a green button at the bottom of the login page
- [y] Fix a NullPointerException at login (fixes #45)
- [y] Implement upload to OpenStack containers

> `Fix several bugs` doesn't provide sufficient information for one to know what is in the commit.
> `Couldn't have a green button at the bottom of the login page` doesn't say what is in the commit, it only says what problem that commit is concerned with.


---

### Question 6 [3 points]

What is true about the comments in the following method `average`?


```java
public double average(double[] array) {
  double sum = 0;

  // Compute the sum of all the values
  for (double v : array) {
    sum += v;
  }

  // This is where the magic happens
  double avg = sum / array.length;
  return avg;
}
```

- [n] The name of the variables makes it hard to understand the code
- [y] First comment is useless and the second comment is useless
- [n] First comment is useful and the second comment is useless
- [n] Second comment is useless and the second comment is useful
- [n] Second comment is useful and the second comment is useful

> Both comments are useless because they add no information to the code.

---

### Question 7 [3 points]

What is true about TDD (Test-driven development)?

- [n] TDD makes it easier to distribute tasks in a team
- [n] Bugs cannot occur in software developed using TDD
- [y] With TDD, you find bugs very early in the development cycle
- [n] TDD only requires writing the tests, and not the actual implementation
- [n] In TDD, one first writes the implementation and then writes tests
- [y] In TDD, one first writes the tests and then writes the implementation

---

### Question 8 [4 points]

What is true of the following git history?

```
$ git log --oneline

0b6440e(HEAD -> master, origin/master, origin/HEAD) Make the share feature unit tests pass
11e9ea2 Good this time?
eee0221 And try again
4df7d10 Try to fix those tests
5d7193b Implement share to Swingger and Facesweng
a217948 Add share features tests
```

- [n] The team should use TDD to improve their workflow
- [y] The team seems to use TDD in their workflow
- [n] Commit messages for `4df7d10` and `11e9ea2` are both good commit messages
- [n] Commit messages for `5d7193b` and `eee0221` are both bad commit messages
- [y] Commit messages for `a217948` and `0b6440e` are both good commit messages
- [n] It evidences bad practice of committing when the code doesn't pass the tests

> The order of commits (tests then implementation) suggests the team is using TDD.
> Working on a feature branch is a good practice in most development teams. 

---

### Question 9 [4 points]

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

For this question, apply the following rules when computing line coverage:

 - The method signature, including the first `{`, is not counted as a line
 - Lines that only have a brace don't count (i.e., `{` or `}`)
 - All the other lines of the method are counted

Therefore, there are 3 lines in the `abs` method.

What coverage does this test achieve on this method? 

- [n] 100% line coverage, 100% branch coverage
- [n] 100% line coverage, 50% branch coverage
- [n] 66% line coverage, 100% branch coverage
- [y] 66% line coverage, 50% branch coverage
- [n] 33% line coverage, 100% branch coverage
- [n] 33% line coverage, 50% branch coverage

(For this question, 1/3=0.33 and 2/3=0.66)

---

### Question 10 [4 points]

Consider the function `sum` that is supposed to compute the sum of the numbers in a list, and the `testSum` test below it:

```java
public static double sum(List<Double> numbers) {
  if (numbers.size() <= 1) {
    return numbers.get(0);
  }
  
  return numbers.stream().mapToDouble(a -> a).sum();
}

@Test
public void testSum() {
  assertEquals(sum(List.of(5D)), 5D);
  assertEquals(sum(List.of(5D, 10D)), 15D);
}
```

The `testSum` test passes. What can you say? 

- [n] `sum` has no bug, but the test is inadequate because it doesn't cover all important cases, so it could leave bugs undiscovered if run against some other implementations
- [n] `sum` has a bug, but the test covers all relevant cases, so it's a bug that cannot be found via unit testing
- [y] `sum` has a bug, and it could have been uncovered with additional test cases
- [n] `sum` has no bug, and the test covers all relevant cases

(By "relevant cases", we mean test cases with interesting values. As you know, it is intractable to test all the possible values of this method. However, with a few carefully picked "relevant cases", that are more susceptible to cause bugs, one has a better chance of uncovering bugs.)


> Here, testing for `sum(List.of())` (empty list) would have uncovered the bug.
