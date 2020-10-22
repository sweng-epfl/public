package ch.epfl.sweng.project.location;

import android.location.Criteria;
import android.location.LocationManager;
import ch.epfl.sweng.project.location.LocationModule.LocationProvider;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class AndroidLocationService implements LocationService {

    private final LocationManager locationManager;
    private final String locationProvider;
    private final Criteria locationCriteria;

    @Inject
    AndroidLocationService(LocationManager locationManager, @LocationProvider @Nullable String locationProvider, Criteria locationCriteria) {
        this.locationManager = locationManager;
        this.locationProvider = locationProvider;
        this.locationCriteria = locationCriteria;
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
