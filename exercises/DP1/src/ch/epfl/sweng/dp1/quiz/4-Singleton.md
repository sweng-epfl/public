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
-[ ] The class allows switching the concrete HttpClient returned
-[ ] The SwengHttpClientFactory decides what type of HttpClient to return

## Question 2
Suppose you implement an iPhone game that uses [AirPlay](https://en.wikipedia.org/wiki/AirPlay) mirroring to display the image both on the phone's screen and on an external display. Thus, your code needs to manage two screens. Is the display manager of your application a good use case for the Singleton Design Pattern?
- [ ] Yes
- [ ] No
