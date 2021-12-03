package model;

/**
 * Exception class for invalid credential errors
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class InvalidCredentialException extends DatabaseException {

    /**
     * Construct a new invalid credential exception
     * @param userName The username that caused the exception
     */
    public InvalidCredentialException(String userName) {
        super("Invalid credentials for user '" + userName + "'");
    }

}
