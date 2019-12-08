# Part 2: Theory (40% of final)
This is part 2 of the final exam. It has 8 questions, with the number of points indicated next to each one.

This file is named `Theory.md`. Provide the answers in this file and commit the file to the master branch of this `final-GASPAR` repo. 
Do not change the name of the file, and do not change the existing text unless otherwise indicated. 
For multiple-choice questions, choices are indicated by empty checkboxes `[]`; you should change each one to `[y]` for all correct (yes) choices and to `[n]` for all incorrect (no) choices. 
Checkboxes left empty will indicate you did not answer that question, so please be careful.


### Question 1 [2 points]

What is true of a program that gets 100% line coverage during testing?

[y] Every line of the program is executed at least once during test execution

[n] If all tests passed, the program has no bugs

[n] All of the program’s outputs are checked by the tests

[y] It is less likely to contain bugs than an untested program

`Grading:` _Each correct answer was worth 0.5 points. Answers not marked "y" or "n" were worth 0 points._

### Question 2 [2 points]

(2.a) Identify the design pattern outlined by this interface:

```java
public interface A<T> {
    public boolean hasNext();
    public T next();
    public boolean remove();
}
```

Write your answer here: __Iterator__

`Grading`: _Correctly identifying the pattern was worth 1 point._

(2.b) In the chosen pattern, what does “`next()`” do, if “`hasNext()`” has just returned false?

[n] Crashes

[n] Returns the next element

[n] Returns the same element

[n] Returns the first element

[n] Returns the `Default<T>()` value

[y] Throws `NoSuchElementException`

[n] Throws `InvalidArgumentException`

[n] Throws `ArrayIndexOutOfBoundsException`

`Grading`: _The correct answer was worth 1 point. We also accepted `Crashes` but only awarded 0.5 points for it. If both were selected, a maximum 1 point was given._

### Question 3 [2 points]

You’re designing a data model for a `SantaFactory`, consisting of `Shipper` and `Artisan` objects, both of which are `Elf` objects. 
What is the correct relationship among these entities?

[y] `Shipper` and `Artisan` inherit from `Elf`; `SantaFactory` contains `Artisan` and `Shipper` objects

[n] `Shipper` inherits from `Elf`; `Artisan` inherits from `Shipper`; `SantaFactory` contains `Artisan` and `Shipper` objects

[n] `Elf` inherits from `Artisan`; `Shipper` inherits from `Artisan`; `SantaFactory` contains `Shipper` and `Artisan` objects

[n] `Shipper` and `Artisan` inherit from `SantaFactory`

`Grading`: _Each correct answer was worth 0.5 points. Answers not marked "y" or "n" were worth 0 points._

### Question 4 [3 points]

In Java, the `Map<K,V>` interface allows its implementations to decide which keys they accept. 
Thus, an implementation can choose to allow only certain types, or can decide to disallow `null` keys and/or values, 
or throw exceptions if these invariants are violated.

Suppose someone implements the following maps and provides the following informal contracts:

- `IntMap`, which implements `Map<Integer,Integer>` but disallows `null` keys and values
- `EvenIntMap`, which extends `IntMap` and disallows odd keys and values
- `OddIntMap`, which implements `Map<Integer,Integer>` but only allows odd keys and values

Indicate which statements are true:

[y] `IntMap`’s contract makes sense

[n] `EvenIntMap`’s contract makes sense

[y] `OddIntMap`’s contract makes sense

`Grading`: _Each correct answer was worth 1 point. Answers not marked "y" or "n" were worth 0 points._

### Question 5 [7 points]

Determine which SwEng best practices are violated in the code below. 
For each line that violates one or more best practice, add a comment to the code as follows (name the violation with just a few keywords):
```java
  	int Count_v; // violation(s): bad variable name
```

```java
// Javadoc comment (1)
public class RankingSystem {
  public final int rank; // encapsulation, isolation: rank should be private (2)
  private final List<String> references; // bad variable names (m*) (3)

  public RankingSystem(int rank, List<String> references) {
      this.rank = rank;
      this.references = references;     // safety: should check for null (4)
                                        // safety: the list should be deep-copied (5)
  }
 
  public String getReferenceAt(int position) {
      // violation: an exception would be thrown if position is out of bounds (6)
      return this.references.get(position);
  }
}
```

`Grading`: _Each correct violation was worth 1.75 points. Each non-violation incorrectly reported was taxed with -1.75 points. Negative total values were rounded off to 0, while values greater than 7 were rounded to 7._

`Explanation`:

  - Accepted violations (+1.75 each):
    - (1) Missing javadoc comments
    - (2) Rank should be private
    - (3) Bad variable name (only if explicitly said that it should be prefixed with `m_`)
    - (4) Should check for null in constructor
    - (5) Deep copy the list
    - (6) Check for paramter bounds

  - Non-violations (-1.75 each):
    - Final value should be explicit
    - Variable not initialized
    - Should use `Array`
    - Store list size
    - `final` variable should be in capital
    - Copy of string
    - Indicate in the constructor that `references` and `rank` are `final`
    - `references` declared `final` but returned as a reference
    - Parameter has the same name as the fields
    - Exposed internal class
    - Return should be on a different line than get
    - Should be `static`
    - `List<>` cannot be instantiated
    - No check for rank uniqueness

### Question 6 [4 points]

Make the code below comply with SwEng best practices. Do not modify the public method and/or constructor signatures. 
Modify the code in-place.

```java
public class RankingSystem {
  private final int rank; // (1a)
  private final List<String> references;
 
  public int getRank() { // (1b)
    return this.rank;
  }

  public RankingSystem(int rank, List<String> references) {
    if (references == null) { // (2)
      throw new NullPointerException("null references in constructor");
    }
    this.rank = rank;
    this.references = new ArrayList<>(references); // (3)
  }
 
  public String getReferenceAt(int position) {
    if (position < 0 || position >= this.references.size()) { // (4)
      throw new WrongReferencePosition();
    }
    return this.references.get(position);
  }
}
```

`Grading`:

  - +1 point for (1a) and (1b) (but 0 points for just one of them)
  _or_
  - +1 point for using `public static final int RANK`

  - +1 point per correct rectification of the violation (2), (3) and (4)
    - +0.5 in case `List.clone()` was used to deep copy
  - +1 point if meaningful comments were added.


### Question 7 [7 points]

You are a newly hired web developer in a young start-up that sells a variety of cupcakes through their website, 
and you are responsible for the implementation of the web app. 
Your goal is to create a complete web application, i.e., including the backend and frontend.

You consider using the MVC pattern and implement a web app with the following workflow from the user’s point of view:

1. The user wants to access the web app and see all the types of chocolate cupcakes on offer. 
   To do this, they access the link https://cupcakes.com?type=chocolate.
2. The request reaches your server, and an action `showAction(String type)` is triggered, where `type` contains the string `"chocolate"`.
3. Using the type variable, `showAction` retrieves all the chocolate cupcakes from the database,
   and stores them in a collection `List<Cupcake> chocolateCupcakes`, 
   where each cupcake's information is encapsulated in a `Cupcake` class.
4. `showAction` finally sends a response back to the user. 
   This response contains an HTML document with the formatted information from the `chocolateCupcakes` collection, 
   ready to be displayed in the user's browser.
5. The user receives the response and can now salivate while viewing the different varieties of cupcakes.

Using the information from the above workflow, identify below which elements represent the Model, the Controller, and the View in your MVC architecture. Clearly explain and justify your answers.

Model: __The `chocolateCupcakes` collection and the `Cupcake` class and objects.__

View: __The HTML response sent via `showAction` containing the list of cupcakes.__

Controller: __The `showAction` method (or) The router that translates the route typed in the browser (https://cupcakes.com?type=chocolate) and links it showAction(type=“chocolate”).__

`Grading`: _Each correct answer for Model and Controller was worth 2.5 points: 1.5 points for correctness, and 1 point for the justification. For View, the correct answer was worth 2 points: 1 point for correctness and 1 point for the justification._

`Rationale`:

_**Model**: The model is **not** the database. The model is the code that manages and represent the data stored in the database and everything related to that data. Note that the Database Management System and the database itself are not the same._

_**View**: Simply put, the view is everything related to displaying the organized information (the model)._

_**Controller**: The controller is the “manager” that enables the links between the elements and control them. The controller is the one to retrieve the requests of the user, the one that calls the model and the one that sends that data to the view._

### Question 8 [12 points]

Suppose you are working on an API for modeling EPFL people and student organizations, such as Agepoly or Balelec. 
In each organization, there are people who bear a title such as president, treasurer, PR advisor, etc. 
You have written the following classes:

```java
   	public class Organization
   	public class Student
   	public class OrganizationPresident extends Student
   	public class OrganizationTreasurer extends Student
```

Now you realize that you cannot know all the titles in advance. 
Also, you would like to leave some freedom to the users of your API (in particular, they want to be able to specify custom titles for their organization). 
Therefore, you decide to drop the inheritance model you used until now, and instead use a SwEng design pattern. 
Indicate below your choice, and justify clearly and concisely with max 1 sentence each option. Clearly state your assumptions, if any.

[n] Adapter

[y] Composite

[y] Decorator

[n] Visitor

`Grading`: _Each correct answer was worth 1 point. Each correct justification was worth 2 points. Answers not marked "y" or "n" were worth 0 points._
