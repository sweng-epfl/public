package example;

/**
 * A cache, associating values to keys.
 *
 * @param <K> the type of the keys.
 * @param <V> the type of the values.
 */
public interface Cache<K, V> {

    /**
     * Returns true iff the cache contains the given key.
     *
     * @param key the key to check.
     */
    boolean contains(K key);

    /**
     * Associates the given value to the given key. If the key was already
     * associated to a value, the old value is replaced.
     *
     * @param key   the key to associate the value to.
     * @param value the value to associate to the key.
     */
    void put(K key, V value);

    /**
     * Returns the value associated to the given key, or null if the key is not
     * associated to any value.
     *
     * @param key the key to get the value of.
     */
    V get(K key);

    /**
     * Removes all the entries from the cache.
     */
    void clear();
}