package presenter;

import model.AuthenticatedUser;
import model.DatabaseException;

/**
 * Interface for application presenters
 */
public interface Presenter {

    /**
     * Authenticate a user.
     *
     * @param userName The name of the user
     * @param password The password of the user
     */
    void authenticateUser(String userName, String password);

}
