package ch.epfl.sweng;

import ch.epfl.sweng.database.Database;
import ch.epfl.sweng.database.DatabaseException;

/**
 * This class adapts a {@link Database} so that it implements the {@link StudentDatabase} interface.
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
public final class StudentDatabaseAdapter implements StudentDatabase {

    /**
     * Constructs a new student database adapter.
     *
     * @param database The adapted database.
     * @throws IllegalArgumentException Thrown if the argument is null.
     */
    public StudentDatabaseAdapter(Database database) {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    @Override
    public void put(Student student) throws DatabaseException {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    @Override
    public void remove(String sciper) throws DatabaseException {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    @Override
    public Student get(String sciper) throws DatabaseException {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }
}
