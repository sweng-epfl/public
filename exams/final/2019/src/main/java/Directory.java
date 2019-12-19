// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

import java.util.List;

@FunctionalInterface
interface Directory {
    /**
     * Searches for people with a name close to the given name.
     * Never returns a null list.
     * Never returns null list items.
     *
     * @throws IllegalArgumentException if and only if at least one of the following issues arise:
     *                                  the name is null, the name is empty, or the name only contains spaces.
     */
    List<Person> search(String name);
}
