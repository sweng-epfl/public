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

- [n] `Minor improvements`
- [n] `Second attempt at fixing bug`
- [y] `Add tests for the Leaderboard class`
- [n] `Tweaks to the Leaderboard class`

**Explanation**: The only reasonable option here is the third one, which clearly explains what the developer did.
Other options are either too vague (what are _improvements_ or _tweaks_?) or useless (whether it's the second or thousandth attempt at fixing a bug doesn't matter, only what the bug was and possibly a summary of how it was fixed).

### Question 2 [3 points]
You are in the middle of a sprint, and you find a major bug that has been lurking in the code for months.
What should you do?

- [n] Find someone on your team who is free and tell them to fix the bug
- [n] Fix the bug yourself as soon as possible
- [y] Enter the bug in your issue tracking system and tell the Product Owner
- [n] Speed up to finish your sprint work, and then fix the bug

**Explanation**: The only option here is the third one, since as a software engineer you cannot change the backlog (to make the bug a priority) or direct other developers.

### Question 3 [3 points]
The `java.util.Stack` class represents a last-in-first-out data structure, also known as a stack.
Thus, it declares `push` and `pop` operations to add an element on the stack and retrieve the last added element, respectively.
It inherits from the `java.util.Vector` class, which means it also has methods such as `add` to add an element at any index, and `remove` to remove an element at any index.
Is this good design?

- [n] Yes, since a stack is logically a collection, its interface should extend an existing collection
- [n] Yes, since a stack can be implemented using a collection, it should inherit one for code reuse
- [y] No, since a stack should be more restricted than a vector, its interface should be separate
- [n] No, since a stack does not use all vector operations, it should not use a vector as part of its implementation

**Explanation**: Methods such as `add` and `remove` completely break the stack abstraction. Therefore, a stack interface should not contain those.
It would be fine to implement a stack using the `Vector` class internally, i.e. with composition, but not inheritance.
Note that the Java standard library contains a more recent and properly designed interface for a stack called `Deque`.

### Question 4 [3 points]
Which of the following can you achieve by writing tests?

- [n] Prove that code is correct
- [y] Prove that code has bugs
- [y] Catch bugs early in the development cycle
- [y] Catch bugs late in the development cycle

**Explanation**: You cannot prove that code is correct with testing since this would require testing with all possible inputs, and the space of inputs is sometimes infinite, e.g. all possible `String`s.
But you can prove that the code has bugs (by finding some), and catch bugs at any point in the development cycle (by writing tests to expose them).

### Question 5 [3 points]
If you have to choose between fuzzing with AFL and unit testing with JUnit (e.g. because you have limited time and cannot do both), which of the following are good reasons to use fuzzing instead of unit testing?

- [n] Fuzzing will always cover more code in less CPU time
- [y] Fuzzing can quickly find difficult corner cases that humans cannot think of
- [n] Unit testing cannot find security bugs
- [y] Unit testing requires more manual effort

**Explanation**:

- There are cases where fuzzing is not very effective, e.g. if the code validates the integrity of data using a checksum, it's very unlikely that random fuzzing will find a valid checksum.
- On the other hand, randomly mutating the input is an effective way to test many corner cases that humans forget about.
- Unit testing can certainly find security bugs, such as a method that forgets to authenticate an user before performing a privileged operation.
- Unit testing definitely requires more manual effort, since the developer has to write tests as opposed to launching a fuzzer with a few seeds.

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

- [n] `(1)`
- [n] `(2)`
- [y] `(3)`
- [n] `(4)`

**Explanation**:

- `(1)` is invalid because it refers to a private member of the class, which contracts cannot use
- `(2)` is invalid because a class cannot "ensure" anything; in a class,there is no notion of an execution after which something could happen
- `(3)` is a valid contract that guarantees the method will not fail because of the absence of a cookie
- `(4)` is invalid because methods cannot have invariants

### Question 7 [3 points]
Which of the following are possible benefits of refactoring code?

- [y] Making program logic more coherent
- [y] Improving code readability
- [y] Improving performance in common cases
- [y] Decreasing code complexity

**Explanation**: All of these are possible benefits of refactoring. This does not mean that _any_ refactoring will bring _all_ benefits, only that _some_ refactorings can bring _some_ benefits.
Think carefully before refactoring code!

### Question 8 [3 points]
If a `Student` is a `Human`, is a `Collection<Student>` a `Collection<Human>`? (given the Java definition of [`Collection<E>`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html))

- [n] Yes, because anything that is true of `Student`s should be true of `Human`s as well
- [n] Yes, because the internal representation is the same since both `Student`s and `Human`s are reference types
- [n] No, because elements retrieved from a `Collection<Human>` can be any `Human`, not just `Student`s
- [y] No, because elements added to a `Collection<Human>` can be any `Human`, not just `Student`s

**Explanation**: This is a classic example of variance. Consider the following code:

```java
Collection<Student> students = ...; // somehow obtain students
Collection<Human> humans = students;
humans.put(new Professor()); // ouch!
```

We have just put a `Professor` into a collection of `Student`s! Clearly, this violates type safety, which is why this is not allowed.

On the other hand, it's perfectly acceptable for a `Collection<Human>` to only ever return `Student`s as its contents, since `Student`s are `Human`s.

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

- [n] 0 ≤ path coverage ≤ 25%
- [n] 25% < path coverage ≤ 50%
- [y] 50% < path coverage ≤ 75%
- [n] 75% < path coverage ≤ 100%

**Explanation**: It is not possible to cover the third branch, since `Math.sqrt(size) > 100` and `size > 100 * 100` are equivalent conditions.
However, all three other branches can be covered, so the answer is `75%`.

### Question 10 [3 points]
Which of the following are good examples of code comments?

- [y] `// FooLibrary claims to handle negative numbers but in fact it does not`
- [n] `// Instantiate a new Person using the name the user gave us`
- [y] `// This algorithm has poor complexity, but the input is guaranteed to have <10 elements`
- [n] `// The "getName" method returns the name.`

**Explanation**:

- The first comment is useful: it explains why the code does something non-obvious (handling negative numbers explicitly even though it calls a library that claims to handle them)
- The second comment is useless: this information is already in the code
- The third comment is useful: it explains why an apparently poor algorithm was chosen, with knowledge not necessarily available in the code (the size of the input)
- The fourth comment is useless: it restates the method's name as a sentence, with no additional information.

More details regarding the second comment: good code should be modular and self-explanatory. The code that actually creates a Person should not need to care about the origins of the name: user input, database, etc. The code that passes the name to the Person creator is the one that must sanitize any user input, but it must do so explicitly. If the only way of knowing which variables contain untrusted user input are comments, your code most likely contains a horde of bugs ready to be exploited; we are human, and we cannot expect to make no mistakes when dealing with some variables that are safe and some that are not. Instead, the code should very clearly sanitize user input as soon as possible, and then pass this sanitized input to code that can assume everything is safe. Thus, this comment is not a good fit anywhere: the Person creation does not care about user input, the input sanitization does not care about creating a Person, and the code that binds those two must be explicit enough to not require a comment.
