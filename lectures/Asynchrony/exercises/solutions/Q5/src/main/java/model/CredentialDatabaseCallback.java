package model;

public interface CredentialDatabaseCallback {
    void onSuccess(StudentUser user);
    void onError(DatabaseException exception);
}
