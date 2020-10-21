# Exercise 1 (Factory)

You are the proud owner of a modern pizza shop, and you wrote the following code to make pizza (see [`Pizza.java`](src/main/java/ex1/Pizza.java) for what a pizza is):

```java
Pizza orderPizza() {
		Pizza pizza = new Pizza();
 
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
```

But, to be competitive, you need to be able to bake more than one type of pizza. So you add some code that determines the appropriate type of pizza and makes it:

```java
Pizza orderPizza(String type) {
		Pizza pizza;
 
 		if (type.equals("cheese")) {
 			pizza = new CheesePizza();
 		} else if (type.equals("greek") {
 			pizza = new GreekPizza();
 		} else if (type.equals("pepperoni") {
 			pizza = new PepperoniPizza();
 		}

		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
```

You realize that your competitors have added a couple of trendy pizzas to their menus -- Clam Pizza and Veggie Pizza -- so you add these items to your menu. Since your Greek Pizza doesn't sell well, you also decide to take that off the menu. So now you have [`CheesePizza.java`](src/main/java/ex1/CheesePizza.java), [`ClamPizza.java`](src/main/java/ex1/ClamPizza.java), [`PepperoniPizza.java`](src/main/java/ex1/PepperoniPizza.java), and [`VeggiePizza.java`](src/main/java/ex1/VeggiePizza.java).

Unfortunately, this kind of changes are likely to keep occurring, and it will be a mess to deal directly with these concrete class instantiations.

Your task is to make this code more elegant using the Factory design pattern.
