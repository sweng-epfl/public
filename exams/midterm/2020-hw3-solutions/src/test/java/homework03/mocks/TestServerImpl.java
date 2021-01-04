package homework03.mocks;

import homework03.util.AlreadyBookedException;
import homework03.util.Database;
import homework03.util.DayFullException;
import homework03.util.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class TestServerImpl implements Server {

    private static final int BOOKINGS_PER_DAY_THRESHOLD = 5;
    private final Database database;
    private final Map<String, Integer> bookingsPerDay;
    private BookFunction bookImpl;
    private GetBookingsFunction getBookingsImpl;

    public TestServerImpl(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }

        this.database = database;
        this.bookingsPerDay = new HashMap<>();

        this.bookImpl = (sciper, day, onSuccess, onError) -> {
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

            this.database.get(sciper,
                    bookedDates -> {
                        if (bookedDates.contains(day)) {
                            onError.accept(new AlreadyBookedException("This SCIPER already booked this day"));
                            return;
                        }

                        if (this.bookingsPerDay.computeIfAbsent(day, d -> 0) > BOOKINGS_PER_DAY_THRESHOLD) {
                            onError.accept(new DayFullException(String.format("The day %s is full", day)));
                            return;
                        }

                        this.bookingsPerDay.compute(day, (d, amount) -> amount != null ? ++amount : 1);
                        this.database.put(sciper, day, onSuccess, onError);
                    },
                    onError);
        };

        this.getBookingsImpl = this.database::get;
    }

    @Override
    public void book(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError) {
        this.bookImpl.accept(sciper, day, onSuccess, onError);
    }

    public void setBookImpl(BookFunction bookFunction) {
        if (bookFunction == null) {
            throw new IllegalArgumentException();
        }

        this.bookImpl = bookFunction;
    }

    @Override
    public void getBookings(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError) {
        this.getBookingsImpl.accept(sciper, onSuccess, onError);
    }

    public void setGetBookingsImpl(GetBookingsFunction getBookingsFunction) {
        if (getBookingsFunction == null) {
            throw new IllegalArgumentException();
        }

        this.getBookingsImpl = getBookingsFunction;
    }

    @FunctionalInterface
    public interface BookFunction {
        void accept(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError);
    }

    @FunctionalInterface
    public interface GetBookingsFunction {
        void accept(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError);
    }
}
