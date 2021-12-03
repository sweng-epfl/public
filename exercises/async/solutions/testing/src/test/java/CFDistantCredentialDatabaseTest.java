import model.AlreadyExistsUserException;
import model.AuthenticatedUser;
import model.InvalidCredentialException;
import model.UnknownUserException;
import model.compfuture.CFDistantCredentialDatabase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CFDistantCredentialDatabaseTest {

    // 1. Trying to authenticate a user that does not exist throws
    @Test
    public void tryToAuthUserThatDoesNotExistThrowsUnknownUser() {
        CFDistantCredentialDatabase db = new CFDistantCredentialDatabase();
        AtomicBoolean hasRightType = new AtomicBoolean(false);
        CompletableFuture<AuthenticatedUser> cf = db.authenticate("any", "user").orTimeout(10, TimeUnit.SECONDS).exceptionally(e -> {
            hasRightType.set(e.getCause() instanceof UnknownUserException);
            throw new CompletionException(e);
        });
        assertThrows(ExecutionException.class, cf::get);
        assertThat(cf.isCompletedExceptionally(), is(true));
        assertThat(hasRightType.get(), is(true));
    }

    // 2. Calling `addUser` on empty database will add the correct user
    @Test
    public void callAddUserOnEmptyDBAddsUserToDB() throws ExecutionException, InterruptedException {
        CFDistantCredentialDatabase db = new CFDistantCredentialDatabase();
        CompletableFuture<AuthenticatedUser> cf = db.addUser("newuser", "pwd", 1234).orTimeout(10, TimeUnit.SECONDS);
        AuthenticatedUser user = cf.get();
        assertThat(user.getUserName(), is("newuser"));
        assertThat(user.getSciper(), is("1234"));
    }

    // 3. Calling `addUser` twice with the same user will fail
    @Test
    public void callAddUserTwiceWithSameCredentialsThrowsAlreadyExists() throws ExecutionException, InterruptedException {
        CFDistantCredentialDatabase db = new CFDistantCredentialDatabase();
        AtomicBoolean hasRightType = new AtomicBoolean(false);
        CompletableFuture<Void> cf = CompletableFuture.allOf(db.addUser("new", "user", 1234).orTimeout(10, TimeUnit.SECONDS),
                db.addUser("new", "user", 1234).orTimeout(10, TimeUnit.SECONDS)).exceptionally(e -> {
            hasRightType.set(e.getCause() instanceof AlreadyExistsUserException);
            throw new CompletionException(e);
        });
        assertThrows(ExecutionException.class, cf::get);
        assertThat(cf.isCompletedExceptionally(), is(true));
        assertThat(hasRightType.get(), is(true));
    }

    // 4. Adding a user to the database and trying to `authenticate` the same user will yield the correct user
    @Test
    public void addUserAndAuthYieldCorrectUser() throws ExecutionException, InterruptedException {
        CFDistantCredentialDatabase db = new CFDistantCredentialDatabase();
        CompletableFuture<AuthenticatedUser> cf = db.addUser("newuser", "pwd", 1234).orTimeout(10, TimeUnit.SECONDS);
        cf.get();
        cf = db.authenticate("newuser", "pwd").orTimeout(10, TimeUnit.SECONDS);
        AuthenticatedUser user = cf.get();
        assertThat(user.getUserName(), is("newuser"));
        assertThat(user.getSciper(), is("1234"));
    }

    // 5. Adding a user to the database and trying to `authenticate` the same user with a wrong password will throw
    @Test
    public void addUserAndAuthWithWrongPwdThrowsInvalidCredentials() {
        CFDistantCredentialDatabase db = new CFDistantCredentialDatabase();
        CompletableFuture<AuthenticatedUser> cf = db.addUser("newuser", "pwd", 1234).orTimeout(10, TimeUnit.SECONDS);
        cf.join();
        AtomicBoolean hasRightType = new AtomicBoolean(false);
        cf = db.authenticate("newuser", "wrongpwd").orTimeout(10, TimeUnit.SECONDS).exceptionally(e -> {
            hasRightType.set(e.getCause() instanceof InvalidCredentialException);
            throw new CompletionException(e);
        });
        assertThrows(ExecutionException.class, cf::get);
        assertThat(cf.isCompletedExceptionally(), is(true));
        assertThat(hasRightType.get(), is(true));
    }
}
