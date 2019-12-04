package ch.epfl.sweng;

import java.util.*;

/**
 * Papademia user.
 */
public final class PapademiaUser {
    /**
     * The user's name.
     */
    public final String name;

    /**
     * The courses taught by the user.
     */
    public final List<PapademiaCourse> taughtCourses;

    /**
     * The courses attended by the user.
     */
    public final List<PapademiaCourse> attendedCourses;


    public PapademiaUser(final String name, final List<PapademiaCourse> taughtCourses, final List<PapademiaCourse> attendedCourses) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (taughtCourses == null) {
            throw new IllegalArgumentException("TaughtCourses cannot be null");
        }
        for (final PapademiaCourse course : taughtCourses) {
            if (course == null) {
                throw new IllegalArgumentException("TaughtCourses cannot contain null values");
            }
        }
        if (attendedCourses == null) {
            throw new IllegalArgumentException("AttendedCourses cannot be null");
        }
        for (final PapademiaCourse course : attendedCourses) {
            if (course == null) {
                throw new IllegalArgumentException("AttendedCourses cannot contain null values");
            }
        }

        this.name = name;
        this.taughtCourses = Collections.unmodifiableList(new ArrayList<>(taughtCourses));
        this.attendedCourses = Collections.unmodifiableList(new ArrayList<>(attendedCourses));
    }
}
