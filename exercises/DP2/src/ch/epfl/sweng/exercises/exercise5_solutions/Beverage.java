package ch.epfl.sweng.exercises.exercise5_solutions;

public abstract class Beverage {
    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
