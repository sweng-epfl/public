package model.mock;

import model.User;
import model.auth.AuthenticationService;

import java.util.concurrent.CompletableFuture;

public class MockAuthenticationService implements AuthenticationService {
    private User user;

    @Override
    public CompletableFuture<Void> login(String username, String password) {
        user = new User(username, password);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> logout() {
        user = null;
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public User getAuthenticatedUser() {
        return user;
    }
}
