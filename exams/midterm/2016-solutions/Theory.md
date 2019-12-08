# Part 2: Theory [40 points]

This is part 2 of the midterm exam. It has 7 questions, with the number of points indicated next to each one.

This file is named `Theory.md`. Provide the answers in this file and commit the file to the `master` branch of this `midterm-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless otherwise indicated. For multiple-choice questions, we marked the choices as `[]`; you should change each one to `[y]` for all correct (yes) choices and to `[n]` for all incorrect (no) choices. Checkboxes left empty will indicate you did not answer that question, so please be careful.

### Question 1 [2 points]

Choose zero, one, or more answers that are true of containment (as opposed to inheritance):

[y] Should be used when classes share common data but not behavior

[n] Should be used when classes share common behavior

[n] Helps avoid excessive method forwarding

[y] The base class controls the interface and provides its implementation

`Grading:` _Each correct answer was worth 0.5 points. Answers not marked "y" or "n" were worth 0 points._

### Question 2 [2 points]

What is(are) the relationship(s) between the `Factory` design pattern and `Abstract Factory` design pattern?

[y] `Abstract Factory` is a generalization of `Factory`

[n] `Factory` is a generalization of `Abstract Factory`

[y] `Abstract Factory` can make use of more than one `Factory`

[n] `Abstract Factory` and `Factory` are not related to each other, despite the similar name

_Explanation: The `Factory` pattern produces implementations of a particular interface, whereas the `Abstract Factory` pattern produces implementations of a particular `Factory` interface. The `Abstract Factory` pattern is therefore a two-level hierarchy which provides a more general interface and that encompasses the `Factory` pattern._

`Grading`: _Each correct answer was worth 0.5 points. Answers not marked "y" or "n" were worth 0 points. The explanation was not required._

### Question 3 [3 points]

If you were asked to write a caching system for a smartphone app that accesses a remote database, which design pattern would you use? Briefly explain your choice(s) on the same line as the choice(s).

[y] `Proxy`, because it creates an intermediary between your app and the remote database, and offers the same interface as the remote database; therefore the cache proxy looks just like a faster database to the app.

[n] `Decorator`, because it allows the addition of new behavior to an individual object without affecting the behavior of other objects instantiating the same class, which is not the intended behaviour of a cache.

[n] `Adapter`, because it allows objects which share functionality but which differ in interface to satisfy the same interface; it can (potentially) be used to implement a Proxy within an Adapter.

[n] `Composite`, because it creates a partitioning structure to represent part-whole hierarchies and is used when we need to treat a group of objects as a single objects; therefore is not applicable to a caching system.

`Grading`: _To get full credit, a student had to mark the right choices along with providing a correct justification. We also accepted the `Adapter` pattern (together with `Proxy`), as long as it was well justified. Marking the correct choices but having an incomplete justification was worth 2 points. Correctly marking and justifying `Proxy` but then incorrectly marking other choices was worth 1 point. Not marking `Proxy` as the correct choice resulted in 0 points._

### Question 4 [3 points]

Consider the following types of projects; indicate for which ones you consider the _waterfall_ development model to be more suitable than _agile_ models. Briefly explain your choice(s) on the same line as the choice(s).

[n] One-off applications (e.g., an event guide, a kiosk app for a conference), because requirements might change easily and are not fully known at the beginning.

[n] Apps that require a short time to market (e.g., a flash game, a small company's website), because you would lose time at the beginning to define the requirements. Furthermore, the client would probably want to participate in the development of the software and might not even know what he/she wants, so agile development is better.

[y] Safety-critical apps (e.g., airplane firmware, nuclear plant control), because requirements should be well-defined in a large complex project. The waterfall development model provides more structure and enforces the stability of requirements and strong control over the development process.

[n] Large-scale consumer apps (e.g., social network, messenger app), because requirements might change easily (consumer-oriented application) and time is precious (the app must be the first to propose a new idea).

`Grading`: _Each correctly marked choice was worth 0.5 points. Each correct justification was worth 0.25 points. Answers not marked "y" or "n" were worth 0 points regardless of justification._

### Question 5 [12 points]

The Cofoja framework allows a developer to add contracts to their methods. These contracts are formulated as normal Java expressions in double quotes (`“like this”`). Inside the contracts you can reference the method arguments and class members.

Here is an example of a class invariant (“s != null”):

```java
@Invariant("s != null")
public class CharacterSet {
    public CharacterSet() {
        s = new StringBuffer();
    }
...
```

The following implementation of a stack may sometimes throw `java.lang.ArrayIndexOutOfBoundsException` exceptions. To solve this question, please provide in the code below the class invariant, preconditions, and postconditions for methods `pop` and `push` that prevent these exceptions from arising, and thus make the code valid. You should **provide replacements for the 5 placeholders `“<write your code here(a/b/c/d/e)>”`**. You are not permitted to reference private class members in the pre- and post-condtions.

```java
@Invariant("0 <= numberOfElements && numberOfElements <= capacity && contents != null")

class Stack {
    private int capacity;
    private int numberOfElements;
    private int[] contents;

    public Stack(int capacity) {
        this.capacity = capacity;
        numberOfElements = 0;
        contents = new int[capacity];
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public boolean isFull() {
        return numberOfElements == capacity;
    }

    @Requires("!isEmpty()")
    @Ensures("!isFull()")
    public int pop() {
        numberOfElements -= 1;
        return contents[numberOfElements];
    }

    @Requires("!isFull()")
    @Ensures("!isEmpty()")
    public void push(int x) {
        contents[numberOfElements] = x;
        numberOfElements += 1;
    }
}
```
`Grading`:
_The class invariant and the contracts were worth 3 points each, for a total of 9 points. We awarded 3 points if the solution did not use the private class members in the pop/push contracts. To get all 3 points for an invariant/contract, the answer had to be both syntactically and logically correct. If an invariant/contract was provided that did not contradict the reference solution but was incomplete, it earned 1 point._

### Question 6 [5 points]

Bob is a software engineer at Boeing, working on the mission-critical systems for the 787 Dreamliner aircraft. Bob was fired because he used bad coding practices and was unable to reason about software complexity, and Alice was hired in his stead.

Bob’s program contains _N_ statements. Assume that the number of bugs in his code is proportional to code size, and the bugs are randomly distributed throughout the code.

Alice’s workflow is as follows: she compiles the program, runs it, notices a bug, finds and fixes that bug, and then recompiles the code before searching for the next bug. Alice notices that the time it takes her to find a bug is proportional to the size of the program. She then models the time spent debugging as follows:

    BugCount ∝ N  (i.e., "proportional to N")
    DebugTime ∝ N * BugCount ∝ N²  (i.e., "proportional to N²")

Debug time grows quadratically with the size of the program size, and that will prevent Alice from finishing on time. Fortunately, Alice took SwEng at EPFL and remembers that the most effective way to reduce complexity is divide-and-conquer. So she divides the software into _K_ modules of roughly equal size (assume each module contains _N / K_ statements).

Assuming the modules implement independent features, and that bugs in one module remain contained within those modules, please describe below what you can say about the new `DebugTime` in this modularized program. Your answer should be as precise and concrete as possible.

**Answer:**

* Module-level numLinesOfCode = _N/K_
* Module-level BugCount ∝ numLinesOfCode ∝ _N/K_
* Module-level DebugTime ∝ numLinesOfCode × BugCount ∝ _N/K_ × _N/K_ = _N²/K²_
* Total DebugTime for all code ∝ _K_ × module-level DebugTime ∝ _K_ × _N²/K²_ = _N²/K_

`Grading`:

_We distinguished among 6 cases for grading this question:_

* _A correct answer with good explanation received 5 points_
* _Correct per-module debug time calculation but incorrect total calculation received 3 points_
* _Correct answer but just the final formula shown (with calculation shown in the formula itself) received 3 points_
* _Correct per-module bug count but incorrect calculation thereafter received 2 points_
* _Completely incorrect calculation but qualitative indication of time saved received 1 point_
* _Correct answer but just the final formula shown with no explanation at all received 0 points_

### Question 7 [13 points]

Consider the simple Reverse Polish Notation Calculator shown below. The private methods `add()`, `subtract()`, and `factorial()` perform the corresponding operation by popping values from the `OperandStack` and executing the required algorithm. **You do not need to understand the inner workings of the algorithms in order to answer this question**.

The user accesses the `RpnCalculator` class through its public `execute(String operatorName)` method. The class suffers from a defect that allows a clumsy caller to provide an unsupported operator as an argument to `execute()` and cause it to throw an exception.

In the code below, use the Strategy pattern to refactor the `String operatorName` argument of the `execute()` method with an interface called `MathOperator`.

Edit the code in place.

```java
public interface MathOperator {
    public void execute(OperandStack values);
}

public class Add implements MathOperator {
    @Override
    public void execute(OperandStack values) {
        BigInteger rhs = values.peek();
        values.pop();
        BigInteger lhs = values.peek();
        BigInteger res = lhs.add(rhs);
        values.replaceTop(res);
    }
}

public class Subtract implements MathOperator {
    @Override
    public void execute(OperandStack values) {
        BigInteger rhs = values.peek();
        values.pop();
        BigInteger lhs = values.peek();
        BigInteger res = lhs.subtract(rhs);
        values.replaceTop(res);
    }
}

public class Factorial implements MathOperator {
    @Override
    public void execute(OperandStack values) {
        BigInteger operand = values.peek();
        BigInteger res = BigInteger.ONE;

        while (operand.compareTo(BigInteger.ONE) > 0) {
            res = res.multiply(operand);
            operand = operand.subtract(BigInteger.ONE);
        }

        values.replaceTop(res);
    }
}


public class RpnCalculator {
    // private class fields
    private OperandStack<Integer> values;
    ...

    public void execute(MathOperator op) {
        op.execute(values);
    }
}
```
`Grading`:

_The `MathOperator` interface can be defined either inside or outside the `RpnCalculator` class. If defined inside the class, then the `MathOperator`'s execution method does not forcefully need to be passed the `OperandStack` as a method parameter. However, if defined outside the class, then the `OperandStack` needs to be provided as argument to the `MathOperator`'s execution methods._

_If some elements were missing, but it was possible to infer that they were typos (due to correct code elsewhere in the solution), then full points were awarded for the corresponding criteria._

_Solutions that only partially implemented a criterion received half the corresponding points._

_We used the cumulative sum of the criteria below in grading this problem:_

* _Creating an interface called `MathOperator` (1 point)_
* _The `MathOperator` interface has a `public` method that will be used to `execute()` the computation, not to further redirect the computation by, for example, returning a `String` again. The name of the method is not important. (2 points)_
* _Replaced original `String` argument of `execute()` method with `MathOperator` interface (2 points)_
* _Calls `MathOperator` interface execution method in original `execute()` method correctly (4 points)_
* _Created at least one of the `Add`, `Subtract`, `Factorial` classes that implement the `MathOperator` interface._
    _Only one of the criteria below applies here:_
    1. __Moves__ _implementation of original `add()` method to new interface’s execution method (4 points)_
    2. __Calls__ _`add()` code in original `RpnCalculator` class from new interface's execution method, therefore not decoupling algorithm selection from implementation (2 points)_
* _Forgot to add `OperandStack` argument to `MathOperator` execution method, if applicable (-1 point)_
* _Forgot to remove `add()`, `subtract()`, and `factorial()` from `RpnCalculator` class.  This point is not removed if you already lost points for calling `add()`, `subtract()`, and `factorial()` from the newly created classes (-1 point)_
* _Inconsistencies between method declarations and method invocations (-1 point)_
