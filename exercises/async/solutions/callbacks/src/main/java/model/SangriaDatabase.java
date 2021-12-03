package model;

import java.util.Map;

/**
 * Credential database mock implementation
 */
public final class SangriaDatabase implements CredentialDatabase {

    // Internal database data
    private static final Map<String, String> PASSWORDS = Map.of("john", "sweng");
    private static final Map<String, Integer> SCIPERS = Map.of("john", 123456);

    @Override
    public void authenticate(String userName, String userPassword, OnAuthenticationResult callback) {
        if (PASSWORDS.get(userName) == null) {
            callback.onError(new UnknownUserException(userName));
        } else if (!PASSWORDS.get(userName).equals(userPassword)) {
            callback.onError(new InvalidCredentialException(userName));
        } else {
            callback.onSuccess(new StudentUser(userName, String.valueOf(SCIPERS.get(userName))));
        }
    }
}
