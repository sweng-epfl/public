import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * A class representing an EPFL student. Students may follow courses, and have a name and a unique
 * ID number.
 * <p>
 * It is <bold>not</bold> valid to create two students with the same ID number.
 */
public class Student {

  /**
   * The name of the student.
   */
  public final String name;

  /**
   * The unique ID number of the student.
   */
  public final int id;

  /**
   * The names of the courses followed by the student. May not be modified.
   */
  public final Set<String> courses;

  /*
   * The modifiable set of courses followed by the student.
   */
  private final Set<String> followed;

  public Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.followed = new TreeSet<>();
    this.courses = Collections.unmodifiableSet(followed);
  }

  /**
   * Makes the student follow a course.
   *
   * @param course the name of the course to follow
   */
  public void take(String course) {
    followed.add(course);
  }

  /**
   * Makes the student drop a course.
   *
   * @param course the name of the course to drop
   */
  public void drop(String course) {
    followed.remove(course);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    return id == student.id &&
        Objects.equals(name, student.name) &&
        Objects.equals(courses, student.courses);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, id, courses);
  }
}
