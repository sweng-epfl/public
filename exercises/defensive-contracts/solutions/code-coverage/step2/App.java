import model.Course;
import model.Lecturer;
import store.Courses;

public class App {
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
