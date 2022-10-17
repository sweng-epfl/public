import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A (fake) repository of the students. There are (reportedly) no bugs in here, sorry!
 */
public class Database {

  private Database() {
    // Not instantiable.
  }

  private static final String[] names = {
      "Alexandre", "Bob", "Charlie", "David", "Eve", "Frank", "George", "Heidi", "Ivan", "Judy",
      "Karim", "Louca", "Lucien", "Matthieu", "Nancy", "Oscar", "Peggy", "Quentin", "Ruth", "Solal",
      "Trudy", "Ursula", "Victor", "Wendy", "Xavier", "Yannis", "Yugesh", "Zacharie",
  };

  /**
   * Returns a stream of (deterministically) randomly generated students.
   */
  public static Stream<Student> students() {
    return Stream.generate(new StudentSupplier());
  }

  private static final class StudentSupplier implements Supplier<Student> {

    private final Random random = new Random(42); // Fixed seed to help with reproducibility.
    private final Set<Integer> assigned = new HashSet<>();

    private int nextId() {
      // We're just overly cautious here, to avoid any possible collisions.
      int id;
      do {
        id = random.nextInt(Integer.MAX_VALUE);
      } while (!assigned.add(id));
      return id;
    }

    @Override
    public Student get() {
      var id = nextId();
      return new Student(names[id % names.length], id);
    }
  }
}
