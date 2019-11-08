# Identifying & removing code smells : Solutions

## Solution 2.1

We have what is called a [_data clump_](https://en.wikipedia.org/wiki/Data_clump). All the variables describing the weapon should be grouped together into a single object. It makes a lot more sense for a player to own a weapon rather than having a weapon name, damage, range, ... Therefor we extracted a new `Weapon` class.

```java
public class Weapon {
    private String name;
    private int damage;
    private int range;
    private boolean isRanged;
    
    public Weapon(String name, int damage,
                  int range, boolean isRanged) {
        // initialize attributes
    }
    
}

public class Player {
    private int hitPoints;
    private Weapon weapon;
    
    public Player(int initialHitPoints, Weapon startingWeapon) {
        // initialize attributes
    }
    // other methods
}
```

## Solution 2.2

This method could be considered as a _long method_. Even though it isn't that complicated and is well commented, it could be broken up into smaller parts. The comments inside the method can then be removed since the extracted methods' names are explicit enough. Note that here we could have created a `computeStd` that takes in the mean as an argument for better efficiency.

```java
private static double computeMean(double[] data) {
    double sum = 0;
    for (double d : data)
        sum += d;
    return sum / data.length;
}

private static double computeStd(double[] data) {
    double mean = computeMean(data);
    double acc = 0;
    for (double d : data)
        acc += (d - mean) * (d - mean);
    return Math.sqrt(acc / data.length);
}

public static double[] normalize(double[] data) {
    double mean = computeMean(data);
    double std = computeStd(data);
    double[] out = new double[data.length];
    for (int i = 0; i < out.length; i++)
        out[i] = (data[i] - mean) / std;
    return out;
}
```

## Solution 2.3

Here we had a line that was too long, making the code difficult to read, understand, debug and modify. We address this by breaking up the computation into the 3 parts from the comment describing the used price formula. It is now a lot more clearer what is going on and a lot easier to find a potential error.

```java
double price() {
  final double basePrice = quantity * itemPrice;
  final double quantityDiscount = Math.max(0, quantity - 500) * itemPrice * 0.05;
  final double shipping = Math.min(basePrice * 0.1, 100.0);
  return basePrice - quantityDiscount + shipping;
}
```

## Solution 2.4

This is a small example of a _feature envy_, where a class uses the methods of another class excessively. [Martin Fowler](https://en.wikipedia.org/wiki/Martin_Fowler_(software_engineer)) puts it like this:  
> _The whole point of objects is that they are a technique to package data with the processes used on that data. A classic smell is a method that seems more interested in a class other than the one it is in. The most common focus of the envy is the data._

Here the `Human` class uses 3 different methods of the `Phone` class to format the number, task that should be taken care of by the latter. Therefor we move the method to the correct class.

```java
public class Phone {
    private final String unformattedNumber;
    // other attributes
    
    // constructor and other methods
    
    private String getAreaCode() {
        return unformattedNumber.substring(0,3);
    }
    
    private String getPrefix() {
        return unformattedNumber.substring(3,6);
    }
    
    private String getNumber() {
        return unformattedNumber.substring(6,10);
    }
    
    public String getFormattedNumber() {
        return "(" + getAreaCode() + ") " +
                getPrefix() + "-" +
                getNumber();
    }
}

public class Human {
    private Phone phone;
    // other attributes
    
    // constructor and other methods
    
    public String getPhoneNumber() {
        return phone.getFormattedNumber();
    }
}
```
