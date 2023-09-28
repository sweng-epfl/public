# Designing exams and exercises

Exams are how we test whether students achieve the course objectives.
Our exercises are "exam-like": they have the same structure and we provide solutions that include high-level criteria for how one might grade them.
Exercise sets also include concrete ideas for how students could use the lecture's teachings in their own projects.

## Objectives

Questions must be:
- _Valid_: their results must reflect students' mastery, which needs clarity what we grade and how we grade it
- _Reliable_: answers must be graded consistently, so grades depend on the answer and not on the grader, the student, or other external factors
- _Scalable_: grading answers should be quick, so that a pair of graders can go through a couple hundred answers in an afternoon

Potential issues include:
- Questions that require reading too much or writing too much, leading to grades that reflect reading or writing skills rather than thinking skills
- Questions with multiple reasonable interpretations graded as if there was only one, leading to invalid grades
- Questions that students would not reasonably expect, such as knowledge of specific anecdotes from lectures
- Questions biased towards some part of the population, such as a problem about tennis that is easier for those who play or watch tennis
- Questions that require too much time to evaluate properly, leading to slow and poor quality grading
- Grading with too many manual steps and thus potential for errors, such as "running code in the graders' head" to check if it "should" run instead of automatically running it
- Grading that reflects graders' bias, such as giving the benefit of the doubt to some students based on the staff's impression of them

## Exam structure

Exams are laptop-based, open-book, and have 3 theoretical questions and 2 practical questions.
They have 100 total points, split ~39/59 for theory/practice, with 2 points for filling a feedback survey.
Theoretical subjects such as requirements do lead to practical questions.

The first theoretical question should be straightforward but not trivial, to welcome students into the exam.
The last theoretical question should be harder and test common misconceptions, with "obvious" answers that are wrong for a good reason.

The second practical question should have some interesting component that involves design skills, to challenge students who are already good at "writing code".

## Designing an exam / exercise set

The first thing to ask is _what is each question testing_?
Each question should be related to lecture objectives.
There should not be too much redundancy in objectives among questions.
There can be some; students should not be able to play an elimination game to figure out what a given question is testing.

## Designing a theoretical question

Theoretical questions introduce context, then ask questions about it, with students answering each sub-question in 1 or 2 sentences.
For instance, a theoretical question might introduce a system, ask how one would test it, then ask about performance optimization for it.

Each theoretical question should have a rubric with three, or four at most, expected "fully correct" answers.
_This limit makes grading scalable, do not exceed it_.
Graders will then create alternate "partially correct" rubric items as they see them.
The private grading criteria may contain additional information, such as "we will not accept X here because...", or "Y should only get 2/4 points here because...".

Sub-questions start with the minimum amount of context necessary. They then have a question, a placeholder for answers, and grading criteria, in the following format:
```
In 1 sentence, explain/suggest/... ...:

> ???

_You will receive up to 5 points for ..._
```
Students should know what properties their answer should have, so that they do not waste time writing more than is expected or lose points writing less than is expected.

Questions should feel like a conversation with engineers or managers. For instance, questions can involve a colleague asking for the student's opinion about a piece of code.
Importantly, this means questions must involve real-world applications of concepts, they should not be abstract questions about facts.
Accordingly, grading criteria should be similar to how an answer might be evaluated by a colleague.
For instance, merely answering "yes" or "no" to a design question is typically not enough, as colleagues would expect a reason behind an answer.

Do not make sub-questions depend on each other, such as "what is the problem?" and then "how can you fix it?",
as students cannot get the dependent sub-questions right if they did not get the dependency right.

Do not write multi-part sub-questions such as "which of these three things satisfy property P", because students will write in different orders which makes grading slow,
and a student who says nothing will get points for not mentioning the wrong things, which is invalid. Use multiple sub-questions instead.

## Designing a practical question

Practical questions can have two sub-parts: design and testing.

Design is about solving a problem, such as adding a feature to an existing code base, graded with automated tests and manual inspection.
To be gradable automatically, there should be one or more top-level methods that tests can call, and the internal design of the implementation can be graded manually.

Testing is about writing tests for an existing piece of code, potentially modifying the code to make it testable.
Coverage of passing tests for specific classes can be graded automatically, and the design of tests can be graded manually.
In particular, there should be manually-graded points for having useful assertions.

A practical question should begin with the minimal amount of context necessary in its statement.
Any information specific to classes or methods should only be in those methods' documentation in the code, to avoid redundancy and inconsistencies, and can be hyperlinked in the statement.

Each practical question should have a rubric with three, or four at most, expected properties of the code that must be manually graded, such as "readable code" or "injection of the dependencies".
_This limit makes grading scalable, do not exceed it_.
Graders will then create alternate "partially good" rubric items as they see them.
The private grading criteria may contain additional information, such as "we expect students to create a new class with design pattern X because...".
Expected properties should be high-level ones such as "features X and Y are not coupled", not low-level ones such as "using design pattern X", even if there is one obvious solution,
since students may come up with alternate, equally valid solutions that should not be penalized merely for being different than the intended solution.

Questions should include public high-level grading criteria indicating how many points students will get for each sub-task, including their (test) code being maintainable.
Criteria should all be positive grading, giving points for things students do, and not taking points away for things they haven't done.

The code we provide must use the exact same build files as the exercises, so that students know they will be able to build the exam if they can build the exercises.
It must also be of the quality expected from the best students: documented, good naming, robust error handling, no copy/pasted code, no unused imports, and so on.

Exam statements provide one basic test for both code and testing sub-parts.
For code, the provided test is for a basic scenario, such as one used as an example in the statement, and fails when run on the code given to students.
For tests, the test is for `1 + 1 = 2`, to have something in the file that students can already run.


## Repo structure for exams

Each student has one "exam" repo with one folder per exam, including a "mock" released in advance for them to make sure they can clone the repo and build code.

The `ReadMe.md` file at the top-level of the repo contains the shared information for all exams, which students can read in advance when the mock is released.

The repo also includes GitHub Actions. Each exam folder has a script for CI, and the overall exam repo finds the newest exam folder and executes its script.

## Checklists for exams

**These checklists are mandatory requirements, not mere suggestions, as they are derived from past mistakes.**

Statements
- High-level grading criteria are provided to students for each question
- Low-level grading criteria are privately agreed upon by the staff, _before the exam_
- All Markdown is readable without a Markdown reader, including line breaks to avoid horizontal scrolling since students may not notice they need to scroll and miss key information
- All practical exercises state that students may add files
- All practical exercises without a graded testing part state that students are encouraged to test their code anyway
- All practical exercises include instructions on how to run the main method if there is one, and how to run tests if there are some, for Windows, Linux, and macOS
- There are no "when we say X, we mean Y" kind of instructions at the top of the exam, each section is self-contained and clear
- No use of the words "positive" or "negative" because they mean different things in different languages (e.g., in English positive is >0 but in French it's >=0); use comparisons to 0 instead

Code
- All classes have documentation indicating whether they should or should not be modified and how (see below)
- All methods to be implemented by students are stubbed as `throw new UnsupportedOperationException("TODO");`, not empty, so that tests always fail on unimplemented code
- All references to classes in code documentation use @link so that an IDE can point out mistakes in such references
- All methods clearly describe whether `null` is allowed for each parameter and for the return value, as well as for sub-values such as list items
- All methods have `@throws` for any exception they should throw, including unchecked ones, with the exact constraints
  - For async methods, make it clear if this is inside a Future or directly outside of it
- There are no static constructors and no static fields except compile-time constants, otherwise initialization failures in these will prevent all code from running and automated tests will yield 0 points
- There are no references to stdin, stdout, or stderr, unless the exercise is specifically about these, as handling them in tests is hard and students occasionally forget debug prints that could cost them a lot of points if tests check std*
- All "value-like" classes have `toString`, `equals`, and `hashCode` implementations; `toString` is needed for readable test failures in grading reports, and `equals`/`hashCode` make testing easier (or use Java `record`)
- If conversions between byte arrays and strings are needed, the documentation explicitly states which encoding is used
- If code is expected to be run interactively with `gradlew run`, the folder should contain a `gradle.properties` file with contents `org.gradle.console=plain` to avoid Gradle's progress bar messing up input

Graded tests
- Every test checks exactly one thing at a time, using specific methods like `contains` when needed instead of testing an entire string just to check one part,
  so that student mistakes are accurately graded instead of causing many tests to fail
- Tests for multi-line strings work for both Windows-style `\r\n` and Unix-style `\n`
- No use of `setup`, `teardown`, fields, or constructors, as failures in these cause all tests to fail, and errors have annoying stack traces; use explicit method calls for sharing
- Tests only use the specific piece of student code they are testing, and do not use other existing classes for convenience, since students might have modified these other existing classes
- Tests refer to interface types rather than classes whenever possible, so that students can remove checked exceptions within implementations if they want to
- "Stress tests" need a very good reason to exist, as they can cause timeouts or crashes for the entire test suite leading to 0 points

## Class documentation headers

Put in the Javadoc for the class contained in the file.
These must be class documentation, not file headers as some IDEs collapse those.

For `build.gradle`, provided "sanity check" tests, and any source file students shouldn't touch:

```
// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!
```

For provided example tests, or other provided examples such as a "main" class:

```
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN edit everything in this file
// You CAN delete this file
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
```

For test files student need to fill (note the placeholder):

```
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
You MUST use this file to test <classname>
You CAN edit everything in this file
You MUST NOT rename or delete this file
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
```

For all other source files:

```
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods and constructors
// You CAN add new `private` members
// You MUST NOT add interface implementations or new non-`private` members, unless explicitly instructed to do so
// You MUST NOT edit the names, parameters, checked exceptions, or return types of existing members
// You MUST NOT delete existing members
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
```
