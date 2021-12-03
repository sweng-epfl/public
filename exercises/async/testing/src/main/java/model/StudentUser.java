package model;

/**
 * Represent a student user in the database
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class StudentUser implements AuthenticatedUser {

    private final String name;
    private final String sciper;

    /**
     * Construct a new student user.
     *
     * @param name   The student's name.
     * @param sciper The student's SCIPER as a String.
     */
    public StudentUser(String name, String sciper) {
        this.name = name;
        this.sciper = sciper;
    }

    @Override
    public String getUserName() {
        return this.name;
    }

    @Override
    public String getSciper() {
        return this.sciper;
    }
}
