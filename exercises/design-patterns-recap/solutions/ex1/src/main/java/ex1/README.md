# Exercise 1

You are now in charge of displaying some statistics such as the number of loans made each day. Currently, the statistics are stored in a `DataStore`. You are able to show these info on your `Phone` and computer `Screen` using the following code:  

```java
    dataStore.updateScreen(screen);
    dataStore.updatePhone(phone);
```
        
```java
    public void updatePhone(Phone phone) {
        phone.printToPhone(this.data);
    }

    public void updateScreen(Screen screen) {
        screen.display(this.data);
    }
``` 
When some data are updated, these changes will be shown on your `Phone` and `Screen`. This works well for now when you have only one `Phone` and one `Screen`.

The bank now wants to display these statistics on different kinds of `Screen` so that the bosses can monitor (e.g. your computer screen, the screen in the conference room, the screen in the cafeteria...). They also want these statistics to be shown on different kinds of `Phone` as well. 

Modify the code to accommodate these requirements by using a suitable design pattern.

### Solution

In this exercise, the solution is to use the Observer pattern.<br>
Here, the subject is the `DataStore` and the observers are the different kind of `Phone` and `Screen`. 
When the subject changes, we want the observers to be notified in order to update their content to display the newly updated data.