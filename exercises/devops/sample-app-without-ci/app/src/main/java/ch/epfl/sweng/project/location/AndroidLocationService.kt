package ch.epfl.sweng.project.location

import android.content.Context
import android.location.Criteria
import android.location.LocationManager

class AndroidLocationService internal constructor(
    private val locationManager: LocationManager,
    private val locationProvider: String?,
    private val locationCriteria: Criteria?
) : LocationService {
    private fun getLocationProvider(): String? {
        return locationProvider ?: locationManager.getBestProvider(locationCriteria!!, true)
    }

    // We need to explicitly catch this exception, even though we throw it again immediately
    override val currentLocation: Location?
        get() = try {
            val loc = locationManager.getLastKnownLocation(
                getLocationProvider()!!
            )
            Location(loc!!.latitude, loc.longitude)
        } catch (ex: SecurityException) {
            // We need to explicitly catch this exception, even though we throw it again immediately
            throw ex
        }

    companion object {
        private fun buildManagerFromContext(context: Context): LocationManager {
            return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        /**
         * Return a new LocationsService with a criteria to choose the best location provider
         *
         * @param context  the context of the activity invoking this service
         * @param criteria the criteria used to choose which location provider to use
         * @return
         */
        fun buildFromContextAndCriteria(context: Context, criteria: Criteria?): LocationService {
            return AndroidLocationService(
                buildManagerFromContext(context),
                null,
                criteria
            )
        }
    }
}