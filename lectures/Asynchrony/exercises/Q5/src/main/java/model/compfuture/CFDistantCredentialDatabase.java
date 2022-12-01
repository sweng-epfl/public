package model.compfuture;

import internal.RandomSleeper;
import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Simulate a distant database with unknown processing time between call and result
 */
public class CFDistantCredentialDatabase implements CFCredentialDatabase {
    private final Map<String, String> PASSWORDS = new HashMap<>();
    private final Map<String, Integer> IDs = new HashMap<>();

    @Override
    public CompletableFuture<StudentUser> addUser(String userName, String userPassword, int id) {
        return CompletableFuture.supplyAsync(() -> {
            RandomSleeper.sleepRand(5);
            if (PASSWORDS.containsKey(userName)) {
                throw new CompletionException(new AlreadyExistsUserException(userName));
            } else {
                PASSWORDS.put(userName, userPassword);
                IDs.put(userName, id);
                return new StudentUser(userName, String.valueOf(IDs.get(userName)));
            }
        });
    }

    @Override
    public CompletableFuture<StudentUser> authenticate(String userName, String userPassword) {
        return CompletableFuture.supplyAsync(() -> {
            RandomSleeper.sleepRand(5);
            if (PASSWORDS.get(userName) == null) {
                throw new CompletionException(new UnknownUserException(userName));
            }
            if (!PASSWORDS.get(userName).equals(userPassword)) {
                throw new CompletionException(new InvalidCredentialException(userName));
            }
            return new StudentUser(userName, String.valueOf(IDs.get(userName)));
        });
    }
}
