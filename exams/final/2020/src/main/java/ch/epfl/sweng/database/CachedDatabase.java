package ch.epfl.sweng.database;

/**
 * This class decorates an existing {@link Database} implementation and adds a caching functionality.
 * A query for a key should get the value from the underlying database and store it locally.
 * Subsequent queries for the same key should then return the stored value.
 * Other database operations can however invalidate this cache.
 * 
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN change the bodies of existing methods/constructors
 * You CAN add new private methods/constructors
 * You CANNOT add interface implementations unless explicitly instructed to do so
 * You CANNOT add new public, package-private or protected methods/constructors
 * You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
 * You CANNOT delete existing methods/constructors
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class CachedDatabase implements Database {

    /**
     * Constructs a new cached database decorator.
     *
     * @param database The underlying database.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public CachedDatabase(Database database) {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    /**
     * The cache entry corresponding to the given key, if present, should be invalidated (i.e., removed),
     * except if the new value is the same as the cached one.
     * Even if the key is not present in the cache, this method should not store the provided
     * key/value pair in the cache.
     */
    @Override
    public void put(String key, String value) {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    /**
     * If the key corresponds to a key/value pair that is present in the cache, the cache entry
     * should be invalidated (i.e., removed).
     */
    @Override
    public void remove(String key) throws DatabaseException {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    /**
     * Returns the key/value pair stored in the cache, if present.
     * If the key/value pair is not in the cache, then the value should be queried from
     * the database and the result should be stored in the cache.
     */
    @Override
    public String get(String key) throws DatabaseException {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }
}
