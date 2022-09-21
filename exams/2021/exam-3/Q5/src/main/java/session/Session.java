package session;

/**
 * User session.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class Session {

    private String authenticatedUser;
    private String token;
    private final static String validToken = "sweng2021";

    /**
     * Creates a session given an authenticated user and token.
     */
    public Session(String authenticatedUser, String token) {
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("authenticatedUser cannot be null!");
        }
        if (token == null) {
            throw new IllegalArgumentException("token cannot be null!");
        }
        this.authenticatedUser = authenticatedUser;
        this.token = token;
    }

    /**
     * Sets the authenticated user.
     */
    public void setAuthenticatedUser(String authenticatedUser) {
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("authenticatedUser cannot be null!");
        }
        this.authenticatedUser = authenticatedUser;
    }

    /**
     * Sets the token.
     */
    public void setToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token cannot be null!");
        }
        this.token = token;
    }

    /**
     * Checks whether the given user is authenticated.
     */
    public boolean isAuthenticated(String username) {
        return this.authenticatedUser.equals(username) && validateToken();
    }

    /**
     * Logs out.
     */
    public void logout() {
        this.authenticatedUser = null;
        this.token = null;
    }

    private boolean validateToken() {
        // Token validation
        return token != null && token.equals(validToken);
    }

}
