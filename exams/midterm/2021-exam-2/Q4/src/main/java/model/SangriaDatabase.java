package model;

import java.util.Map;

/**
 * Credential database mock implementation
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class SangriaDatabase implements CredentialDatabase {

    public static final String USERNAME_KEY = "userName";
    public static final String SCIPER_KEY = "sciper";
    // Internal database data
    private static final Map<String, String> PASSWORDS = Map.of("john", "sweng");
    private static final Map<String, Integer> SCIPERS = Map.of("john", 123456);

    @Override
    public AuthResult authenticate(String userName, String userPassword) {
        if (PASSWORDS.get(userName) == null) {
            return AuthResult.fromException(new UnknownUserException(userName));
        }
        if (!PASSWORDS.get(userName).equals(userPassword)) {
            return AuthResult.fromException(new InvalidCredentialException(userName));
        }
        return AuthResult.fromData(Map.of(USERNAME_KEY, userName, SCIPER_KEY, SCIPERS.get(userName)));
    }
}
