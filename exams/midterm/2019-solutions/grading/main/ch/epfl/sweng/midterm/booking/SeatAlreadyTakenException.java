package ch.epfl.sweng.midterm.booking;

// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class SeatAlreadyTakenException extends Exception {
    public SeatAlreadyTakenException() {
        super();
    }

    public SeatAlreadyTakenException(String message) {
        super(message);
    }
}
