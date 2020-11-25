package ch.epfl.sweng.project.weather;

import android.content.Context;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import ch.epfl.sweng.project.R;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class WeatherModule {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface ApiKey {
    }

    @Binds
    public abstract WeatherService bindWeatherService(OpenWeatherMapWeatherService weatherServiceImpl);

    @Provides
    @ApiKey
    public static String provideApiKey(@ApplicationContext Context context) {
        return context.getString(R.string.openweather_api_key);
    }
}
