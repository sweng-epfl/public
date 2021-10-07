import ch.epfl.sweng.locator.Geolocator;
import ch.epfl.sweng.locator.PositionRange;
import ch.epfl.sweng.locator.Precision;

public class TreasureFinder {

    private final Geolocator geolocator;

    // There MUST be a parameterless constructor,
    // it is used by our Super-Fancy-Framework-That-Does-Not-Support-Parameters™
    public TreasureFinder() {
        geolocator = new Geolocator(Precision.FINE);
    }

    public String getHint(Position treasurePos) {
        if (!geolocator.isLocationServiceRunning()) {
            try {
                geolocator.startLocationService();
            } catch(Exception e) {
                return "The treasure is on Saturn!";
            }
        }

        PositionRange userPositionRange = geolocator.getUserPosition();
        double userLatitude = (userPositionRange.maxLatitude + userPositionRange.minLatitude) / 2.0;
        double userLongitude = (userPositionRange.maxLongitude + userPositionRange.minLongitude) / 2.0;

        if (userLatitude > 70) {
            return "Nope, the treasure is not at the North Pole.";
        }

        // Not accurate because of the Earth's curvature. Better calculation coming next sprint!
        double diff = Math.sqrt(Math.pow(treasurePos.latitude - userLatitude, 2) + Math.pow(treasurePos.longitude - userLongitude, 2));

        if (diff < 0.005) {
            return "You're right there!";
        }

        if (diff < 0.05) {
            return "Close...";
        }

        if (diff < 0.5) {
            return "Not too far.";
        }

        return "Far away.";
    }
}
