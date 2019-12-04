# Software Engineering

## Final Exam Solutions

---

# Part 1: Practice [75 points]

## Part 1.1: Homepage [30 points]
  
Poodle, Inc. determined that users want to see the following on the homepage:

- Start by greeting the user by name: `Hello, <NAME>!`
- The courses the user teaches come first, sorted alphabetically in ascending order. 
  Each course is displayed by printing its name, preceded by a dash `-` and a space.
- The courses the user attends come second, with the same sorting and formatting as above.
- Each list is preceded by a title: `Courses you teach` and `Courses you attend`, respectively.
- If the lists are empty, they should be replaced by the texts `You are not teaching any courses.` and `You are not attending any courses.`, and the titles should not be displayed.
- The page text should end with a new line.

For `Alice`, teaching `Software Engineering` and attending `Intro to Jenkins`, the page should be:

```
Hello, Alice!
Courses you teach
- Software Engineering
Courses you attend
- Intro to Jenkins

```

I.M. Badd, a consultant for the startup, implemented the [`Homepage`](src/main/java/ch/epfl/sweng/Homepage.java) class' single method `String print(User, List<Course>)`. But users are not satisfied with how it works, and furthermore there are no unit tests.

Your tasks are to:

- Write unit tests to provide 100% line coverage of the [`Homepage`](src/main/java/ch/epfl/sweng/Homepage.java) class. **[15 points]**
- Fix all bugs in the [`Homepage`](src/main/java/ch/epfl/sweng/Homepage.java) class so that it fully satisfies the above specification, and the tests provide 100% line coverage. **[15 points]**

__Solution__

For the first part, [HomepageTests.java](src/test/java/ch/epfl/sweng/tests/graded/HomepageTests.java) contains a set of example tests that achieve 100% coverage.

For the second part, the `Homepage` class had at least the following five bugs:

- No check was made for a null `user` argument, as the introduction asked for
- Courses were not sorted alphabetically, unlike what the requirements asked for
- The name of the user was trimmed to 8 characters, thus incorrectly altering the name
- Course lists' titles were displayed even if their lists were empty, unlike what the requirements asked for
- Placeholders for empty lists were not displayed, unlike what the requirements asked for

__Grading__

In your repo you find a report called `GradingReport_Practice.md` (similar to [this one](GradingReport_Practice.md)) containing a summary of which tests you passed and how many points you received for each test. 

For the coverage (shown in the general report named `GASPAR_final_report.md`), we awarded 15 points for 100% test coverage, 11 points for (90%,99%], 8 points for (80%,89%], 6 points for (70%,79%], 5 points for (60%,69%], 3 points for (30%,59%], and 1 point for (0%,29%]. The automated tests we used to grade your bug fixes can be found in [HomepageTests.java](src/test/java/ch/epfl/sweng/tests/graded/HomepageTests.java).  

For how we graded the quality of your code, please see the corresponding section at the bottom of this page.

## Part 1.2: Integration with IS-Papademia [30 points]

Poodle's killer feature is integration with an existing legacy system, IS-Papademia. Students and teachers enroll themselves in courses via IS-Papademia.

Unfortunately for Poodle, IS-Papademia's models are not directly compatible with the [`User`](src/main/java/ch/epfl/sweng/User.java) and [`Course`](src/main/java/ch/epfl/sweng/Course.java) classes:

- [`PapademiaUser`](src/main/java/ch/epfl/sweng/PapademiaUser.java) has a `name`, a list of `taughtCourses` and a list of `attendedCourses`. (A user will never be both teaching and attending a course)
- [`PapademiaCourse`](src/main/java/ch/epfl/sweng/PapademiaCourse.java) has a `name`.

In addition, IS-Papademia sometimes has `null` values in the lists of users and courses. These values must be ignored, i.e. treated as if they did not exist.
Aside from this issue, you can assume that the data from IS-Papademia is always correct; there are no invalid usernames or any other kind of nonsensical data.

Your tasks are to:

- Implement [`PapademiaAdapter`](src/main/java/ch/epfl/sweng/PapademiaAdapter.java) by filling in the `getUsers` and `getCourses` methods. **[15 points]**
- Write tests for [`PapademiaAdapter`](src/main/java/ch/epfl/sweng/PapademiaAdapter.java) that achieve 100% line coverage. **[15 points]**

__Solution__

Please see [PapademiaAdapter.java](src/main/java/ch/epfl/sweng/PapademiaAdapter.java) for one way to solve the first part. The `getUsers` method simply takes the names of Papademia users and adds them as Poodle users. The `getCourses` method is more involved, but uses roughly the same logic as the `Homepage` code.

For the second part, see [PapademiaAdapterTests.java](src/main/java/ch/epfl/sweng/PapademiaAdapterTests.java) for a set of tests that achieves 100% line coverage.

__Grading__

The `GradingReport_Practice.md` file contains a summary of which tests you passed and how many points you received for each test. 

For the coverage (shown in the general report named `GASPAR_final_report.md`), we awarded 15 points for 100% test coverage, 11 points for (90%,99%], 8 points for (80%,89%], 6 points for (70%,79%], 5 points for (60%,69%], 3 points for (30%,59%], and 1 point for (0%,29%]. The automated tests we used to grade your code can be found in [PapademiaAdapterTests.java](src/test/java/ch/epfl/sweng/tests/graded/PapademiaAdapterTests.java). 

For how we graded the quality of your code, see the corresponding section at the bottom of this page.

## Part 1.3: Course quizzes [15 points]

Course quizzes, which test students' knowledge, are displayed by the `CourseQuizFormatter` interface, which is currently not implemented.

Depending on how a user is related to a course, they see quizzes in different ways.

[`CourseQuizFormatter`](src/main/java/ch/epfl/sweng/CourseQuizFormatter.java) has the following methods:

- `String getGreeting()`, which returns a greeting depending on the user:
  - If the user is teaching the course: `Hello, Prof.` followed by a space, then the user's name, then an exclamation mark `!`; e.g. `Hello, Prof. Alice!`.
  - If the user is attending the course: `Hello,` followed by a space, then the user's name, then an exclamation mark `!`; e.g. `Hello, Alice!`.
  - Otherwise, the text `Hello!`.
- `String getQuestionText(String statement, String answer)`, which returns a question description in the following way:
  - If the user is teaching the course: the `statement`, followed by a space, followed by the `answer`.
  - If the user is attending the course: the `statement` only.
  - Otherwise, the text `You cannot see this question.`.

Your task is to:

- Implement the `CourseQuizFormatter getQuizFormatter(User)` method in the [`Course`](src/main/java/ch/epfl/sweng/Course.java) class using the Factory design pattern. **[15 points]**
  - To get full credit for your Factory implementation, make sure none of the `CourseQuizFormatter` implementations contain any conditions (such as `if` statements).

We strongly recommend that you write basic tests to check that your implementation is correct, but there are no points assigned for testing in this part (1.3) of the exam.

__Solution__

Please see [`Course.java`](src/main/java/ch/epfl/sweng/Course.java). This creates three implementations of `CourseQuizFormatter` using the provided specification. 

__Grading__: 

The tests we used are in [CourseQuizFormatterTests.java](src/test/java/ch/epfl/sweng/tests/graded/CourseQuizFormatterTests.java), and the grading report is in `GradingReport_Practice.md` in your repo.

We took off 50% of the points if you did not use a Factory (as the statement requested). Multiple students wrote fully functioning implementations that, e.g., checked their constructor `User` argument for `null` -- this is a good use for private nested classes, which do not need to accept any input and can just assume that the externally facing methods will have sanitized it, thus requiring less code.

For code quality grading, see below.

## Code Quality Grading

We took off points for the following types of errors:

- Indentation and/or formatting that _deceives_ the reader, e.g., a line of code that is indented as if it's inside an `if` body but in reality it is outside, or using parentheses in a way that dangerously obfuscates the code [-5 points]
- Indentation and/or formatting that is confusing and inconsistent throughout the source code, i.e., done one way in one place and differently in another place [-2 points]
- Tests that do not assert anything, i.e. that contain no JUnit `assert...` calls, thus providing code coverage without any real testing [-5 points]
- Tests that were supposed to check for exceptions being thrown but pass even if the code under test does not `throw` anything [-5 points]
- Usage of the `assert` keyword in tests, since the `assert` keyword in Java is not enabled by default (one has to use a specific command-line switch when launching the JAR) and thus those tests are ineffective when running vanilla `./gradlew check` [-3 points]
- Convoluted implementations that are not resilient to minor specification changes, or that are extremely hard to understand when reading the code [-5 points]
- Confusing implementations that are needlessly complex and require developer effort to understand [-3 points]
- Unused constructor parameters in `CourseQuizFormatter` implementations [-2 points]
- Brittle flow control, such as using string comparisons to check the list title in `Homepage` in order to decide which placeholder to use [-4 points]
- String comparisons using `==` instead of `equals`, i.e. reference equality, which may or may not work depending on internal details of the JVM [-5 points]
- Duplicated code, such as a copy/pasted anonymous `Comparator` to sort courses in `Homepage` [-3 points]
- Wrong declaration of constants, such as not making them `final` or `private` when appropriate [-2 points]
- Naming that confuses the reader, such as random-looking identifier names [-5 points]
- Naming that doesn't conform to SwEng best practices, such as using _snake\_case_ instead of _camelCase_ for variable names [-2 points]
- Various case-by-case issues, as indicated in the grading report.

There were other issues that we saw but decided to not penalize this time:

- Poor spacing between lines, such as 4 consecutive linebreaks in a method for no good reason
- A single test class testing multiple classes, which hinders maintainability by making it hard to know where tests for a given class are;
- Using JUnit's `assertEquals` with parameters in the wrong order; it should be _expected_ then _actual_
- Using `assertTrue(x.equals(y))` instead of `assertEquals(x,y)`, which causes the error message to be non-descriptive (e.g. `expected false, but was true` instead of `expected 10, but was 5`)
- Test methods with non-descriptive names (e.g `errorTest`)
- Loops using `for(Thing t : things)` vs. `for(int i = 0; i < things.length; i++)` style inconsistently
- Commented-out code left in the solution submission
- Hardcoded `\n` separators instead of using `System.lineSeparator()`, which leads to lack of portability (e.g., the separator is `\r\n` on Windows but `\n` on Linux)