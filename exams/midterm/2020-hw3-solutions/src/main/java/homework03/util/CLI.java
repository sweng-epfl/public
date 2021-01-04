// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!
package homework03.util;

/**
 * A command-line interface for bookings.
 * 
 * It receives an instance of `Client` as argument of its constructor.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface CLI {

    /**
     * Gets the query from standard input and executes the command given by the user, printing the result.
     * <p>
     * Possible commands:
     * <p>
     * - book DATE
     * <p>
     * - getBookings
     * <p>
     * The DATE format is dd.MM.yyyy. (WARNING: the DATE must exist!!)
     * <p>
     * You can assume that correct commands will have only one space character between each argument and no before nor
     * after. Everything that does not conform to this format can be considered as being wrongly formatted.
     * <p>
     * The method prints "Please enter your query:\n" and then waits for an input.
     * <p>
     * The outcomes for the command must have this form:
     * book DATE:
     * - if the booking is successful: "Your booking for {DATE} was successful.\n"
     * - if the booking is NOT successful because all slots are occupied: "Your booking for {DATE} was unsuccessful. All the available slots are occupied.\n"
     * - if the booking is NOT successful because your already booked this date: "Your booking for {DATE} was unsuccessful. You already booked this date.\n"
     * - if the date is wrong:
     * "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.\n"
     * - if the booking is NOT successful for some other reason: "An error occurred, please try again later.\n"
     * <p>
     * where {DATE} is replaced by the date booked
     * <p>
     * getBookings:
     * - if the bookings are retrieved:
     * "Here are your current booking(s):\n"
     * then each booking on a new line preceded by a tabulation character:
     * "\t{BOOKING}\n" where {BOOKING} is replaced by a booking's date. The order does not matter.
     * - if an error occurs: "An error occurred, please try again later.\n"
     * <p>
     * <p>
     * If the command has the wrong format: "Your query does not conform to the syntax.\n"
     */
    void getQueryAndExecute();
}
