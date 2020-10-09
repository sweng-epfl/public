package ch.epfl.sweng.project;

import org.junit.Test;

import ch.epfl.sweng.project.location.Location;
import ch.epfl.sweng.project.utils.Tuples2;

import static org.junit.Assert.assertEquals;

public class LocationTest {
    @Test
    public void constructorDoesNotCrash(){
        Location loc = new Location(new Tuples2<>(42.0, 12.0));
    }

    @Test
    public void instanceIsNotModifiableFromOutside(){
        // this test triggers the bug 3. The tuple returned by the Location instance should be
        // a new one, independent from the one stored into the instance so that modifying
        // this copy has no effect on the Location instance itself.
        double latitude = 42.7;
        double longitude = 6.9;
        double delta = 0.00001;

        Location loc = new Location(new Tuples2<>(latitude, longitude));
        assertEquals(latitude, loc.getLatitudeLongitudeTuple().getFirst(), delta);
        assertEquals(longitude, loc.getLatitudeLongitudeTuple().getSecond(), delta);

        loc.getLatitudeLongitudeTuple().setFirst(0.0);
        loc.getLatitudeLongitudeTuple().setSecond(0.0);

        assertEquals(latitude, loc.getLatitudeLongitudeTuple().getFirst(), delta);
        assertEquals(longitude, loc.getLatitudeLongitudeTuple().getSecond(), delta);
    }
}
