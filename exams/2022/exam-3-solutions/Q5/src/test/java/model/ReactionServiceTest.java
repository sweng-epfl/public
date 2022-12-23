package model;

import model.auth.AuthenticationService;
import model.data.Database;
import model.data.GradedBook;
import model.data.Reaction;
import model.mock.MockAuthenticationService;
import model.mock.MockDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class for the tests of {@link ReactionService}.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 * You MUST use this file to test {@link ReactionService}                   <p>
 * You CAN add new methods, variables and nested classes to this class.     <p>
 * You MUST NOT rename this file.                                           <p>
 * You MUST NOT delete this file.                                           <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 */
class ReactionServiceTest {
    private Database database;
    private AuthenticationService authenticationService;
    private ReactionService reactionService;
    // Source: https://myanimelist.net/topbook.php
    private final Set<GradedBook> bookSet = Set.of(
            new GradedBook("JoJo no Kimyou na Bouken Part 7: Steel Ball Run", 928),
            new GradedBook("Vagabond", 920),
            new GradedBook("Berserk", 946),
            new GradedBook("Slam Dunk", 907),
            new GradedBook("One Piece", 921),
            new GradedBook("Oyasumi Punpun", 903),
            new GradedBook("Monster", 914),
            new GradedBook("Grand Blue", 902),
            new GradedBook("Fullmetal Alchemist", 904),
            new GradedBook("Vinland Saga", 900)
    );

    @BeforeEach
    public void setup() {
        database = new MockDatabase(bookSet);
        authenticationService = new MockAuthenticationService();
        reactionService = new ReactionService(authenticationService, database);
    }

    private void login() {
        authenticationService.login("Sweng", "1").orTimeout(5, TimeUnit.SECONDS).join();
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionForNullDatabase() {
        assertThrows(IllegalArgumentException.class, () -> new ReactionService(authenticationService, null));
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionForNullAuthService() {
        assertThrows(IllegalArgumentException.class, () -> new ReactionService(null, database));
    }

    @Test
    public void likeThrowsIllegalArgumentExceptionWhenNoUserAreAuthenticated() {
        var exception = assertThrows(CompletionException.class, () -> reactionService.like("").join());
        assertThat(exception.getCause(), isA(ReactionServiceException.class));
    }

    @Test
    public void dislikeThrowsIllegalArgumentExceptionWhenNoUserAreAuthenticated() {
        var exception = assertThrows(CompletionException.class, () -> reactionService.dislike("").join());
        assertThat(exception.getCause(), isA(ReactionServiceException.class));
    }

    @Test
    public void likeThrowsReactionServiceExceptionWhenMediumDoesNotExist() {
        login();

        var exception = assertThrows(CompletionException.class, () -> reactionService.like("").join());
        assertThat(exception.getCause(), isA(ReactionServiceException.class));
    }

    @Test
    public void dislikeThrowsReactionServiceExceptionWhenMediumDoesNotExist() {
        login();

        var exception = assertThrows(CompletionException.class, () -> reactionService.like("").join());
        assertThat(exception.getCause(), isA(ReactionServiceException.class));
    }

    @Test
    public void likeAddsReactionsToTheDatabase() {
        var username = "Sweng";
        var titles = List.of("Berserk",  "One Piece", "Slam Dunk");

        var reactions = authenticationService.login(username, "1")
                .thenCompose(v -> reactionService.like(titles.get(0)))
                .thenCompose(v -> reactionService.like(titles.get(1)))
                .thenCompose(v -> reactionService.like(titles.get(2)))
                .thenCompose(v -> database.getReactionsByUser(username))
                .orTimeout(5, TimeUnit.SECONDS)
                .join();

        var reactionTitles = reactions.stream()
                .map(Reaction::title)
                .toList();

        assertThat(reactionTitles, containsInAnyOrder(titles.toArray()));
    }

    @Test
    public void dislikeAddsReactionsToTheDatabase() {
        var username = "Sweng";
        var titles = List.of("Berserk",  "One Piece", "Slam Dunk");

        var reactions = authenticationService.login(username, "1")
                .thenCompose(v -> reactionService.dislike(titles.get(0)))
                .thenCompose(v -> reactionService.dislike(titles.get(1)))
                .thenCompose(v -> reactionService.dislike(titles.get(2)))
                .thenCompose(v -> database.getReactionsByUser(username))
                .orTimeout(5, TimeUnit.SECONDS)
                .join();

        var reactionTitles = reactions.stream()
                .map(Reaction::title)
                .toList();

        assertThat(reactionTitles, containsInAnyOrder(titles.toArray()));
    }

    @Test
    public void likeAddsReactionToTheDatabaseThatAreLiked() {
        var username = "Sweng";
        var title = "One Piece";

        var reactions = authenticationService.login(username, "1")
                .thenCompose(v -> reactionService.like(title))
                .thenCompose(v -> database.getReactionsByUser(username))
                .orTimeout(5, TimeUnit.SECONDS)
                .join();

        reactions.forEach(r -> assertThat(r.liked(), is(true)));

    }

    @Test
    public void dislikeAddsReactionToTheDatabaseThatAreNotLiked() {
        var username = "Sweng";
        var title = "One Piece";

        var reactions = authenticationService.login(username, "1")
                .thenCompose(v -> reactionService.dislike(title))
                .thenCompose(v -> database.getReactionsByUser(username))
                .orTimeout(5, TimeUnit.SECONDS)
                .join();

        reactions.forEach(r -> assertThat(r.liked(), is(false)));
    }
}