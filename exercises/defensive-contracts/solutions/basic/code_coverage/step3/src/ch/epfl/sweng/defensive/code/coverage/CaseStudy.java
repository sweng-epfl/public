package ch.epfl.sweng.defensive.code.coverage;

import java.util.Optional;

import ch.epfl.sweng.defensive.code.coverage.model.Course;
import ch.epfl.sweng.defensive.code.coverage.model.Lecturer;
import ch.epfl.sweng.defensive.code.coverage.store.Courses;

public class CaseStudy {
  public static void demonstrate() {
    String code = "A code";
    Optional<Course> optionalCourse = Courses.findByCode(code);
    if (optionalCourse.isPresent()) {
      Course course = optionalCourse.get();
      Optional<Lecturer> optionalLecturer = course.getLecturer();
      if (optionalLecturer.isPresent()) {
        Lecturer lecturer = optionalLecturer.get();
        Optional<String> optionalName = lecturer.getName();
        if (optionalName.isPresent()) {
          String name = optionalName.get();
          System.out.println(name);
        }
      }
    }
  }
}