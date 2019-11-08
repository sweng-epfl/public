package one;

public class Benchpress extends Exercise {
    private int weight;

    public Benchpress(int weight) {
        name = "bench press";
        this.weight = weight;
    }

    public boolean requiresWeights() {
        return true;
    }

    public int getWeight() {
        return weight;
    }

    public void doExercise() {
        System.out.println("Wow, I totally just did a " + name);
    }
}
