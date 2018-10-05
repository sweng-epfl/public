# Exercise 2: Code Coverage vs. Defensive Programming
This exercise help you understand the impact that defensive programming has on code coverage. Code coverage is a measure used to describe the degree to which the source code of a program is executed when a particular test suite runs.

On the other hand, defensive programming protects your code from invalid inputs and barricades your program to contain the damage caused by errors.

We will now explore the interaction between code coverage and defensive programming. For that purpose, you will apply the principles of defensive programming to a code snippet and eventually evolve it from the imperative to the functional paradigm.

### Java 8's Optional
Since JDK 1.8, Java introduced the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) container object with the purpose of providing a type-level solution for representing optional values instead of using null references.

There are several ways of creating and using Optional objects.

To create an empty Optional object:

```java
Optional<String> opt = Optional.empty();
```

To create a non-empty Optional object:

```java
Optional<String> opt = Optional.of("Hello, world!");
```

To create an Optional for a possibly null object:

```java
Optional<String> opt = Optional.ofNullable(object);
```

To check if there is a value in it or not:

```java
Optional<String> opt = Optional.ofNullable(object);
if (opt.isPresent()) { /* ... */ }
```

To run some code on the wrapped object if a value is present:

```java
Optional<String> opt = Optional.ofNullable(object);
opt.ifPresent(str -> System.out.println(str));
```

To get the value of the wrapped object:
```java
Optional<String> opt = Optional.ofNullable(object);
String str = opt.get();
```

> You should avoid using the `get` method in general. The latter would require a call to `isPresent` beforehand. The goal of Optional is rather to use `map`, `flatMap`, and `ifPresent`.

To transform the wrapped object into another wrapped object:
```java
Optional<String> opt = Optional.ofNullable(object);
Optional<Integer> optInt = opt.flatMap(str -> Integer.valueOf(str));
```

For further details, see the [Optional API](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html).

### Case study
Consider this code:

```java
Course course = Courses.findByCode(code);
Lecturer lecturer = course.getLecturer();
String name = lecturer.getName();
System.out.println(name);
```

This code can obviously break with `NullPointerException` if any term is null.

A typical way of avoiding this would be doing the following:

```java
Course course = Courses.findByCode(code);
if (course != null) {
  Lecturer lecturer = Course.getLecturer();
  if (lecturer != null) {
    String name = lecturer.getName();
    if (name != null) {
      System.out.println(name);
    }
  }
}
```

This is ugly and brittle, and it's easy to miss some null checks. You can also expect the code coverage to decrease.

An `if` statement actually adds a node in the decision tree of your program. In other words, it introduces two branches: one branch for when the condition is true, and one branch for when the condition is false.

You gain robustness at the cost of complexity.

Let's try with Java 8's Optional:

```java
Optional<Course> optionalcourse = Courses.findByCode(code);
if (optionalcourse.isPresent()) {
  Course course = optionalcourse.get();
  Optional<Lecturer> optionalLecturer = course.getLecturer();
  if (optionalLecturer.isPresent()) {
    Lecturer lecturer = optionalLecturer.get();
    Optional<String> optionalName = lecturer.getName();
    if (optionalName.isPresent()) {
      String name = optionalName.get();
      System.out.println(name);
    }
  }
}
```

This isn't a lot better than null checks. Some might argue that it makes your intent clearer, but in the end there isn't much of a difference, because null checks are pretty obvious in these kinds of situations.

The problem lies in the code logic itself—it's typical imperative programming: you call a function, get the returned value, and take a decision based on the latter.

Object-oriented programming and dynamic polymorphism are useless here. They don't apply to this code pattern.

Let's try "the functional way":

```java
Courses.findByCode(code)
  .flatMap(Course::getLecturer)
  .flatMap(Lecturer::getName)
  .ifPresent(System.out::println);
```

By using Optional, and never working with null, you can avoid null checks altogether. Furthermore, with the functional paradigm, you can also avoid adding new branches in the decision tree of your program.

The code becomes clearer, less error-prone, robust, and simple.

> While this is true on the surface, in practice `Optional` just hides the very same branch you got rid of. The code coverage will surely increase. The complexity will remain the same under the hood, though. Therefore, keep in mind that `Optional` improves correctness by making it harder to make mistakes, not by decreasing total complexity.

### Tasks

Reading this was your only task. You are aware now that defensive programming certainly impacts code coverage.

You completed the exercise. Congrats!