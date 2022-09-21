package authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import session.Session;
import util.AsyncUtil;

/**
 * Remote authentication service.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class RemoteAuthenticationService implements AuthenticationService {

    private Map<String, String> users;
    private static String validToken = "sweng2021";

    public RemoteAuthenticationService() {
        users = new HashMap<>();
        users.put("SwengFan", "123456");
    }

    @Override
    public CompletableFuture<Session> login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            AsyncUtil.simulateAsync();
            if (users.containsKey(username) && users.get(username).equals(password)) {
                var session = new Session(username, validToken);
                return session;
            }
            throw new AuthenticationException("Failed to authenticate user: " + username);
        });


    }

}
