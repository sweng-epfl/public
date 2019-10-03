package ch.epfl.sweng.defensive.garbage.in.non.garbage.out;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  static Logger logger = Logger.getLogger("");

  static final String[] columns = {"datetime", "ip address", "user-agent", "url"};
  static final String[] regexes = {
    "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
    "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",
    "^.+$",
    "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$"
  };
  
  public static void main(String[] args) {
    if (args.length < 4) {
      System.out.println("usage : -i <input> -o <output> -v");
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

    try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
      BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName));
      stream.forEach(line -> {
        if (!line.isEmpty()) {
          String[] values = line.split(",");
          if (values.length != columns.length) {
            if (verbose) {
              logger.info(String.format("ignored {%s} : missing values", line));
            }
          } else {
            Boolean valid = true;
            for (int i = 0; valid && i < values.length; ++i) {
              if (!Format.matches(values[i], regexes[i])) {
                valid = false;
                if (verbose) {
                  logger.info(String.format("ignored {%s} : invalid {%s}", line, columns[i]));
                }
              }
            }
            if (valid) {
              try {
                writer.write(line);
                writer.newLine();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }
      });
      writer.close();
      stream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}