package ch.epfl.sweng.project.location;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

public final class AndroidLocationService implements LocationService {
    private final LocationManager locationManager;
    private final String locationProvider;
    private final Criteria locationCriteria;

    AndroidLocationService(LocationManager locationManager, String locationProvider, Criteria locationCriteria) {
        this.locationManager = locationManager;
        this.locationProvider = locationProvider;
        this.locationCriteria = locationCriteria;
    }

    private static LocationManager buildManagerFromContext(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Return a new LocationsService with a fixed location provider
     *
     * @param context  the context of the activity invoking this service
     * @param provider the location provider to use
     * @return
     */
    public static LocationService buildFromContextAndProvider(Context context, String provider) {
        return new AndroidLocationService(
                buildManagerFromContext(context),
                provider,
                null
        );
    }

    /**
     * Return a new LocationsService with a criteria to choose the best location provider
     *
     * @param context  the context of the activity invoking this service
     * @param criteria the criteria used to choose which location provider to use
     * @return
     */
    public static LocationService buildFromContextAndCriteria(Context context, Criteria criteria) {
        return new AndroidLocationService(
                buildManagerFromContext(context),
                null,
                criteria
        );
    }

    private String getLocationProvider() {
        if (this.locationProvider != null) {
            return this.locationProvider;
        }

        return this.locationManager.getBestProvider(this.locationCriteria, true);
    }

    @Override
    public Location getCurrentLocation() {
        try {
            android.location.Location loc = this.locationManager.getLastKnownLocation(getLocationProvider());

            if (loc == null) return null;

            return new Location(loc.getLatitude(), loc.getLongitude());
        } catch (SecurityException ex) {
            // We need to explicitly catch this exception, even though we throw it again immediately
            throw ex;
        }

    }
}
