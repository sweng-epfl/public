package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a database result containing various fields
 * or an exception.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class AuthResult {

    private final Map<String, Object> data;
    private final DatabaseException exception;

    private AuthResult(DatabaseException exception, Map<String, Object> data) {
        this.exception = exception;
        this.data = Map.copyOf(data);
    }

    /**
     * Construct a result containing the provided data fields
     *
     * @param data The data as a dictionary of objects
     * @return A successful result with the provided data
     */
    public static AuthResult fromData(Map<String, Object> data) {
        return new AuthResult(null, data);
    }

    /**
     * Construct a result containing no data and the resulting exception
     *
     * @param exception The exception that resulted from the query
     * @return A failed result with the provided exception
     */
    public static AuthResult fromException(DatabaseException exception) {
        return new AuthResult(exception, new HashMap<>());
    }

    /**
     * Check if a result is successful (i.e., did not result in an exception)
     *
     * @return True if the result contains and no exception, false otherwise
     */
    public boolean isSuccessful() {
        return this.exception == null;
    }

    /**
     * @return The result's data fields as a dictionary of objects
     */
    public Map<String, Object> getData() {
        return Map.copyOf(this.data);
    }

    /**
     * @return The result's exception
     */
    public DatabaseException getException() {
        return this.exception;
    }

}
