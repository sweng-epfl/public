package ch.epfl.sweng;

import java.util.*;

/**
 * Adapter from Papademia to Poodle.
 */
public final class PapademiaAdapter {
    private final List<PapademiaUser> users;
    private final List<PapademiaCourse> courses;

    public PapademiaAdapter(final List<PapademiaUser> users, final List<PapademiaCourse> courses) {
        if (users == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }
        if (courses == null) {
            throw new IllegalArgumentException("Courses cannot be null");
        }

        this.users = Collections.unmodifiableList(new ArrayList<>(users));
        this.courses = Collections.unmodifiableList(new ArrayList<>(courses));
    }

    /**
     * Gets the users as Poodle users.
     */
    public List<User> getUsers() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Gets the courses as Poodle courses.
     */
    public List<Course> getCourses() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
