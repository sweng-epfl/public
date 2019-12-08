package ch.epfl.sweng.midterm.booking;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Louis Vialar
 */
public class SwengBookingSystem implements BookingSystem {
    private Map<String, Person> seatAssignment = new HashMap<>();

    @Override
    public void chooseSeat(@Nonnull Person person, @Nonnull String seat) throws IllegalSeatException, PersonAlreadySeatedException, SeatAlreadyTakenException {

    }

    @Override
    public void freeSeat(@Nonnull Person person) throws PersonNotSeatedException {

    }

    @Override
    public Optional<Person> getPersonAtSeat(@Nonnull String seat) throws IllegalSeatException {
        return Optional.empty();
    }

    @Override
    public Optional<String> getSeatForPerson(@Nonnull Person person) {
        return Optional.empty();
    }
}
