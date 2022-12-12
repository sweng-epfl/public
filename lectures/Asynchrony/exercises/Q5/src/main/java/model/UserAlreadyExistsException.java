package model;

public class UserAlreadyExistsException extends DatabaseException {
    public UserAlreadyExistsException(String userName) {
        super("User '" + userName + "' already exists in the database");
    }
}
