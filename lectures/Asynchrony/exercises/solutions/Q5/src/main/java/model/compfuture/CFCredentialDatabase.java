package model.compfuture;

import model.StudentUser;

import java.util.concurrent.CompletableFuture;

public interface CFCredentialDatabase {

    /**
     * Add a user to the database
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param id           The user's id
     * @return the authenticated user or a completion exception containing the real exception
     */
    CompletableFuture<StudentUser> addUser(String userName, String userPassword, int id);

    /**
     * Authenticate a user in the database and query its personal data.
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @return the authenticated user or a completion exception containing the real exception
     */
    CompletableFuture<StudentUser> authenticate(String userName, String userPassword);

}
