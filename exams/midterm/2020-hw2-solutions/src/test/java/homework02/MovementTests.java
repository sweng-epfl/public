package homework02;

import homework02.util.Movement;
import homework02.util.MovementType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MovementTests {
    @Test
    public void equalsWorks() {
        Movement m1 = new Movement(ItemKind.APPLE_WATCH, MovementType.ADD);
        Movement m2 = new Movement(ItemKind.APPLE_WATCH, MovementType.ADD);
        Movement m3 = new Movement(ItemKind.JEANS, MovementType.ADD);
        Movement m4 = new Movement(ItemKind.APPLE_WATCH, MovementType.REMOVE);
        assertThat(m1.equals(null), is(false));
        assertThat(m1.equals(m1), is(true));
        assertThat(m1.equals(m2), is(true));
        assertThat(m1.equals(m3), is(false));
        assertThat(m1.equals(m4), is(false));
        assertThat(m1.equals(new ArrayList<>()), is(false));
    }

    @Test
    public void hashcodeWorks(){
        Movement m1 = new Movement(ItemKind.APPLE_WATCH, MovementType.ADD);
        Movement m2 = new Movement(ItemKind.APPLE_WATCH, MovementType.ADD);
        Movement m3 = new Movement(ItemKind.JEANS, MovementType.ADD);

        assertThat(m1.hashCode() == m2.hashCode(), is(true));
        assertThat(m1.hashCode() == m3.hashCode(), is(false));
    }
}
