package ch.epfl.sweng.mvc;

import ch.epfl.sweng.Student;

/**
 * Represents the result of a student database query. This class encapsulates the raw
 * database result along with the request duration in milliseconds.
 *
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class ModelGetResult {

    /**
     * Duration of the query in milliseconds.
     */
    public final long duration;

    /**
     * The query result.
     * Can be null if the query did not found any matching student.
     */
    public final Student student;

    public ModelGetResult(long duration, Student student) {
        this.duration = duration;
        this.student = student;
    }
}
