package homework03.mocks;

import homework03.util.Client;
import homework03.util.Server;

import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class TestClientImpl implements Client {

    private final String sciper;
    private final Server server;
    private BookFunction bookImpl;
    private GetBookingsFunction getBookingsImpl;

    public TestClientImpl(String sciper, Server server) {
        if (sciper == null) {
            throw new IllegalArgumentException("The SCIPER cannot be null");
        }

        if (!Pattern.matches("[0-9]{6}", sciper)) {
            throw new IllegalArgumentException("The SCIPER must be in the format XXXXXX where X is a digit from 0 to 9");
        }

        if (server == null) {
            throw new IllegalArgumentException("The server cannot be null");
        }

        this.sciper = sciper;
        this.server = server;

        this.bookImpl = (day, onSuccess, onError) -> {
            if (day == null) {
                throw new IllegalArgumentException("The day cannot be null");
            }

            if (onSuccess == null) {
                throw new IllegalArgumentException("onSuccess callback cannot be null");
            }

            if (onError == null) {
                throw new IllegalArgumentException("onError callback cannot be null");
            }


            this.server.book(this.sciper, day, onSuccess, onError);
        };

        this.getBookingsImpl = (onSuccess, onError) -> {
            if (onSuccess == null) {
                throw new IllegalArgumentException("onSuccess callback cannot be null");
            }

            if (onError == null) {
                throw new IllegalArgumentException("onError callback cannot be null");
            }

            this.server.getBookings(this.sciper, onSuccess, onError);
        };
    }

    @Override
    public void book(String day, Consumer<String> onSuccess, Consumer<Exception> onError) {
        this.bookImpl.accept(day, onSuccess, onError);
    }

    public void setBookImpl(BookFunction bookFunction) {
        if (bookFunction == null) {
            throw new IllegalArgumentException();
        }

        this.bookImpl = bookFunction;
    }

    @Override
    public void getBookings(Consumer<Set<String>> onSuccess, Consumer<Exception> onError) {
        this.getBookingsImpl.accept(onSuccess, onError);
    }

    public void setGetBookingsImpl(GetBookingsFunction getBookingsFunction) {
        if (getBookingsFunction == null) {
            throw new IllegalArgumentException();
        }

        this.getBookingsImpl = getBookingsFunction;
    }

    @FunctionalInterface
    public interface BookFunction {
        void accept(String day, Consumer<String> onSuccess, Consumer<Exception> onError);
    }

    @FunctionalInterface
    public interface GetBookingsFunction {
        void accept(Consumer<Set<String>> onSuccess, Consumer<Exception> onError);
    }
}
