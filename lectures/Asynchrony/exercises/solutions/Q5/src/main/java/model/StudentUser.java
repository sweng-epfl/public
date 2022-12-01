package model;

/**
 * Represent a student user in the database
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class StudentUser {

    private final String name;
    private final String studentID;

    /**
     * Construct a new student user.
     *
     * @param name      The student's name.
     * @param studentID The student's SCIPER as a String.
     */
    public StudentUser(String name, String studentID) {
        this.name = name;
        this.studentID = studentID;
    }

    public String getUserName() {
        return this.name;
    }

    public String getID() {
        return this.studentID;
    }
}
