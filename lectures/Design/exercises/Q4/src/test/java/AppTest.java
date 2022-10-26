import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class AppTest {
    @Test
    public void computeYieldsCorrectResult() throws FileNotFoundException {
        List<Double> normalized = App.compute();
        List<Double> expected = Arrays.asList(
                -1.5666989036012806,
                -1.2185435916898848,
                -0.8703882797784892,
                -0.5222329678670935,
                -0.17407765595569785,
                0.17407765595569785,
                0.5222329678670935,
                0.8703882797784892,
                1.2185435916898848,
                1.5666989036012806
        );
        assertThat(normalized.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); ++i) {
            assertThat(normalized.get(i), closeTo(expected.get(i), 0.00001));
        }
    }
}
