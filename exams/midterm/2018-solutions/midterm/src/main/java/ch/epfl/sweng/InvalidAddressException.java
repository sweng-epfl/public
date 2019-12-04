package ch.epfl.sweng;

/**
 * Do not modify this file!
 * An exception that signals that the requested address does not make sense
 * or ill formed. For example, an empty list, or missing a house number.
 * If the address is sensible, but does not exist, throw
 * {@link AddressNotFoundException} instead.
 */

public class InvalidAddressException extends Exception {
    public InvalidAddressException(String cause) {
        super(cause);
    }
}
