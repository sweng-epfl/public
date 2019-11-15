package ch.epfl.sweng.midterm.booking;

// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

import javax.annotation.Nonnull;
import java.util.Optional;

public interface BookingSystem {
    /**
     * Save a seat preference
     * 
     * @param person the person to seat
     * @param seat   the seat at which the person should be seated
     * @throws IllegalSeatException         if the seat is not valid (format invalid
     *                                      or too big for the plane)
     * @throws PersonAlreadySeatedException if the person has already a seat in the
     *                                      plane
     * @throws SeatAlreadyTakenException    if the seat is already taken by a person
     */
    void chooseSeat(@Nonnull Person person, @Nonnull String seat)
            throws IllegalSeatException, PersonAlreadySeatedException, SeatAlreadyTakenException;

    /**
     * Free the seat occupied by a person.
     * 
     * @param person the person whom seat should be freed
     * @throws PersonNotSeatedException if the person occupies no seat
     */
    void freeSeat(@Nonnull Person person) throws PersonNotSeatedException;

    /**
     * Get who is seated there, or an empty optional if no one is seated here
     * 
     * @param seat the seat to query
     * @throws IllegalSeatException if the seat is not valid (format invalid or too
     *                              big for the little plane)
     */
    Optional<Person> getPersonAtSeat(@Nonnull String seat) throws IllegalSeatException;

    /**
     * Get the seat where the person is seated, or an empty optional if the person
     * has no seat
     */
    Optional<String> getSeatForPerson(@Nonnull Person person);

}
