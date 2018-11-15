# Solution

## Inheritance/Containment

The solution uses inheritance, interfaces and containment to carry out the orders from High Command.

First, an abstract class `Ship` provides a base class that all ships inherit from (civilian, military, and their descendants). 
This class also implements a few things that all ships require: a paint job, cargo bay, crew, mass, and fuel, as well as methods to load and unload cargo.

The Cargo requirement is fulfilled using *containment*.
A `Ship` 'has a' cargo bay, which is a list of `CargoPod`s, each of which 'has' `CargoItem`s.

`CivilianShip` and `MilitaryShip` both inherit from `Ship` (i.e. a `MilitaryShip` 'is a' `Ship`)
Both are still abstract, because they are ship types and not actual ship classes, and thus cannot be built (instantiated).
`CivilianShip` overrides the `Unload` method to do the required manifest processing.
`MilitaryShip` adds an array of `Weapon`s, to which different `Weapon`s can be added.
They also have a `Fire` method that iterates over all of the weapons and fires them.

`Weapon` is an abstract class representing weapons, that can be added to military ships.
It is also abstract, and there are three concrete weapons that inherit from it --- `Railgun`, `Missile`, and `PlasmaCannon`, as required.
`Railgun` and `Missile` also implement the `Ammo` interface, because they require ammunition.
`PlasmaCannon` does not.
The `Ammo` interface provides method declarations required to get the current amount of ammunition and to reload the weapon.
`MilitaryShip`s use *containment* to implement their weapon loadouts -- a ship 'has a' `Weapon` (or several), it is not a weapon itself (in the literal sense).

A number of `interface`s are used to represent the mission profiles that the various ship classes are required to be capable of executing.
Some are shared among both `Civilian` and `Military` ships.
For example, the `Destroyer` class extends `MilitaryShip` and implements the `DeliveryMissionProfile` interface and the `PatrolMissionProfile` interface.
It does not implement the `AutonomousShip` interface, because as the spec dictates, fleet destroyers have not received this upgrade -- they are still crewed.
The interfaces themselves provide methods for High Command to issue specific orders to ships to execute missions.
Interfaces allow us to specify a single mission profile type, and "add" it to whatever ship is required to execute that mission profile.

## Interface Contracts

Interface contracts allow developers to better express their expectations of the users of their interface and the guarantees their interface provides.
Interface designer typically constructs contracts out of three kinds of elements:
1. A method precondition: a condition that must hold whenever someone calls a method.
   The condition may refer to the method arguments or the public fields and methods of the class.
   It is a responsibility of the caller to make sure the condition holds.
2. A method postcondition: a condition that is guaranteed to hold right after the method returns.
   The condition may refer to the method arguments, public fields and methods of the class (their value prior and after the call) and the method return value.
3. A class invariant: a condition that holds between method calls.
   You can think of the class invariant as a mandatory part of every pre- and postcondition of every method.
   It is the implementation of the class that makes sure the invariant holds, by assuming it at the start of every method and ensuring at the end of every method.
   
Note, that the function signature is itself a kind of a contract.
It requries the arguments of specific types and guarantees the particular return type.
Depending on the type system of the language, this contract can be pretty powerful.

### Cofoja

The [Cofoja](https://github.com/nhatminhle/cofoja) framework allows a developer to add contracts to their methods.
These contracts are formulated as normal Java expressions in double quotes (`“like this”`).
Inside the contracts you can reference the method arguments and class members.

Here is an example of a class invariant (“s != null”):

```java
@Invariant("s != null")
public class CharacterSet {
    public CharacterSet() {
        s = new StringBuffer();
    }
...
```

#### Exercise 1. Fibonacci and Preconditions
In [`interface-contracts/src/main/java/ch/epfl/sweng/contracts/RecursiveFibonacci.java`](interface-contracts/src/main/java/ch/epfl/sweng/contracts/RecursiveFibonacci.java) we provide an implementation of a recursive fibonacci number generator.
Fibonacci numbers is a function defined on non-negative numbers, hence the contract: `0 <= n` and `0 <= result`;

[`RecursiveFibonacciTest.java`](interface-contracts/src/test/java/ch/epfl/sweng/contracts/RecursiveFibonacciTest.java) contains two junit tests.
`fibOnNegativeFails` checks that cofoja indeed protects the function from incorrect calls (which violate the precondition).
`fibOf4` tests the corectness of the fibonacci sequence computation.

Run the tests as usual, with `./gradlew test`

But, wait a minute. The second test fails! Turns out the precondition is violated.
Check `build/reports/tests/test/index.html** report file to see the stack trace that points to the line that violated the precondition.

*Solution*:
The bug was in handling the base case (see the code). `apply` called itself recursively until it would reach a negative argument, at which point the precondition thrown an exception.

#### Exercise 2. Dynamic Vector

Let's get a bit more sophisticated now.
In the [`interface-contracts/src/main/java/ch/epfl/sweng/contracts/SwengVector.java`](interface-contracts/src/main/java/ch/epfl/sweng/contracts/SwengVector.java) you can find an example of a java `interface` augmented with a contract.
The `add` post condition contains two assertions.
1. The newly added element stays in the vector.
2. All the old elements remain in the vector. (the javadoc comment provides some more details on how we formulate it)

[`SwengArrayList`](interface-contracts/src/main/java/ch/epfl/sweng/contracts/SwengArrayList.java) implements the `SwengVector` interface, but contains two bugs.
The interface postcondition can capture both of the bugs, but we need to run the code such that it exercises the buggy behaviour, because cofoja checks the contracts dynamically, i.e. during execution, and can not verify them statically during compilation.

So [`SwengVectorTests`](interface-contracts/src/test/java/ch/epfl/sweng/contracts/SwengArrayList.java) contains a test that fails because of the bugs.
Use the gradle report to find the failing checks.
Use cofoja contracts to confirm your assumptions about the code.

*Hint:* you may want to add a "old(toList()).stream().allMatch(this::contains)" postcondition to the `expandArray** method.

**Solution**:
The first bug was that we stored a `0` instead of an `element` in the array.
The second bug was in the `expandArray` method.
Additional postcondition for this method allowed us to locate the bug quickly, limiting the scope by just one method body, instead of the whole class.

#### Exercise 3. Fraction
The following class implements rational numbers. Unfortunately, `toInt()` method sometimes throws an `ArithmeticException`.
Add an invariant and a constructor precondition to make sure `toInt()` never throws an exception.
To do that, replace `<write your code here(a/b)>` with valid expressions.

You can copy this snippet into `interface-contracts/src/main/java/ch/epfl/sweng/contracts/Fraction.java` and add some tests into [`interface-contracts/src/test/java/ch/epfl/sweng/contracts/FractionTests.java`](interface-contracts/src/test/java/ch/epfl/sweng/contracts/FractionTests.java) to compile and test it.

```java
@Invariant("<write your code here(a)>")
public class Fraction {
  private int n;
  private int d;

  @Requires("<write your code here(b)>")
  public Fraction(int n, int d) {
    this.n = n;
    this.d = d;
  }

  public int toInt() {
    return n/d;
  }
}
```

**Solution**:
See [Fraction](interface-contracts/src/main/java/ch/epfl/sweng/contracts/Fraction.java) and [FractionTests](interface-contracts/src/test/java/ch/epfl/sweng/contracts/FractionTests.java).

#### Exercise 4. Stack
The following implementation of a stack may sometimes throw `java.lang.ArrayIndexOutOfBoundsException` exceptions. To solve this question, please provide in the code below the class invariant, preconditions, and postconditions for methods `pop` and `push` that prevent these exceptions from arising, and thus make the code valid. You should **provide replacements for the 5 placeholders `“<write your code here(a/b/c/d/e)>”`**. You are not permitted to reference private class members in the pre- and post-conditions.

You can copy this snippet into `interface-contracts/src/main/java/ch/epfl/sweng/contracts/SwengStack.java` and add some tests into [`interface-contracts/src/test/java/ch/epfl/sweng/contracts/SwengStackTests.java`](interface-contracts/src/test/java/ch/epfl/sweng/contracts/SwengStackTests.java) to compile and test it.

```java
@Invariant("<write your code here(a)>")
class SwengStack {
    private int capacity;
    private int numberOfElements;
    private int[] contents;

    public SwengStack(int capacity) {
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

**Solution**: See [SwengStack](interface-contracts/src/main/java/ch/epfl/sweng/contracts/SwengStack.java) and [SwengStackTests](interface-contracts/src/test/java/ch/epfl/sweng/contracts/SwengStackTests.java)

### Exercise 5(bonus). [Covariance and contravariance](https://en.wikipedia.org/wiki/Covariance_and_contravariance_(computer_science))
Inheritance can be treated from the contractual point of view, as the obligation of the subclass to respect the contracts of the superclass.
So a `SwEngStudent` is first of all a `Student`.
This means you can always treat a `SwEngStudent` as if it were a student, i.e. you can ask for their `gaspar`, or expect them to take an exam.

It gets trickier when you consider collections of objects.
In java `List<? extends Student>` expresses a lower bound on the type of the objects in the list. (Where we consider `Object` the lowest object).
Such a list contains objects that can be treated as `Student`s.
`List<? super Student>` expresses an upper bound on the type of the objects in the list.
In such list you can find `Object`s or `Student`s - any of the superclasses of `Student` including itself, but never a subclass (e.g. no `SwEngStudent`);

The latter case may sound pointless, but think about this two lists:

```java
List<? extends Student> l1 = ...;
List<? super Student> l2 = ...;
```

Which of the uses of `l1` and `l2` are safe?

```java
Student s1 = l1.get(0);//1
Student s2 = l2.get(0);//2
l1.add(new Student());//3
l2.add(new Student());//4
```

**Solution**:
1. Operation is completely legitimate, because we know that whatever element is in `l1`, it extends `Student`, so we can refer to it as to a `Student`.
2. Operation is dangerous, because `l2` may contain a super class of `Student`, e.g. `Object`, that does not implement `Student`, so `s2` may not respond correctly when we try to call methods of `Student` on it.
3. Operation is also dangerous, because `l1` may contain objects that are more specific than a `Student`, e.g. `SwengStudent`. In such a case, in another place the code may expect to get `SwengStudent`s out of the list, and it will break when they pull out the `Student` that we added here.
4. Operation is safe, because the type guarantees that `l2` contains objects of type `Student` or lower in the inheritance chain. Hence, whatever code works with `l2` will not expect anything more specific than a `Student`, and we can safely put a `Student` there knowing that it will fullfill the expectations.

## Code Comments

### Exercise 1
The problem of these comments is that they tell what self-explanatory code makes.
This can easily be fixed by removing most of them and writing more concise and precise ones, to simply give a flavor of what each part is doing.
### Exercise 2
Yes, using comments to prevent potential operabilities issue may be beneficial for the code, if used correctly. It allows other programmer to be aware of the problem and save them precious time.
The problem is that the comments explain **how** the code does something instead of **what** it does. With this in mind, it is pretty straightforward to fix the problem.
### Exercise 3
The comments explain the code.  This is often a sign of confusing code.
When confronting this kind of issues, find how your code may be simplified in order to be able to remove these comments (by refactoring or rewriting the method, for instance).
### Exercise 4
When writing JavaDoc for a class, it is important to stay at a high-level, the description should not contain implementation details. Nevertheless, the description should be complete enough to allow the user to get what the class does without looking at the implementation. These documentation can be fairly long (you can look at the [Java Collections source](http://www.docjar.net/html/api/java/util/Collections.java.html) as a good example).
For the methods, the description should be much smaller (1 or 2 sentences) and it should explain the intent of the method.
The documentation should also contain a description of the parameters as well the values they can take.
This JavaDoc comment also lists the exceptions that may be thrown. You should to use the annotations (@param, @throws, @returns) to clarify the formatting.
If writing the JavaDoc for a method is really complicated, it may mean that the function needs to be refactored.

## Coding Style

```Java
/**
 *  class Rocket
 */
class Rocket extends Projectile implements Explosive {
    private final static float ROCKET_RADIUS = 3;
    private Integer power;
    private Point position;
    private Point velocity;
    private boolean inFlight;

    private void incrementPosition(Point increment) {
        this.Position.move(increment);
    }

    public Rocket(Integer power,
                  Point position,
                  Point velocity,
                  Player owner)
            throws OutOfBoundsException {
        super(position, Owner);
        this.power = power;
        this.position = position;
        this.velocity = velocity;
        this.inFlight = true;
    }

    private void move(float timeElapsed) {
        incrementPosition(Velocity.multiply(timeElapsed));
    }

    public boolean isColliding(Point objectCenter,
                               float objectRadius) {
        if (!this.inFlight) return false;
        return Circle.doIntersect(this.position, objectCenter, objectRadius);
    } 

    public Boom explode() {
        this.inFlight = false;
        return new Boom(position, power);
    }
}
```
