# CS305 – Software Engineering

## Final Exam

## Theory

This final exam has two parts, theory and practice.

The theory part is worth 20 points out of a total of 100 for the exam.
There are a total of 20 questions in this part of the exam, each worth 1 point.

Unless noted, each question has a single correct answer.

The practice part is a series of programming exercises,
and is worth 80 points of the 100 points on the exam. Please plan your time accordingly.

**Please fill in the theory part by marking an 'x' in the appropriate boxes
below (``[x]``).**

**Don't forget to commit this file and push it to Github on the `master` branch!**

You can directly edit this file in GitHub, simply click on the 'Edit this file' button in the top right corner (with the pencil icon).

**If you make any other marks on the test besides checking your selected answers,
you may lose points!**

### Question 1

What is usually true of code implemented using design patterns?
(multiple answers may be possible)

- [ ] It does not require code review.
- [ ] It runs faster.
- [ ] It is easier to understand and maintain.


### Question 2

You’re designing a data model for a `SwEngClass`, consisting of `Student`s and `Instructor`s,
both of which are `Human`s. What is the correct relation among these entities?

- [ ] `Student` and `Instructor` inherits from `Human`, `SwEngClass` contains `Student`s and `Instructor`s.
- [ ] `Instructor` inherits from `Human`, `Student` inherits from `Instructor`, `SwEngClass` contains `Student`s and `Instructor`s.
- [ ] `Human` inherits from `Instructor`, `Student` inherits from `Instructor`, `SwEngClass` contains `Student`s and `Instructor`s.
- [ ] `Student` and `Instructor` inherit from `SwEngClass`.


### Question 3

Can a `List<Giraffe>` be passed as an argument to a method that requires a
`List<Animal>` (where `Giraffe` inherits from `Animal`)?

- [ ] No, because `List<Giraffe>` and `List<Animal>` do not have the same internal representation.
- [ ] No, because its `get` method returns a `Giraffe` and not an `Animal`.
- [ ] No, because its `add` method takes in a `Giraffe` and not an `Animal`.
- [ ] Yes, because `Giraffe`s are `Animal`s.


### Question 4

Which of the following statements is **not** true of the Singleton design pattern?

- [ ] It ensures that only one instance of an object is created.
- [ ] It prevents errors in concurrent and parallel programs.
- [ ] It has similar uses to global variables.


### Question 5

It does not matter if a method checks at the beginning for a `null` argument and throws
an exception, if a statement later in the method body dereferences the argument
and throws a `NullPointerException`.

- [ ] Yes, this is always true and good practice because it make the code shorter and easier to read.
- [ ] Yes, this is always true, but it is still helpful to document the invariant by adding the explicit test.
- [ ] No, sometimes it matters.
- [ ] No, it always matters whether a null test is made explicitly.


### Question 6

Assume you have implemented three classes, `Integer`, `Rational`, and `Real`,
corresponding to numeric quantities. What is the proper inheritance relationship
among these clases?

- [ ] `Integer` implements `Rational`, which implements `Real`.
- [ ] `Real` implements `Rational`, which implements `Integer`.
- [ ] They should not inherit from each other because of the Liskov Substitution Principle.
- [ ] They should not inherit from each other because `Real` numbers are approximate.


### Question 7

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

- [ ] #1
- [ ] #2
- [ ] #3
- [ ] #4
- [ ] #5
- [ ] #6


### Question 8

Which of the following statements about testing are correct? (multiple answers may be possible)

- [ ] Carefully designed and implemented code is easier to test.
- [ ] Program verification is always better at finding bugs than testing.
- [ ] Random testing is the last, desperate act of incompetent programmers.
- [ ] Code with 100% test coverage is correct.


### Question 9

Consider the following code that tests whether a number is prime:

```java
boolean isPrime(int n) {
  if (n < 2) return false;
  boolean result = true;
  for (int i = 2; i <= Math.sqrt(n); ++i) {
    if (n % i == 0) {
      result = false;
    }
  }
  return result;
}
```

Consider the following optimizations:

1. Store the result of `Math.sqrt(n)` in a variable
2. Replace the loop condition by `i * i <= n`
3. Change the loop so that it tests only 2 and odd numbers
4. Replace `result = false` with `return false`

Which of these statements are true? (multiple answers may be possible)

- [ ] All of these optimizations are valid.
- [ ] Some of these optimizations are invalid (e.g., they change the result of
  the program).
- [ ] All of these optimizations give at most a constant speedup.
- [ ] Compilers can perform these optimizations automatically.
- [ ] One of these optimizations is a form of loop unrolling.
- [ ] One of these optimizations is a form of precomputation.


### Question 10

Imagine an Android app like Google Maps that enables you to find driving directions between two locations and displays the result on a map.
Which parts of the app implement the MVC pattern elements: model, view, controller, respectively?

- [ ] Network provider, widgets, activities.
- [ ] Activities, network provider, widgets.
- [ ] Widgets, activities, network provider.
- [ ] Activities, widgets, activities.


### Question 11

Which of the following questions about security are correct? (multiple answers may be possible)

- [ ] Testing is effective in finding security flaws.
- [ ] No software is secure.
- [ ] The Liskov substitution principle can prevent intentional misuse of components.
- [ ] The principle of least privilege can localize the consequent of a security flaw.


### Question 12

According to proponents of Agile software development, which of the following statements are true of the Scrum process? (multiple answers may be possible)

- [ ] Rapid iteration is a good substitute for advance planning.
- [ ] Joint ownership means no management.
- [ ] A sprint should be 2-4 weeks long.
- [ ] It works for projects of any size.


### Question 13

When should you do non-trivial refactoring of your code?

- [ ] Continuously: do it whenever you have a few minutes to spare.
- [ ] Before you start testing it.
- [ ] When the declining quality of the code impedes further development.
- [ ] Whenever the code changes and the number of bugs increases.


### Question 14

What is true about static program analysis? (multiple answers may be possible)

- [ ] A bug report that is not a real bug is called a *false positive.*
- [ ] Developers can focus on the most important issues by filtering static analysis reports by age, severity, etc.
- [ ] An IOException that occurs at runtime is a form of static analysis output.
- [ ] Android Lint is an example of a static analysis tool.
- [ ] Fuzz testing is an example of static analysis.
- [ ] FindBugs is an example of a static analysis tool.
- [ ] Compiler warnings are a form of static analysis output.


### Question 15

You would like to use an external library in your Android app. However, the library was not designed for Android, and it exposes objects that Android cannot display directly. Which design pattern should you use?

- [ ] Adapter
- [ ] Proxy
- [ ] Strategy
- [ ] Composite


### Question 16

Which of the following are good reasons to use design patterns that separate programming logic and user interaction such as MVC? (multiple answers may be possible)

- [ ] It results in less code overall.
- [ ] The code becomes easier to understand and maintain.
- [ ] It becomes easier to port the application to a different platform.
- [ ] Testing is easier.
- [ ] Most platforms, including Android, are already based on MVC-like patterns.


### Question 17

What is meant by *defensive programming*?

- [ ] Achieving 100% test coverage.
- [ ] Checking inputs to a program to ensure they are valid.
- [ ] Developing code on Mac OS rather than Windows.
- [ ] It's a term defined in the Department of Defense's "SCG-1 Secure Coding
  Guidelines."
- [ ] Never pushing to the master branch, and using pull requests instead.
- [ ] Using a system such as Jenkins to detect errors early.


### Question 18

Which of the following should be in code comments?

- [ ] *What* the code is doing.
- [ ] *When* the code was written.
- [ ] *Who* wrote the code.
- [ ] *Why* the code is written the way it is.


### Question 19

In a Java application to control fighting spaceships, you have the following code:

```java
DeathStar star = getDeathStar();
if (star.hasNotBeenBlownUp()) {
    star.shootAt(Planets.ALDERAAN);
}
```

What can you say about this code?

- [ ] It exemplifies defensive programming, which is a good practice.
- [ ] It is not enough: other conditions should also be checked, such as `star.isCannonWorking()`.
- [ ] It contains a race condition.
- [ ] It does not contain enough comments.


### Question 20

git is: (multiple answers may be possible)

- [ ] A version control system.
- [ ] Linus Torvalds' greatest work.
- [ ] Linus Torvalds' second greatest work, after Linux.
- [ ] An instrument of the devil, created to destroy the files those who are foolish enough to try to understand its full power.

### Question 21

Who chooses the items that are moved to the Sprint Backlog from the Product Backlog?

- [ ] The Product Owner
- [ ] The Scrum Master
- [ ] The Development Team

### Question 22

The Product Owner is responsible for

- [ ] coaching the Development Team
- [ ] maximizing the value of the product
- [ ] maximizing the work of the Development Team

## Happy Holidays!