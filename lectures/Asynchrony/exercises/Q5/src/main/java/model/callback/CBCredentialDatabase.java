package model.callback;

import model.CredentialDatabaseCallback;

public interface CBCredentialDatabase {

    /**
     * Add a user to the database
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param id           The user's id
     * @param callback     The callback
     */
    void addUser(String userName, String userPassword, int id, CredentialDatabaseCallback callback);

    /**
     * Authenticate a user in the database and query its personal data.
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param callback     The callback
     */
    void authenticate(String userName, String userPassword, CredentialDatabaseCallback callback);

}
