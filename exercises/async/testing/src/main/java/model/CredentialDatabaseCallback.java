package model;

public interface CredentialDatabaseCallback {
    void onSuccess(AuthenticatedUser user);
    void onError(DatabaseException exception);
}
