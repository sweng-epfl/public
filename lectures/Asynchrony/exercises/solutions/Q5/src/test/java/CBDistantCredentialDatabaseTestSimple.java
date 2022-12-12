import model.*;
import model.callback.CBDistantCredentialDatabase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CBDistantCredentialDatabaseTestSimple {

    // 1. Trying to authenticate a user that does not exist throws
    @Test
    public void tryToAuthUserThatDoesNotExistThrowsUnknownUser() throws Throwable {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        var callback = new Callback();
        db.authenticate("unknown", "user", callback);
        assertThrows(UnknownUserException.class, () -> callback.join(10));
    }

    // 2. Calling `addUser` on empty database will add the correct user
    @Test
    public void callAddUserOnEmptyDBAddsUserToDB() throws Throwable {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        var callback = new Callback();
        db.addUser("usrnm", "pwd", 1234, callback);
        var user = callback.join(10);
        assertThat(user.getUserName(), is("usrnm"));
        assertThat(user.getID(), is("1234"));
    }

    // 3. Calling `addUser` twice with the same user will fail
    @Test
    public void callAddUserTwiceWithSameCredentialsThrowsAlreadyExists() throws Throwable {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        var callback1 = new Callback();
        db.addUser("usrnm", "pwd", 1234, callback1);
        callback1.join(10);
        var callback2 = new Callback();
        db.addUser("usrnm", "pwd", 1234, callback2);
        assertThrows(UserAlreadyExistsException.class, () -> callback2.join(10));
    }

    // 4. Adding a user to the database and trying to `authenticate` the same user will yield the correct user
    @Test
    public void addUserAndAuthYieldCorrectUser() throws Throwable {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        var callback1 = new Callback();
        db.addUser("usrnm", "pwd", 1234, callback1);
        callback1.join(10);
        var callback2 = new Callback();
        db.authenticate("usrnm", "pwd", callback2);
        var user = callback2.join(10);
        assertThat(user.getUserName(), is("usrnm"));
        assertThat(user.getID(), is("1234"));
    }

    // 5. Adding a user to the database and trying to `authenticate` the same user with a wrong password will throw
    @Test
    public void addUserAndAuthWithWrongPwdThrowsInvalidCredentials() throws Throwable {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        var callback1 = new Callback();
        db.addUser("usrnm", "pwd", 1234, callback1);
        callback1.join(10);
        var callback2 = new Callback();
        db.authenticate("usrnm", "wrongpwd", callback2);
        assertThrows(InvalidCredentialException.class, () -> callback2.join(10));
    }

    /**
     * Helper class to block for a callback
     */
    private static class Callback implements CredentialDatabaseCallback {
        private final CompletableFuture<StudentUser> future = new CompletableFuture<>();

        @Override
        public void onSuccess(StudentUser user) {
            assertThat(future.isDone(), is(false));
            future.complete(user);
        }

        @Override
        public void onError(DatabaseException exception) {
            assertThat(future.isDone(), is(false));
            future.completeExceptionally(exception);
        }

        public StudentUser join(int timeoutInSeconds) throws Throwable {
            try {
                return future.orTimeout(timeoutInSeconds, TimeUnit.SECONDS).join();
            } catch (CompletionException e) {
                // makes testing easier
                throw e.getCause();
            }
        }
    }
}
