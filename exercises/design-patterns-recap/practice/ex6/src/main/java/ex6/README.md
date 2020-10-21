# Exercise 6 (Iterator)

In this exercise, you are provided with a minimal implementation of a linked list. We can create a new linked list and add new elements the following way:

```java
    MyLinkedList<String> names = new MyLinkedList<>();
    names.add("Julian");
    names.add("Oscar");
    names.add("Steve");
    names.add("Roger");
```

However, it is not yet possible to iterate through this list to print all elements. i.e. for the moment, the following snippet yields an error.

```java
    for(String name : names){
        System.out.println(name);
    }
``` 

This is where the **Iterator** Design Pattern comes into action. In Java, for this piece of code to work, our linked list should implement the ```Iterable```  interface and needs to override the ```iterator``` method.

Your task is to make this implementation such that the application can print these 4 names using this for-each loop.