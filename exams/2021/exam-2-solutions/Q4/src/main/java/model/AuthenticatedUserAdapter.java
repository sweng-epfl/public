package model;

import java.util.Map;

/**
 * Adapt a database query to an AuthenticatedUser
 */
public class AuthenticatedUserAdapter implements AuthenticatedUser {

    private final String userName;
    private final String sciper;

    public AuthenticatedUserAdapter(Map<String, Object> data) {
        if (!data.containsKey("userName") || !data.containsKey("sciper")) {
            throw new IllegalArgumentException("Invalid data!");
        }
        this.userName = (String)data.get("userName");
        this.sciper = String.valueOf(data.get("sciper"));
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getSciper() {
        return sciper;
    }
}
