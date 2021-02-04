package ch.epfl.sweng;

import ch.epfl.sweng.database.DatabaseException;

/**
 * A database that stores {@link Student} instances. Students stored in this database can be
 * retrieved by their SCIPER. The mapping is one-to-one, i.e., a SCIPER maps to exaclty one student
 * and every student has exactly one associated SCIPER.
 *
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface StudentDatabase {

    /**
     * Pushes a new student in the database.
     * The student's SCIPER should be used as the key for the key/value mapping.
     * If a student with the same SCIPER is already present in the database,
     * then the currently stored value is replaced by the value passed as argument.
     *
     * @param student The student to add in the database.
     * @throws IllegalArgumentException Thrown if the argument is null.
     * @throws DatabaseException        Thrown if any other error occurs.
     */
    void put(Student student) throws DatabaseException;

    /**
     * Removes a student from the database.
     * The student that is removed should have the same SCIPER value as provided in the argument.
     *
     * @param sciper The SCIPER value of the student to remove from the database.
     * @throws IllegalArgumentException Thrown if the argument is null.
     * @throws DatabaseException        Thrown if no student match the provided SCIPER value.
     */
    void remove(String sciper) throws DatabaseException;

    /**
     * Retrieves a student from the database.
     * The queried student should have the same SCIPER value as provided in the argument.
     * If the database returns data that is corrupted (i.e., that cannot be
     * de-serialized), the appropriate exception should be thrown.
     *
     * @param sciper The searched student's SCIPER value.
     * @return The student whose SCIPER is equal to the argument or null if this student does
     * not exist.
     * @throws IllegalArgumentException Thrown if the argument is null.
     * @throws DatabaseException        Thrown if any other error occurs.
     */
    Student get(String sciper) throws DatabaseException;

}
