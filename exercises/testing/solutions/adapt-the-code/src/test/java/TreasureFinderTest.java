import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

public final class TreasureFinderTest {

    @Test
    public void getHintTriggerEasterEgg() {
        LocationService ls = new LocationService() {
            @Override
            public Position getUserPosition() throws Exception {
                return new Position(71.0, 0.0);
            }
        };
        TreasureFinder finder = new TreasureFinder(ls);
        Position treasurePos = new Position(0.0, 0.0);
        assertThat(finder.getHint(treasurePos), is("Nope, the treasure is not at the North Pole."));
    }

    @Test
    public void getHintWorkOnLocationException() {
        LocationService ls = new LocationService() {
            @Override
            public Position getUserPosition() throws Exception {
                throw new Exception();
            }
        };
        TreasureFinder finder = new TreasureFinder(ls);
        Position treasurePos = new Position(0.0, 0.0);
        assertThat(finder.getHint(treasurePos), is("The treasure is on Saturn!"));
    }

    @Test
    public void getHintWorkOnWrongLocation() {
        LocationService ls = new LocationService() {
            @Override
            public Position getUserPosition() throws Exception {
                return new Position(0.0, 0.0);
            }
        };
        TreasureFinder finder = new TreasureFinder(ls);
        Position treasurePos = new Position(10.0, 10.0);
        assertThat(finder.getHint(treasurePos), is("Far away."));
    }

    /************************************************/
    /*                   Mockito                    */
    /************************************************/

    @Test
    public void mockito_getHintTriggerEasterEgg() throws Exception {
        LocationService ls = mock(LocationService.class);
        when(ls.getUserPosition()).thenReturn(new Position(71.0, 0.0));
        TreasureFinder finder = new TreasureFinder(ls);
        Position treasurePos = new Position(0.0, 0.0);
        assertThat(finder.getHint(treasurePos), is("Nope, the treasure is not at the North Pole."));
    }

    @Test
    public void mockito_getHintWorkOnLocationException() throws Exception {
        LocationService ls = mock(LocationService.class);
        when(ls.getUserPosition()).thenThrow(new Exception());
        TreasureFinder finder = new TreasureFinder(ls);
        Position treasurePos = new Position(0.0, 0.0);
        assertThat(finder.getHint(treasurePos), is("The treasure is on Saturn!"));
    }

    @Test
    public void mockito_getHintWorkOnWrongLocation() throws Exception {
        LocationService ls = mock(LocationService.class);
        when(ls.getUserPosition()).thenReturn(new Position(0.0, 0.0));
        TreasureFinder finder = new TreasureFinder(ls);
        Position treasurePos = new Position(10.0, 10.0);
        assertThat(finder.getHint(treasurePos), is("Far away."));
    }

}
