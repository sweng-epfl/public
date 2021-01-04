package homework03.mocks;

import homework03.util.Database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class TestDatabaseImpl implements Database {

    private final Map<String, Set<String>> bookingsPerSciper = new HashMap<>();
    private PutFunction putImpl;
    private GetFunction getImpl;
    private GetBookingsCountFunction getBookingsCountImpl;

    public TestDatabaseImpl() {
        putImpl = (sciper, day, onSuccess, onError) -> {
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

            try {
                this.bookingsPerSciper.computeIfAbsent(sciper, s -> new HashSet<>()).add(day);
            } catch (Exception e) {
                onError.accept(e);
                return;
            }

            onSuccess.accept(day);
        };

        getImpl = (sciper, onSuccess, onError) -> {
            if (sciper == null) {
                throw new IllegalArgumentException("The SCIPER cannot be null");
            }

            if (onSuccess == null) {
                throw new IllegalArgumentException("onSuccess callback cannot be null");
            }

            if (onError == null) {
                throw new IllegalArgumentException("onError callback cannot be null");
            }

            try {
                Set<String> bookings = this.bookingsPerSciper.getOrDefault(sciper, new HashSet<>());
                onSuccess.accept(bookings);
            } catch (Exception e) {
                onError.accept(e);
            }
        };

        getBookingsCountImpl = (day, onSuccess, onError) -> {
            if (day == null) {
                throw new IllegalArgumentException("The day cannot be null");
            }

            if (onSuccess == null) {
                throw new IllegalArgumentException("onSuccess callback cannot be null");
            }

            if (onError == null) {
                throw new IllegalArgumentException("onError callback cannot be null");
            }

            int count = 0;
            for (Map.Entry<String, Set<String>> entry : this.bookingsPerSciper.entrySet()) {
                if (entry.getValue().contains(day)) {
                    count++;
                }
            }

            onSuccess.accept(count);
        };
    }

    @Override
    public void put(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError) {
        this.putImpl.accept(sciper, day, onSuccess, onError);
    }

    public void setPutImpl(PutFunction putFunction) {
        if (putFunction == null) {
            throw new IllegalArgumentException();
        }

        this.putImpl = putFunction;
    }

    @Override
    public void get(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError) {
        this.getImpl.accept(sciper, onSuccess, onError);
    }

    public void setGetImpl(GetFunction getFunction) {
        if (getFunction == null) {
            throw new IllegalArgumentException();
        }

        this.getImpl = getFunction;
    }

    @Override
    public void getBookingsCount(String day, Consumer<Integer> onSuccess, Consumer<Exception> onError) {
        this.getBookingsCountImpl.accept(day, onSuccess, onError);
    }

    public void setGetBookingsCountImpl(GetBookingsCountFunction getBookingsCountFunction) {
        if (getBookingsCountFunction == null) {
            throw new IllegalArgumentException();
        }

        this.getBookingsCountImpl = getBookingsCountFunction;
    }

    @FunctionalInterface
    public interface PutFunction {
        void accept(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError);
    }

    @FunctionalInterface
    public interface GetFunction {
        void accept(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError);
    }

    @FunctionalInterface
    public interface GetBookingsCountFunction {
        void accept(String day, Consumer<Integer> onSuccess, Consumer<Exception> onError);
    }
}
