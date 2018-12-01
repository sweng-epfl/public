package ch.epfl.sweng.defensive.code.coverage;

import ch.epfl.sweng.defensive.code.coverage.model.Course;
import ch.epfl.sweng.defensive.code.coverage.model.Lecturer;
import ch.epfl.sweng.defensive.code.coverage.store.Courses;

public class CaseStudy {
  public static void demonstrate() {
    String code = "A code";
    Course course = Courses.findByCode(code);
    if (course != null) {
      Lecturer lecturer = course.getLecturer();
      if (lecturer != null) {
        String name = lecturer.getName();
        if (name != null) {
          System.out.println(name);
        }
      }
    }
  }
}