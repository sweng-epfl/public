package ch.epfl.sweng.project.location;

import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import ch.epfl.sweng.project.location.LocationModule.LocationProvider;

@Singleton
public final class AndroidLocationService implements LocationService {
    private int listenerId = 1;
    private final Map<Integer, LocationListener> listeners = new HashMap<>();
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

    @Override
    public int subscribeToLocationUpdates(Consumer<Location> consumer, long minTimeDelta, long minDistanceDelta) {
        int id = ++listenerId;
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location loc) {
                Location location = new Location(loc.getLatitude(), loc.getLongitude());
                consumer.accept(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        listeners.put(id, listener);
        try {
            consumer.accept(getCurrentLocation());
            this.locationManager.requestLocationUpdates(getLocationProvider(), minTimeDelta, minDistanceDelta, listener);
        } catch (SecurityException ex) {
            // We need to explicitly catch this exception, even though we throw it again immediately
            throw ex;
        }

        return id;
    }

    @Override
    public void unsubscribeFromLocationUpdates(int subscriptionId) {
        LocationListener listener = listeners.remove(subscriptionId);
        if (listener != null) {
            this.locationManager.removeUpdates(listener);
        }
    }
}
