package ch.epfl.sweng.project.location;

import java.util.function.Consumer;

/**
 * This service allows you to get the current location of the user.
 */
public interface LocationService {
    /**
     * Finds the current location of the user and pass it to the given callback function.
     *
     * @return the current location of the user
     */
    Location getCurrentLocation();

    /**
     * Subscribes to get location updates from the location service. Every time a new location that
     * is at least {@code minDistanceDelta} meters away from previous location is available, the
     * provided consumer will be called with this new location.
     *
     * @param consumer         a {@link Consumer} whose
     *                         {@link Consumer#accept} method will be called for
     *                         each location update
     * @param minTimeDelta     minimum time interval between location updates, in milliseconds
     * @param minDistanceDelta minimum distance between location updates, in meters
     * @return the id of the subscription, used to cancel it using {@link LocationService#unsubscribeFromLocationUpdates(int)}
     */
    int subscribeToLocationUpdates(Consumer<Location> consumer, long minTimeDelta, long minDistanceDelta);

    /**
     * Unsubscribes from location updates.
     *
     * @param subscriptionId the id of the subscription to cancel. The consumer will no longer receive
     *                       location updates.
     */
    void unsubscribeFromLocationUpdates(int subscriptionId);

}
