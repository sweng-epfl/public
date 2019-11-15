package ch.epfl.sweng.midterm.booking;

public class PersonAlreadySeatedException extends Exception {
    public PersonAlreadySeatedException(String message) {
        super(message);
    }
}
