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
        // This method is extremely simple since Poodle users have only a name
        // Note that we don't need to care about preserving referential equality
        // (i.e. having the same instance for the same user)
        // since User implements equals in a way that only checks for the name
        // thus we can just `new User(...)` each time

        final List<User> poodleUsers = new ArrayList<>();
        for (final PapademiaUser user : users) {
            // Remember, IS-Papademia can have null users
            if (user != null) {
                poodleUsers.add(new User(user.name));
            }
        }
        return poodleUsers;
    }

    /**
     * Gets the courses as Poodle courses.
     */
    public List<Course> getCourses() {
        // This method is more involved
        // But it is fundamentally the same logic as the Homepage.
        // Same remark as in `getUsers` regarding referential equality.

        final List<Course> poodleCourses = new ArrayList<>();
        for (final PapademiaCourse course : courses) {
            // Remember, IS-Papademia can have null courses
            if (course == null) {
                continue;
            }
            final List<User> lecturers = new ArrayList<>();
            final List<User> students = new ArrayList<>();

            for (final PapademiaUser user : users) {
                if (user.taughtCourses.contains(course)) {
                    lecturers.add(new User(user.name));
                }
                if (user.attendedCourses.contains(course)) {
                    students.add(new User(user.name));
                }
            }

            poodleCourses.add(new Course(course.name, lecturers, students));
        }

        return poodleCourses;
    }
}
