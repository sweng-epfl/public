# Exercise 9 (Advanced)

One type of observers that you often encounter are listeners. In this exercise, you have a button that says “Should I do it?”.
 
 ```java
JButton button = new JButton("Should I do it?");
```

When you click on that button, two listeners (observers) get to answer the question in any way they want (i.e. each of them prints a message to the console). For instance, the first one prints `Don't do it` while the second one prints `Do it!`.

How would you implement this? One possibility is to create two listeners that subclass the [ActionListener](https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html).

