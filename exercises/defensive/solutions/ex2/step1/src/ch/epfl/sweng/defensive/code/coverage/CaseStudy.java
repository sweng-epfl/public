package ch.epfl.sweng.defensive.code.coverage;

import ch.epfl.sweng.defensive.code.coverage.model.Course;
import ch.epfl.sweng.defensive.code.coverage.model.Lecturer;
import ch.epfl.sweng.defensive.code.coverage.store.Courses;

public class CaseStudy {
  public static void demonstrate() {
    String code = "A code";
    Course course = Courses.findByCode(code);
    Lecturer lecturer = course.getLecturer();
    String name = lecturer.getName();
    System.out.println(name);
  }
}