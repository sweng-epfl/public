# Singleton Design Pattern

## Question 1
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
-[ ] This is a standard example of the original GOF factory
-[ ] This is a standard example of the static factory method
-[ ] This is a standard example of Singleton
-[x] The class allows switching the concrete HttpClient returned
-[ ] The SwengHttpClientFactory decides what type of HttpClient to return

**Explanation**
The example is not a standard factory method. This is because factory methods are meant to centralize object creation. However, in the SwEngHTTPFactory example, the fact that the setInstance method exists means that instances of the AbstractHttpClient are meant to be created outside of the factory and passed as arguments. This contradicts the basic idea of a Factory. For the same reason, this is not a classic Singleton, as external instantation of AbstractHttpClient implies that there may be more simultaneous instances at a time.
The SwengHttpClientFactory does not decide what type of HttpClient to return. An external module can instantiate any subclass of the AbstractHttpClient and set the instance to that object, and the SwengHttpClientFactory will return that instance.
This implementation allows switching the concrete HttpClient programatically. This is, in fact, the reason for its implementation. It allows the test suite to dynamically switch the HttpClient that is used by your QuizApp implementation.

## Question 2
Suppose you implement an iPhone game that uses [AirPlay](https://en.wikipedia.org/wiki/AirPlay) mirroring to display the image both on the phone's screen and on an external display. Thus, your code needs to manage two screens. Is the display manager of your application a good use case for the Singleton Design Pattern?
- [x] Yes
- [ ] No

**Explanation**
Even though there are two screens, they always display the same information. Thus, it makes sense to make the display manager a Singleton, because there is always a single image to render.
