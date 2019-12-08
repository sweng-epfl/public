# CS305 – Software Engineering

## Midterm Exam

# Part 2: Practice [60 points]

In this part of the exam you need to write real code. As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles:

- Write correct and rock-solid code
- Follow the SwEng coding principles
- Write readable and concise code
- Use comments judiciously

You should use the same development environment as for the [bootcamp](https://github.com/sweng-epfl/public/tree/master/bootcamp). To validate your installation, you can do a `java -version` from the command line. You are ready to go as soon as the command outputs something like this:

```
java version "1.8.0_144"
Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)
```
The version number need not be identical to the one above, but it must start with `1.8`.

You can run locally `./gradlew test` and `./gradlew jacoco`, after which you can find coverage results in `midterm/build/reports/jacoco/test/html/index.html`. You are free to use an editor or IDE of your choice, as long as the code and tests build using `./gradlew build`. On Windows, use `gradlew.bat` instead of `./gradlew`.

Please remember that we will only grade whatever is in your `master` branch. We will use `./gradlew build` to build your code.

We provide you with continuous integration at [travis-ci.com](https://travis-ci.com/). It automatically retrieves the code from your `master` branch.
If you forget to commit a file, or if you push the wrong version or a version that breaks your previously working version, you may lose all points, so please check Travis after every push.

The authoritative code coverage numbers are those reported by running `./gradlew jacoco`, even if you use CodeClimate.

---
#### Some tips for Android Studio users *(skip this section if you don't use Android Studio)*
- If Android Studio fails to recognize the `midterm` folder as containing a module, double click on it in the project view, go to the "Sources" tab, and click on "+ Add Content Root" and "OK" and "OK" to let the studio find the midterm module.
- If Android Studio does not list the files in the 'Project' view, click on the 'Sync Project with Gradle Files' button (top-right, left of the Android Devices button).
- If running the tests in Android Studio fails with no tests found, open the Gradle view (View > Tool Windows > Gradle), click on the 'Execute Gradle Task' button (with the Gradle logo), and run the 'check' task. This will fail, but once it finishes running the tests through a class' right-click menu in the Project view will work.
- If Android Studio cannot find the `junit` package, click on the 'Sync Project with Gradle Files' button (top-right, left of the Android Devices button).


---

This practical part of the exam has 4 questions with the number of points indicated next to each one. The preamble section introduces the setting of the problem. 

### Preamble

The Ministry of Information of Seychelles has placed you in charge of building an Address Catalogue System for them. This system is responsible for storing the addresses of all houses within their country. The address of any house in Seychelles is represented as a list of four strings: 

1. Country name
2. Island name
3. District name
4. House ID

An example address is: `["Seychelles", "Mahe", "Pointe La Rue", "1110"]`. 

As you have probably guessed by now, addresses form a hierarchy, i.e., one country consists of several islands, and an island consists of several districts, and each district contains several houses. To design this hierarchical system in an efficient yet flexible manner, you decide to use the Composite design pattern. The class hierarchy looks like this: 

![](class-hierarchy-composite.png) 

Classes `AddressUnit`, `CompositeAddressUnit`, and `House` implement the Composite design pattern.
They form a tree-like structure, as in the following picture.
The red arrows show the address `["Seychelles", "Mahe", "Port Glaud", "111"]`:

![](address-hierarchy.png)

The Class `MinistryOfInformation` represents the client and is used by the government to build upon the services provided by your system. This class uses an instance of the Address Catalogue System to perform a number of functions. 

In this exam, you will be working **ONLY** with the Address Catalogue System.
Under no circumstances are you allowed to change signatures of the existing methods in the class `MinistryOfInformation`.
If you do, you will receive 0 points for the practical problems.
You are free to _add_ new methods that you need, and you **must** add/modify code in the specified regions of the `MinistryOfInformation` class. 
Detailed information regarding what you can and cannot change can be found in the class hierarchy diagram above. 

The `MinistryofInformationTests` class contains a number of unit tests that you can use for different questions below. 

### Question 1: Find A House [15 points]

The `MinistryOfInformation` class uses the `findHouse` method to locate a house in the database.
This function is part of a public interface, and the inputs it receives cannot be assumed to be safe or compliant in any way.
Your first task is to implement this function while respecting the best defensive programming practices.
You should make sure that `findHouse`:

- returns a valid `House` for a valid address 
- throws the `InvalidAddressException` if the address provided is invalid (e.g., does not contain 4 strings)
- throws `AddressNotFoundException` if the address provided is valid but does not exist in the database.

Implement the function along with additional functions in other classes (if required), according to the Composite design pattern. Make sure the code cannot be crashed by providing bad inputs, i.e., either it returns a valid `House` object or throws one of `InvalidAddressException` or `AddressNotFoundException`.

### Question 2: Population Census [15 points]

The Address Catalogue System works so well that the Ministry of Information decided to reuse it for keeping track of Seychelles's population.

Each `House` object already keeps track of the number of people living there, in a field called `population`.
Your task is to implement a `getPopulation` method in the `MinistryOfInformation` class using the Composite design pattern.

The `getPopulation` method should report the total population for a given address prefix.
For example (numbers are arbitrary):

- `["Seychelles", "Mahe", "Pointe La Rue", "1110"]` -> `2` indicates that there is only a family of two living in this house
- `["Seychelles", "Mahe", "Pointe La Rue"]` -> `3172` indicates that the number of people living in the district Pointe La Rue is 3,172
- `["Seychelles", "Mahe"]` -> `95000` indicates that the population of the Mahe island in Seychelles is 95,000 
- `["Seychelles"]` -> `95843` indicates that the total population of the country of Seychelles is 95,843

Remove the corresponding `@Ignore` annotation in the `MinistryOfInformationTests` class to run the example unit test. 

### Question 3: Message Broadcast [15 points]

Due to the success of the Address Catalogue System, the Ministry of Information has decided to use it for more and more purposes.
In particular, they want to use it to send letters to all houses within a given address prefix. 
To enable this new request, you decide to add functionality to print the full addresses of all houses in a given address prefix.

For example:

- `["Seychelles", "Mahe", "Pointe La Rue"]` -> Print the address of all houses in the district Pointe La Rue
- `["Seychelles", "Mahe"]` -> Print the address of all houses on the island of Mahe
- `["Seychelles"]` -> Print the address of all houses in the country of Seychelles

Each address should be printed in full (ordered from bigger to smaller address unit, e.g.: first country, then island, then district, then house) and on a new line with a comma and space between different divisions. For example:

```
Seychelles, Mahe, Port Glaud, 234
Seychelles, Mahe, Port Glaud, 432
```

Importantly, to maintain flexibility during the addition of new functionality, you decide to implement this new functionality using the Visitor design pattern. 
Normally, you would refactor the code to redo the `getPopulation` method as well, but for now, leave the `getPopulation`method as-is and only implement a visitor for the printing of all addresses within a prefix. 

You have been provided with the `AddressUnitVisitor` interface and a skeleton for the `PrintAddressesVisitor` class. 
Remove the corresponding `@Ignore` annotation in the `MinistryOfInformationTests`to run the example unit test.

### Question 4: Quality Assurance [15 points]

By this point you have implemented all the _functionality_ required for this exam. However, as a good SwEng student, you know that implementing new functionality is only part of the job. It is now time to check the robustness of your code by writing tests. 

Your final task is to write tests that achieve 95% statement coverage on all code in the `sweng.epfl.ch` package, including code written by you.

Grading: You will receive 0 points if coverage is below 85%. You will receive 15 points if your coverage is above 95%. In between these two, the number of points will be distributed according to an exponential curve that captures the fact that, the higher the coverage, the harder it gets to improve it. For example, 87% coverage earns you 4 points, 90% earns you 7 points, and 93% coverage earns you 12 points.

Remember that you can run `./gradlew test` and `./gradlew jacoco`, after which you can find coverage results in `midterm/build/reports/jacoco/test/html/index.html`. On Windows, use `gradlew.bat` instead of `./gradlew`.
 
