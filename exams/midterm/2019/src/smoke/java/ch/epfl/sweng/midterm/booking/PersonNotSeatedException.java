package ch.epfl.sweng.midterm.booking;

public class PersonNotSeatedException extends Exception {
    public PersonNotSeatedException(String message) {
        super(message);
    }
}
