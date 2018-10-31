# Solutions

Here are the solutions to the 3 [theoretical questions](#theory) and the 5 [practical exercises](#practice).

<a name="theory"></a>
## Theory

### Question 1

- [ ] `StudentsDatabase` is the model, `StudentsManager` is the controller, and `WebApplication` is the the view.
- [x] `StudentsDatabase` is the model, `StudentsManager` is the view, and `WebApplication` is the controller.
- [ ] `StudentsManager` is the model, `StudentsDatabase` is the view, and `StudentsManager` is the controller.
- [ ] This is not MVC, because `StudentsManager` must use a listener to be notified when the database changes.

**Explanation:** 
The `StudentsDatabase` is the model; it stores the data that is displayed in the `StudentsManager` view. The controller `WebApplication` selects what data to display, based on the request URL received from the user.

### Question 2

- [x] The strategy pattern uses aggregation instead of inheritance in order to avoid code duplication
- [ ] The strategy pattern requires to settle for a particular implementation of an algorithm at compile time
- [x] It is possible for a class to implement multiple unrelated strategies to be used in different contexts
- [ ] The strategy pattern requires a field of the desired strategy interface to be present in the class that wants to use the strategy

**Explanation:** 
The strategy pattern allows to mix and match different strategies without having to duplicate code.
Strategies are regular classes. The particular instantiation that is used in a program can be changed at run time.
A strategy class could implement multiple strategy interfaces, as permitted by Java.
The strategy pattern does not specify how the instances of concrete strategy classes are passed. They could as well be passed as function parameters.

### Question 3

- [x] Iterating over a list of 10,000 elements takes twice as much time as iterating over a list of 5,000 elements
- [ ] Iterating over a list of 10,000 elements takes four times as much time as iterating over a list of 5,000 elements
- [x] Determining if the iterator reached the end of the list takes a fixed amount of time on each iteration in the `next()` method
- [x] Accessing the item at the current position of the iterator takes a fixed amount of time

**Explanation:** 
`ArrayList`'s `get()` and `size()` methods take a constant amount of time to execute (independent of the size of the list).
Therefore, iterating over all elements of `MyArrayList` using `MyListIterator` is O(n).

<a name="practice"></a>
## Practice

* [`Exercise 1`](src/ch/epfl/sweng/dp3/solutions/ex1)
* [`Exercise 2`](src/ch/epfl/sweng/dp3/solutions/ex2)
* [`Exercise 3`](src/ch/epfl/sweng/dp3/solutions/ex3)
* [`Exercise 4`](src/ch/epfl/sweng/dp3/solutions/ex4)
* [`Exercise 5`](src/ch/epfl/sweng/dp3/solutions/ex5)