/**
 * An interface for weather APIs whose predictions can be overridden, to let others observe such events.
 */
public interface WeatherObservable {
    void registerOverrideListener(Runnable listener);
}
