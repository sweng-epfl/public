package ch.epfl.sweng.tests;

import org.junit.Test;

import ch.epfl.sweng.EuclideanVector;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public final class EuclideanVectorTests {
    @Test
    public void normIsCorrect() {
        final EuclideanVector vector = new EuclideanVector(42);

        assertThat(vector.norm(), is(42.0));
    }
}
