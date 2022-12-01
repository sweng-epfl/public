import model.*;
import model.callback.CBDistantCredentialDatabase;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CBDistantCredentialDatabaseTestSimple {

    // 1. Trying to authenticate a user that does not exist throws
    @Test
    public void tryToAuthUserThatDoesNotExistThrowsUnknownUser() throws InterruptedException {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        CallbackWait callback = new CallbackWait();
        db.authenticate("unknown", "user", callback);
        assertThat(callback.waitOnNotify(10), is(true));
        assertThat(callback.users, is(empty()));
        assertThat(callback.dbExceptions.size(), is(1));
        assertThat(callback.dbExceptions.element(), instanceOf(UnknownUserException.class));
    }

    // 2. Calling `addUser` on empty database will add the correct user
    @Test
    public void callAddUserOnEmptyDBAddsUserToDB() throws InterruptedException {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        CallbackWait callback = new CallbackWait();
        db.addUser("usrnm", "pwd", 1234, callback);
        assertThat(callback.waitOnNotify(10), is(true));
        assertThat(callback.dbExceptions, is(empty()));
        assertThat(callback.users.size(), is(1));
        assertThat(callback.users.element().getUserName(), is("usrnm"));
        assertThat(callback.users.element().getID(), is("1234"));
    }

    // 3. Calling `addUser` twice with the same user will fail
    @Test
    public void callAddUserTwiceWithSameCredentialsThrowsAlreadyExists() throws InterruptedException {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        CallbackWait callback = new CallbackWait();
        db.addUser("usrnm", "pwd", 1234, callback);
        assertThat(callback.waitOnNotify(10), is(true));
        callback = new CallbackWait();
        db.addUser("usrnm", "pwd", 1234, callback);
        assertThat(callback.waitOnNotify(10), is(true));
        assertThat(callback.users, is(empty()));
        assertThat(callback.dbExceptions.size(), is(1));
        assertThat(callback.dbExceptions.element(), instanceOf(AlreadyExistsUserException.class));
    }

    // 4. Adding a user to the database and trying to `authenticate` the same user will yield the correct user
    @Test
    public void addUserAndAuthYieldCorrectUser() throws InterruptedException {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        CallbackWait callback = new CallbackWait();
        db.addUser("usrnm", "pwd", 1234, callback);
        assertThat(callback.waitOnNotify(10), is(true));
        callback = new CallbackWait();
        db.authenticate("usrnm", "pwd", callback);
        assertThat(callback.waitOnNotify(10), is(true));
        assertThat(callback.dbExceptions, is(empty()));
        assertThat(callback.users.size(), is(1));
        assertThat(callback.users.element().getUserName(), is("usrnm"));
        assertThat(callback.users.element().getID(), is("1234"));
    }

    // 5. Adding a user to the database and trying to `authenticate` the same user with a wrong password will throw
    @Test
    public void addUserAndAuthWithWrongPwdThrowsInvalidCredentials() throws InterruptedException {
        CBDistantCredentialDatabase db = new CBDistantCredentialDatabase();
        CallbackWait callback = new CallbackWait();
        db.addUser("usrnm", "pwd", 1234, callback);
        assertThat(callback.waitOnNotify(10), is(true));
        callback = new CallbackWait();
        db.authenticate("usrnm", "wrongpwd", callback);
        assertThat(callback.waitOnNotify(10), is(true));
        assertThat(callback.users, is(empty()));
        assertThat(callback.dbExceptions.size(), is(1));
        assertThat(callback.dbExceptions.element(), instanceOf(InvalidCredentialException.class));
    }

    /**
     * Helper class for credential db callback
     * This class will block waiting on a result while async call complete and log the different results
     */
    private static class CallbackWait implements CredentialDatabaseCallback {
        public final Queue<StudentUser> users = new LinkedList<>();
        public final Queue<DatabaseException> dbExceptions = new LinkedList<>();
        private final CountDownLatch doneSignal = new CountDownLatch(1);

        @Override
        public void onSuccess(StudentUser user) {
            users.add(user);
            doneSignal.countDown();
        }

        @Override
        public void onError(DatabaseException exception) {
            dbExceptions.add(exception);
            doneSignal.countDown();
        }

        public boolean waitOnNotify(int timeout) throws InterruptedException {
            return doneSignal.await(timeout, TimeUnit.SECONDS);
        }
    }
}