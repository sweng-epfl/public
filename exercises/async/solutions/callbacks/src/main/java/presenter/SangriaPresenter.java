package presenter;

import model.AuthenticatedUser;
import model.CredentialDatabase;
import model.DatabaseException;
import view.View;

/**
 * Presenter implementation for the Sangria application
 */
public class SangriaPresenter implements Presenter {

    private final View view;
    private final CredentialDatabase database;

    /**
     * Construct a new presenter
     * @param view The MVP view
     * @param database The MVP model
     */
    public SangriaPresenter(View view, CredentialDatabase database) {
        if (view == null || database == null) {
            throw new IllegalArgumentException();
        }
        this.database = database;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void authenticateUser(String userName, String password) {
        database.authenticate(userName, password, new CredentialDatabase.OnAuthenticationResult() {
            @Override
            public void onSuccess(AuthenticatedUser user) {
                view.displayUser(user);
            }

            @Override
            public void onError(DatabaseException exception) {
                view.displayError(exception.getMessage());
            }
        });
    }

}
