import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EXERCISE: Write some automated tests for these functions.
 * Think about which properties the functions have and how you can test them.
 */
public final class Functions {
    /**
     * Returns the Fibonacci number 'n'. Throws if n < 0.
     */
    public static int fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci requires an N >= 0.");
        }
        if (n < 2) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Returns the non-empty substrings of 'str' that are separated by 'separator'.
     */
    public static List<String> splitString(String str, char separator) {
        var results = new ArrayList<String>();
        var current = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == separator) {
                results.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        results.add(current.toString());
        return results;
    }

    /**
     * Randomly shuffles the given 'values'.
     */
    public static <T> void shuffle(T[] values) {
        var random = new Random();
        for (int i = 0; i <= values.length - 2; i++) {
            int j = random.nextInt(values.length - i) + i;
            var temp = values[i];
            values[i] = values[j];
            values[j] = temp;
        }
    }
}
