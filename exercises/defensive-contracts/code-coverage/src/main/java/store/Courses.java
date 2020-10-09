package store;

import java.util.HashMap;
import java.util.Map;

import model.Course;
import model.Lecturer;

public class Courses {
  private static Map<String, Course> courses = new HashMap<>();

  static {
    Lecturer lecturer = new Lecturer("The lecturer");
    Course course = new Course("The code", lecturer);
    courses.put(course.getCode(), course);
  }

  public static Course findByCode(String code) {
    assert code != null : "null code";
    return courses.getOrDefault(code, null);
  }
}