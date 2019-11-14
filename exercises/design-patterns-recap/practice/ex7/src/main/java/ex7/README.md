# Exercise 7

Assume you are operating a coffee shop that offers the following beverages: 
`HouseBlend`, `DarkRoast`, etc. 
For tracking the beverages, you created an `abstract class Beverage`
that is extended by specific beverage classes. Furthermore, each beverage
includes a `description` and a `cost` as seen in the figure below.

![](classes.png)

You decided to add different condiments in your coffees, 
such as steamed milk, whipped milk, soy, etc. 
You could introduce these condiments in your code by introducing classes such as
 `DarkRoastWithSteamedMilk`, `DecafWithWhippedMilk`, etc. 
 Among others, such an approach is not satisfactory, since it would bloat our codebase with extra classes.

Therefore, we are going to use the **decorator pattern**. Fill up the remaining code or create more classes if needed
so you pass from the `CafeShopTest` class.

Use the following prices:

![](prices.png)


Try to understand what happens when we calculate some cost, 
e.g., `new WhippedMilk(new Soy(new Steamed Milk(new DarkRoast()))).cost()`.


 