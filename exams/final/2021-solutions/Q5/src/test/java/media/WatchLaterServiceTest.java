package media;

import authentication.AuthenticationException;
import authentication.AuthenticationService;
import authentication.MockAuthenticationService;
import data.Database;
import data.MockDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!
 */
public class WatchLaterServiceTest {

    private Database database;
    private AuthenticationService authService;
    private WatchLaterService watchLaterService;

    @BeforeEach
    public void setup() {
        database = new MockDatabase();
        authService = new MockAuthenticationService();
        watchLaterService = new WatchLaterService(database, authService);
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionForNullDatabase() {
        assertThrows(IllegalArgumentException.class, () -> new WatchLaterService(null, authService));
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionForNullAuthService() {
        assertThrows(IllegalArgumentException.class, () -> new WatchLaterService(database, null));

    }

    @Test
    public void loginThrowsIllegalArgumentExceptionForNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> watchLaterService.login(null, "abc"));
    }

    @Test
    public void loginThrowsIllegalArgumentExceptionForNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> watchLaterService.login("abc", null));
    }

    @Test
    public void retrieveListThrowsExceptionForNullUsername() {
        assertThrows(CompletionException.class, () -> watchLaterService.retrieveList(null).join());
    }

    @Test
    public void retrieveListThrowsAuthenticationExceptionWhenUnauthenticated() {
        var exception = assertThrows(CompletionException.class, () -> watchLaterService.retrieveList("SwengFan").join());
        assertThat(exception.getCause(), isA(AuthenticationException.class));
    }

    @Test
    public void retrieveListReturnsAddedIDsWhenSameUserIsAuthenticated() {
        Set<Integer> retrieved = watchLaterService.login("SwengFan", "123456")
                .thenCompose(o -> watchLaterService.add("SwengFan", 1))
                .thenCompose(o -> watchLaterService.add("SwengFan", 19))
                .thenCompose(o -> watchLaterService.add("SwengFan", 15))
                .thenCompose(o -> watchLaterService.retrieveList("SwengFan"))
                .orTimeout(5, TimeUnit.SECONDS)
                .join();

        assertThat(retrieved, containsInAnyOrder(1, 15, 19));
    }

    @Test
    public void retrieveListThrowsWhenOtherUserIsAuthenticated() {
        login();
        assertThrows(CompletionException.class, () -> watchLaterService.retrieveList("NotAFan").join());
    }

    @Test
    public void addThrowsIllegalStateExceptionWhenUnauthenticated() {
        var exception =
                assertThrows(CompletionException.class, () -> watchLaterService.add("SwengFan", 1).join());
        assertThat(exception.getCause(), isA(IllegalStateException.class));
    }

    @Test
    public void addThrowsIllegalStateExceptionWhenOtherUserIsAuthenticated() {
        login();
        var exception =
                assertThrows(CompletionException.class, () -> watchLaterService.add("NotAFan", 1).join());
        assertThat(exception.getCause(), isA(IllegalStateException.class));
    }

    @Test
    public void removeThrowsIllegalStateExceptionWhenUnauthenticated() {
        var exception =
                assertThrows(CompletionException.class, () -> watchLaterService.remove("SwengFan", 1).join());
        assertThat(exception.getCause(), isA(IllegalStateException.class));
    }

    @Test
    public void removeRemovesItemFromListWhenAuthenticated() {
        Set<Integer> retrieved = watchLaterService.login("SwengFan", "123456")
                .thenCompose(o -> watchLaterService.add("SwengFan", 1))
                .thenCompose(o -> watchLaterService.add("SwengFan", 19))
                .thenCompose(o -> watchLaterService.add("SwengFan", 15))
                .thenCompose(o -> watchLaterService.remove("SwengFan", 19))
                .thenCompose(o -> watchLaterService.retrieveList("SwengFan"))
                .orTimeout(5, TimeUnit.SECONDS)
                .join();

        assertThat(retrieved, containsInAnyOrder(1, 15));
    }

    @Test
    public void removeThrowsIllegalStateExceptionWhenOtherUserIsAuthenticated() {
        login();
        var exception =
                assertThrows(CompletionException.class, () -> watchLaterService.remove("NotAFan", 1).join());
        assertThat(exception.getCause(), isA(IllegalStateException.class));
    }

    private void login() {
        watchLaterService.login("SwengFan", "123456").orTimeout(5, TimeUnit.SECONDS).join();
    }

}
