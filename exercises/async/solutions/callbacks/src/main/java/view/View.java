package view;

import model.AuthenticatedUser;
import presenter.Presenter;

/**
 * Interface for application views
 */
public interface View {

    /**
     * Set the presenter for this view.
     * This method should be called before a call to {@link #startApplication()}.
     *
     * @param presenter A reference to the presenter that handles this view.
     */
    void setPresenter(Presenter presenter);

    /**
     * Display a user in the view.
     *
     * @param user The user to display.
     */
    void displayUser(AuthenticatedUser user);

    /**
     * Display an error in the view.
     *
     * @param msg The error message to display.
     */
    void displayError(String msg);

    /**
     * Start the application. A call to this method is blocking and returns once
     * the application terminates.
     */
    void startApplication();
}
