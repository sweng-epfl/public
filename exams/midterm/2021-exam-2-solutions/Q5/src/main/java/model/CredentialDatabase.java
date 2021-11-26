package model;

/**
 * Interface for credential database classes
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface CredentialDatabase {

    /**
     * Authenticate a user in the database and query its personal data.
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @return The authenticated user
     * @throws DatabaseException Thrown when the user cannot get authenticated
     */
    AuthenticatedUser authenticate(String userName, String userPassword) throws DatabaseException;

}
