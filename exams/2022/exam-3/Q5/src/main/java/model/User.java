package model;

import static java.util.Objects.requireNonNull;

/**
 * Represents a record user with a username and a password.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public record User(String username, String password) {
    /**
     * Creates a new user.
     * @param username the username of the user
     * @param password the password of the user
     */
    public User {
        requireNonNull(username);
        requireNonNull(password);
    }
}
