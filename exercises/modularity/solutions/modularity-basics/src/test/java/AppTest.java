import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(expected, normalized);
    }

    @Test
    public void loadCorrectFile() throws FileNotFoundException {
        List<Double> expected = Arrays.asList(1d,2d,3d,4d,5d,6d,7d,8d,9d,10d);
        List<Double> loaded = App.loadFromFile("data");
        assertEquals(expected, loaded);
    }

    @Test
    public void loadWrongFileThrows() throws FileNotFoundException {
        assertThrows(FileNotFoundException.class, () -> { App.loadFromFile("loremipsum"); });
    }

    @Test
    public void meanIsCorrect() throws FileNotFoundException {
        List<Double> numbers = App.loadFromFile("data");
        double mean = App.mean(numbers);
        assertEquals(5.5, mean, 0);
    }

    @Test
    public void zeroMean() {
        List<Double> zeroes = Arrays.asList(0d,0d,0d,0d);
        double mean = App.mean(zeroes);
        assertEquals(0, mean, 0);
    }

    @Test
    public void meanWithNegatives() {
        List<Double> numbers = Arrays.asList(-1d,1d);
        double mean = App.mean(numbers);
        assertEquals(0, mean, 0);
    }

    @Test
    public void stdIsCorrect() throws FileNotFoundException {
        List<Double> numbers = App.loadFromFile("data");
        double mean = App.mean(numbers);
        double std = App.std(numbers, mean);
        assertEquals(2.8722813232690143, std, 0);
    }

    @Test
    public void zeroStd() {
        List<Double> zeroes = Arrays.asList(0d,0d,0d,0d);
        double std = App.std(zeroes, 0);
        assertEquals(0, std, 0);
    }

    @Test
    public void normalizeIsCorrect() throws FileNotFoundException {
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
        List<Double> numbers = App.loadFromFile("data");
        double mean = App.mean(numbers);
        double std = App.std(numbers, mean);
        List<Double> normalized = App.normalize(numbers, mean, std);
        assertEquals(expected, normalized);
    }

    @Test
    public void writeToFileIsCorrect() throws FileNotFoundException {
        List<Double> data = Arrays.asList(1d,2d,3d,4d);
        App.writeToFile(data);
        List<Double> written = App.loadFromFile("output");
        for (int i = 0; i < 4; i++) {
            assertEquals(data.get(i), written.get(i));
        }
    }
}
