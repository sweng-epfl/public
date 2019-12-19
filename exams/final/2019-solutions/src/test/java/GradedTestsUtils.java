import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilities for graded tests.
 */
final class GradedTestsUtils {
    /**
     * Like Collections.emptyList/Collections.singletonList/Arrays.asList but the result is modifiable.
     */
    @SafeVarargs
    static <E> List<E> list(E... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    /**
     * Like Collections.singletonMap but the result is modifiable.
     */
    static <K, V> Map<K, V> map(K key, V value) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    /**
     * Like Collections.singletonMap but the result is modifiable and contains 2 pairs
     */
    static <K, V> Map<K, V> map(K key1, V value1, K key2, V value2) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }
}
