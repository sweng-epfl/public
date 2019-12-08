package ch.epfl.sweng;

import java.util.*;

/**
 * Poodle home page.
 */
public final class Homepage {
    /**
     * Print the home page of the given user, using the given courses.
     */
    public static String print(final User user, final List<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Courses cannot be null.");
        }

        final List<Course> taughtCourses = new ArrayList<>();
        final List<Course> attendedCourses = new ArrayList<>();

        for (final Course course : courses) {
            if (course.teachers.contains(user)) {
                taughtCourses.add(course);
            }
            if (course.students.contains(user)) {
                attendedCourses.add(course);
            }
        }

        final StringBuilder builder = new StringBuilder();
        printGreeting(builder, user);
        printCourses(builder, "Courses you teach", taughtCourses);
        printCourses(builder, "Courses you attend", attendedCourses);

        return builder.toString();
    }

    private static void printGreeting(final StringBuilder builder, final User user) {
        builder.append("Hello, ");
        builder.append(user.name.substring(0, Math.min(user.name.length(), 8)));
        builder.append("!");
        builder.append(System.lineSeparator());
    }

    private static void printCourses(final StringBuilder builder, final String title, final List<Course> courses) {
        builder.append(title);
        builder.append(System.lineSeparator());

        for (final Course course : courses) {
            builder.append("- ");
            builder.append(course.name);
            builder.append(System.lineSeparator());
        }
    }
}
