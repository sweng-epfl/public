import model.*;
import org.junit.jupiter.api.Test;
import presenter.Presenter;
import presenter.SangriaPresenter;
import view.View;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Class for the application tests of part 3.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You MUST use this file for application tests.
 * You CAN add new constructors, methods, and nested classes to this class.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class PresenterTest {

    @Test
    public void viewDisplaysUserInfoOnValidCredential() {
        String testName = "user";
        String testSciper = "sciper";

        AtomicBoolean flag = new AtomicBoolean(false);

        View view = new View() {
            private Presenter presenter;

            @Override
            public void setPresenter(Presenter presenter) {
                this.presenter = presenter;
            }

            @Override
            public void displayUser(AuthenticatedUser user) {
                assertThat(user.getUserName(), is(testName));
                assertThat(user.getSciper(), is(testSciper));
                flag.set(true);
            }

            @Override
            public void displayError(String msg) {
                fail();
            }

            @Override
            public void startApplication() {
                presenter.authenticateUser(testName, "pwd");
            }
        };

        CredentialDatabase database = (userName, userPassword) -> new StudentUser(userName, testSciper);

        new SangriaPresenter(view, database);
        view.startApplication();

        assertThat(flag.get(), is(true));
    }

    @Test
    public void viewDisplaysErrorOnInvalidCredential() {
        String testName = "user";

        AtomicBoolean flag = new AtomicBoolean(false);

        View view = new View() {
            private Presenter presenter;

            @Override
            public void setPresenter(Presenter presenter) {
                this.presenter = presenter;
            }

            @Override
            public void displayUser(AuthenticatedUser user) {
                fail();
            }

            @Override
            public void displayError(String msg) {
                assertThat(msg, containsString(testName));
                flag.set(true);
            }

            @Override
            public void startApplication() {
                presenter.authenticateUser(testName, "pwd");
            }
        };

        CredentialDatabase database = (userName, userPassword) -> {
            throw new InvalidCredentialException(userName);
        };

        new SangriaPresenter(view, database);
        view.startApplication();

        assertThat(flag.get(), is(true));
    }

    @Test
    public void viewDisplaysBlockedOnThreeInvalidCredentials() {
        String testName = "user";

        AtomicInteger count = new AtomicInteger(0);

        View view = new View() {
            private Presenter presenter;

            @Override
            public void setPresenter(Presenter presenter) {
                this.presenter = presenter;
            }

            @Override
            public void displayUser(AuthenticatedUser user) {
                fail();
            }

            @Override
            public void displayError(String msg) {
                assertThat(msg, containsString(testName));
                if (count.get() >= 3) {
                    assertThat(msg, containsString("blocked"));
                }
                count.incrementAndGet();
            }

            @Override
            public void startApplication() {
                presenter.authenticateUser(testName, "pwd");
                presenter.authenticateUser(testName, "pwd");
                presenter.authenticateUser(testName, "pwd");
                presenter.authenticateUser(testName, "pwd");
            }
        };

        CredentialDatabase database = (userName, userPassword) -> {
            throw new InvalidCredentialException(userName);
        };

        new SangriaPresenter(view, new SecureSangriaDatabase(database));
        view.startApplication();

        assertThat(count.get(), is(4));
    }
}
