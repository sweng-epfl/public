## Design patterns: The basics

Which of the following could be considered an abuse of the Exceptions design pattern? Select all that apply.

- [ ] Throwing a RuntimeException when the error is caused by a programmer mistake
- [x] Throwing a RuntimeException in order to break out of several nested loops 
- [x] Throwing an EmptyListException in the size() method of a list class, if the list is empty 
- [ ] Throwing an IOException in a network socket class if the connection is dropped

> The first case is not an abuse: RuntimeExceptions should indeed be thrown in exceptional circumstances caused by programmer mistakes (e.g., NPEs, invalid arguments, etc.)
      The second case is an abuse: it takes advantage of the unwinding properties of the exceptions to break the control flow in a non-exceptional case.
      The third case is also an abuse: an empty list is not an exceptional situation, and throwing an exception for that is unnecessary.
      The fourth case is not an abuse: Dropped connections are caused by external factors that we cannot control, so the code should be prepared to receive this exception.

Which of the following are advantages of inheritance over encapsulation? Select all that apply.

- [ ] Inheritance reduces the amount of code coupling
- [x] Inheritance better prevents code duplication 
- [ ] Inheritance improves system performance
- [ ] Inheritance simplifies system testing
- [ ] Inheritance enables the use of design patterns, while encapsulation does not

>   Inheritance actually increases the amount of code coupling, since subclasses have access to the internal state of their superclasses. Thus, they depend on how the superclass is implemented (video @12:20).
        Inheritance prevents code duplication, since subclasses inherit methods and fields from the superclass and do not need to re-implement them.
        Both inheritance and encapsulation can have negative impacts on performance; encapsulation adds the necessity of accessing fields through method calls, while inheritance adds the overhead of the dispatcher, which needs to select which method implementation to call in a given context. In some designs, inheritance is more efficient, while in others encapsulation is more efficient.
        Inheritance can make testing more complex, due to the increased coupling between classes. With encapsulation, the implementations of classes can be evaluated independently (because as long as the interfaces are respected, the particularities of implementations are irrelevant). With inheritance, the two implementations are dependent and cannot be separated (the subclass depends on the superclass).
        Some design patterns use inheritance, while others use encapsulation.

## Factory Method Design Pattern

What Object-Oriented Progamming feature makes the original GOF Factory Method pattern possible? Select one.

- [x] Polymorphism 
- [ ] Encapsulation
- [ ] Interfaces
- [ ] Static methods

> The Factory Method pattern relies on polymorphism; subclasses must re-implement the factory method (see video at 9:30).

Which of the following statements are true? Select all that apply.

- [ ] Both the Static Factory Method and original GOF Factory Method are meant to control the number of instances that exist in a system
- [x] The GOF Factory Method can return different types of objects (subtypes of a common base class), while the Static Factory Method returns the same type of object 
- [x] The Static Factory Method and original GOF Factory Method serve different purposes 
- [ ] The Static Factory Method and original GOF Factory Method are two alternative implementations of the same concept
- [x] In the original GOF Factory Method, the "createObject" method that instantiates an member of a class should never be static 

> The static factory method aims to control the creation of objects of a certain type. It can control the number of instances of an object, or provide alternative means of creating objects. 
      The original GOF Factory Method aims to delegate responsibility of creation to the actual subclasses. The base class itself creates and uses objects without actually knowing the actual subtype of the objects. This is enabled by a non-static, abstract creation method, which is necessarily implemented by all subtypes of the base class.

## Abstract Factory Design Pattern

What are the tradeoffs of the Abstract Factory Pattern? Select all that apply.

- [x] It's hard to add or remove products 
- [ ] It's hard to add or remove factories
- [ ] It's hard to switch between product families
- [ ] It's hard to write unit tests

> The main downside of using the Abstract Factory Pattern is that it is difficult to add or remove products. This is because all concrete factories need to be changed when a product is added or removed.
      It is not difficult to add new concrete factories. As long as they satisfy the requirements of the Abstract Factory (i.e., have same methods), the new concrete factory can just subclass the Abstract Factory.
      The ease of switching between product families is the main advantage of this pattern. In order to switch families, the developer just needs to change the concrete instantiation of the factory.
      The abstract factory pattern does not make testing more difficult. All concrete factories have the same interface, and therefore the same unit tests can check multiple concrete implementations.

## Singleton Design Pattern

Consider the following piece of code, which returns an httpClient for establishing a network connection:

```java
public class SwengHttpClientFactory {
    private static AbstractHttpClient httpClient;
    public static synchronized AbstractHttpClient getInstance() {
        if (httpClient == null) {
            httpClient = create();
        }
        return httpClient;
    }
    
    public static synchronized void setInstance(AbstractHttpClient instance) {
        httpClient = instance;
    }
...
```

Which of the following statements are true regarding this design? Select all that apply.

- [ ] This is a standard example of the original GOF factory
- [ ] This is a standard example of the static factory method
- [ ] This is a standard example of Singleton
- [x] The class allows switching the concrete HttpClient returned 
- [ ] The SwengHttpClientFactory decides what type of HttpClient to return

>   The example is not a standard factory method. This is because factory methods are meant to centralize object creation. However, in the SwEngHTTPFactory example, the fact that the setInstance method exists means that instances of the AbstractHttpClient are meant to be created outside of the factory and passed as arguments. This contradicts the basic idea of a Factory. For the same reason, this is not a classic Singleton, as external instantation of AbstractHttpClient implies that there may be more simultaneous instances at a time.
        The SwengHttpClientFactory does not decide what type of HttpClient to return. An external module can instantiate any subclass of the AbstractHttpClient and set the instance to that object, and the SwengHttpClientFactory will return that instance.
        This implementation allows switching the concrete HttpClient programatically. This is, in fact, the reason for its implementation. It allows the test suite to dynamically switch the HttpClient that is used by your QuizApp implementation.

Suppose you implement an iPhone game that uses [AirPlay](http://en.wikipedia.org/wiki/AirPlay) mirroring to display the image both on the phone's screen and on an external display. Thus, your code needs to manage two screens. Is the display manager of your application a good use case for the Singleton Design Pattern?

- [x] Yes 
- [ ] No

> Even though there are two screens, they always display the same information. Thus, it makes sense to make the display manager a Singleton, because there is always a single image to render.

## Observer Design Pattern

Which of the following examples are likely to implement the Observer Design Pattern or the callback/listener? Select all that apply.

- [x] Push notifications 
- [x] android.widget.TextView and android.text.TextWatcher 
- [ ] java.util.concurrent.ThreadPoolExecutor.execute(java.lang.Runnable command)
- [x] Collaborative Office-like tools 

> Push notifications are sent when a change in the state of an app is relevant to its clients. Clients subscribe to the changes they are interested in.
      One can attach multiple TextWatchers to the same TextView, and they are all called whenever the text changes. 
      java.util.concurrent.ThreadPoolExecutor.execute is used to execute the Runnable command piece of code in the future, not to react to some events.
      Collaborative Office-like tools can implement the Observer design pattern to keep a document in sync across multiple devices. The devices can subscribe to a central repository that sends events at every change of the document state.

