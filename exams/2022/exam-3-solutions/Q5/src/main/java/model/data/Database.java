package model.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Represents an interface for a database.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public interface Database {

    /**
     * Return all the {@link GradedBook} from the database.
     *
     * @return a {@link CompletableFuture} containing the set of all the {@link GradedBook}.
     */
    CompletableFuture<Set<GradedBook>> getAllBooks();

    /**
     * Return the {@link GradedBook} with the given title.
     *
     * @param title the title of the book.
     * @return a {@link CompletableFuture} containing the book with the given title.
     */
    CompletableFuture<GradedBook> getByTitle(String title);

    /**
     * Updates the grade of the book with the given title.
     *
     * @param title  the title of the book.
     * @param update the value that will be added to the grade.
     * @return a {@link CompletableFuture} that completes when the grade has been updated or fails if the book does not exist.
     */
    CompletableFuture<Void> updateGrade(String title, int update);

    /**
     * Updates the reaction of the user with the given username to the book with the given title.
     *
     * @param username the username of the user.
     * @param title    the title of the book.
     * @param liked    true if the user liked the book, false otherwise.
     * @return a {@link CompletableFuture} that completes when the reaction has been updated or fails if the book does not exist.
     */
    CompletableFuture<Void> updateReaction(String username, String title, boolean liked);

    /**
     * Gets all the {@link Reaction}s of the user with the given username.
     *
     * @param username the username of the user.
     * @return a {@link CompletableFuture} containing the set of {@link Reaction} of the user.
     */
    CompletableFuture<Set<Reaction>> getReactionsByUser(String username);
}
