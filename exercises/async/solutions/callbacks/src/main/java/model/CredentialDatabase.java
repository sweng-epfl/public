package model;

/**
 * Interface for credential database classes
 */
public interface CredentialDatabase {

    interface OnAuthenticationResult {
        void onSuccess(AuthenticatedUser user);
        void onError(DatabaseException exception);
    }

    /**
     * Authenticate a user in the database and query its personal data.
     *
     * @param userName     The user's name
     * @param userPassword The user's password in plaintext
     * @param callback     The result callback
     */
    void authenticate(String userName, String userPassword, OnAuthenticationResult callback);

}
