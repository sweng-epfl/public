package model;

/**
 * Interface for authenticated user classes
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface AuthenticatedUser {

    /**
     * @return The user's name
     */
    String getUserName();

    /**
     * @return The user's SCIPER as a String
     */
    String getSciper();

}
