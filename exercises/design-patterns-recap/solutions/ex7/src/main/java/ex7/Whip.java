package ex7;

public class Whip extends CondimentDecorator {

    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Milk";
    }

    @Override
    public double cost() {
        return 0.10 + beverage.cost();
    }
}
