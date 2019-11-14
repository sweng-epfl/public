# Exercise 9 (Advanced)

Another type of observers that you usually encounter are listeners. In this exercise, you’ve got a button that says “Should I do it?”.
 
 ```java
JButton button = new JButton("Should I do it?");
```

When you click on that button, two listeners (observers) get to answer the question in any way they want (i.e. each of them prints a message to the console). For instance, the first one prints `Don't do it` while the second one prints `Do it!`.

What you can do is to create two listeners which are subclasses of the ActionListener. Use the following documentation to implement these two Listeners: https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html

