import model.AlreadyExistsUserException;
import model.InvalidCredentialException;
import model.StudentUser;
import model.UnknownUserException;
import model.compfuture.CFCredentialDatabase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class FutureBasedTests {
    protected abstract CFCredentialDatabase getFutureBasedDatabase();

    // 1. Trying to authenticate a user that does not exist throws
    @Test
    public void tryToAuthUserThatDoesNotExistThrowsUnknownUser() {
        CFCredentialDatabase db = getFutureBasedDatabase();
        var exception = assertThrows(CompletionException.class, () -> db.authenticate("any", "user").orTimeout(10, TimeUnit.SECONDS).join());
        assertThat(exception.getCause(), isA(UnknownUserException.class));
    }

    // 2. Calling `addUser` on empty database will add the correct user
    @Test
    public void callAddUserOnEmptyDBAddsUserToDB() {
        CFCredentialDatabase db = getFutureBasedDatabase();
        CompletableFuture<StudentUser> cf = db.addUser("newuser", "pwd", 1234).orTimeout(10, TimeUnit.SECONDS);
        StudentUser user = cf.join();
        assertThat(user.getUserName(), is("newuser"));
        assertThat(user.getID(), is("1234"));
    }

    // 3. Calling `addUser` twice with the same user will fail
    @Test
    public void callAddUserTwiceWithSameCredentialsThrowsAlreadyExists() {
        CFCredentialDatabase db = getFutureBasedDatabase();
        var exception = assertThrows(CompletionException.class, () -> CompletableFuture.allOf(
                db.addUser("new", "user", 1234).orTimeout(10, TimeUnit.SECONDS),
                db.addUser("new", "user", 1234).orTimeout(10, TimeUnit.SECONDS)
        ).join());
        assertThat(exception.getCause(), isA(AlreadyExistsUserException.class));
    }

    // 4. Adding a user to the database and trying to `authenticate` the same user will yield the correct user
    @Test
    public void addUserAndAuthYieldCorrectUser() {
        CFCredentialDatabase db = getFutureBasedDatabase();
        db.addUser("newuser", "pwd", 1234).orTimeout(10, TimeUnit.SECONDS).join();
        CompletableFuture<StudentUser> cf = db.authenticate("newuser", "pwd").orTimeout(10, TimeUnit.SECONDS);
        StudentUser user = cf.join();
        assertThat(user.getUserName(), is("newuser"));
        assertThat(user.getID(), is("1234"));
    }

    // 5. Adding a user to the database and trying to `authenticate` the same user with a wrong password will throw
    @Test
    public void addUserAndAuthWithWrongPwdThrowsInvalidCredentials() {
        CFCredentialDatabase db = getFutureBasedDatabase();
        db.addUser("newuser", "pwd", 1234).orTimeout(10, TimeUnit.SECONDS).join();
        var exception = assertThrows(CompletionException.class, () -> db.authenticate("newuser", "wrongpwd").orTimeout(10, TimeUnit.SECONDS).join());
        assertThat(exception.getCause(), isA(InvalidCredentialException.class));
    }

}
