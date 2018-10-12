# Theoretical questions
## Warmup

### Question 1:

What is modular programming? Why is it important?

Modular programming is a technique used to divide program into many small, manageable, logical and functional modules or blocks. It makes many things easier, both in developing and maintaining code. It can be easier to isolate bugs with well-defined modules that accomplish specific tasks. Also, if every component has a clearly defined API, it is generally easier to extend and build new features into an existing codebase.

### Question 2:

What is module coupling and why is it a good idea to minimize it?

Module coupling is the degree of interdependence between different software modules. When creating different modules, it is generally good to minimize how much one depends on the other. The less coupled modules are, the easier it is to test them, to divide the development responsibilities, etc.

### Question 3:

Which of the following are true about modular programming? (Some things might be true in general, but not specific to modular programming⏤in that case do not check the box.)
- [ ] We should divide any function longer than 25 lines into several smaller functions
- [x] We should attempt to identify code duplication and refactor it
- [ ] It makes it harder to test the code behaviour
- [ ] It always reduces code size
- [x] It allows the work to be divided among different developers

### Question 4:

What are some advantages of modular programming?

- Ease of use: By dividing the code into separate modules, it becomes easier to understand the behavior of the code and what each module does.
- Reusability: The same module can be used in several places
- Ease of maintenance: Maintaining legacy code becomes easier as we only need to understand what one module does instead of the entirity of the code.

### Question 5:

What is the role of interfaces? When would you use an abstract class instead of an interface?

The term _interface_ is used to define an abstract type that contains no data but defines behaviors as method signatures.

An _abstract class_ on the other hand is a class that contains abstract methods, which are methods declared without an implementation.

You would use an abstract class instead of an interface when you want to define a class with non-final variables or you actually want to implement some of the class methods but leave other class methods to be implemented by the user of the class. For example, you may have a `List `class fully implemented except for the `sort()` function, which you define as an abstract method, leaving it up to the user of the class to define how to sort the list in a way that is optimal for their use case.
