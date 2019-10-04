# Abstract Factory Design Pattern

## Question 1
What are the tradeoffs of the Abstract Factory Pattern? Select all that apply.
- [x] It's hard to add or remove products
- [ ] It's hard to add or remove factories
- [ ] It's hard to switch between product families
- [ ] It's hard to write unit tests

**Explanation**
The main downside of using the Abstract Factory Pattern is that it is difficult to add or remove products. This is because all concrete factories need to be changed when a product is added or removed.
It is not difficult to add new concrete factories. As long as they satisfy the requirements of the Abstract Factory (i.e., have same methods), the new concrete factory can just subclass the Abstract Factory.
The ease of switching between product families is the main advantage of this pattern. In order to switch families, the developer just needs to change the concrete instantiation of the factory.
The abstract factory pattern does not make testing more difficult. All concrete factories have the same interface, and therefore the same unit tests can check multiple concrete implementations.
