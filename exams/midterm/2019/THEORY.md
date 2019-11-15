# Software Engineering - Midterm Exam

## Part 1: Theory [30 points]

This part of the midterm exam has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

---

### Question 1 [1 points]

In Scrum, the team gathers every day at a fixed time for the daily standup meeting. What is true about this meeting? 

- [ ] The Product Owner attends daily standups
- [ ] Daily standups allow each member of the team to summarize their progress
- [ ] Daily standups are used to discuss the Backlog of the next Sprint
- [ ] Daily standups usually last 1 hour


---

### Question 2 [2 points]
 
Say you are a developer on a team that uses Scrum. In the middle of a Sprint, you discover a serious, critical bug. What do you do?

- [ ] I add the bug to the Product Backlog
- [ ] I start fixing it right away
- [ ] I file a bug report and assign it to the Scrum Master
- [ ] I file a bug report and assign it to the Product owner

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

- [ ] `WriteOnlyPipe` is poorly implemented because `InterruptedException` is not the right exception to throw in `nextByte()`
- [ ] `Pipe` is a good abstraction, and `WriteOnlyPipe` is a good implementation
- [ ] Implementing `WriteOnlyPipe.nextByte()` as an infinite loop would be more consistent with the `Pipe` interface than throwing an exception
- [ ] If we wanted pipes that can only be written to, then the right thing to do is not have a `Pipe` interface but define two interfaces, `ReadablePipe` and `WriteablePipe`

---

### Question 4 [3 points]

Consider the following abstraction:

```java

public interface WeatherService {
  Weather getWeatherInCity(City city, HttpMethod method, HttpHeader headers) throws HttpException;
}
```

What is true of this abstraction?

- [ ] It is a good abstraction
- [ ] It is not a good abstraction because it exposes implementation details
- [ ] It is not a good abstraction because it is missing necessary methods
- [ ] It is not a good abstraction because the method takes too many parameters


---

### Question 5 [2 points]

Which of the messages below are acceptable commit messages?

- [ ] Fix several bugs
- [ ] Couldn't have a green button at the bottom of the login page
- [ ] Fix a NullPointerException at login (fixes #45)
- [ ] Implement upload to OpenStack containers


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

- [ ] The name of the variables makes it hard to understand the code
- [ ] First comment is useless and the second comment is useless
- [ ] First comment is useful and the second comment is useless
- [ ] Second comment is useless and the second comment is useful
- [ ] Second comment is useful and the second comment is useful


---

### Question 7 [3 points]

What is true about TDD (Test-driven development)?

- [ ] TDD makes it easier to distribute tasks in a team
- [ ] Bugs cannot occur in software developed using TDD
- [ ] With TDD, you find bugs very early in the development cycle
- [ ] TDD only requires writing the tests, and not the actual implementation
- [ ] In TDD, one first writes the implementation and then writes tests
- [ ] In TDD, one first writes the tests and then writes the implementation

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

- [ ] The team should use TDD to improve their workflow
- [ ] The team seems to use TDD in their workflow
- [ ] Commit messages for `4df7d10` and `11e9ea2` are both good commit messages
- [ ] Commit messages for `5d7193b` and `eee0221` are both bad commit messages
- [ ] Commit messages for `a217948` and `0b6440e` are both good commit messages
- [ ] It evidences bad practice of committing when the code doesn't pass the tests


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

- [ ] 100% line coverage, 100% branch coverage
- [ ] 100% line coverage, 50% branch coverage
- [ ] 66% line coverage, 100% branch coverage
- [ ] 66% line coverage, 50% branch coverage
- [ ] 33% line coverage, 100% branch coverage
- [ ] 33% line coverage, 50% branch coverage

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

- [ ] `sum` has no bug, but the test is inadequate because it doesn't cover all important cases, so it could leave bugs undiscovered if run against some other implementations
- [ ] `sum` has a bug, but the test covers all relevant cases, so it's a bug that cannot be found via unit testing
- [ ] `sum` has a bug, and it could have been uncovered with additional test cases
- [ ] `sum` has no bug, and the test covers all relevant cases

(By "relevant cases", we mean test cases with interesting values. As you know, it is intractable to test all the possible values of this method. However, with a few carefully picked "relevant cases", that are more susceptible to cause bugs, one has a better chance of uncovering bugs.)

