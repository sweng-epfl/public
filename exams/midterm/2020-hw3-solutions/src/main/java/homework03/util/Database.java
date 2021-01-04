// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!
package homework03.util;

import java.util.Set;
import java.util.function.Consumer;

/**
 * A database storing the days booked by each SCIPER. It does NOT perform any checks, neither on SCIPER or date format
 * nor on bookings' validity.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface Database {

    /**
     * Adds the given day to the bookings of the given SCIPER, calling the right callback afterwards. You can assume
     * that the SCIPER and the day have a valid format, although you still need to check for and prevent null arguments.
     *
     * @param sciper    the SCIPER of the student booking the day
     * @param day       the day to book
     * @param onSuccess callback to execute on success; it receives the booked day as argument
     * @param onError   callback to execute on error; it receives the eventual exception as argument
     */
    void put(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError);

    /**
     * Gets all the bookings for the given SCIPER as a set, calling the right callback afterwards.
     * If a SCIPER booked no dates, returns the empty set.
     * You can assume that the SCIPER has a valid format, although you still need to check for and prevent null arguments.
     *
     * @param sciper    the SCIPER to use
     * @param onSuccess callback to execute on success; it receives the set of bookings as argument.
     *                  The set is the empty set if the SCIPER never booked a date.
     * @param onError   callback to execute on error; it receives the eventual exception as argument
     */
    void get(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError);

    /**
     * Gets the number of SCIPERs that booked the given day as an integer, calling the right callback afterwards.
     * You can assume that the day has a valid format, although you still need to check for and prevent null arguments.
     *
     * @param day       the day to check for
     * @param onSuccess callback to execute on success; it receives the number of bookings for the given day as argument
     * @param onError   callback to execute on error; it receives the eventual exception as argument
     */
    void getBookingsCount(String day, Consumer<Integer> onSuccess, Consumer<Exception> onError);
}
