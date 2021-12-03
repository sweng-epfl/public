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
    public AuthenticatedUser authenticate(String userName, String userPassword) throws DatabaseException {
        if (PASSWORDS.get(userName) == null) {
            throw new UnknownUserException(userName);
        }
        if (!PASSWORDS.get(userName).equals(userPassword)) {
            throw new InvalidCredentialException(userName);
        }
        return new StudentUser(userName, String.valueOf(SCIPERS.get(userName)));
    }
}
