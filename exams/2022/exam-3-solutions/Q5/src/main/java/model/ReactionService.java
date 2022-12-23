package model;

import model.auth.AuthenticationService;
import model.data.Database;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a reaction service.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public class ReactionService {
    private final AuthenticationService authService;
    private final Database database;

    /**
     * Creates a new reaction service.
     *
     * @param authService the {@link AuthenticationService} to use
     * @param database    the {@link Database} to use
     */
    public ReactionService(AuthenticationService authService, Database database) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }
        if (authService == null) {
            throw new IllegalArgumentException("The authService cannot be null");
        }

        this.authService = authService;
        this.database = database;
    }

    /**
     * Adds a reaction to the book with the given title from the user currently authenticated.
     *
     * @param title the title of the book to react to
     * @param liked true if the user liked the book, false otherwise.
     * @return a future that completes when the reaction has been added or fails if the user is not authenticated.
     */
    private CompletableFuture<Void> addReaction(String title, boolean liked) {
        var user = authService.getAuthenticatedUser();

        if (user == null) {
            return CompletableFuture.failedFuture(new ReactionServiceException("User not logged in"));
        }

        return database.updateReaction(user.username(), title, liked)
                .thenCompose(v -> database.updateGrade(title, liked ? 1 : -1))
                .exceptionally(e -> {
                    throw new ReactionServiceException("Failed to add reaction", e);
                });
    }

    /**
     * Adds a like to the book with the given title from the user currently authenticated.
     *
     * @param title the title of the book to like
     * @return a future that completes when the like has been added or fails if the user is not authenticated.
     */
    public CompletableFuture<Void> like(String title) {
        return addReaction(title, true);
    }

    /**
     * Adds a dislike to the book with the given title from the user currently authenticated.
     *
     * @param title the title of the book to like
     * @return a future that completes when the dislike has been added or fails if the user is not authenticated.
     */
    public CompletableFuture<Void> dislike(String title) {
        return addReaction(title, false);
    }

}
