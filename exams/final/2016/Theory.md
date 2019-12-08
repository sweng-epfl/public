# Part 2: Theory (40% of final)
This is part 2 of the final exam. It has 8 questions, with the number of points indicated next to each one.

This file is named `Theory.md`. Provide the answers in this file and commit the file to the master branch of this `final-GASPAR` repo. 
Do not change the name of the file, and do not change the existing text unless otherwise indicated. 
For multiple-choice questions, choices are indicated by empty checkboxes `[]`; you should change each one to `[y]` for all correct (yes) choices and to `[n]` for all incorrect (no) choices. 
Checkboxes left empty will indicate you did not answer that question, so please be careful.


### Question 1 [2 points]

What is true of a program that gets 100% line coverage during testing?

[] Every line of the program is executed at least once during test execution

[] If all tests passed, the program has no bugs

[] All of the program’s outputs are checked by the tests

[] It is less likely to contain bugs than an untested program

### Question 2 [2 points]

(2.a) Identify the design pattern outlined by this interface:

```java
public interface A<T> {
    public boolean hasNext();
    public T next();
    public boolean remove();
}
```

Write your answer here: ____________

(2.b) In the chosen pattern, what does “`next()`” do, if “`hasNext()`” has just returned false?

[] Crashes

[] Returns the next element

[] Returns the same element

[] Returns the first element

[] Returns the `Default<T>()` value

[] Throws `NoSuchElementException`

[] Throws `InvalidArgumentException`

[] Throws `ArrayIndexOutOfBoundsException`

### Question 3 [2 points]

You’re designing a data model for a `SantaFactory`, consisting of `Shipper` and `Artisan` objects, both of which are `Elf` objects. 
What is the correct relationship among these entities?

[] `Shipper` and `Artisan` inherit from `Elf`; `SantaFactory` contains `Artisan` and `Shipper` objects

[] `Shipper` inherits from `Elf`; `Artisan` inherits from `Shipper`; `SantaFactory` contains `Artisan` and `Shipper` objects

[] `Elf` inherits from `Artisan`; `Shipper` inherits from `Artisan`; `SantaFactory` contains `Shipper` and `Artisan` objects

[] `Shipper` and `Artisan` inherit from `SantaFactory`

### Question 4 [3 points]

In Java, the `Map<K,V>` interface allows its implementations to decide which keys they accept. 
Thus, an implementation can choose to allow only certain types, or can decide to disallow `null` keys and/or values, 
or throw exceptions if these invariants are violated.

Suppose someone implements the following maps and provides the following informal contracts:

- `IntMap`, which implements `Map<Integer,Integer>` but disallows `null` keys and values
- `EvenIntMap`, which extends `IntMap` and disallows odd keys and values
- `OddIntMap`, which implements `Map<Integer,Integer>` but only allows odd keys and values

Indicate which statements are true:

[] `IntMap`’s contract makes sense

[] `EvenIntMap`’s contract makes sense

[] `OddIntMap`’s contract makes sense

### Question 5 [7 points]

Determine which SwEng best practices are violated in the code below. 
For each line that violates one or more best practice, add a comment to the code as follows (name the violation with just a few keywords):
```java
  	int Count_v; // violation(s): bad variable name
```

```java
public class RankingSystem {
  public final int rank;
  private final List<String> references;
 
  public RankingSystem(int rank, List<String> references) {
      this.rank = rank;
      this.references = references;
  }
 
  public String getReferenceAt(int position) {
      return this.references.get(position);
  }
}
```

### Question 6 [4 points]

Make the code below comply with SwEng best practices. Do not modify the public method and/or constructor signatures. 
Modify the code in-place.

```java
public class RankingSystem {
  public final int rank;
  private final List<String> references;
 
  public RankingSystem(int rank, List<String> references) {
      this.rank = rank;
      this.references = references;
  }
 
  public String getReferenceAt(int position) {
      return this.references.get(position);
  }
}
```

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

Model: ________________

View: ________________

Controller: ________________


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

[] Adapter

[] Composite

[] Decorator

[] Visitor
