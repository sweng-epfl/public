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
    private final Map<String, Integer> SCIPERS = new HashMap<>();

    @Override
    public CompletableFuture<AuthenticatedUser> addUser(String userName, String userPassword, int sciper) {
        return CompletableFuture.supplyAsync(() -> {
            RandomSleeper.sleepRand(5);
            if (PASSWORDS.containsKey(userName)) {
                throw new CompletionException(new AlreadyExistsUserException(userName));
            } else {
                PASSWORDS.put(userName, userPassword);
                SCIPERS.put(userName, sciper);
                return new StudentUser(userName, String.valueOf(SCIPERS.get(userName)));
            }
        });
    }

    @Override
    public CompletableFuture<AuthenticatedUser> authenticate(String userName, String userPassword) {
        return CompletableFuture.supplyAsync(() -> {
            RandomSleeper.sleepRand(5);
            if (PASSWORDS.get(userName) == null) {
                throw new CompletionException(new UnknownUserException(userName));
            }
            if (!PASSWORDS.get(userName).equals(userPassword)) {
                throw new CompletionException(new InvalidCredentialException(userName));
            }
            return new StudentUser(userName, String.valueOf(SCIPERS.get(userName)));
        });
    }
}
