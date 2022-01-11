import model.Course;
import model.Lecturer;
import store.Courses;

public class App {
  public static void demonstrate() {
    String code = "A code";
    Course course = Courses.findByCode(code);
    Lecturer lecturer = course.getLecturer();
    String name = lecturer.getName();
    System.out.println(name);
  }
}
