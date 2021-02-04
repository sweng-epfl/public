package ch.epfl.sweng.database;

/**
 * A database that stores string key/value pairs. It is a one-to-one mapping meaning that
 * a key has always exactly one value and a value always has exactly one key. Keys are unique
 * in the database but different keys can map to the same string value.
 *
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface Database {

    /**
     * Pushes a new key/value mapping in the database.
     * If the given key is already present in the database, the current stored value is replaced
     * by the one passed as argument.
     *
     * @param key   The key of the key/value mapping.
     * @param value The value of the key/value mapping.
     * @throws IllegalArgumentException Thrown if either the arguments are null.
     */
    void put(String key, String value);

    /**
     * Removes a key/value mapping from the database.
     *
     * @param key The key of the mapping to remove from the database.
     * @throws IllegalArgumentException Thrown if the argument is null.
     * @throws DatabaseException        Thrown if the key is not present in the database.
     */
    void remove(String key) throws DatabaseException;

    /**
     * Gets the value of a stored key/value mapping in the database.
     *
     * @param key The key of the key/value mapping.
     * @return The value of the key/value mapping or null if the key does not correspond to an
     * existing mapping.
     * @throws DatabaseException        Thrown if an internal error occurs.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    String get(String key) throws DatabaseException;
}
