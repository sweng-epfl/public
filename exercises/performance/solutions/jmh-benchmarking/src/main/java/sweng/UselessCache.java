package sweng;

/**
 * Cache implementation that does nothing
 */
public class UselessCache<K, V> implements Cache<K, V> {

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void clear() {

    }
}
