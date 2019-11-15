package ch.epfl.sweng.midterm.booking;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit the names, parameters or return types of existing methods/constructors
// You CANNOT add or remove checked exceptions to existing methods/constructors
// You CANNOT delete existing methods/constructors
// You CAN change the bodies of existing methods/constructors
// You CAN add new methods/constructors
// You CAN add interface implementations
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SwengBookingSystem implements BookingSystem {
    private Map<String, Person> seatAssignment = new HashMap<>();

    @Override
    public void chooseSeat(@Nonnull Person person, @Nonnull String seat)
            throws IllegalSeatException, PersonAlreadySeatedException, SeatAlreadyTakenException {

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
