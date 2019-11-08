# Removing code smell

Good job on identifying the code smells, now it's time to remove them !
You can either directly edit this file or copy the code into an IDE / text editor.
Write down which method(s) you used to refactor the code.
Note that parts of the code are omitted as they aren't relevant for the exercise.

## Exercise 2.1

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

## Exercise 2.2

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

## Exercise 2.3

```java
double price() {
  // Price consists of: base price - discount + shipping cost
  return quantity * itemPrice -
    Math.max(0, quantity - 500) * itemPrice * 0.05 +
    Math.min(quantity * itemPrice * 0.1, 100.0);
}
```

## Exercise 2.4

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

Good job, now your code is smell-free !