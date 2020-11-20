# Software Engineering - Homework 2

## Part 2: Practice [80 points]

In this part of the homework you need to write real code.
As is often the case in software engineering, the problem itself is fairly easy to reason about, 
and what really matters is that your code employs solid software engineering principles.

You should write code that is:
- Correct and robust
- Readable and concise
- Judiciously commented
- Well tested, aiming for 100% statement coverage

This homework uses Java 11.
To validate your installation, you can run `java -version` from the command line; the output should start with something like `java version "11.0.8"`.
The version number need not be identical, but it must start with `11`.

> :information_source: If you do not have Java 11, you can get it from [https://jdk.java.net/archive/](https://jdk.java.net/archive/). More recent versions may not be compatible.

You can build locally with `gradlew.bat build` on Windows or `./gradlew build` on Mac and Linux, which is also what we will use to build and test your code.
You may not modify the Gradle setup. For instance, you may not add new dependencies, or change the versions of existing dependencies.
You are free to use an editor or IDE of your choice, as long as the code and tests build using the `./gradlew build` command line.
We recommend Android Studio, which can import this project as a Gradle project.

Please remember that we will only grade whatever is in your `master` branch.

**Do not modify in any way the public interface of the code that is given to you**, not even to add checked exceptions.
We will use automated tests when grading; if we cannot build and run your code, you will receive 0 points.
You may add private methods and constructors.

Your code should fail cleanly when it cannot continue safely, and it should throw adequately specific runtime exceptions (or custom versions of these):

- When implementing known interfaces, follow their prescribed exceptions
- Throw `IndexOutOfBoundsException` for any kind of index whose value is too low or too high
- Throw `IllegalArgumentException` for arguments (other than indexes) whose value is invalid
- Throw `IllegalStateException` for operations that fail because of an object's current state


## Introduction

You are working in a company that develops a tool to manage inventory for (online) shops. The user, which is the shop manager, needs to add items into the inventory, get items out of it, and monitor the quantity of each item. The feature that makes your product better than the competition is the automation of the resupply process: when an item's quantity drops below a threshold, the program automatically orders more of the missing item. The threshold and the quantity of the resupply process are set by the user. The program also logs every inventory movement.

The program contains the following concepts and classes:

- Items are represented by an enumeration `ItemKind` that contains the price.
- The inventory is stored in a database represented by the `InventoryDatabase` class, whose implementation we provide; it keeps track of how many instances of each item are in the inventory.
- The `InventoryManager` provides inventory operations, including tracking, for use in the rest of the application.
- The `InventorySupplier` _observes_ the `InventoryManager` and resupplies items when there are not enough in the inventory.
- The `InventoryLogger` _observes_ the `InventoryManager` to store all inventory movements as a list of messages.
- The user interface is represented by the `UserInterface` class; it provides methods to add and remove items, as well as to get a textual representation of the current inventory and of the movements. It also offers methods to change the resupply threshold and quantity.

## Part 2.1 [40 points]

Your first task is to implement the [`InventoryManager`](src/main/java/homework02/InventoryManager.java)'s methods according to [its interface documentation](src/main/java/homework02/util/Manager.java), as well as its constructor according to its documentation.

This includes an implementation of the [`InventoryObservable`](src/main/java/homework02/util/InventoryObservable.java) interface, as the manager will be _observed_ by different other objects.

## Part 2.2 [5 points]

You can now implement the [`InventoryLogger`](src/main/java/homework02/InventoryLogger.java)'s methods according to [its interface documentation](src/main/java/homework02/util/Logger.java), as well as its constructor according to its documentation. It _observes_ an `InventoryManager`.

## Part 2.3 [15 points]

You can now implement the [`InventorySupplier`](src/main/java/homework02/InventorySupplier.java)'s methods according to [its interface documentation](src/main/java/homework02/util/Supplier.java), as well as its constructor according to its documentation. It also _observes_ an `InventoryManager`.

## Part 2.4 [10 points]

Everything you need to implement the [`UserInterface`](src/main/java/homework02/UserInterface.java) is now ready. Implement its methods and constructor, according to its documentation.

## Part 2.5 [10 points]

Since a user should only interact with a single instance of `UserInterface`, let's use a singleton.

In this last part, you need to implement [`UserInterfaceSingleton`](src/main/java/homework02/UserInterfaceSingleton.java) according to its documentation. It is a class providing a unique instance of `UserInterface`.
