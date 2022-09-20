import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class IsolatedFunctions {
    /**
     * Returns the Fibonacci number 'n'. Throws if n < 0.
     * See e.g. https://en.wikipedia.org/wiki/Fibonacci_number
     */
    public static long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci requires an N >= 0.");
        }
        if (n < 2) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Returns the (non-empty) strings in 'str' separated by 'separator'.
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
     * Randomly shuffles the given 'array'.
     */
    public static <T> void randomShuffle(T[] array) {
        var random = new Random();
        for (int i = 0; i <= array.length - 2; i++) {
            int j = random.nextInt(array.length - i) + i;
            var temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
