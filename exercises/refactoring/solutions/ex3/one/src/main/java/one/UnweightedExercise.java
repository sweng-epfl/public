package one;

public abstract class UnweightedExercise extends Exercise {

    public UnweightedExercise(String name) {
        super(name);
    }

    @Override
    public boolean requiresWeight() {
        return false;
    }
}
