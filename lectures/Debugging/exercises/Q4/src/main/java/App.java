import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;

public class App {

  public static void main(String[] args) {
    sorting();
  }

  private static void sorting() {
    // Take 100 numbers.
    var numbers = IntStream.range(0, 100).boxed().toArray(Integer[]::new);
    Collections.shuffle(Arrays.asList(numbers));

    // Sort them according to their natural order.
    Quicksort.sort(numbers, Comparator.naturalOrder());

    // Print the result.
    System.out.println(Arrays.toString(numbers));
  }
}
