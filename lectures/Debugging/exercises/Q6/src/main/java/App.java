import java.io.BufferedWriter;
import java.io.IOException;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) throws IOException {
        if (args.length < 4) {
            System.out.println("usage : -i <input> -o <output> [-v]");
            System.exit(1);
        }

        if (!args[0].equals("-i")) {
            System.out.println("missing input file");
            System.exit(1);
        }
        String inputFileName = args[1];

        if (!args[2].equals("-o")) {
            System.out.println("missing output file");
            System.exit(1);
        }
        String outputFileName = args[3];

        final Boolean verbose = args.length == 5 && args[4].equals("-v");

        sanitizeCsv(inputFileName, outputFileName, verbose);
    }

    public static void sanitizeCsv(String inputFileName, String outputFileName, boolean verbose) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName));
            stream.forEach(line -> {
                /** Your implementation goes here */
            });
            writer.close();
        }
    }
}

