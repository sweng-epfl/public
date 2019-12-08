package ex1;

import java.util.ArrayList;

abstract public class Pizza {
    protected String name;
    protected String dough;
    protected String sauce;
    protected ArrayList<String> toppings = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void prepare() {
        System.out.println("Preparing " + name);
    }

    public void bake() {
        System.out.println("Baking " + name);
    }

    public void cut() {
        System.out.println("Cutting " + name);
    }

    public void box() {
        System.out.println("Boxing " + name);
    }

    public String toString() {
        // code to display pizza name and ingredients
        StringBuffer display = new StringBuffer();
        display.append("---- " + name + " ----\n");
        display.append(dough + "\n");
        display.append(sauce + "\n");
        for (String topping : toppings) {
            display.append(topping + "\n");
        }
        return display.toString();
    }

    public void preparePizza(){
        this.prepare();
        this.bake();
        this.cut();
        this.box();
    }

    public static Pizza createCheesePizza() {
        Pizza pizza = new CheesePizza();
        pizza.preparePizza();
        return pizza;
    }

    public static Pizza createClamPizza() {
        Pizza pizza = new ClamPizza();
        pizza.preparePizza();
        return pizza;
    }

    public static Pizza createPepperoniPizza() {
        Pizza pizza = new PepperoniPizza();
        pizza.preparePizza();
        return pizza;
    }

    public static Pizza createVeggiePizza() {
        Pizza pizza = new VeggiePizza();
        pizza.preparePizza();
        return pizza;
    }
}

