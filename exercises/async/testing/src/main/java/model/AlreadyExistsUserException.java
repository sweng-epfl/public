package model;

public class AlreadyExistsUserException extends DatabaseException {

    /**
     * Construct a new invalid credential exception
     *
     * @param userName The username that caused the exception
     */
    public AlreadyExistsUserException(String userName) {
        super("User '" + userName + "' already exists in the database");
    }

}
