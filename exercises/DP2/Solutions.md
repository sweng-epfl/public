# Design Patterns II (Solutions)

## Theory

### Question 1 (Answer)

- [ ] Write an `NLPFacebookPost` class that subclasses `FacebookPost` and also implements `NLPDocument`'s interface, then change the NLP library to accept `NLPFacebookPost` objects instead of `NLPDocument` objects.
- [x] Write a `FacebookPostNLPDocumentAdapter` class that adapts the interface of `FacebookPost` to the `NLPDocument` interface
- [ ] Write an `NLPDocumentFacebookPostAdapter` class that adapts the interface of `NLPDocument` to the `FacebookPost` interface
- [ ] Use the Proxy design pattern to make `FacebookPost` objects behave like `NLPDocument` objects

***Explanation***

`FacebookPost` and `NLPDocument` serve similar purposes in this context: they encapsulate the content of a Facebook user post. However, the interface for accessing this content is different in the two classes. 

The correct choice is #2, namely to use the Adapter design pattern to make a `FacebookPost` object be "digestable" by the NLP library. No functionality needs to be modified, just the interface used to get to the content of the Facebook post.

Option #1 is incorrect, and the biggest red flag is the modification of the NLP library. The purpose of libraries is to encourage code reuse so, if a library needs to be changed, then either the library is poorly designed or the application chose the wrong library for the job. Option #3 reverses the direction in which the interface is adapted, and so the NLP library cannot be used to analyzed Facebook posts, which defeats the very point of the application. Option #4 is also wrong, because proxies do not help classes with different interfaces work with each other but rather create surrogate objects that enable the communication between clients and servers.

### Question 2 (Answer)

- [x] Adapter
- [ ] Proxy
- [ ] Composite
- [ ] Decorator

***Explanation***

The telephone directory data is stored in the SQL database, and the `Person` class does not maintain any state. All it does is provide an interface between the application and the database.

The correct choice is #1: the `Person` class essentially converts between the SQL interface provided by the database to the phone directory data and the setter/getter interface needed by the application to operate on that data. There is no change of functionality, just an adaptation of the interface.

Option #2 is incorrect, even though it matches architecturally the client/server model used to illustrate the proxy design pattern. It nevertheless isn't a good match here, because a proxy by definition preserves the interface provided by the server, whereas here the SQL interface provided by the server is not exposed to the client. One might argue that the application uses both an adapter and a proxy, but the proxy is not strictly necessary here, whereas the adapter is. Option #3 is incorrect because the `Person` class does not provide any additional functionality over the database, yet that is a key property of composites. Option #4 is incorrect because the SQL interface is significantly different from the `Person` interface, yet a decorator does not change interfaces.

### Question 3 (Answer)

- [ ] Adapter
- [x] Proxy
- [ ] Composite
- [ ] Decorator

***Explanation***

With this change, the `Person` class no longer modifies the SQL interface into the getter/setter interface, but rather all it does is provide a stand-in object that mediates between the application and the RPC service on the database backend. The correct choice is therefore #2. It is worth nothing that the `RemotePerson` class on the backend is now an adapter, because it translates between the SQL interface and the getter/setter interface.

Option #1 is incorrect, because the interface of `RemotePerson` and `Person` is the same, thus is not being adapted. Options #3 and #4 are incorrect because `Person` does not modify the functionality of `RemotePerson`.

### Question 4 (Answer)

- [x] yes
- [ ] no

***Explanation***

Yes, it is possible, if a `Composite` has a reference to an ancestor node. For example, a menu might refer to another menu (as in "this dessert menu can also be had in the Cafe, so if the Diner has no available seats, you can go to enjoy a dessert in the Cafe"). This would lead to a cycle.

In general, having such loops in a composite is not a good idea, because operations applied recursively might never finish, unless additional checks are put in place.

### Question 5 (Answer)

- [ ] Not possible, since without the `accept()` method one cannot visit all elements
- [ ] Call `visit(lhs)` and `visit(rhs)`, or `visit(op1)` and `visit(op2)`, to visit the sub-elements of an operation
- [x] Identify the type of the sub-elements and call the appropriate methods, such as `visit((VariableNode)lhs)` or `visit((NumberLiteralNode)lhs)`

***Explanation***

The role of the `accept()` method is to serve as the second round of dispatch in the double-dispatch scheme of the Visitor pattern. By calling the abstract `accept()`, the exact node type is identified at runtime and the appropriate `accept()` method is dispatched, in turn calling the appropriate `visit()` (see video lecture at 5m:08s). 

Without the `accept()` method, we have to identify the type of the sub-elements explicitly, as done in option #3. Then it becomes possible to visit all elements of a structure even without using the classic Visitor pattern, which makes option #1 incorrect. However, without the Visitor pattern the code is more complex and less manageable. Regarding option #2, simply calling `visit(lhs)` instead of `lhs.accept()` would cause a compiler error, because the target `visit()` overload cannot be determined statically.

## Practice

* [`Exercise 1`](src/ch/epfl/sweng/exercises/exercise1_solutions)
* [`Exercise 2`](src/ch/epfl/sweng/exercises/exercise2_solutions)
* [`Exercise 3`](src/ch/epfl/sweng/exercises/exercise3_solutions)
* [`Exercise 4`](src/ch/epfl/sweng/exercises/exercise4_solutions)
* [`Exercise 5`](src/ch/epfl/sweng/exercises/exercise5_solutions)
* [`Exercise 6`](src/ch/epfl/sweng/exercises/exercise6_solutions)
* [`Exercise 7`](src/ch/epfl/sweng/exercises/exercise7_solutions)
* [`Exercise 8`](src/ch/epfl/sweng/exercises/exercise8_solutions)
* [`Exercise 9`](src/ch/epfl/sweng/exercises/exercise9_solutions)
