package model;

import model.data.Database;
import model.data.GradedBook;
import model.mock.MockDatabase;
import model.mock.NonStoppableFuture;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class for the tests of {@link TrendingService}.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 * You MUST use this file to test {@link TrendingService}                   <p>
 * You CAN add new methods, variables and nested classes to this class.     <p>
 * You MUST NOT rename this file.                                           <p>
 * You MUST NOT delete this file.                                           <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 */
class TrendingServiceTest {
    // Source: https://myanimelist.net/topbook.php
    private final List<GradedBook> sortedBook = List.of(
            new GradedBook("Berserk", 946),
            new GradedBook("JoJo no Kimyou na Bouken Part 7: Steel Ball Run", 928),
            new GradedBook("One Piece", 921),
            new GradedBook("Vagabond", 920),
            new GradedBook("Monster", 914),
            new GradedBook("Slam Dunk", 907),
            new GradedBook("Fullmetal Alchemist", 904),
            new GradedBook("Oyasumi Punpun", 903),
            new GradedBook("Grand Blue", 902),
            new GradedBook("Vinland Saga", 900)
    );
    private Database database;
    private TrendingService trendingService;

    @BeforeEach
    public void setup() {
        database = new TestDatabase(new HashSet<>(sortedBook));
        trendingService = new TrendingService(database);
    }

    @Test
    @DisplayName("`getTrending` returns all books, sorted")
    public void getTrendingReturnsAllBooksSorted() {
        var books = trendingService.getTrending().orTimeout(2, TimeUnit.SECONDS).join();
        assertThat(books, containsInRelativeOrder(sortedBook.toArray()));
    }

    @Test
    @DisplayName("`getTrending` on an empty database returns the empty list")
    public void getTrendingOnEmptyDatabaseReturnsEmptyList() {
        var emptyTrendingService = new TrendingService(new MockDatabase(Collections.emptySet()));
        var books = emptyTrendingService.getTrending().orTimeout(2, TimeUnit.SECONDS).join();
        assertThat(books, is(Collections.emptyList()));
    }

    @Test
    @DisplayName("`getTrending` throws an `IllegalArgumentException` if the given number is bigger than the list size")
    public void getTrendingThrowsIllegalArgumentExceptionIfNumberBiggerThanListSize() {
        var error = assertThrows(
                CompletionException.class, () ->
                        trendingService.getTrending(sortedBook.size() + 1).orTimeout(2, TimeUnit.SECONDS).join()
        );

        assertThat(error.getCause(), instanceOf(IllegalArgumentException.class));
    }

    @Test
    @DisplayName("`getTrending` throws an `IllegalArgumentException` if the given number is below 0")
    public void getTrendingThrowsIllegalArgumentExceptionIfNumberBelowZero() {
        var error = assertThrows(
                Exception.class, () ->
                        trendingService.getTrending(-1).orTimeout(2, TimeUnit.SECONDS).join()
        );

        // Here we use this if-statement to check both possible implementations of the error handling;
        // in a real codebase and not an exam, the team would decide on the convention and only test for the chosen variant
        if (error instanceof CompletionException) {
            assertThat(error.getCause(), instanceOf(IllegalArgumentException.class));
        } else {
            assertThat(error, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    @DisplayName("`getTrending` given a number of books returns that number of top trending books")
    public void getTrendingWithNumberReturnsSortedSublist() {
        var number = 5;
        var books = trendingService.getTrending(number).orTimeout(2, TimeUnit.SECONDS).join();
        assertThat(books.size(), is(number));
        assertThat(books, containsInRelativeOrder(sortedBook.subList(0, number).toArray()));
    }

    @Test
    @DisplayName("`getTrending` given the size of the database as number returns all books, sorted")
    public void getTrendingWithNumberTheSizeOfTheDatasetReturnsEntireList() {
        var books = trendingService.getTrending(sortedBook.size()).orTimeout(2, TimeUnit.SECONDS).join();
        assertThat(books, containsInRelativeOrder(sortedBook.toArray()));
    }

    @Test
    @DisplayName("`getTrending` given 0 as argument returns the empty list")
    public void getTrendingWithZeroAsArgumentReturnsEmptyList() {
        var number = 0;
        var books = trendingService.getTrending(number).orTimeout(2, TimeUnit.SECONDS).join();
        assertThat(books, is(Collections.emptyList()));
    }

    /**
     * Special database returning futures that check the implementation doesn't break asynchrony.
     */
    private static class TestDatabase extends MockDatabase {
        private final Set<GradedBook> bookSet;

        public TestDatabase(Set<GradedBook> bookSet) {
            super(bookSet);
            this.bookSet = bookSet;
        }

        @Override
        public CompletableFuture<Set<GradedBook>> getAllBooks() {
            CompletableFuture<Set<GradedBook>> future = new NonStoppableFuture<>();
            future.complete(bookSet);
            return future;
        }
    }
}
