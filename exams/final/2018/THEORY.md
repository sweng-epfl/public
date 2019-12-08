# CS305 – Software Engineering
## Final Exam

# Part 1: Theory [30 points]

This part of the final exam has theoretical questions, with the number of points indicated next to each one.

- This file is named `THEORY.md`.
- Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo.  
  Do not change the name of this file, and do not change the existing text unless the question's statement asks you to do so.  
  We will only grade the `master` branch.
- The answer choices are provided as `[ ]`.  
  You should change each one to `[y]` for a correct answer and to `[n]` for each incorrect answer.  
  This requires that you replace the space between all the brackets with either `y` or `n`.  
  Do not change anything else in any way, such as leaving extraneous spaces or moving text around.
- We provide a [`TheoryReporter`](src/test/java/ch/epfl/sweng/tests/TheoryReporter.java) test class, which will check that the format of this file is correct.  
  It is your responsibility to run it and make sure that the test passes.
- If you leave any checkbox within a question empty, it means that you did not finish answering the question, so we will not grade that question at all, and you will get 0 points for it.
- Unless otherwise specified, questions may have zero, one, or more correct answer choices.

### Question 1 [3 points]
Which of the following are good examples of commit messages?

- [ ] `Minor improvements`
- [ ] `Second attempt at fixing bug`
- [ ] `Add tests for the Leaderboard class`
- [ ] `Tweaks to the Leaderboard class`

### Question 2 [3 points]
You are in the middle of a sprint, and you find a major bug that has been lurking in the code for months.
What should you do?

- [ ] Find someone on your team who is free and tell them to fix the bug
- [ ] Fix the bug yourself as soon as possible
- [ ] Enter the bug in your issue tracking system and tell the Product Owner
- [ ] Speed up to finish your sprint work, and then fix the bug

### Question 3 [3 points]
The `java.util.Stack` class represents a last-in-first-out data structure, also known as a stack.
Thus, it declares `push` and `pop` operations to add an element on the stack and retrieve the last added element, respectively.
It inherits from the `java.util.Vector` class, which means it also has methods such as `add` to add an element at any index, and `remove` to remove an element at any index.
Is this good design?

- [ ] Yes, since a stack is logically a collection, its interface should extend an existing collection
- [ ] Yes, since a stack can be implemented using a collection, it should inherit one for code reuse
- [ ] No, since a stack should be more restricted than a vector, its interface should be separate
- [ ] No, since a stack does not use all vector operations, it should not use a vector as part of its implementation

### Question 4 [3 points]
Which of the following can you achieve by writing tests?

- [ ] Prove that code is correct
- [ ] Prove that code has bugs
- [ ] Catch bugs early in the development cycle
- [ ] Catch bugs late in the development cycle

### Question 5 [3 points]
If you have to choose between fuzzing with AFL and unit testing with JUnit (e.g. because you have limited time and cannot do both), which of the following are good reasons to use fuzzing instead of unit testing?

- [ ] Fuzzing will always cover more code in less CPU time
- [ ] Fuzzing can quickly find difficult corner cases that humans cannot think of
- [ ] Unit testing cannot find security bugs
- [ ] Unit testing requires more manual effort

### Question 6 [3 points]
Given this code:

```java
@Invariant("contents != null") // (1)
@Ensures("!contents.empty()") // (2)
class Box {
  private List<Cookie> contents;

  public Box(int size) {
    contents = new ArrayList<>();
    for (int n = 0; n < size; n++) {
      contents.add(new Cookie());
    }
  }
  
  public boolean isEmpty() {
    return contents.size() == 0;
  }

  @Requires("!isEmpty()") // (3)
  @Invariant("monster != null") // (4)
  public void feed(CookieMonster monster) {
    Cookie c = contents.get(0);
    contents.remove(0);
    monster.eat(c);
  }
}
```

Which of the numbered lines make sense as code contracts?

- [ ] `(1)`
- [ ] `(2)`
- [ ] `(3)`
- [ ] `(4)`

### Question 7 [3 points]

Which of the following are possible benefits of refactoring code?

- [ ] Making program logic more coherent
- [ ] Improving code readability
- [ ] Improving performance in common cases
- [ ] Decreasing code complexity

### Question 8 [3 points]
If a `Student` is a `Human`, is a `Collection<Student>` a `Collection<Human>`? (given the Java definition of [`Collection<E>`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html))

- [ ] Yes, because anything that is true of `Student`s should be true of `Human`s as well
- [ ] Yes, because the internal representation is the same since both `Student`s and `Human`s are reference types
- [ ] No, because elements retrieved from a `Collection<Human>` can be any `Human`, not just `Student`s
- [ ] No, because elements added to a `Collection<Human>` can be any `Human`, not just `Student`s

### Question 9 [3 points]
If you were tasked to write tests for the following code, what is the maximum path coverage you could obtain?

```java
int size = getSize(); // can return anything
if (size < 0) {
  System.out.println("Size is negative");
} else if (Math.sqrt(size) > 100) {
  System.out.printn("Size is huge");
} else if (size > 100 * 100) {
  System.out.println("Size is pretty big");
} else {
  System.out.println("Size is okay, I guess");
}
```

- [ ] 0 ≤ path coverage ≤ 25%
- [ ] 25% < path coverage ≤ 50%
- [ ] 50% < path coverage ≤ 75%
- [ ] 75% < path coverage ≤ 100%

### Question 10 [3 points]
Which of the following are good examples of code comments?

- [ ] `// FooLibrary claims to handle negative numbers but in fact it does not`
- [ ] `// Instantiate a new Person using the name the user gave us`
- [ ] `// This algorithm has poor complexity, but the input is guaranteed to have <10 elements`
- [ ] `// The "getName" method returns the name.`
