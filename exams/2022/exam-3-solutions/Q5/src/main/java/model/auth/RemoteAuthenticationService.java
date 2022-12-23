package model.auth;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Represents an authentication service.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public class RemoteAuthenticationService implements AuthenticationService {
    private final Map<String, User> users;
    private User currentUser;

    /**
     * Creates a new remote authentication service.
     */
    public RemoteAuthenticationService() {
        users = new HashMap<>();
        users.put("Sweng", new User("Sweng", "1"));
        currentUser = null;
    }

    @Override
    public CompletableFuture<Void> login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            if (users.containsKey(username) && users.get(username).password().equals(password)) {
                currentUser = users.get(username);
                return null;
            }
            throw new IllegalArgumentException("Failed to authenticate user: " + username);
        });
    }

    @Override
    public CompletableFuture<Void> logout() {
        return CompletableFuture.supplyAsync(() -> {
            currentUser = null;
            return null;
        });
    }

    @Override
    public User getAuthenticatedUser() {
        return currentUser;
    }

}
