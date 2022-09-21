package view;

import model.AuthenticatedUser;

/**
 * Simple view to display an authenticated user.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class AuthenticatedUserView {

    private final AuthenticatedUser user;

    /**
     * Construct a new authenticated user view
     * @param user The user's information that the view displays
     */
    public AuthenticatedUserView(AuthenticatedUser user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        this.user = user;
    }

    @Override
    public String toString() {
        return "User: " + this.user.getUserName() + ", SCIPER: " + this.user.getSciper();
    }
}
