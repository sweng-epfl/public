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
        // BUG: No check was made for a null user
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
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

        // BUG: Courses were not sorted
        final Comparator<Course> coursesSorter = new Comparator<Course>() {
            @Override
            public int compare(Course x, Course y) {
                return x.name.compareTo(y.name);
            }
        };
        Collections.sort(taughtCourses, coursesSorter);
        Collections.sort(attendedCourses, coursesSorter);

        final StringBuilder builder = new StringBuilder();
        printGreeting(builder, user);

        printCourses(builder, "Courses you teach", taughtCourses, "You are not teaching any courses.");
        printCourses(builder, "Courses you attend", attendedCourses, "You are not attending any courses.");

        return builder.toString();
    }

    private static void printGreeting(final StringBuilder builder, final User user) {
        builder.append("Hello, ");
        // BUG: Name was unnecessarily trimmed
        builder.append(user.name);
        builder.append("!");
        builder.append(System.lineSeparator());
    }

    private static void printCourses(final StringBuilder builder, final String title, final List<Course> courses, final String placeholder) {
        // BUG: Titles were displayed even if the lists were empty
        // BUG: Placeholders in case of empty lists were not displayed
        if (courses.isEmpty()) {
            builder.append(placeholder);
            builder.append(System.lineSeparator());
            return;
        }

        builder.append(title);
        builder.append(System.lineSeparator());

        for (final Course course : courses) {
            builder.append("- ");
            builder.append(course.name);
            builder.append(System.lineSeparator());
        }
    }
}
