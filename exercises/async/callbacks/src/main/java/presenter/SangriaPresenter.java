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
        this.view = view;
        this.database = database;

        this.view.setPresenter(this);
    }

    @Override
    public void authenticateUser(String name, String password) {
        try {
            AuthenticatedUser user = database.authenticate(name, password);
            this.view.displayUser(user);
        } catch(DatabaseException de) {
            this.view.displayError(de.getMessage());
        }
    }

}
