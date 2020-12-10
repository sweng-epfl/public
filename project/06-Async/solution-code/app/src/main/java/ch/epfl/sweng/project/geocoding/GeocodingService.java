package ch.epfl.sweng.project.geocoding;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import ch.epfl.sweng.project.location.Location;

/**
 * This service enables you to get a {@link ch.epfl.sweng.project.location.Location} from a String
 * representing a location (i.e. a city, a street, a country...), or to get a {@link Address} from
 * a {@link ch.epfl.sweng.project.location.Location}
 */
public interface GeocodingService {

    /**
     * Gets the location of a textual address
     *
     * @param address the address, i.e. Lausanne, Switzerland
     * @return a future holding the location for this address, or a {@link IOException} in case of error
     */
    CompletionStage<Location> getLocation(@NonNull String address);

    /**
     * Get the address corresponding to a location
     *
     * @param location the location for which you want an address
     * @return a future holding the address of this location, or a {@link IOException} in case of error
     */
    CompletionStage<Address> getAddress(@NonNull Location location);
}
