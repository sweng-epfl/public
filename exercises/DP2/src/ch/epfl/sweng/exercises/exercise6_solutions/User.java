package ch.epfl.sweng.exercises.exercise6_solutions;

public class User {

    private String username;
    private String personalInfo;

    public User(String username) {
        this.username = username;
        this.personalInfo = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }
}
