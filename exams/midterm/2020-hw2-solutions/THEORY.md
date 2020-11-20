# Software Engineering - Homework 2

## Part 1: Theory [20 points]

This part of the homework has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `student-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces or stray characters, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

### Question 1 [2 points]

Which of the following statements are true about testing?

- [ ] Test-driven development ensures that code is bug-free, since all code is written just to make tests pass
- [ ] A bug regression test aims to prevent a specific bug from happening again under the same execution conditions
- [ ] End-to-end testing always includes stress testing
- [ ] Every bug found by whitebox fuzzing is a true positive, i.e., it indeed causes the code to fail


### Question 2 [3 points]

If a `Gruyere` is a `Cheese`, is a `List<Gruyere>` a `List<Cheese>` (given the Java definition of [`List<E>`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/List.html))?

- [ ] Yes, because anything that is true of `Gruyere` should be true of `Cheese` as well
- [ ] Yes, because the internal representation of `Gruyere` and `Cheese` are the same since both are reference types
- [ ] No, because elements retrieved from a `List<Cheese>` must be any `Cheese`, not just `Gruyere`
- [ ] No, because elements added to a `List<Cheese>` can be any `Cheese`, not just `Gruyere`


### Question 3 [1 point]

Which of the following variable declarations adhere to SwEng good practices for variable naming in Java?

- [ ] `String City`
- [ ] `int numberOfQuestions`
- [ ] `ConnectionHandler cnHdlr`
- [ ] `boolean menu_open`


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

- [ ] A second `Student(String name, int birthdateEpoch)` constructor
- [ ] The Factory design pattern
- [ ] The Composite design pattern
- [ ] The Proxy design pattern


### Question 5 [1 point]

Which of the following statements are true about coding conventions?

- [ ] Linters can find coding conventions violations
- [ ] Code formatters perform static analysis to detect programming errors and coding conventions violations
- [ ] Coding conventions in a programming language may be partly defined by the creators of the language
- [ ] Programming teams should always use the Google style guides when starting a new project in a language designed by Google


### Question 6 [2 points]

Which of the following statements are true about fuzzing?

- [ ] Fuzzing can find all bugs in a codebase
- [ ] Mutation-based fuzzing (such as AFL or libfuzzer) are effective in finding memory safety bugs
- [ ] Fuzzing can replace unit tests
- [ ] A fuzzer can perform better with a faster CPU and more memory


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

- [ ] 0 ≤ path coverage < 33%
- [ ] 33% ≤ path coverage < 66%
- [ ] 66% ≤ path coverage < 100%
- [ ] path coverage = 100%


### Question 8 [2 points]

Which of the following are likely to be good code comments?

- [ ] `// User parameter expected to be null if user is not logged in`
- [ ] `// instantiate a new Complex number given x double and y double`
- [ ] `/** @author John Sweng */`
- [ ] `// evil floating point bit level hacking`


### Question 9 [2 points]

Which of the following are good ways to deal with potentially bad inputs?

- [ ] A video player reads a bad frame from an MP4 file: terminate the program process
- [ ] A voice chat software detects a mic input with constant maximum volume at ultrasound frequency: send a constant zero audio signal instead
- [ ] Add a single global `try-catch` of any `Exception` as the first statement of the `main` method
- [ ] A `Logger` class obtains a mutable `List<Message> messages` as a constructor parameter to persist it onto disk: save the field with a defensive copy first using `Collections.unmodifiableList(new ArrayList<Message>(messages))`


### Question 10 [2 points]

Which of the following are clearly defined user stories?

- [ ] As an employee, I want to see a display of each month’s payroll, so that I can confirm my number of work hours
- [ ] A text box is used to submit feedback to developers
- [ ] As a user, I can zoom on the map to find nearby doctors
- [ ] As an accountant, I want to click a RaisedButton in the BottomNavigationBar, so that I can access the EmployeeSalaries database table.

