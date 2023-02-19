package ch.epfl.sweng.project.geocoding

import ch.epfl.sweng.project.location.Location
import java.io.IOException

/**
 * This service enables you to get a [ch.epfl.sweng.project.location.Location] from a String
 * representing a location (i.e. a city, a street, a country...), or to get a [Address] from
 * a [ch.epfl.sweng.project.location.Location]
 */
interface GeocodingService {
    /**
     * Gets the location of a textual address
     *
     * @param address the address, i.e. Lausanne, Switzerland
     * @return the location for this address
     *
     * @throws IOException if the network is unavailable
     */
    @Throws(IOException::class)
    fun getLocation(address: String): Location?

    /**
     * Get the address corresponding to a location
     *
     * @param location the location for which you want an address
     * @return the address of this location
     *
     * @throws IOException if the network is unavailable
     */
    @Throws(IOException::class)
    fun getAddress(location: Location): Address?
}