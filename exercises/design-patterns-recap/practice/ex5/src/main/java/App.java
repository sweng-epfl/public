public class App {
    public static void main(String[] args) {
        Espresso espresso = new Espresso();
        System.out.println(String.format("An %s costs %.2f$", espresso.description, espresso.cost()));
    }
}
