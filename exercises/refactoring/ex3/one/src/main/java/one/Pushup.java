package one;

public class Pushup extends Exercise {
    public Pushup() {
        name = "push-up";
    }

    public boolean requiresWeights() {
        return false;
    }

    public void doExercise() {
        System.out.println("Wow, I totally just did a " + name);
    }
}
