package ch.epfl.sweng;

/**
 * Do not modify this file!
 * An exception that signals that the requested address was not found in
 * the current address directory.
 * If the address does not make sense at all, throw
 * {@link InvalidAddressException} instead.
 */

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException(String cause) {
        super(cause);
    }
}
