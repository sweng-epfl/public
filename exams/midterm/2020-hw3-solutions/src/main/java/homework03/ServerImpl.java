// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
package homework03;

import homework03.util.AlreadyBookedException;
import homework03.util.Database;
import homework03.util.DayFullException;
import homework03.util.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN change the bodies of existing methods/constructors
 * You CAN add new private methods/constructors
 * You CANNOT add interface implementations unless explicitly instructed to do so
 * You CANNOT add new public, package-private or protected methods/constructors
 * You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
 * You CANNOT delete existing methods/constructors
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class ServerImpl implements Server {

    private static final int BOOKINGS_PER_DAY_THRESHOLD = 5;

    private final Database database;
    private final Map<String, Integer> bookingsPerDay;

    public ServerImpl(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }

        this.database = database;
        this.bookingsPerDay = new HashMap<>();
    }

    @Override
    public void book(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError) {
        if (sciper == null) {
            throw new IllegalArgumentException("The SCIPER cannot be null");
        }

        if (day == null) {
            throw new IllegalArgumentException("The day cannot be null");
        }

        if (onSuccess == null) {
            throw new IllegalArgumentException("onSuccess callback cannot be null");
        }

        if (onError == null) {
            throw new IllegalArgumentException("onError callback cannot be null");
        }

        database.get(sciper,
                bookedDates -> {
                    if (bookedDates.contains(day)) {
                        onError.accept(new AlreadyBookedException("This SCIPER already booked this day"));
                        return;
                    }

                    if (bookingsPerDay.computeIfAbsent(day, d -> 0) > BOOKINGS_PER_DAY_THRESHOLD) {
                        onError.accept(new DayFullException(String.format("The day %s is full", day)));
                        return;
                    }

                    bookingsPerDay.compute(day, (d, amount) -> amount != null ? ++amount : 1);
                    database.put(sciper, day, onSuccess, onError);
                },
                onError);
    }

    @Override
    public void getBookings(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError) {
        database.get(sciper, onSuccess, onError);
    }
}
