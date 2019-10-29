# Software Engineering - Mock Midterm

## Part 1: Theory [40 points]

This part of the midterm exam has 14 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`.
Provide the answers in this file and commit the file to the `master` branch.
Do not change the name of the file, and do not change the existing text except for the answer choices.
We will only grade the `master` branch.
Do not introduce extraneous spaces, as that may break our scripts and cause you to lose points.

The answer choices are provided as `[ ]`.
You should change each one to `[y]` for a yes answer and to `[n]` for each no answer.
This requires that you replace the space between all the brackets with either `y` or `n`.
Unless otherwise specified, questions may have zero, one, or more correct answer choices.

If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all and you will get 0 points for it.


### Question 1 [1 point]

Given the following classes, would you rather choose containment or inheritance to improve the current design?

- [y] Inheritance
- [n] Containment

```java
public class Lion {
	public final String NAME = "Lion";

	public String sleeps() {
		return "All day!";
	}

	public String eats() {
		return "Zebras";
	}
}

public class Tiger {
	public final String NAME = "Tiger";

	public String sleeps() {
		return "All day!";
	}

	public String eats() {
		return "Boars";
	}
}

public class Cat {
	public final String NAME = "Cat";

	public String sleeps() {
		return "All day!";
	}

	public String eats() {
		return "Mice";
	}
}
```

> Inheritance is a good choice to improve the current design because all the classes share common methods and behaviour.
> A more general `Feline` base class can be created, the provided classes being just specializations of that class, exhibiting an "is a" relationship.
> On the other hand, containment means implementing a class with the help of another class, exhibiting an "has a" relationship, which is not the case here.


### Question 2 [3 points]

Which of the following is/are true:

- [y] In TDD, a newly-written test must always fail
- [n] In TDD, a newly-written test may pass but is likely to fail
- [n] You can prove the correctness of your program by writing tests for enough cases
- [n] Fomatting of the code is useless as the code is written to be executed by a computer


### Question 3 [3 points]

Which of the following constructs may violate the single-exit principle of structured programming:

- [y] Unconditional jump (`goto`)
- [n] Selection (`if` statement)
- [n] Loops (`while`, `for`)
- [y] Exceptions (`throw`)


### Question 4 [3 points]

Which of the following are true about Scrum:

- [n] Scrum should be used when all the requirements are known before development
- [y] Scrum enables the dev team to interact frequently and receive feedback often from the customer
- [n] The Scrum master is a manager that decides which tasks are assigned to the developers
- [y] The product owner is not allowed to change the sprint backlog


### Question 5 [3 points]

Which of the following are good Git commit messages:

- [n] "I fixed an annoying bug in the Account class."
- [y] "Add Transaction class modeling money transfer logic"
- [n] "Refactor database as singleton as it should not be instantiated multiple times, the instance is spawned lazily at first use and the getInstance factory must be called to use it"
- [n] "A bug fix"


### Question 6 [3 points]

Which of the following Scrum events is attended by the key stakeholders?

- [n] Scrum Retrospective
- [n] Sprint Planning
- [y] Sprint Review
- [n] Daily Scrum


### Question 7 [3 points]

Which of the following guidelines should you follow when designing an interface:
- [n] Maximize the coupling to other classes.
- [n] Avoid layers of abstraction.
- [y] Avoid exposing the environment.
- [n] Don’t hide information e.g. design and implementation choices.


### Question 8 [3 points]

What is the fastest/easiest way to determine whether a new pull request won’t break a large, complex codebase and is safe to merge?
- [n] First pull the branch to be merged to your local repository (on your machine), build the project and run all the checks and tests locally to make sure everything is OK.
- [n] Ask several experienced people from the Development Team to review the pull request and if everyone approves it means it is safe to merge.
- [y] Use a Continuous Integration tool as part of the central development workflow.
- [n] First pull the branch to be merged to your local repository (on your machine), build the project and write new tests for everything that might go wrong. If they all pass, it means the branch is safe to merge along with the new tests that will catch later possible regressions.


### Question 9 [3 points]

Which workflow represents the TDD approach:

- [n] feature request; feature implementation; test for the feature
- [n] test for the feature; feature request; feature implementation
- [y] feature request; test for the feature; feature implementation
- [n] feature implementation; test for the feature; feature request
- [n] feature implementation; feature request; test for the feature


### Question 10 [3 points]

In Scrum, what should happen to your backlogs when your team discovers a major bug in the middle of a sprint?

- [n] The bug becomes an item in the sprint backlog and the team starts working on it right away.
- [n] The bug becomes an item in the sprint backlog. After the team finishes their other tasks for the sprint, if there is time, they fix the bug.
- [n] The bug becomes an item in the product backlog. It has the highest priority.
- [y] The bug is communicated to the product owner, and she puts it in the product backlog and decides what priority it gets.


### Question 11 [3 points]

You’re designing a data model for a `SwEngClass`, consisting of `Student`s and `Instructor`s,
both of which are `Human`s. What is the correct relation among these entities?
(Only one is correct)

- [y] `Student` and `Instructor` inherit from `Human`, `SwEngClass` contains `Student`s and `Instructor`s.
- [n] `Instructor` inherits from `Human`, `Student` inherits from `Instructor`, `SwEngClass` contains `Student`s and `Instructor`s.
- [n] `Human` inherits from `Instructor`, `Student` inherits from `Instructor`, `SwEngClass` contains `Student`s and `Instructor`s.
- [n] `Student` and `Instructor` inherit from `SwEngClass`.


### Question 12 [3 points]

Can a `List<Giraffe>` be passed as an argument to a method that requires a
`List<Animal>` (where `Giraffe` inherits from `Animal`)?
(Only one is correct)

- [n] No, because `List<Giraffe>` and `List<Animal>` do not have the same internal representation.
- [n] No, because its `get` method returns a `Giraffe` and not an `Animal`.
- [y] No, because its `add` method takes in a `Giraffe` and not an `Animal`.
- [n] Yes, because `Giraffe`s are `Animal`s.


### Question 13 [3 points]

Which preconditions or postconditions violate rules about inheritance? (multiple answers may be possible)

```java
public class Shape {
    @Requires("xScale > 0") // #1
    @Requires("yScale > 0") // #2
    @Ensures("after.area().equals(before.area() * xScale * yScale)") // #3
    void enlarge(double xScale, double yScale);
}

public class Square extends Shape {
    @Requires("xScale > 0") // #4
    @Requires("xScale == yScale") // #5
    @Ensures("true") // #6
    void enlarge(real xScale, real yScale);
}
```

- [n] #1
- [n] #2
- [n] #3
- [n] #4
- [y] #5
- [y] #6


### Question 14 [3 points]

Which of the following statements about testing are correct? (multiple answers may be possible)

- [y] Carefully-designed and implemented code is easier to test.
- [n] Program verification is always better at finding bugs than testing.
- [n] Random testing is the last, desperate act of incompetent programmers.
- [n] Code with 100% test coverage is correct.
