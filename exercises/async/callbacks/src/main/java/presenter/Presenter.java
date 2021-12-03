package presenter;

/**
 * Interface for application presenters
 */
public interface Presenter {

    /**
     * Authenticate a user.
     *
     * @param userName The user's name.
     * @param password The user's password in plaintext.
     */
    void authenticateUser(String userName, String password);

}
