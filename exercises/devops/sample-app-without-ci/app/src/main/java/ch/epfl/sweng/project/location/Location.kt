package ch.epfl.sweng.project.location

/**
 * This class represents a location in geodesic coordinates (latitude, longitude).
 *
 * Solution notes:
 * - We used `final` fields here. Using `final` makes your location immutable, which is
 * definitely a desired property here - as a location doesn't need to change.
 * - We used public fields instead of writing getter methods. This is not so common in Java, but
 * is fine for a data class since the fields are final and can therefore not be modified
 * by the client.
 * - A design using Interfaces instead of a final class could make sense, depending on the
 * system you are trying to build.
 */
class Location(val latitude: Double, val longitude: Double)