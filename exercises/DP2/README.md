# Design Patterns II
This exercise set covers the following design patterns:

* Adapter
* Decorator
* Proxy
* Composite
* Visitor

There are several theory questions followed by several practical exercises.

For the practical exercises, you can directly open them using Android Studio as follows:

1. Import Project
2. Point to the directory of this exercise set (i.e., the parent folder of this `README.md` file)
3. Follow the steps in the wizard by pressing `Next`

## Theory

### Question 1

Imagine you're writing an application that analyzes Facebook users' messages using natural language processing (NLP) in order to automatically discover the most important discussion topics. Your application uses two existing libraries: One library interfaces with the Facebook servers, extracts the content of a user post, encapsulates it into a `FacebookPost` object, and returns this object to the caller. The other library takes in an `NLPDocument` object, automatically extracts the most relevant topics from the respective document, and returns them to the caller.

Of the following four options, which is the one most effective way of building the desired application?

- [ ] Write an `NLPFacebookPost` class that subclasses `FacebookPost` and also implements `NLPDocument`'s interface, then change the NLP library to accept `NLPFacebookPost` objects instead of `NLPDocument` objects.
- [ ] Write a `FacebookPostNLPDocumentAdapter` class that adapts the interface of `FacebookPost` to the `NLPDocument` interface
- [ ] Write an `NLPDocumentFacebookPostAdapter` class that adapts the interface of `NLPDocument` to the `FacebookPost` interface
- [ ] Use the Proxy design pattern to make `FacebookPost` objects behave like `NLPDocument` objects

### Question 2

Imagine you have an application for managing a telephone directory through a web UI.  This application operates on `Person` objects, with each `Person` representing an entry in the phone directory. The data of the directory is stored in a SQL database on the backend, and each `Person` object corresponds to a row in a _Directory_ table in the database. Whenever the application uses a getter of a `Person` object, such as `Person.getZipcode()`, the `Person` implementation goes to the database to read the data and return it. Whenever the application uses a `Person` setter, like `Person.setZipcode(...)`, the code goes to the database and updates the corresponding row. Beyond this, the `Person` code does little else.

Of the following four design patterns, which would you say is the one implemented in the `Person` class?

- [ ] Adapter
- [ ] Proxy
- [ ] Composite
- [ ] Decorator

### Question 3

Consider the same application as in the previous question, but now the backend has been updated to also provides a higher-performance Remote Procedure Call (RPC) interface to the database in addition to the SQL interface. This RPC service provides a `RemotePerson` interface that has the same getters and setters as `Person`.

In order to change the app to use this faster way of accessing the database, you change `Person` to just do the network communication with the RPC server and turn local calls into remote procedure calls to the corresponding `RemotePerson` methods.

Of the following four design patterns, which would you say is the one implemented in the `Person` class?

- [ ] Adapter
- [ ] Proxy
- [ ] Composite
- [ ] Decorator

### Question 4

The lecture on the Composite design pattern used as a running example a tree of menus and menu items; a tree is by definition acyclic. Is it possible to get cyclic data structures when employing the Composite design pattern?

- [ ] yes
- [ ] no

### Question 5

Consider the Abstract Syntax Tree example in the lecture video about the Visitor Design Pattern (implementation sketch around 3m:30s). Suppose the children of the `AddNode` and `AssignmentNode` were publicly accessible, but that there was no `accept()` method in the `Node` interface.

How would you need to change the `NodeVisitor` class to achieve functionality similar to the Visitor pattern?

- [ ] Not possible, since without the `accept()` method one cannot visit all elements
- [ ] Call `visit(lhs)` and `visit(rhs)`, or `visit(op1)` and `visit(op2)`, to visit the sub-elements of an operation
- [ ] Identify the type of the sub-elements and call the appropriate methods, such as `visit((VariableNode)lhs)` or `visit((NumberLiteralNode)lhs)`

## Practice

* [`Exercise 1`](src/ch/epfl/sweng/exercises/exercise1)
* [`Exercise 2`](src/ch/epfl/sweng/exercises/exercise2)
* [`Exercise 3`](src/ch/epfl/sweng/exercises/exercise3)
* [`Exercise 4`](src/ch/epfl/sweng/exercises/exercise4)
* [`Exercise 5`](src/ch/epfl/sweng/exercises/exercise5)
* [`Exercise 6`](src/ch/epfl/sweng/exercises/exercise6)
* [`Exercise 7`](src/ch/epfl/sweng/exercises/exercise7)
* [`Exercise 8`](src/ch/epfl/sweng/exercises/exercise8)
* [`Exercise 9`](src/ch/epfl/sweng/exercises/exercise9)
