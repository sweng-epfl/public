package media;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import authentication.AuthenticationException;
import authentication.AuthenticationService;
import data.Database;
import session.Session;

/**
 * Service to manage "watch later" lists.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class WatchLaterService {

    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    private Database database;
    private Session currentSession;
    private AuthenticationService authService;

    public WatchLaterService(Database database, AuthenticationService authService) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }
        if (authService == null) {
            throw new IllegalArgumentException("The authService cannot be null");
        }

        this.database = database;
        this.authService = authService;
    }

    public CompletableFuture<Void> login(String username, String password) {
        if (username == null) {
            throw new IllegalArgumentException("username cannot be null");
        }
        if (password == null) {
            throw new IllegalArgumentException("password cannot be null");
        }
        return this.authService.login(username, password).thenApply((session -> {
            this.currentSession = session;
            return null;
        }));
    }

    public CompletableFuture<Set<Integer>> retrieveList(String username) {
        if (!checkUserRights(username)) {
            return CompletableFuture.failedFuture(new AuthenticationException(UNAUTHORIZED_ACCESS));
        }
        return database.retrieveList(username);
    }

    public CompletableFuture<Void> add(String username, int videoId) {
        if (!checkUserRights(username)) {
            return CompletableFuture.failedFuture(new IllegalStateException(UNAUTHORIZED_ACCESS));
        }
        return database.add(username, videoId);
    }

    public CompletableFuture<Void> remove(String username, int videoId) {
        if (!checkUserRights(username)) {
            return CompletableFuture.failedFuture(new IllegalStateException(UNAUTHORIZED_ACCESS));
        }
        return database.remove(username, videoId);
    }

    private boolean checkUserRights(String username) {
        return username != null && this.currentSession != null && this.currentSession.isAuthenticated(username);
    }
}
