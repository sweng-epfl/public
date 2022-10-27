import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class ExerciseTests {
    @Test
    void counterStartsAtZero() {
        var counter = new PeopleCounter(10);
        assertThat(counter.value(), is(0));
    }

    @Test
    void counterIncrementAdds1ToValue() {
        var counter = new PeopleCounter(10);
        counter.increment();
        assertThat(counter.value(), is(1));
    }

    @Test
    void counterResetSetsValueTo0() {
        var counter = new PeopleCounter(10);
        counter.increment();
        counter.reset();
        assertThat(counter.value(), is(0));
    }

    @Test
    void counterCannotExceedMaximum() {
        var counter = new PeopleCounter(1);
        counter.increment();
        counter.increment();
        assertThat(counter.value(), is(1));
    }

    @Test
    void counterMaximumCannotBeBelow0() {
        assertThrows(IllegalArgumentException.class, () -> new PeopleCounter(-1));
    }
}
