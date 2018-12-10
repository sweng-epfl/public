# CS305 – Software Engineering

## Midterm Exam

# Part 1: Theory [40 points]

This part of the midterm exam has 11 questions, with the number of points indicated next to each one.

This file is named `THEORETICAL.md`. Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You should change each one to `[y]` for a correct answer and to `[n]` for each incorrect answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

### Question 1 [3 points]
Assume you are in charge of designing a combat game which has the following types of soldiers: `Army`, `Navy`, `AirForce` and `Marines`. To track these different types and to allow for new branches of military (your product owner recently hinted at a `SpaceForce`), you created an `abstract class Soldier` that is then extended by each specific soldier type. Each soldier has an `ID` and an `attackPower` specific to his/her type. 

The product owner now wants to add new kinds of weapons, such as laser guns and hand-held bazookas. These weapons can be used by any soldiers irrespective of their type, and they improve the soldiers' `attackPower`. Additionally, your product owner is still coming up with more weapons, which require your design to be flexible. 

Which one of the following design patterns is best suited to efficiently maintaining the combat game codebase? Justify your answer in 1-2 sentences. 

- [ ] Adapter
- [ ] Proxy
- [ ] Composite
- [ ] Decorator 

### Question 2 [3 points]
Which of the following is/are true:

- [ ] In TDD, a newly written test must always fail
- [ ] In TDD, a newly written test may pass but is likely to fail
- [ ] The Decorator DP is suitable when we want to extend the functionality of **all** object instances of a particular class
- [ ] The Decorator DP is suitable when we want to extend the functionality of **some** object instances of a particular class


### Question 3 [6 points]
You are now working for Tweeeeter. Your task is to implement a simple tweets server for celebrities and a console tweets viewer for followers.
Many people want to follow the tweets from a `Celebrity` and be notified in real time when he/she writes a new tweet.
The latest tweet is simply printed to the client console (because the followers are hipsters that use Tweeeeter from the command line).
Everytime a new `Follower` connects to the server, `addObserver` is implicitly called.

Complete the Observer design pattern implementation provided below such that clients get notified when the celebrity writes a new tweet, and the new text is printed to the console (you can assume that the latest tweet is at the end of the arraylist).

We use the following interfaces to implement the Observer pattern:

```java
public interface Observable {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}

public interface Observer {
    public void update(Observable observable);
}
```

**Modify the code in place:**

```java
public class Celebrity implements Observable {
    private List<Observer> followers = new ArrayList<>();
    private List<String> tweets = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        // your code here
    }

    @Override
    public void removeObserver(Observer observer) {
        // your code here
    }

    @Override
    public void notifyObservers() {
        // your code here
    }

    public List<String> getTweets() {
        return Collections.unmodifiableList(new ArrayList<String>(tweets));
    }

    public void addTweet(String tweet) {
        this.tweets.add(tweet);
        // your code here
    }
}
```

```java
public class Follower implements Observer {

    @Override
    public void update(Observable observable) {
        Celebrity celebrity = (Celebrity) observable;
        // your code here
    }
}
```

### Question 4 [3 points]
Which of the following constructs may violate the single-exit principle of structured programming:

- [ ] Unconditional jump (`goto`)
- [ ] Selection (`if` statement)
- [ ] Loops (`while`, `for`)
- [ ] Exceptions (`throw`)

### Question 5 [3 points]
Which of the following are true about Scrum:

- [ ] Scrum should be used when all the requirements are known before development
- [ ] Scrum enables the dev team to interact frequently and receive feedback often from the customer
- [ ] The Scrum master is a manager who decides which tasks are assigned to the developers
- [ ] The product owner is not allowed to change the sprint backlog

### Question 6 [5 points]
The following code snippet describes a typed temperature state which is stored and constructed from Celsius degrees.
Modify the class so that it can also be instantiated from Fahrenheit and Kelvin degrees.
You are not allowed to duplicate state (i.e., you cannot create more attributes to store the value) and the internal representation must stay in Celsius degrees (i.e., a call to `get()` will always return the temperature in Celsius degrees). You cannot modify the existing constructor and method; you cannot add more classes.

The conversion from Fahrenheit to Celsius is `C = (F-32) * 5/9` and the conversion from Kelvin to Celsius is `C = K - 273.15`

Modify the following code in place:

```java
public class Temperature {
    private double celsius;

    public Temperature(double celsius) {
        this.celsius = celsius;
    }

    public Double get() {
        return this.celsius;
    }
}
```

If you used a design pattern, specify its name and justify briefly (one sentence) why it was required:

***(Your answer here)***


### Question 7 [3 points]
Which of the following are good Git commit messages:

- [ ] "I fixed an annoying bug in the Account class."
- [ ] "Add Transaction class to model money transfer logic"
- [ ] "Refactor database as singleton as it should not be instantiated multiple times, the instance is spawned lazily at first use and the getInstance factory must be called to use it"
- [ ] "A bug fix"


### Question 8 [5 points]
You are now working at the Credit Sweng bank. Of course, your code needs to be extremely reliable.
Credit Sweng is working on a new feature with a credit card company, MasterCat, that allows customers to pay their credit card invoice by directly debiting their bank account. For that purpose, MasterCat provides the following API which retrieves the corresponding data from their back-end (you can assume that the `Invoice` class definition exists) :

```java
public class MasterCatService {
    public Invoice getMonthlyInvoice() {
        // proprietary implementation
    };
}
```

However `getMonthlyInvoice()` may return null if the customer does not have a MasterCat card or if an error occured while processing the request. Your management has decided that the internal codebase cannot use null values (as they are far more likely to break code with `NullPointerException`) and they must be replaced by the `Optional` container object.

The `Optional` class is a type-safe way to describe the inexistence of an object. `null` is represented by `Optional.empty()`, while existing instances are wrapped by `Optional.of(myObject)`. This forces users at the receiving end of the Optional to check if the contained value actually exists.

Hence, we would like to communicate with the credit card service using the following interface :

```java
public interface CreditCardService {
    public Optional<Invoice> getMonthlyInvoice();
}
```

Use the Object Adapter design pattern to implement a `MasterCatAdapter` class which adapts and delegates the `getMonthlyInvoice()` calls to the MasterCatService.
The whole codebase will then use the function defined in this class to retrieve the invoice.

Modify the following code in place by replacing the two instances of ???):

```java
public class MasterCatAdapter implements ??? {
    MasterCatService mastercat = new MasterCatService();

    ???
}
```

### Question 9 [3 points]
Which workflow represents the TDD approach:

- [ ] feature request; feature implementation; test for the feature
- [ ] test for the feature; feature request; feature implementation
- [ ] feature request; test for the feature; feature implementation
- [ ] feature implementation; test for the feature; feature request 
- [ ] feature implementation; feature request; test for the feature


### Question 10 [3 points]
Which of the following meetings require that a product owner be present?

- [ ] Daily scrum meeting
- [ ] Sprint review
- [ ] Sprint retrospective
- [ ] Sprint planning

### Question 11 [3 points]
You have to design a class `Workout` which is implemented by `TricepsWorkout`. `TricepsWorkout` consists of objects of type `TricepsDips`,`SeatedDumbbellPress`, `CloseGripBarbellBenchPress`, and `VBarPulldown`, all of which are `Workout` objects themselves. 
Which one of the following design patterns is best suited to efficiently designing this class? Justify your answer in 1-2 sentences. 

- [ ] Adapter
- [ ] Proxy
- [ ] Composite
- [ ] Decorator 
