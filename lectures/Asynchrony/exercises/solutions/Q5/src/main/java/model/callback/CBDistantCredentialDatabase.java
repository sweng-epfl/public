package model.callback;

import internal.RandomSleeper;
import model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulate a distant database with unknown processing time between call and result
 */
public class CBDistantCredentialDatabase implements CBCredentialDatabase {
    private final Map<String, String> PASSWORDS = new HashMap<>();
    private final Map<String, Integer> Ids = new HashMap<>();

    @Override
    public void addUser(String userName, String userPassword, int id, CredentialDatabaseCallback callback) {
        RandomSleeper.sleepRandAndRun(5, () -> {
            if (PASSWORDS.containsKey(userName)) {
                callback.onError(new AlreadyExistsUserException(userName));
            } else {
                PASSWORDS.put(userName, userPassword);
                Ids.put(userName, id);
                callback.onSuccess(new StudentUser(userName, String.valueOf(Ids.get(userName))));
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
            callback.onSuccess(new StudentUser(userName, String.valueOf(Ids.get(userName))));
        });
    }
}
