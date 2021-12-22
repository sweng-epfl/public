package authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import session.Session;

public class MockAuthenticationService implements AuthenticationService {

    private static final String VALID_TOKEN = "sweng2021";
    private final Map<String, String> users;

    public MockAuthenticationService() {
        users = new HashMap<>();
        users.put("SwengFan", "123456");
    }

    @Override
    public CompletableFuture<Session> login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            var session = new Session(username, VALID_TOKEN);
            return CompletableFuture.completedFuture(session);
        }
        return CompletableFuture.failedFuture(new AuthenticationException("Failed to authenticate user: " + username));
    }

}