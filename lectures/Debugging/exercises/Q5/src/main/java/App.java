import java.util.HashMap;
import java.util.Random;

public class App {

  public static void main(String[] args) {
    simulate();
  }

  private static void simulate() {
    var random = new Random();

    var classes = new String[]{"SwEng", "SDP", "DB", "OS", "Algo", "ML"};
    var rooms = new String[]{"BC 05", "BC 06", "BC 07", "BC 08", "BC 09", "BC 10"};
    var students = Database.students().limit(100).toArray(Student[]::new);

    // At the beginning of the day, each student chooses a random classroom in which they will
    // study.
    var locations = new HashMap<Student, String>();
    for (var student : students) {
      var location = rooms[random.nextInt(rooms.length)];
      locations.put(student, location);
    }

    // During the day, each student may (or may not) prepare for a random exam. However, some
    // students may decide to take a day off, have a coffee and not prepare for anything.
    for (var student : students) {
      if (random.nextBoolean()) {
        var course = classes[random.nextInt(classes.length)];
        student.take(course);
        System.out.println(student.name + " is preparing for " + course + ".");
      } else {
        System.out.println(student.name + " is taking a day off.");
      }
    }

    // At the end of the day, all students leave their classrooms and go home.
    for (var student : students) {
      locations.remove(student);
    }

    System.out.println("See you tomorrow !");

    // The BC building should be empty. Or is it ?
    if (!locations.isEmpty()) {
      throw new AssertionError("Some classrooms are not empty!");
    }
  }
}
