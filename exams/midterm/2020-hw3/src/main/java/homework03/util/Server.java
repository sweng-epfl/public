// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!
package homework03.util;

import java.util.Set;
import java.util.function.Consumer;

/**
 * A server used to handle bookings. It checks that bookings are valid. It does NOT check however the format of the
 * SCIPER nor the date's.
 * 
 * It receives an instance of `Database` as argument of its constructor.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface Server {

    /**
     * Books the given day for the given SCIPER.
     * A SCIPER can book at most once a given date.
     * No more than 5 (<= 5) SCIPERs can book a given date.
     * You can assume that the SCIPER and the day have a valid format, although you still need to check for and prevent null arguments.
     *
     * @param sciper    the SCIPER of the student booking the day
     * @param day       the day to book
     * @param onSuccess callback to execute on success; it receives the booked day as argument
     * @param onError   callback to execute on error; it receives the eventual exception as argument;
     *                  the exception is an instance of {@link DayFullException} if 5 other SCIPERs already booked the given day
     *                  the exception is an instance of {@link AlreadyBookedException} if the given SCIPER already booked the given day
     */
    void book(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError);

    /**
     * Gets all the bookings for the given SCIPER as a set.
     *
     * @param sciper    the SCIPER to use
     * @param onSuccess callback to execute on success; it receives the set of bookings as argument
     * @param onError   callback to execute on error; it receives the eventual exception as argument
     */
    void getBookings(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError);
}
