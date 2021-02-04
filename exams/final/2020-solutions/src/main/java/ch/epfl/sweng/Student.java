package ch.epfl.sweng;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class that represents a student with a name, SCIPER and faculty.
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN change the bodies of existing methods/constructors
 * You CAN add new private methods/constructors
 * You CANNOT add interface implementations unless explicitly instructed to do so
 * You CANNOT add new public, package-private or protected methods/constructors
 * You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
 * You CANNOT delete existing methods/constructors
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class Student implements Serializable {

    /**
     * Name of the student. e.g. "John"
     */
    public final String name;

    /**
     * SCIPER code of the student. e.g. "692735"
     */
    public final String sciper;

    /**
     * Faculty of the student. e.g. "IC"
     */
    public final String faculty;

    /**
     * Constructs a new Student with a name, SCIPER and faculty
     *
     * @throws IllegalArgumentException Thrown if one or more arguments are null.
     */
    public Student(String name, String sciper, String faculty) {
        if (name == null || sciper == null || faculty == null) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.sciper = sciper;
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sciper='" + sciper + '\'' +
                ", faculty='" + faculty + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) &&
                sciper.equals(student.sciper) &&
                faculty.equals(student.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sciper, faculty);
    }
}
