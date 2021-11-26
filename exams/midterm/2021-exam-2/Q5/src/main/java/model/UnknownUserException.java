package model;

/**
 * Exception class for unknown user errors
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class UnknownUserException extends DatabaseException {

    /**
     * Construct a new unknown user exception
     * @param userName The username that caused the exception
     */
    public UnknownUserException(String userName) {
        super("Unknown user '" + userName + "'");
    }

}
