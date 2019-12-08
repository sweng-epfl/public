package ch.epfl.sweng.midterm.booking;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Louis Vialar
 */
public class SwengBookingSystem implements BookingSystem {
    private Map<String, Person> seatAssignment = new HashMap<>();

    private boolean validSeat(String seat) {
        Pattern p = Pattern.compile("(\\d+)[A-F]");
        Matcher m = p.matcher(seat);
        if (!m.matches()) {
            return false;
        }

        int seatNumber = Integer.parseInt(m.group(1));
        return seatNumber >= 1 && seatNumber <= 26;
    }

    @Override
    public void chooseSeat(Person person, String seat)
            throws IllegalSeatException, PersonAlreadySeatedException, SeatAlreadyTakenException {
        if (seatAssignment.containsValue(person)) {
            throw new PersonAlreadySeatedException(person + " already seated");
        }
        if (seatAssignment.containsKey(seat)) {
            throw new SeatAlreadyTakenException(seat + " already taken");
        }
        if (!validSeat(seat)) {
            throw new IllegalSeatException("Illegal seat");
        }
    }

    @Override
    public void freeSeat(Person person) throws PersonNotSeatedException {
        Optional<String> seat = getSeatForPerson(person);
        if (seat.isPresent()) {
            seatAssignment.remove(seat.get());
        } else {
            throw new PersonNotSeatedException(person + " not seated");
        }
    }

    @Override
    public Optional<Person> getPersonAtSeat(String seat) throws IllegalSeatException {
        if (!validSeat(seat)) {
            throw new IllegalSeatException("Illegal seat");
        }
        if (seatAssignment.containsKey(seat)) {
            return Optional.of(seatAssignment.get(seat));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getSeatForPerson(Person person) {
        for (Map.Entry<String, Person> entry : seatAssignment.entrySet()) {
            if (entry.getValue().equals(person)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }
}
