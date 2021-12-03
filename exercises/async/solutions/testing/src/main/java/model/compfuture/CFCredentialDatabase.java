package model.compfuture;

import model.AuthenticatedUser;

import java.util.concurrent.CompletableFuture;

public interface CFCredentialDatabase {

    /**
     * Add a user to the database
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param sciper       The user's sciper
     * @param callback     The callback
     * @return the authenticatd user or a completion exception containing the real exception
     */
    CompletableFuture<AuthenticatedUser> addUser(String userName, String userPassword, int sciper);

    /**
     * Authenticate a user in the database and query its personal data.
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param callback     The callback
     * @return the authenticatd user or a completion exception containing the real exception
     */
    CompletableFuture<AuthenticatedUser> authenticate(String userName, String userPassword);

}
