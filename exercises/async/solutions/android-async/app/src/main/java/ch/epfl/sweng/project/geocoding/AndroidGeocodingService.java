package ch.epfl.sweng.project.geocoding;

import android.location.Geocoder;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import ch.epfl.sweng.project.location.Location;

@Singleton
public class AndroidGeocodingService implements GeocodingService {
    private final Geocoder geocoder;

    @Inject
    AndroidGeocodingService(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Override
    public CompletionStage<Location> getLocation(@NonNull String address) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getLocationSync(address);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        });
    }

    public Location getLocationSync(@NonNull String address) throws IOException {
        return this.geocoder.getFromLocationName(address, 5)
                .stream()
                .filter(addr -> addr.hasLatitude() && addr.hasLongitude())
                .map(addr -> new Location(addr.getLatitude(), addr.getLongitude()))
                .findFirst()
                .orElse(null);
    }

    private Address getAddressSync(@NonNull Location location) throws IOException {
        android.location.Address address = this.geocoder.getFromLocation(location.latitude, location.longitude, 1).get(0);
        List<String> addressLines = new ArrayList<>();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); ++i)
            addressLines.add(address.getAddressLine(i));

        return new Address(addressLines);
    }

    @Override
    public CompletionStage<Address> getAddress(@NonNull Location location) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getAddressSync(location);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        });
    }
}
