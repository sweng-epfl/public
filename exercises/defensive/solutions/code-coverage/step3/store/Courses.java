package store;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import model.Course;
import model.Lecturer;

public class Courses {
  private static Map<String, Course> courses = new HashMap<>();

  static {
    Lecturer lecturer = new Lecturer("The lecturer");
    Course course = new Course("The code", lecturer);
    courses.put(course.getCode(), course);
  }

  public static Optional<Course> findByCode(String code) {
    assert code != null : "null code";
    return Optional.ofNullable(courses.getOrDefault(code, null));
  }
}
