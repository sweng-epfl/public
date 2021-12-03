package model;

import java.util.HashMap;
import java.util.Map;

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
    public AuthenticatedUser authenticate(String userName, String userPassword) throws DatabaseException {
        int authTries = this.tries.getOrDefault(userName, 0);
        if (authTries >= 3) {
            throw new BlockedUserException(userName);
        }
        AuthenticatedUser user;
        try {
            user = this.database.authenticate(userName, userPassword);
        } catch(InvalidCredentialException ice) {
            this.tries.put(userName, ++authTries);
            throw ice;
        }
        this.tries.remove(userName);
        return user;
    }
}
