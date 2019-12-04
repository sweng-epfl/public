package ch.epfl.sweng;

import java.util.*;

/**
 * Poodle course.
 */
public final class Course {
    /**
     * The course's name.
     */
    public final String name;

    /**
     * The course's teachers.
     */
    public final List<User> teachers;

    /**
     * The course's students.
     */
    public final List<User> students;


    public Course(final String name, final List<User> teachers, final List<User> students) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (teachers == null) {
            throw new IllegalArgumentException("Teachers cannot be null");
        }
        for (final User user : teachers) {
            if (user == null) {
                throw new IllegalArgumentException("Teachers cannot contain null values");
            }
        }
        if (students == null) {
            throw new IllegalArgumentException("Students cannot be null");
        }
        for (final User user : teachers) {
            if (user == null) {
                throw new IllegalArgumentException("Students cannot contain null values");
            }
        }

        this.name = name;
        this.teachers = Collections.unmodifiableList(new ArrayList<>(teachers));
        this.students = Collections.unmodifiableList(new ArrayList<>(students));
    }


    public CourseQuizFormatter getQuizFormatter(final User user) {
        throw new UnsupportedOperationException("Not implemented.");
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Course)) {
            return false;
        }
        final Course other = (Course) obj;
        return name.equals(other.name)
                && teachers.equals(other.teachers)
                && students.equals(other.students);
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash += name.hashCode();
        hash *= 17;
        hash += teachers.hashCode();
        hash *= 17;
        hash += students.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(name);

        builder.append(" / teachers: ");
        for (final User teacher : teachers) {
            builder.append(teacher.name);
            builder.append(" ");
        }

        builder.append(" / students: ");
        for (final User student : students) {
            builder.append(student.name);
            builder.append(" ");
        }

        return builder.toString();
    }
}
