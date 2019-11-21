# DP & UI quiz

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

## Inheritance vs Proxy vs Adapter

Imagine you're writing an application that analyzes Facebook users' messages using natural language processing (NLP) in order to automatically discover the most important discussion topics. Your application uses two existing libraries: One library interfaces with the Facebook servers, extracts the content of a user post, encapsulates it into a `FacebookPost` object, and returns this object to the caller. The other library takes in an `NLPDocument` object, automatically extracts the most relevant topics from the respective document, and returns them to the caller.

Of the following four options, which is the one most effective way of building the desired application?

- [ ] Write an `NLPFacebookPost` class that subclasses `FacebookPost` and also implements `NLPDocument`'s interface, then change the NLP library to accept `NLPFacebookPost` objects instead of `NLPDocument` objects.
- [ ] Write a `FacebookPostNLPDocumentAdapter` class that adapts the interface of `FacebookPost` to the `NLPDocument` interface
- [ ] Write an `NLPDocumentFacebookPostAdapter` class that adapts the interface of `NLPDocument` to the `FacebookPost` interface
- [ ] Use the Proxy design pattern to make `FacebookPost` objects behave like `NLPDocument` objects

## MVC

The following pseudo-code is a sketch of an online students management application that uses the MVC pattern.

```java
class StudentsDatabase {
    List<Student> mStudents;

    //...

    List<Student> getStudents();
}
class StudentsManager implements WebPage {
    @Override
    public String toHtml() {
        //...
        for (Student s : mDb.getStudents()) {
            toReturn = toReturn + s.name + "<br/>";
        }
        return toReturn;
    }
}

class WebApplication {
    public String getRequest(String url) {
        //...
        WebPage page = new StudentsManager(...);
        //...
        return page.toHtml();
    }
}
```

Which of the following statements is (are) true?

- [ ] `StudentsDatabase` is the model, `StudentsManager` is the controller, and `WebApplication` is the the view.
- [ ] `StudentsDatabase` is the model, `StudentsManager` is the view, and `WebApplication` is the controller.
- [ ] `StudentsManager` is the model, `StudentsDatabase` is the view, and `StudentsManager` is the controller.
- [ ] This is not MVC, because `StudentsManager` must use a listener to be notified when the database changes.