# Factory Method Design Pattern

## Question 1
What Object-Oriented Progamming feature makes the original GOF Factory Method pattern possible? Select one.
- [x] Polymorphism
- [ ] Encapsulation
- [ ] Interfaces
- [ ] Static methods

**Explanation**
The Factory Method pattern relies on polymorphism; subclasses must re-implement the factory method.

## Question 2
Which of the following statements are true? Select all that apply.
- [ ] Both the Static Factory Method and original GOF Factory Method are meant to control the number of instances that exist in a system
- [x] The GOF Factory Method can return different types of objects (subtypes of a common base class), while the Static Factory Method returns the same type of object
- [x] The Static Factory Method and original GOF Factory Method serve different purposes
- [ ] The Static Factory Method and original GOF Factory Method are two alternative implementations of the same concept
- [x] In the original GOF Factory Method, the "createObject" method that instantiates an member of a class should never be static

**Explanation**
The static factory method aims to control the creation of objects of a certain type. It can control the number of instances of an object, or provide alternative means of creating objects.
The original GOF Factory Method aims to delegate responsibility of creation to the actual subclasses. The base class itself creates and uses objects without actually knowing the actual subtype of the objects. This is enabled by a non-static, abstract creation method, which is necessarily implemented by all subtypes of the base class.