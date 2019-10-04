# Design patterns: The basics

## Question 1
Which of the following could be considered an abuse of the Exceptions design pattern? Select all that apply.
- [ ] Throwing a RuntimeException when the error is caused by a programmer mistake
- [x] Throwing a RuntimeException in order to break out of several nested loops
- [x] Throwing an EmptyListException in the size() method of a list class, if the list is empty
- [ ] Throwing an IOException in a network socket class if the connection is dropped

**Explanation**
The first case is not an abuse: RuntimeExceptions should indeed be thrown in exceptional circumstances caused by programmer mistakes (e.g., NPEs, invalid arguments, etc.)
The second case is an abuse: it takes advantage of the unwinding properties of the exceptions to break the control flow in a non-exceptional case.
The third case is also an abuse: an empty list is not an exceptional situation, and throwing an exception for that is unnecessary.
The fourth case is not an abuse: Dropped connections are caused by external factors that we cannot control, so the code should be prepared to receive this exception.

## Question 2
Which of the following are advantages of inheritance over encapsulation? Select all that apply.
- [ ] Inheritance reduces the amount of code coupling
- [x] Inheritance better prevents code duplication
- [ ] Inheritance improves system performance
- [ ] Inheritance simplifies system testing
- [ ] Inheritance enables the use of design patterns, while encapsulation does not

**Explanation**
Inheritance actually increases the amount of code coupling, since subclasses have access to the internal state of their superclasses. Thus, they depend on how the superclass is implemented (video @12:20).
Inheritance prevents code duplication, since subclasses inherit methods and fields from the superclass and do not need to re-implement them.
Both inheritance and encapsulation can have negative impacts on performance; encapsulation adds the necessity of accessing fields through method calls, while inheritance adds the overhead of the dispatcher, which needs to select which method implementation to call in a given context. In some designs, inheritance is more efficient, while in others encapsulation is more efficient.
Inheritance can make testing more complex, due to the increased coupling between classes. With encapsulation, the implementations of classes can be evaluated independently (because as long as the interfaces are respected, the particularities of implementations are irrelevant). With inheritance, the two implementations are dependent and cannot be separated (the subclass depends on the superclass).
Some design patterns use inheritance, while others use encapsulation.

