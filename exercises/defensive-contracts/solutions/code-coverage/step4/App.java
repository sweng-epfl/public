import model.Course;
import model.Lecturer;
import store.Courses;

public class App {
  public static void demonstrate() {
    String code = "A code";
    Courses.findByCode(code)
      .flatMap(Course::getLecturer)
      .flatMap(Lecturer::getName)
      .ifPresent(System.out::println);
  }
}
