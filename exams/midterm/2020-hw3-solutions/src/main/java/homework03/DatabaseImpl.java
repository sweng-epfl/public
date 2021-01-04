// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
package homework03;

import homework03.util.Database;

import java.util.HashMap;
import java.util.HashSet;
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
public class DatabaseImpl implements Database {

    private final Map<String, Set<String>> bookingsPerSciper = new HashMap<>();

    @Override
    public void put(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError) {
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
            bookingsPerSciper.computeIfAbsent(sciper, s -> new HashSet<>()).add(day);
        } catch (Exception e) {
            onError.accept(e);
            return;
        }

        onSuccess.accept(day);
    }

    @Override
    public void get(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError) {
        if (sciper == null) {
            throw new IllegalArgumentException("The SCIPER cannot be null");
        }

        if (onSuccess == null) {
            throw new IllegalArgumentException("onSuccess callback cannot be null");
        }

        if (onError == null) {
            throw new IllegalArgumentException("onError callback cannot be null");
        }

        Set<String> bookings;
        try {
            bookings = bookingsPerSciper.getOrDefault(sciper, new HashSet<>());
        } catch (Exception e) {
            onError.accept(e);
            return;
        }

        if (bookings == null) {
            onError.accept(new NullPointerException());
            return;
        }

        onSuccess.accept(bookings);
    }

    @Override
    public void getBookingsCount(String day, Consumer<Integer> onSuccess, Consumer<Exception> onError) {
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
        try {
            for (Map.Entry<String, Set<String>> entry : this.bookingsPerSciper.entrySet()) {
                if (entry.getValue().contains(day)) {
                    count++;
                }
            }
        } catch (Exception e) {
            onError.accept(e);
            return;
        }

        onSuccess.accept(count);
    }
}
