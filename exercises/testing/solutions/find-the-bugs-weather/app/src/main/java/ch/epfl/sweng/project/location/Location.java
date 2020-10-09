package ch.epfl.sweng.project.location;

import ch.epfl.sweng.project.utils.Tuples2;

/**
 * This class represents a location in geodesic coordinates (latitude, longitude).
 *
 * Solution notes:
 *  - We used `final` fields here. Using `final` makes your location immutable, which is
 *      definitely a desired property here - as a location doesn't need to change.
 *  - We used public fields instead of writing getter methods. This is not so common in Java, but
 *      is fine for a data class since the fields are final and can therefore not be modified
 *      by the client.
 *  - A design using Interfaces instead of a final class could make sense, depending on the
 *      system you are trying to build.
 */
public final class Location {
    public final Tuples2<Double, Double> latitudeLongitude;

    public Location(Tuples2<Double, Double> latitudeLongitudeTuple) {
        this.latitudeLongitude = new Tuples2<>(latitudeLongitudeTuple.getFirst(), latitudeLongitudeTuple.getSecond());
    }

    public Tuples2<Double, Double> getLatitudeLongitudeTuple(){
        return new Tuples2<>(this.latitudeLongitude.getFirst(), this.latitudeLongitude.getSecond());
    }
}
