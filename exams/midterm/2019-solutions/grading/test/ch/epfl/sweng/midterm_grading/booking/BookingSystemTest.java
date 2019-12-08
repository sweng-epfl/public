package ch.epfl.sweng.midterm_grading.booking;

import ch.epfl.sweng.midterm.booking.*;
import grading.GradedCategory;
import grading.GradedTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@GradedCategory("Practice question 1")
class BookingSystemTest {
    BookingSystem bs;
    Person validPerson = new Person("John", "Doe", "Swiss");
    Person validPerson2 = new Person("Lorem", "Ipsum", "English");
    String validSeat = "14C";

    @BeforeEach
    public void setup() {
        bs = new SwengBookingSystem();
    }

    @GradedTest("after chooseSeat in a valid seat, getPersonAtSeat should return the seated person")
    public void t00_validSeat() throws IllegalSeatException, SeatAlreadyTakenException, PersonAlreadySeatedException {
        String seat = "14C";
        bs.chooseSeat(validPerson, seat);
        assertEquals(Optional.of(validPerson), bs.getPersonAtSeat(seat));
    }

    @GradedTest("after chooseSeat in a valid seat, getSeatForPerson should return the seat number")
    public void t01_validSeat2() throws IllegalSeatException, SeatAlreadyTakenException, PersonAlreadySeatedException {
        String seat = "22A";
        bs.chooseSeat(validPerson, seat);
        assertEquals(Optional.of(seat), bs.getSeatForPerson(validPerson));
    }

    private void checkInvalidSeatNumber(Function<String, Executable> executables) {
        // Valid seats
        for (int i = 1; i <= 26; ++i) {
            for (char c : "ABCDEF".toCharArray()) {
                assertDoesNotThrow(executables.apply(i + "" + c), "seat number " + i + c);
            }
        }

        // Invalid samples
        assertThrows(IllegalSeatException.class, executables.apply("321C"), "321C");
        assertThrows(IllegalSeatException.class, executables.apply("0A"), "0A");
        assertThrows(IllegalSeatException.class, executables.apply("27A"), "27A");

        for (char c : "GHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzéàè&%.-_!$£".toCharArray()) {
            for (int i = 0; i < 100; ++i) {
                String seat = i + "" + c;
                assertThrows(IllegalSeatException.class, executables.apply(seat), seat);
            }
        }

        for (int i = 1; i <= 26; ++i) {
            for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzéàè&%.-_!$£".toCharArray()) {
                int finalI = i;
                assertThrows(IllegalSeatException.class, executables.apply(c + "" + finalI));
            }
        }

        for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzéàè&%.-_!$£".toCharArray()) {
            assertThrows(IllegalSeatException.class, executables.apply(c + ""));
        }

        for (int i = 1; i <= 10000; ++i) {
            int finalI = i;
            assertThrows(IllegalSeatException.class, executables.apply("" + finalI));
        }

        assertThrows(IllegalSeatException.class, executables.apply(""));
        assertThrows(IllegalSeatException.class, executables.apply(" "));
    }

    @GradedTest("chooseSeat accepts only valid seats")
    public void t03_chooseSeatInvalidSeatNumber() throws IllegalSeatException, SeatAlreadyTakenException, PersonAlreadySeatedException {
        checkInvalidSeatNumber(sn -> () -> bs.chooseSeat(new Person(sn, sn, sn), sn));
    }

    @GradedTest("getPersonAtSeat accepts only valid seats")
    public void t05_getPersonAtSeatInvalidSeatNumbers() throws IllegalSeatException, SeatAlreadyTakenException, PersonAlreadySeatedException {
        checkInvalidSeatNumber(seat -> () -> bs.getPersonAtSeat(seat));
    }

    @GradedTest("chooseSeat only succeeds if the person has no seat")
    public void t06_personAlreadySeated()
            throws IllegalSeatException, SeatAlreadyTakenException, PersonAlreadySeatedException {
        assertDoesNotThrow(() -> bs.chooseSeat(validPerson, validSeat));
        assertThrows(PersonAlreadySeatedException.class, () -> {
            bs.chooseSeat(validPerson, "13C");
        });
    }

    @GradedTest("chooseSeat only succeeds if the seat is empty")
    public void t07_seatAlreadyTaken()
            throws IllegalSeatException, SeatAlreadyTakenException, PersonAlreadySeatedException {
        assertDoesNotThrow(() -> bs.chooseSeat(validPerson, validSeat));
        assertThrows(SeatAlreadyTakenException.class, () -> {
            bs.chooseSeat(validPerson2, validSeat);
        });
    }

    @GradedTest("after calling freeSeat on a seated person, that person is no longer seated")
    public void t08_freeWorks() throws IllegalSeatException, SeatAlreadyTakenException, PersonAlreadySeatedException,
            PersonNotSeatedException {
        assertDoesNotThrow(() -> bs.chooseSeat(validPerson, validSeat));
        assertEquals(Optional.of(validPerson), bs.getPersonAtSeat(validSeat));
        assertEquals(Optional.of(validSeat), bs.getSeatForPerson(validPerson));

        assertDoesNotThrow(() -> bs.freeSeat(validPerson));
        assertEquals(Optional.empty(), bs.getSeatForPerson(validPerson));
        assertEquals(Optional.empty(), bs.getPersonAtSeat(validSeat));
    }

    @GradedTest("Calling freeSeat on an unseated person fails")
    public void t09_freeNotSeated() throws PersonNotSeatedException {
        assertThrows(PersonNotSeatedException.class, () -> {
            bs.freeSeat(validPerson);
        });
    }
}
