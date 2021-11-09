package one;

public class Deadlift extends Exercise {
    private int weight;

    public Deadlift(int weight) {
        name = "deadlift";
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
