// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!
package homework03.util;

/**
 * An exception to be used when a SCIPER tries to book twice the same day.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public class AlreadyBookedException extends RuntimeException {

    public AlreadyBookedException(String message) {
        super(message);
    }
}