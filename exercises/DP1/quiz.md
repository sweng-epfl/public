## Design patterns: The basics

Which of the following could be considered an abuse of the Exceptions design pattern? Select all that apply.

- [ ] Throwing a RuntimeException when the error is caused by a programmer mistake
- [ ] Throwing a RuntimeException in order to break out of several nested loops 
- [ ] Throwing an EmptyListException in the size() method of a list class, if the list is empty 
- [ ] Throwing an IOException in a network socket class if the connection is dropped

Which of the following are advantages of inheritance over encapsulation? Select all that apply.

- [ ] Inheritance reduces the amount of code coupling
- [ ] Inheritance better prevents code duplication 
- [ ] Inheritance improves system performance
- [ ] Inheritance simplifies system testing
- [ ] Inheritance enables the use of design patterns, while encapsulation does not

## Factory Method Design Pattern

What Object-Oriented Progamming feature makes the original GOF Factory Method pattern possible? Select one.

- [ ] Polymorphism 
- [ ] Encapsulation
- [ ] Interfaces
- [ ] Static methods

Which of the following statements are true? Select all that apply.

- [ ] Both the Static Factory Method and original GOF Factory Method are meant to control the number of instances that exist in a system
- [ ] The GOF Factory Method can return different types of objects (subtypes of a common base class), while the Static Factory Method returns the same type of object 
- [ ] The Static Factory Method and original GOF Factory Method serve different purposes 
- [ ] The Static Factory Method and original GOF Factory Method are two alternative implementations of the same concept
- [ ] In the original GOF Factory Method, the "createObject" method that instantiates an member of a class should never be static 

## Abstract Factory Design Pattern

What are the tradeoffs of the Abstract Factory Pattern? Select all that apply.

- [ ] It's hard to add or remove products 
- [ ] It's hard to add or remove factories
- [ ] It's hard to switch between product families
- [ ] It's hard to write unit tests

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
- [ ] The class allows switching the concrete HttpClient returned 
- [ ] The SwengHttpClientFactory decides what type of HttpClient to return

Suppose you implement an iPhone game that uses [AirPlay](http://en.wikipedia.org/wiki/AirPlay) mirroring to display the image both on the phone's screen and on an external display. Thus, your code needs to manage two screens. Is the display manager of your application a good use case for the Singleton Design Pattern?

- [ ] Yes 
- [ ] No

## Observer Design Pattern

Which of the following examples are likely to implement the Observer Design Pattern or the callback/listener? Select all that apply.

- [ ] Push notifications 
- [ ] android.widget.TextView and android.text.TextWatcher 
- [ ] java.util.concurrent.ThreadPoolExecutor.execute(java.lang.Runnable command)
- [ ] Collaborative Office-like tools 

