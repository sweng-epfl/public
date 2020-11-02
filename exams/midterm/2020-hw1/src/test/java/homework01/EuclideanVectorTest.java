package homework01;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class EuclideanVectorTest {
    @Test
    public void normIsCorrect() {
        final EuclideanVector vector = new EuclideanVector(42);

        assertThat(vector.norm(), is(42.0));
    }
}