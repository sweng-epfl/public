import java.util.Optional;

import model.Course;
import model.Lecturer;
import store.Courses;

public class App {
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
