# Software Engineering - Homework 2

## Part 1: Theory [20 points]

This part of the homework has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `student-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces or stray characters, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

### Question 1 [2 points]

Which of the following statements are true about testing?

- [n] Test-driven development ensures that code is bug-free, since all code is written just to make tests pass
- [y] A bug regression test aims to prevent a specific bug from happening again under the same execution conditions
- [n] End-to-end testing always includes stress testing
- [y] Every bug found by whitebox fuzzing is a true positive, i.e., it indeed causes the code to fail

> TDD does not ensure the absence of bugs, one needs more mathematically powerful methods such as formal verification to do so.
> End-to-end testing treats software as a black box and simulates a real user using the program following a user flow, from its beginning to its end.
> See [lecture notes about testing](https://docs.google.com/document/d/1BSfR3oCMqVVyh0nzSLODUznh7kuUT7SGqCsPfxXmfMU/edit?usp=sharing)

### Question 2 [3 points]

If a `Gruyere` is a `Cheese`, is a `List<Gruyere>` a `List<Cheese>` (given the Java definition of [`List<E>`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/List.html))?

- [n] Yes, because anything that is true of `Gruyere` should be true of `Cheese` as well
- [n] Yes, because the internal representation of `Gruyere` and `Cheese` are the same since both are reference types
- [n] No, because elements retrieved from a `List<Cheese>` must be any `Cheese`, not just `Gruyere`
- [y] No, because elements added to a `List<Cheese>` can be any `Cheese`, not just `Gruyere`

> Consider the following code:
>
> ```java
> List<Gruyere> gruyeres = …; // get list of gruyeres in some way
> List<Cheese> cheeses = gruyeres;
> cheeses.put(new Emmental()); // !!!
> ```
>
> We put an `Emmental` into a list of `Gruyere`, which violates type safety: this is thus refused by the compiler.
> On the other hand, it is perfectly legal for a `List<Cheese>` to only ever return `Gruyere` elements since a `Gruyere` is a `Cheese`.
> See [lecture about Inheritance vs Containment](https://moodle.epfl.ch/pluginfile.php/2845012/mod_resource/content/2/Recap%20-%20Inheritance%20vs.%20Containment.pdf)

### Question 3 [1 point]

Which of the following variable declarations adhere to SwEng good practices for variable naming in Java?

- [n] `String City`
- [y] `int numberOfQuestions`
- [n] `ConnectionHandler cnHdlr`
- [n] `boolean menu_open`

> The first variable is in PascalCase, which does not comply with the camelCase Java notation.
> The third variable is non-descriptive and cryptic as it has been shortened.
> The fourth variable is snake_case, and since it is a boolean, it should begin with a verb (for instance `isMenuOpen`).
> See [lecture notes about Good code](https://docs.google.com/document/d/1QL_YjefgfkwSwIxDqJNT7LCiP_tiXnK4oE3RMoAlbus/edit?usp=sharing)

### Question 4 [3 points]

Consider the following code snippet:

```java
class Student {
   private final String name;
   private final int ageYears;
 
   public Student(String name, int ageYears) {
       this.name = name;
       this.ageYears = ageYears;
   }
  
   // ... getters and other methods
}
```

We would also like to instantiate students given their name (the same `String name`) and their birth date as an [epoch timestamp](https://en.wikipedia.org/wiki/Unix_time), i.e. an integer that represents the number of seconds between 00:00:00 UTC on 1 January 1970 and the specified birth date, however we will keep the internal representation of this information as the `int ageYears` field. Which of the following code techniques should you use?

- [n] A second `Student(String name, int birthdateEpoch)` constructor
- [y] The Factory design pattern
- [n] The Composite design pattern
- [n] The Proxy design pattern

> The second constructor `Student(String name, int birthdateEpoch)` will be refused by the compiler, as the first constructor has the same type parameters. For this reason, you will have to use the static factory design pattern, which allows you to instantiate students using a static method with a new name which will first transform the input epoch into age years and then call the constructor. One could also make the existing constructor private, for instance if only the epoch construction should be used while keeping the same underlying representation.
> The composite design pattern allows the composition of tree-like structures of hierarchical objects under a unified interface.
> The Proxy implements a delegate for a remote object, but there is no resource to be fetched online.
> See [lecture notes about Good code](https://docs.google.com/document/d/1QL_YjefgfkwSwIxDqJNT7LCiP_tiXnK4oE3RMoAlbus/edit?usp=sharing)

### Question 5 [1 point]

Which of the following statements are true about coding conventions?

- [y] Linters can find coding conventions violations
- [n] Code formatters perform static analysis to detect programming errors and coding conventions violations
- [y] Coding conventions in a programming language may be partly defined by the creators of the language
- [n] Programming teams should always use the Google style guides when starting a new project in a language designed by Google

> Linters perform static analysis over code and can find bugs that are not caught by a compiler (for instance, invoking a method on a null instance explicitly given as parameter to a function).
> Formatters only read the syntax of the language in order to arrange the layout of the code, without deeper static analysis.
> Programming teams should agree on a set of coding conventions before working on a project, but not necessarily use the Google style guides.
> See [lecture notes about Good code](https://docs.google.com/document/d/1QL_YjefgfkwSwIxDqJNT7LCiP_tiXnK4oE3RMoAlbus/edit?usp=sharing)

### Question 6 [2 points]

Which of the following statements are true about fuzzing?

- [n] Fuzzing can find all bugs in a codebase
- [y] Mutation-based fuzzing (such as AFL or libfuzzer) are effective in finding memory safety bugs
- [n] Fuzzing can replace unit tests
- [y] A fuzzer can perform better with a faster CPU and more memory

> Fuzzing, as most forms of testing, can only detect the presence of bugs rather than their absence.
> Fuzzing cannot replace unit tests: for instance, it does not verify that methods return the expected values. It can however complement a solid test suite.
> See [lecture notes about testing](https://docs.google.com/document/d/1BSfR3oCMqVVyh0nzSLODUznh7kuUT7SGqCsPfxXmfMU/edit?usp=sharing)

### Question 7 [2 points]

If you were tasked to write tests for the following code, what is the maximum path coverage you could obtain?

```java
int value = getValue(); // can return any integer value
if (value < 0) {
   System.out.println("Value is negative");
} else if (value >= value * value) {
   System.out.println("Value is bigger than its square");
} else if (value > Integer.MAX_VALUE) {
   System.out.println("Value is huge");
} else {
   System.out.println("Value is something else");
}
```

- [n] 0 ≤ path coverage < 33%
- [n] 33% ≤ path coverage < 66%
- [y] 66% ≤ path coverage < 100%
- [n] path coverage = 100%

> The third statement is unreachable.
> See [lecture notes about testing](https://docs.google.com/document/d/1BSfR3oCMqVVyh0nzSLODUznh7kuUT7SGqCsPfxXmfMU/edit?usp=sharing)

### Question 8 [2 points]

Which of the following are likely to be good code comments?

- [y] `// User parameter expected to be null if user is not logged in`
- [n] `// instantiate a new Complex number given x double and y double`
- [y] `/** @author John Sweng */`
- [n] `// evil floating point bit level hacking`

> The first comment is useful: it explains why the code does something non-obvious
> The second comment is useless: it repeats exactly what the code does
> The third comment is an example of annotation comment used for metadata
> The fourth comment is useless: it does not describe what the non-obvious code does or why it was written that way (this example is taken from [Quake III Arena](https://en.wikipedia.org/wiki/Fast_inverse_square_root#Overview_of_the_code))
> See [lecture notes about Good code](https://docs.google.com/document/d/1QL_YjefgfkwSwIxDqJNT7LCiP_tiXnK4oE3RMoAlbus/edit?usp=sharing)

### Question 9 [2 points]

Which of the following are good ways to deal with potentially bad inputs?

- [n] A video player reads a bad frame from an MP4 file: terminate the program process
- [y] A voice chat software detects a mic input with constant maximum volume at ultrasound frequency: send a constant zero audio signal instead
- [n] Add a single global `try-catch` of any `Exception` as the first statement of the `main` method
- [y] A `Logger` class obtains a mutable `List<Message> messages` as a constructor parameter to persist it onto disk: save the field with a defensive copy first using `Collections.unmodifiableList(new ArrayList<Message>(messages))`

> It would be overkill and frustrating for the user to terminate a video player because of a single bad frame: displaying a black frame instead is more appropriate.
> Exceptions should be handled as close to possible to the source, where sensible.
> See [lecture about defensive programming](https://moodle.epfl.ch/pluginfile.php/2849159/mod_resource/content/1/Slides%20%28Defensive%20programming%29.pdf)

### Question 10 [2 points]

Which of the following are clearly defined user stories?

- [y] As an employee, I want to see a display of each month’s payroll, so that I can confirm my number of work hours
- [n] A text box is used to submit feedback to developers
- [y] As a user, I can zoom on the map to find nearby doctors
- [n] As an accountant, I want to click a RaisedButton in the BottomNavigationBar, so that I can access the EmployeeSalaries database table.

> User stories must have a role, a desired feature and a reason.
> The second option does not mention what kind of user is able to perform the action.
> The last option provides implementation details, which should not be a concern to users and customers.
> See [lecture about Requirements](https://moodle.epfl.ch/pluginfile.php/2842904/mod_resource/content/1/2020-Wk5-Requirements.pdf)
