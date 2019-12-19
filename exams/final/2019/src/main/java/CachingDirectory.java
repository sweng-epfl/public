// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.List;

/**
 * Directory with caching; searches are passed through the wrapped Directory,
 * and the CachingDirectory remembers the results such that it returns
 * these same results on subsequent searches of the same name,
 * without calling the underlying directory again.
 * --
 * Name comparisons for the purpose of caching are case-insensitive, and ignore leading and trailing spaces.
 */
final class CachingDirectory implements Directory {
    /**
     * Creates a new CachingDirectory wrapping the given Directory.
     *
     * @throws IllegalArgumentException if and only if the wrapped directory is null.
     */
    CachingDirectory(Directory wrapped) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Person> search(String name) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
