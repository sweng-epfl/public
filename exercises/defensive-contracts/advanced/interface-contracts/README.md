# [Covariance and contravariance](https://en.wikipedia.org/wiki/Covariance_and_contravariance_(computer_science))
Inheritance can be treated, from the contractual point of view, as the obligation of the subclass to respect the contracts of the superclass.
So a `SwEngStudent` is first of all a `Student`.
This means you can always treat a `SwEngStudent` as if it were a student, i.e., you can ask for their `gaspar`, or expect them to take an exam.

It gets trickier when you consider collections of objects.
In Java, `List<? extends Student>` expresses a lower bound on the type of the objects in the list (where we consider `Object` the lowest object).
Such a list contains objects that can be treated as `Student`s.
`List<? super Student>` expresses an upper bound on the type of the objects in the list.
In such a list you can find `Object`s or `Student`s — any of the superclasses of `Student` including itself, but never a subclass (e.g. no `SwEngStudent`).

The latter case may sound pointless, but consider these two lists:

```java
List<? extends Student> l1 = ...;
List<? super Student> l2 = ...;
```

Which of the uses of `l1` and `l2` are safe?

```java
Student s1 = l1.get(0); // Use case 1
Student s2 = l2.get(0); // Use case 2
l1.add(new Student()); // Use case 3
l2.add(new Student()); // Use case 4
```
