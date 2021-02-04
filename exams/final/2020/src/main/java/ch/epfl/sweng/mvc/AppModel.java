package ch.epfl.sweng.mvc;

import ch.epfl.sweng.Student;
import ch.epfl.sweng.StudentDatabase;
import ch.epfl.sweng.database.DatabaseException;

/**
 * Represents the model of the MVC application. It is a middleware between the controller and
 * the database.
 *
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class AppModel {

    private final StudentDatabase database;

    /**
     * Constructs a new model.
     * It handles the provided database of students.
     *
     * @param database The database that this model handles.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public AppModel(StudentDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException();
        }

        this.database = database;
    }

    /**
     * Puts a student in the database. See {@link StudentDatabase#put(Student)} for
     * more information on its behaviour.
     *
     * @param student The student to add to the database.
     * @throws DatabaseException        Thrown if a database exception occurs.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public void put(Student student) throws DatabaseException {
        this.database.put(student);
    }

    /**
     * Removes a student from the database. See {@link StudentDatabase#remove(String)} for
     * more information on its behaviour.
     *
     * @param sciper The SCIPER of the student to remove from the database.
     * @throws DatabaseException        Thrown if no student matches the provided SCIPER value.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public void remove(String sciper) throws DatabaseException {
        this.database.remove(sciper);
    }

    /**
     * Gets a student from the database. See {@link StudentDatabase#get(String)} for
     * more information on its behaviour.
     * The outcome of the request is bundled along with the duration of the request
     * in milliseconds.
     *
     * @param sciper The searched student's SCIPER value.
     * @return The result containing the student and the request duration.
     * @throws DatabaseException        Thrown if an internal database error occurs.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public ModelGetResult get(String sciper) throws DatabaseException {
        long startTime = System.currentTimeMillis();
        Student student = this.database.get(sciper);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        return new ModelGetResult(duration, student);
    }
}
