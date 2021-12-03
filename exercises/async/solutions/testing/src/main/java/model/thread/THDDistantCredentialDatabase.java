package model.thread;

import internal.RandomSleeper;
import model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulate a distant database with unknown processing time between call and result
 */
public class THDDistantCredentialDatabase implements THDCredentialDatabase {
    private final Map<String, String> PASSWORDS = new HashMap<>();
    private final Map<String, Integer> SCIPERS = new HashMap<>();

    @Override
    public void addUser(String userName, String userPassword, int sciper, CredentialDatabaseCallback callback) {
        RandomSleeper.sleepRandAndRun(5, () -> {
            if (PASSWORDS.containsKey(userName)) {
                callback.onError(new AlreadyExistsUserException(userName));
            } else {
                PASSWORDS.put(userName, userPassword);
                SCIPERS.put(userName, sciper);
                callback.onSuccess(new StudentUser(userName, String.valueOf(SCIPERS.get(userName))));
            }
        });
    }

    @Override
    public void authenticate(String userName, String userPassword, CredentialDatabaseCallback callback) {
        RandomSleeper.sleepRandAndRun(5, () -> {
            if (PASSWORDS.get(userName) == null) {
                callback.onError(new UnknownUserException(userName));
                return;
            }
            if (!PASSWORDS.get(userName).equals(userPassword)) {
                callback.onError(new InvalidCredentialException(userName));
                return;
            }
            callback.onSuccess(new StudentUser(userName, String.valueOf(SCIPERS.get(userName))));
        });
    }
}
