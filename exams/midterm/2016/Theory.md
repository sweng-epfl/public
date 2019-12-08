# Part 2: Theory [40 points]

This is part 2 of the midterm exam. It has 7 questions, with the number of points indicated next to each one.

This file is named `Theory.md`. Provide the answers in this file and commit the file to the `master` branch of this `midterm-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless otherwise indicated. For multiple-choice questions, we marked the choices as `[]`; you should change each one to `[y]` for all correct (yes) choices and to `[n]` for all incorrect (no) choices. Checkboxes left empty will indicate you did not answer that question, so please be careful.

### Question 1 [2 points]

Choose zero, one, or more answers that are true of containment (as opposed to inheritance):

[] Should be used when classes share common data but not behavior

[] Should be used when classes share common behavior

[] Helps avoid excessive method forwarding

[] The base class controls the interface and provides its implementation

### Question 2 [2 points]

What is(are) the relationship(s) between the `Factory` design pattern and `Abstract Factory` design pattern?

[] `Abstract Factory` is a generalization of `Factory`

[] `Factory` is a generalization of `Abstract Factory`

[] `Abstract Factory` can make use of more than one `Factory`

[] `Abstract Factory` and `Factory` are not related to each other, despite the similar name

### Question 3 [3 points]

If you were asked to write a caching system for a smartphone app that accesses a remote database, which design pattern would you use? Briefly explain your choice(s) on the same line as the choice(s).

[] `Proxy`

[] `Decorator`

[] `Adapter`

[] `Composite`

### Question 4 [3 points]

Consider the following types of projects; indicate for which ones you consider the _waterfall_ development model to be more suitable than _agile_ models. Briefly explain your choice(s) on the same line as the choice(s).

[] One-off applications (e.g., an event guide, a kiosk app for a conference)

[] Apps that require a short time to market (e.g., a flash game, a small company's website)

[] Safety-critical apps (e.g., airplane firmware, nuclear plant control)

[] Large-scale consumer apps (e.g., social network, messenger app)


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
@Invariant("<write your code here(a)>")

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

    @Requires("<write your code here(b)>")
    @Ensures("<write your code here(c)>")
    public int pop() {
        numberOfElements -= 1;
        return contents[numberOfElements];
    }

    @Requires("<write your code here(d)>")
    @Ensures("<write your code here(e)>")
    public void push(int x) {
        contents[numberOfElements] = x;
        numberOfElements += 1;
    }
}
```

### Question 6 [5 points]

Bob is a software engineer at Boeing, working on the mission-critical systems for the 787 Dreamliner aircraft. Bob was fired because he used bad coding practices and was unable to reason about software complexity, and Alice was hired in his stead.

Bob’s program contains _N_ statements. Assume that the number of bugs in his code is proportional to code size, and the bugs are randomly distributed throughout the code.

Alice’s workflow is as follows: she compiles the program, runs it, notices a bug, finds and fixes that bug, and then recompiles the code before searching for the next bug. Alice notices that the time it takes her to find a bug is proportional to the size of the program. She then models the time spent debugging as follows:

	BugCount ∝ N  (i.e., "proportional to N")
	DebugTime ∝ N * BugCount ∝ N²  (i.e., "proportional to N²")

Debug time grows quadratically with the size of the program size, and that will prevent Alice from finishing on time. Fortunately, Alice took SwEng at EPFL and remembers that the most effective way to reduce complexity is divide-and-conquer. So she divides the software into _K_ modules of roughly equal size (assume each module contains _N / K_ statements).

Assuming the modules implement independent features, and that bugs in one module remain contained within those modules, please describe below what you can say about the new `DebugTime` in this modularized program. Your answer should be as precise and concrete as possible.

**Answer:**

### Question 7 [13 points]

Consider the simple Reverse Polish Notation Calculator shown below. The private methods `add()`, `subtract()`, and `factorial()` perform the corresponding operation by popping values from the `OperandStack` and executing the required algorithm. **You do not need to understand the inner workings of the algorithms in order to answer this question**.

The user accesses the `RpnCalculator` class through its public `execute(String operatorName)` method. The class suffers from a defect that allows a clumsy caller to provide an unsupported operator as an argument to `execute()` and cause it to throw an exception.

In the code below, use the Strategy pattern to refactor the `String operatorName` argument of the `execute()` method with an interface called `MathOperator`.

Edit the code in place.

```java
public class RpnCalculator {
    // private class fields
    private OperandStack<Integer> values;
    ...

    public void execute(String operatorName) {
        if ("+".equals(operatorName)) {
            add();
        } else if ("-".equals(operatorName)) {
            subtract();
        } else if ("!".equals(operatorName)) {
            factorial();
        } else {
            throw new NoSuchOperator();
        }
    }

    private void add() {
        BigInteger rhs = values.peek();
        values.pop();
        BigInteger lhs = values.peek();
        BigInteger res = lhs.add(rhs);
        values.replaceTop(res);
    }

    private void subtract() {
        BigInteger rhs = values.peek();
        values.pop();
        BigInteger lhs = values.peek();
        BigInteger res = lhs.subtract(rhs);
        values.replaceTop(res);
    }

    private void factorial() {
        BigInteger operand = values.peek();
        BigInteger res = BigInteger.ONE;

        while (operand.compareTo(BigInteger.ONE) > 0) {
            res = res.multiply(operand);
            operand = operand.subtract(BigInteger.ONE);
        }

        values.replaceTop(res);
    }
}
```
