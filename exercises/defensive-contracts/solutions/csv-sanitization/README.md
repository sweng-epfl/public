# Solution - Garbage In ⇏ Garbage Out principle applied to CSV sanitization

### Handling the command-line options

Given the fact that our program expects four arguments, our main program starts first by verifying the expected number of command-line arguments. If this is not the case, we print the program usage. The program then terminates.

```java
if (args.length < 4) {
  System.out.println("usage : -i <input> -o <output> -v");
  System.exit(1);
}
```

We test then that the user has passed the `-i` option. If this is not the case, we print out an error message, and then then program terminates. Otherwise, we retrieve the input filename and we proceed further.

```java
if (!args[0].equals("-i")) {
  System.out.println("missing input file");
  System.exit(1);
}
String inputFileName = args[1];
```

We proceed the same way for the output file case.

```java
if (!args[2].equals("-o")) {
  System.out.println("missing output file");
  System.exit(1);
}
String outputFileName = args[3];
```

### Parsing the CSV input file

Parsing the CSV input file consists in reading the file line by line. The solution makes use of Java's Stream API, but one could obviously proceed in a more traditional way.

```java
try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
  BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName));
  stream.forEach(line -> {
    // ...
```

### Enforcing the format requirements

As suggested in the exercise hints, we enforce the format requirements using regular expressions. To this end, we define the following _regexes_:

- __datetime__: `^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$`
- __IP address__: `^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$`
- __user-agent__: `^.+$`
- __url__: `^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$`

### Ignoring invalid lines

Recall that invalid lines are:

- lines with missing values
- lines with values of incorrect format

#### Line with missing values

We ignore empty lines or splitted lines - with respect to the comma delimiter - whose number of values does not match the number of expected columns:

```java
stream.forEach(line -> {
  if (!line.isEmpty()) {
    String[] values = line.split(",");
    if (values.length != columns.length) {
      if (verbose) {
        logger.info(String.format("ignored {%s} : missing values", line));
      }
    } else {
      // ...
```

#### Lines with values of incorrect format

To test whether a line has values of incorrect format, we define a `Format` utility class that tries to match a Java string to a given regular expression using [Java's regex API]((https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)):

```java
public class Format {
  private Format() {}
  public static boolean matches(String value, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
```

When processing a line of the CSV input file, we apply the aforementioned regular expressions to the corresponding values of the line. If there is a single value that does not respect the expected format, the line is considered invalid:

```java
Boolean valid = true;
for (int i = 0; valid && i < values.length; ++i) {
  if (!Format.matches(values[i], regexes[i])) {
    valid = false;
    // ...
  }
}
```

### Logging ignored lines along with the reason

As required by the exercise statement, we need to log ignored lines. For that purpose, we use [Java's Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html):

```java
if (!Format.matches(values[i], regexes[i])) {
  valid = false;
  if (verbose) {
    logger.info(String.format("ignored {%s} : invalid {%s}", line, columns[i]));
  }
}
```

### Writing a cleaned version of the CSV input file

A line that has not been ignored is a _clean_ line, and as such needs to be written into the output file. Before iterating over the lines of the CSV input file, we create the output file using [Java's BufferedWriter class](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedWriter.html), and then we write into the buffer whenever we come across a valid line.

```java
try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
  BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName));
  stream.forEach(line -> {
    if (!line.isEmpty()) {
      // ...
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
  // ...
}
```
