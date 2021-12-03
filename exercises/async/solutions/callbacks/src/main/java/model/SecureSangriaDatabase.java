package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Secure credential database decorator
 */
public class SecureSangriaDatabase implements CredentialDatabase {

    private final CredentialDatabase database;
    private Map<String, Integer> tries = new HashMap<>();

    /**
     * Construct a new secure database
     * @param other The underlying non-secure database
     */
    public SecureSangriaDatabase(CredentialDatabase other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
        this.database = other;
    }

    @Override
    public void authenticate(String userName, String userPassword, OnAuthenticationResult callback) {
        AtomicInteger authTries = new AtomicInteger(this.tries.getOrDefault(userName, 0));
        if (authTries.get() >= 3) {
            callback.onError(new BlockedUserException(userName));
        } else {
            this.database.authenticate(userName, userPassword, new OnAuthenticationResult() {
                @Override
                public void onSuccess(AuthenticatedUser user) {
                    tries.remove(userName);
                    callback.onSuccess(user);
                }

                @Override
                public void onError(DatabaseException exception) {
                    if (exception instanceof InvalidCredentialException) {
                        tries.put(userName, authTries.get()+1);
                    }
                    callback.onError(exception);
                }
            });
        }
    }
}
