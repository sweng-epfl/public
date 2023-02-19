package ch.epfl.sweng.project.geocoding

import android.content.Context
import android.location.Geocoder
import ch.epfl.sweng.project.location.Location
import java.io.IOException
import java.util.*

class AndroidGeocodingService internal constructor(private val geocoder: Geocoder) :
    GeocodingService {
    @Throws(IOException::class)
    override fun getLocation(address: String): Location {
        return geocoder.getFromLocationName(address, 5)
            .stream()
            .filter { addr: android.location.Address -> addr.hasLatitude() && addr.hasLongitude() }
            .map { addr: android.location.Address -> Location(addr.latitude, addr.longitude) }
            .findFirst()
            .get()
    }

    @Throws(IOException::class)
    override fun getAddress(location: Location): Address {
        val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)[0]
        val addressLines: MutableList<String> = ArrayList()
        for (i in 0..address.maxAddressLineIndex) addressLines.add(address.getAddressLine(i))
        return Address(addressLines)
    }

    companion object {
        fun fromContext(context: Context?): GeocodingService {
            return AndroidGeocodingService(Geocoder(context))
        }
    }
}