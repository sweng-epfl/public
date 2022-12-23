package model;

import model.data.Database;
import model.data.GradedBook;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a service that provides the trending {@link GradedBook}.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 * You CAN add new private constructors, methods, attributes, and nested classes to this class. <p>
 * You MUST NOT edit the parameters, return types, and checked exceptions of the existing methods and constructors. <p>
 * You MUST NOT edit the names of the existing methods and constructors. <p>
 * You MUST NOT rename this file. <p>
 * You MUST NOT delete this file. <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 */
public class TrendingService {
    private final Database database;
    private final Comparator<GradedBook> comparator = Comparator.comparing(GradedBook::getGrade).reversed();

    /**
     * Creates a new {@link TrendingService} with the given {@link Database}.
     *
     * @param database the {@link Database} to use.
     */
    public TrendingService(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }

        this.database = database;
    }

    /**
     * Returns the list of trending {@link GradedBook}. The list is sorted by their grade in descending order.
     *
     * @return a {@link CompletableFuture} that completes with the list of trending {@link GradedBook},
     * sorted by their grade in descending order.
     */
    public CompletableFuture<List<GradedBook>> getTrending() {
        return database.getAllBooks().thenApply(bs -> {
            var l = new ArrayList<>(bs);
            l.sort(comparator);
            return l;
        });
    }

    /**
     * Returns the list of the first "number" trending {@link GradedBook}. The list is sorted by their grade in descending order.
     *
     * @param number the number of books to return.
     * @return a {@link CompletableFuture} that completes with the list of trending {@link GradedBook},
     * sorted by their grade in descending order.
     * @throws IllegalArgumentException if the number is less than 0 or bigger than the total number of {@link GradedBook}.
     */
    public CompletableFuture<List<GradedBook>> getTrending(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("The number of trending books to get cannot be below zero");
        }
        return getTrending().thenApply(l -> {
            if (number > l.size()) {
                throw new IllegalArgumentException("The number of trending books to get cannot be greater than the total number of trending books");
            }

            // The check at the start of the method could also be checked here:
            /*
            if (number < 0) {
                throw new IllegalArgumentException("The number of trending books to get cannot be below zero");
            }
            */
            // However, this would be slower, since the error would only be returned after the list is known even though it is independent of the list

            return l.subList(0, number);
        });
    }

}
