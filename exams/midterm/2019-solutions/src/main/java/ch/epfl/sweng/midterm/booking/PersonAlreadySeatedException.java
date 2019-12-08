package ch.epfl.sweng.midterm.booking;

// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class PersonAlreadySeatedException extends Exception {
    public PersonAlreadySeatedException() {
        super();
    }

    public PersonAlreadySeatedException(String message) {
        super(message);
    }
}
