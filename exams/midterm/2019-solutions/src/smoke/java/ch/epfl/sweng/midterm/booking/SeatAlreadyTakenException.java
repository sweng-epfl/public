package ch.epfl.sweng.midterm.booking;

public class SeatAlreadyTakenException extends Exception {
    public SeatAlreadyTakenException(String message) {
        super(message);
    }
}
