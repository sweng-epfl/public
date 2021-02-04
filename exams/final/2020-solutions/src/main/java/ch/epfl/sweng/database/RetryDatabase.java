package ch.epfl.sweng.database;

/**
 * This class decorates an existing {@link Database} implementation and adds a retry functionality.
 * If a query to the underlying database fails with a {@link DatabaseException}, then this class
 * tries again, up to 10 attempts.
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
public final class RetryDatabase implements Database {

    private final static int RETRY_COUNT = 10;
    private Database database;

    /**
     * Constructs a new retry database decorator.
     *
     * @param database The underlying database
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public RetryDatabase(Database database) {
        if (database == null) {
            throw new IllegalArgumentException();
        }

        this.database = database;
    }

    @Override
    public void put(String key, String value) {
        this.database.put(key, value);
    }

    @Override
    public void remove(String key) throws DatabaseException {
        this.database.remove(key);
    }

    /**
     * If the database fails with a {@link DatabaseException}, then this method
     * tries again, up to 10 attempts (i.e., this method also fails if the database fails
     * on the 10th try).
     *
     * @throws DatabaseException Thrown if the maximum number of retries is exceeded.
     */
    @Override
    public String get(String key) throws DatabaseException {
        for (int i = 0; i < RETRY_COUNT; i++) {
            try {
                return this.database.get(key);
            } catch (DatabaseException ignored) {
                // Nothing
            }
        }

        throw new DatabaseException("exceeded maximum number of retries");
    }
}
