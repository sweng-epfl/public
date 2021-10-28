public class App {
    public static void main(String[] args) {
        Espresso espresso = new Espresso();
        CondimentDecorator espressoWithSteamedMilk = new SteamedMilk(espresso);
        System.out.println(String.format("An %s costs %.2f$", espressoWithSteamedMilk.getDescription(), espressoWithSteamedMilk.cost()));
    }
}
