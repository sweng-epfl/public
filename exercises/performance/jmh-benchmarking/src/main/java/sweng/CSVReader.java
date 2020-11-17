package sweng;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Provides an interface to read Student records from a file
 */
public class CSVReader {

    private final Path path;

    public CSVReader(String fileName) {
        this.path = Paths.get(fileName);
    }

    /**
     * Reads the first n lines of the file
     */
    public List<Student> read(int n) {
        List<Student> result = new ArrayList<>();

        try (Stream<String> stream = Files.lines(path)) {
            stream.skip(1).limit(n).forEach(line -> {
                if (!line.isEmpty()) {
                    String[] values = line.split("\t");
                    Student s = new Student(values[1], values[2], values[3], values[4]);
                    result.add(s);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Reads the first line of the file
     */
    public Student head() {
        return read(1).get(0);
    }




}
