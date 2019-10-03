package ch.epfl.sweng.defensive.code.coverage;

import ch.epfl.sweng.defensive.code.coverage.model.Course;
import ch.epfl.sweng.defensive.code.coverage.model.Lecturer;
import ch.epfl.sweng.defensive.code.coverage.store.Courses;

public class CaseStudy {
  public static void demonstrate() {
    String code = "A code";
    Courses.findByCode(code)
      .flatMap(Course::getLecturer)
      .flatMap(Lecturer::getName)
      .ifPresent(System.out::println);
  }
}