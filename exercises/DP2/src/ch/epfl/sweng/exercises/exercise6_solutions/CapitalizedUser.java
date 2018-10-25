package ch.epfl.sweng.exercises.exercise6_solutions;

public class CapitalizedUser extends User {

    public CapitalizedUser(User user) {
        super(user.getUsername().toUpperCase());
    }
}
