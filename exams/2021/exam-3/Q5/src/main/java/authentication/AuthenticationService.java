package authentication;

import java.util.concurrent.CompletableFuture;

import session.Session;

/**
 * Interface for an authentication service.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface AuthenticationService {

    /**
     * Logs in the user with the provided credentials.
     */
    CompletableFuture<Session> login(String username, String password);

}
