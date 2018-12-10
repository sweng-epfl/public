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

- [n] Adapter
- [n] Proxy
- [n] Composite
- [y] Decorator 

**Justification:** The decorator pattern is useful to extend the functionality of some objects of a class. In this example, it is useful to decorate `Soldiers` with weapons, instead of creating separate classes for each combination. 

### Question 2 [3 points]
Which of the following is/are true:

- [y] In TDD, a newly written test must always fail
- [n] In TDD, a newly written test may pass but is likely to fail
- [n] The decorator DP is suitable when we want to extend the functionality of **all** object instances of a particular class
- [y] The decorator DP is suitable when we want to extend the functionality of **some** object instances of a particular class

**Explanation:** All the functionality in a product developed with the TDD approach is driven by the tests present.
If your new test passes right after your wrote it, that means that it exercises functionality already present in the product and therefore already tested for.
Thus, the test is useless for TDD, as it does not drive new functionality.
TDD doesn't guarantee bug free code, one needs formal verification for that.

Decorator DP (as opposed, e.g. to simple inheritance) allows the application to dynamically modify functionality of certain instances while keeping others intact.


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
        followers.add(observer); // added
    }

    @Override
    public void removeObserver(Observer observer) {
        followers.remove(observer); // added
    }

    @Override
    public void notifyObservers() {
        for (final Observer o : followers) { // added
            o.update(this);                  // added
        }                                    // added
    }

    public List<String> getTweets() {
        return Collections.unmodifiableList(new ArrayList<String>(tweets));
    }

    public void addTweet(String tweet) {
        this.tweets.add(tweet);
        notifyObservers(); // added
    }
}
```

```java
public class Follower implements Observer {

    @Override
    public void update(Observable observable) {
        Celebrity celebrity = (Celebrity) observable;
        List<String> tweets = celebrity.getTweets();     // added
        System.out.println(tweets.get(tweets.size()-1)); // added
    }
}
```

**Explanation:** `addObserver`, `removeObserver` and `notifyObservers` are part of the core implementation of the observer pattern. In `addTweet`, we must call `notifyObservers` to inform the followers that there's a new tweet. In the `Follower` class, we simply get the tweets when notified, and display the last tweet from the list.

### Question 4 [3 pts]
Which of the following constructs may violate the single-exit principle of structured programming:
- [y] Unconditional jump (`goto`)
- [n] Selection (`if` statement)
- [n] Loops (`while`, `for`)
- [y] Exceptions (`throw`)

**Explanation:** Structured programming principles state that good programs should only be composed of 3 block types : sequences of code, selections and iterations. These blocks are single-entry, single-exit constructs that compose ordered and regular code that doesn't "jump around". Such programs are, hence, easier to read and reason about. The single-exit principle is the reason why `goto` statements are generally frown upon in best practices, and why modern languages tend to wrap exception handling with more expressive constructs (such as asynchronous callbacks in many APIs and monads in functional programming).

A reference for the curious reader: "Go To Statement Considered Harmful" by Edsger W. Dijkstra.

### Question 5 [3 points]
Which of the following are true about Scrum:
- [n] Scrum should be used when all the requirements are known before development
- [y] Scrum enables the dev team to interact frequently and receive feedback often from the customer
- [n] The Scrum master is a manager that decides which tasks are assigned to the developers
- [y] The product owner is not allowed to change the sprint backlog

**Explanation:** Scrum should be used when frequent feedback with the customer is required: the iterative nature of the Scrum development cycle allows for adjustments of the requirements over time, which would waste a lot of time for projects with objectives that are already set in advance. The Sprint Reviews allow the Development Team to demo the current prototype to the customer often and get immediate feedback on it. The Scrum Master is not a manager: their role consists of facilitating the work of the Development Team and helping the organization work with Scrum, i.e.,  protect the Development Team from outside interferences (such as the customer or upper-management). Once the Backlog Items for a Sprint are set, the Product Owner cannot change them, he must wait for the end of the current Sprint.

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

    // solution code starts here
    public static Temperature fromFahrenheit(double fahrenheit) {
        return new Temperature((fahrenheit - 32) * 5.0/9.0);
    }

    public static Temperature fromKelvin(double kelvin) {
        return new Temperature(kelvin - 273.15);
    }
    // solution code ends here
    
    public Double get() {
        return this.celsius;
    }
}
```

If you used a design pattern, specify its name and justify briefly (one sentence) why it was required:

**Justification:** We use the factory design pattern to control more ways to create the object. The corresponding functions need to be public and static since we need to be able to call them without any object instance. The factory names do not matter as long as they are self-explanatory. They must return a new `Temperature` object with the converted double value.

We used the factory design pattern because we need additional control over the object creation and we cannot overload the constructor with the same argument type.

### Question 7 [3 points]
Which of the following are good Git commit messages:
- [n] "I fixed an annoying bug in the Account class."
- [y] "Add Transaction class modeling money transfer logic"
- [n] "Refactor database as singleton as it should not be instantiated multiple times, the instance is spawned lazily at first use and the getInstance factory must be called to use it"
- [n] "A bug fix"

**Explanation:**
- "I fixed an annoying bug in the Account class." is not descriptive enough and contains subjective opinion that isn't useful to the commit. It is also good practice to use the imperative mood and not add a period at the end of the sentence.
- "Add Transaction class modeling money transfer logic" is an explicit commit message with proper formatting.
- "Refactor database as singleton as it should not be instantiated multiple times, the instance is spawned lazily at first use and the getInstance factory must be called to use it" is too long and contains too many implementation details that are not essential to the commit message.
- "A bug fix" is not precise enough, it should ideally mention what the bug was and what part of the code was fixed. It also does not follow the imperative form.

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
public class MasterCatAdapter implements CreditCardService { //added the CreditCardService interface
    MasterCatService mastercat = new MasterCatService();

    // solution code starts here
    @Override
    public Optional<Invoice> getMonthlyInvoice() {
        Invoice data = mastercat.getMonthlyInvoice();
        if (data == null) {
            return Optional.empty();
        } else {
            return Optional.of(data);
        }
    }
    // solution code ends here
}
```

**Using auto conversion in the method override is also correct :**

```java
    @Override
    public Optional<Invoice> getMonthlyInvoice() {
        return Optional.ofNullable(mastercat.getMonthlyInvoice());
    }
```

**Explanation:** The adapter class `MasterCatAdapter` must implement the `CreditCardService` interface since it is the one we want to use in the codebase.
The adapter is composed with the `MasterCatService` such that the overriden `getMonthlyInvoice` call can be delegated to the actual `MasterCatService`.
Now, we simply need to wrap the returned object with the desired `Optional` type: for that purpose we simply check if the retrieved data is null.
If that's the case then we simply return an `Optional.empty()`, otherwise we wrap the object using `Optional.of()`.

### Question 9 [3 pts]
Which workflow represents the TDD approach:
- [n] feature request; feature implementation; test for the feature
- [n] test for the feature; feature request; feature implementation
- [y] feature request; test for the feature; feature implementation
- [n] feature implementation; test for the feature; feature request 
- [n] feature implementation; feature request; test for the feature

**Explanation:**
Feature request must precede everything, since there must be a need for a feature before developers start working on it.
In TDD (Test Driven Development), developers write tests according to the feature description before trying to implement the feature.
Once the test is in place, the developer can implement funcitonality only to the extent while the test fails.
Once the test succeeds, the developer repeats the cycle.

### Question 10 [3 pts]
Which of the following meetings require that a Product Owner be present?
- [n] Daily scrum meeting
- [y] Sprint review
- [y] Sprint retrospective
- [y] Sprint planning

**Explanation:** According to the [Scrum Guide](https://www.scrumguides.org/scrum-guide.html), the customer is representated by the Product Owner in the meetings. 
The purpose of the Sprint Review is to synchronize the development progress with the desires of the customers, so the presence of the customer is indispensable.
The Product Owner is a key participant in the Sprint Planning because they own the Product Backlog and they need to communicate with the Development Team what is to be done during the next Sprint.
During the Sprint Retrospective, the Scrum Team (which includes the Product Owner, the Development Team, and the Scrum Master) all inspect how they work together and how to work better in the future Sprints.

Only at the Daily Scrum meeting the Development Team is on their own because it is a coordination meeting on development tasks.

### Question 11 [3 points]
You have to design a class `Workout` which is implemented by `TricepsWorkout`. `TricepsWorkout` consists of objects of type `TricepsDips`,`SeatedDumbbellPress`, `CloseGripBarbellBenchPress`, and `VBarPulldown`, all of which are `Workout` objects themselves. 
Which one of the following design patterns is best suited to efficiently designing this class? Justify your answer in 1-2 sentences. 

- [n] Adapter
- [n] Proxy
- [y] Composite
- [n] Decorator 

**Justification:** Since all the objects can be categorized as part of the same type and some of the workouts are composed out of others, forming a tree, the Composite design pattern is the best fit. 
