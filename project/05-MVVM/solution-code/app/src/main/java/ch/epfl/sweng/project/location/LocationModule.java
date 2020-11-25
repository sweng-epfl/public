package ch.epfl.sweng.project.location;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nullable;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class LocationModule {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface LocationProvider {
    }

    @Binds
    public abstract LocationService bindLocationService(AndroidLocationService locationServiceImpl);

    @Singleton
    @Provides
    public static LocationManager provideLocationManager(@ApplicationContext Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @LocationProvider
    @Nullable
    public static String provideLocationProvider(LocationManager locationManager, Criteria locationCriteria) {
        return locationManager.getBestProvider(locationCriteria, true);
    }

    @Singleton
    @Provides
    public static Criteria provideCriteria() {
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        return criteria;
    }
}
