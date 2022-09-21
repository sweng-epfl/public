package data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a database.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface Database {

    /**
     * Gets all video IDs in the given user's list.
     */
    CompletableFuture<Set<Integer>> retrieveList(String username);

    /**
     * Adds a video by ID to the given user's list.
     */
    CompletableFuture<Void> add(String username, int videoId);

    /**
     * Removes a video by ID from the given user's list.
     */
    CompletableFuture<Void> remove(String username, int videoId);

}
