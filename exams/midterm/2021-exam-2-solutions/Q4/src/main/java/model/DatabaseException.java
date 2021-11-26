package model;

/**
 * Base exception class for database internal errors
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public abstract class DatabaseException extends Exception {

    /**
     * Construct a new database exception
     * @param message The error message of the exception
     */
    public DatabaseException(String message) {
        super(message);
    }

}
