package model;

/**
 * Exception class for blocked user errors
 */
public class BlockedUserException extends DatabaseException {

    /**
     * Construct a new blocked user exception
     * @param userName The username that caused the exception
     */
    public BlockedUserException(String userName) {
        super("User '" + userName + "' is blocked");
    }

}
