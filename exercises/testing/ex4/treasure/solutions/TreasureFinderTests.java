import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TreasureFinderTests {
    @Test
    public void exact() {
        LocationService locator = serviceReturning(1.0, 1.0);
        TreasureFinder finder = new TreasureFinder(locator);
        assertThat(finder.getHint(new Position(1.0, 1.0)), is("You're right there!"));
    }

    @Test
    public void veryVeryClose() {
        LocationService locator = serviceReturning(0.9999, 1.0);
        TreasureFinder finder = new TreasureFinder(locator);
        assertThat(finder.getHint(new Position(1.0, 1.0)), is("You're right there!"));
    }

    @Test
    public void veryClose() {
        LocationService locator = serviceReturning(0.96, 1.0);
        TreasureFinder finder = new TreasureFinder(locator);
        assertThat(finder.getHint(new Position(1.0, 1.0)), is("Close..."));
    }

    @Test
    public void close() {
        LocationService locator = serviceReturning(0.8, 1.0);
        TreasureFinder finder = new TreasureFinder(locator);
        assertThat(finder.getHint(new Position(0.9, 0.9)), is("Not too far."));
    }

    @Test
    public void farAway() {
        LocationService locator = serviceReturning(10.0, 1.0);
        TreasureFinder finder = new TreasureFinder(locator);
        assertThat(finder.getHint(new Position(20.0, 30.0)), is("Far away."));
    }


    @Test
    public void northPole() {
        LocationService locator = serviceReturning(80.0, 1.0);
        TreasureFinder finder = new TreasureFinder(locator);
        assertThat(finder.getHint(new Position(1.0, 1.0)), is("Nope, the treasure is not at the North Pole."));
    }

    private static LocationService serviceReturning(double lat, double lon) {
        return new LocationService() {
            @Override
            public Position getUserPosition() {
                return new Position(lat, lon);
            }
        };
    }
}
