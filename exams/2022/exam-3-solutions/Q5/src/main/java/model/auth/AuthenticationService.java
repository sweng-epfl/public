package model.auth;

import model.User;

import java.util.concurrent.CompletableFuture;

/**
 * Represents an authentication service.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public interface AuthenticationService {
    /**
     * Authenticates the user with the given username and password.
     * @param username the username of the user to authenticate
     * @param password the password of the user to authenticate
     * @return a {@link CompletableFuture} that completes when the user has been authenticated or fails if the authentication failed.
     */
    CompletableFuture<Void> login(String username, String password);

    /**
     * Logs out the currently authenticated user.
     * @return a {@link CompletableFuture} that completes when the user has been logged out or if nobody was logged in.
     */
    CompletableFuture<Void> logout();

    /**
     * Returns the currently authenticated {@link User}.
     * @return the currently authenticated {@link User} or null if no {@link User} is authenticated.
     */
    User getAuthenticatedUser();
}
