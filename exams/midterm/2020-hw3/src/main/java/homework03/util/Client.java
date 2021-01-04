// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!
package homework03.util;

import java.util.Set;
import java.util.function.Consumer;

/**
 * A client used to book days. It is responsible for checking that the received SCIPER's format is valid. It also
 * receives a Server instance in the constructor. It does NOT check however the date format.
 * 
 * Reminder: a valid SCIPER is composed of 6 digits from 0 to 9.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface Client {

    /**
     * Books the given day for the SCIPER passed at instantiation. You can assume that the day has a valid format,
     * although you still need to check for and prevent null argument.
     *
     * @param day       the day to book
     * @param onSuccess callback to execute on success; it receives the booked day as argument
     * @param onError   callback to execute on error; it receives the eventual exception as argument
     *                  the exception is an instance of {@link DayFullException} if 5 other SCIPERs already booked the given day
     *                  the exception is an instance of {@link AlreadyBookedException} if the SCIPER already booked the given day
     */
    void book(String day, Consumer<String> onSuccess, Consumer<Exception> onError);

    /**
     * Gets all the bookings as a set.
     *
     * @param onSuccess callback to execute on success; it receives the set of bookings as argument
     * @param onError   callback to execute on error; it receives the eventual exception as argument
     */
    void getBookings(Consumer<Set<String>> onSuccess, Consumer<Exception> onError);
}
