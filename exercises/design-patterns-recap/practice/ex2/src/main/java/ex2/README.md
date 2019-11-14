# Exercise 2

## Part 1

In this exercise, you need to write the code for an assembly line of computers for a company called Pear. However, Pear produces several computers (namely the eWhackintosh, the eWhack Mini and the eWhack Pro). They also let the customer choose the quantity of DRAM, storage, the processor frequency, whether it has integrated graphics, whether it has USB-C ports, the color of the case, the screen resolution, and optional peripherals such as an HDMI port, an SD Card reader and a Jack audio port. The class is defined in [Computer.java](Computer.java).

However, a constructor call for such an object is rather long, unreadable and error-prone, as all parameters are Java base types:

```java
Computer pc1 = new Computer("eWhackintosh", 8192, 512, 3.5, true, true, 0xffffff, 1920, 1080, true, true, true);
```

Use the **builder design pattern** to make the code more manageable.

## Part 2

Take a look at the `toString()` method of [Computer.java](Computer.java). Why was `StringBuilder` used and how does it relate to the builder design pattern?