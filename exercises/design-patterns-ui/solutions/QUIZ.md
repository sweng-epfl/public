# DP & UI quiz

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

## Inheritance vs Proxy vs Adapter

- [ ] Write an `NLPFacebookPost` class that subclasses `FacebookPost` and also implements `NLPDocument`'s interface, then change the NLP library to accept `NLPFacebookPost` objects instead of `NLPDocument` objects.
- [x] Write a `FacebookPostNLPDocumentAdapter` class that adapts the interface of `FacebookPost` to the `NLPDocument` interface
- [ ] Write an `NLPDocumentFacebookPostAdapter` class that adapts the interface of `NLPDocument` to the `FacebookPost` interface
- [ ] Use the Proxy design pattern to make `FacebookPost` objects behave like `NLPDocument` objects

> `FacebookPost` and `NLPDocument` serve similar purposes in this context: they encapsulate the content of a Facebook user post. However, the interface for accessing this content is different in the two classes.
>
> The correct choice is #2, namely to use the Adapter design pattern to make a `FacebookPost` object be "digestable" by the NLP library. No functionality needs to be modified, just the interface used to get to the content of the Facebook post.
>
> Option #1 is incorrect, and the biggest red flag is the modification of the NLP library. The purpose of libraries is to encourage code reuse so, if a library needs to be changed, then either the library is poorly designed or the application chose the wrong library for the job. Option #3 reverses the direction in which the interface is adapted, and so the NLP library cannot be used to analyzed Facebook posts, which defeats the very point of the application. Option #4 is also wrong, because proxies do not help classes with different interfaces work with each other but rather create surrogate objects that enable the communication between clients and servers.

## MVC

- [ ] `StudentsDatabase` is the model, `StudentsManager` is the controller, and `WebApplication` is the the view.
- [x] `StudentsDatabase` is the model, `StudentsManager` is the view, and `WebApplication` is the controller.
- [ ] `StudentsManager` is the model, `StudentsDatabase` is the view, and `StudentsManager` is the controller.
- [ ] This is not MVC, because `StudentsManager` must use a listener to be notified when the database changes.

**Explanation:**
The `StudentsDatabase` is the model; it stores the data that is displayed in the `StudentsManager` view. The controller `WebApplication` selects what data to display, based on the request URL received from the user.