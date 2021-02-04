package ch.epfl.sweng.mvc;

import ch.epfl.sweng.Student;

/**
 * Represents the view of the MVC application. It is responsible for creating the content that
 * will eventually be output to the user.
 *
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class AppView {

    /**
     * Creates a formatted string that represents an error message.
     * The output can directly be sent back to the user.
     *
     * @param msg The content of the error message.
     * @return The formatted error message.
     */
    public String getErrorMsg(String msg) {
        return String.format("-> Error: %s", msg);
    }

    /**
     * Creates a formatted string that represents a success message.
     * The output can directly be sent back to the user.
     *
     * @param msg The content of the success message.
     * @return The formatted success message.
     */
    public String getSuccessMsg(String msg) {
        return String.format("-> Success: %s", msg);
    }

    /**
     * Creates a formatted string that presents the result of a database query
     * for a specific SCIPER value.
     * The output can directly be sent back to the user.
     *
     * @param sciper   The SCIPER value that was used for the query.
     * @param duration The duration of the query in milliseconds.
     * @param student  The student query result, can be null.
     * @return THe formatted result message.
     */
    public String getStudentResultMsg(String sciper, long duration, Student student) {
        if (student != null) {
            return String.format("-> Success: Found student %s: %s [%s] (took %dms)",
                    student.sciper,
                    student.name,
                    student.faculty,
                    duration);
        }
        return String.format("-> Error: No student matches '%s' (took %dms)", sciper, duration);
    }
}
