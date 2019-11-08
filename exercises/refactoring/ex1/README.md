# Identifying code smell :cheese:

A code that works is not necessarily good code. But how to identify if the code is good or not ? A good way is to learn to identify _code smell_. Examples of code smells include unreadable code, code that is too complex, code that doesn't follow good practices or code that is hard to maintain.

In this exercise your task is to identify some common code smells.

## Exercise 1.1

Consider this class representing a player in a video game. What's wrong ?

```java
public class Player {
    private int hitPoints;
    private String weaponName;
    private int weaponDamage;
    private int weaponRange;
    private boolean isRangedWeapon;
    
    public Player(int initialHitPoints, String weaponName,
        int weaponDamage, int weaponRange, boolean isRangedWeapon) {
        // initialize attributes
    }
    // other methods
}
```

## Exercise 1.2

Here's a method used for normalizing an array of `double`s. Do you see any issues ?

```java
public static double[] normalize(double[] data) {
    // compute mean
    double sum = 0;
    for (double d : data)
        sum += d;
    double mean = sum / data.length;
    
    // compute standart deviation
    double acc = 0;
    for (double d : data)
        acc += (d - mean) * (d - mean);
    double std = Math.sqrt(acc / data.length);
    
    // normalize
    double[] out = new double[data.length];
    for (int i = 0; i < out.length; i++)
        out[i] = (data[i] - mean) / std;
    
    return out;
}
```

## Exercise 1.3

Consider this method that computes the price of an order. What's wrong ?

```java
double price() {
  // Price consists of: base price - discount + shipping cost
  return quantity * itemPrice -
    Math.max(0, quantity - 500) * itemPrice * 0.05 +
    Math.min(quantity * itemPrice * 0.1, 100.0);
}
```

## Exercise 1.4

Consider these two classes: one representing a phone with a 10 digit number, composed of an area code, prefix and subscriber number. The other one represents it's owner, who has a method to print the number in a nicely formatted way. For example the number 1234567890 would be formatted as (123)456-7890. What's the issue here ?

```java
public class Phone {
    private final String unformattedNumber;
    // other attributes
    
    // constructor and other methods
    
    public String getAreaCode() {
        return unformattedNumber.substring(0,3);
    }
    
    public String getPrefix() {
        return unformattedNumber.substring(3,6);
    }
    
    public String getNumber() {
        return unformattedNumber.substring(6,10);
    }
}

public class Human {
    private Phone phone;
    // other attributes
    
    // constructor and other methods
    
    public String getPhoneNumber() {
        return "(" + 
        phone.getAreaCode() + ") " +
        phone.getPrefix() + "-" +
        phone.getNumber();
    }
}
```

* * *

Now that you've correctly identified the code smells, you can move on to [the second exercise](../ex2) in order to remove them !