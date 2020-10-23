# Solution: Modularity - Basics

## Rewriting long code

There are five main tasks which are being done:
- Loading the numbers
- Computing the mean
- Computing the standard deviation
- Normalizing the values
- Writing the results

We can split this behavior into separate functions, making it much easier to test and develop. It also has the benefit of making it significantly easier to understand.

Let's start by writing what we want our main method to contain:

```java
List<Double> numbers = loadFromFile("data");
double mean = mean(numbers);
double std = std(numbers, mean);
List<Double> normalized = normalize(numbers, mean, std);
System.out.println(normalized);
writeToFile(normalized);
```

We can then implement each method:

```java
public static List<Double> loadFromFile(String filepath) throws FileNotFoundException {
    File file = new File(filepath);
    Scanner scanner = new Scanner(file);
    List<Double> numbers = new ArrayList<>();
    while (scanner.hasNextDouble()) {
        double number = scanner.nextDouble();
        numbers.add(number);
    }
    scanner.close();
    return numbers;
}

public static double mean(List<Double> numbers) {
    double sum = 0;
    for (double f : numbers) {
        sum += f;
    }
    return sum / numbers.size();
}

public static double std(List<Double> numbers, double mean) {
    double sumSquare = 0;
    for (double f : numbers) {
        double diff = f - mean;
        sumSquare += diff * diff;
    }
    return Math.sqrt(sumSquare / numbers.size());
}

public static List<Double> normalize(List<Double> numbers, double mean, double std) {
    List<Double> normalized = new ArrayList<>();
    for (double f : numbers) {
        normalized.add((f - mean) / std);
    }
    return normalized;
}

public static void writeToFile(List<Double> numbers) throws IOException {
    try (FileWriter fw = new FileWriter("output")) {
        for (double n : numbers) {
            fw.write(Double.toString(n));
            fw.write("\n");
        }
    }
    System.out.println("Wrote output file.");
}
```


## Testing

Your tests will of course differ based on how you modularized your code; here are examples, which assume there is a "data" file..

```java
@Test
public void loadCorrectFile() throws FileNotFoundException {
    List<Double> loaded = App.loadFromFile("data");
    assertThat(loaded, contains(1d,2d,3d,4d,5d,6d,7d,8d,9d,10d));
}

@Test
public void loadWrongFileThrows() throws FileNotFoundException {
    assertThrows(FileNotFoundException.class, () -> { App.loadFromFile("loremipsum"); });
}

@Test
public void meanIsCorrect() throws FileNotFoundException {
    List<Double> numbers = App.loadFromFile("data");
    double mean = App.mean(numbers);
    assertThat(mean, is(5.5));
}

@Test
public void zeroMean() {
    List<Double> zeroes = Arrays.asList(0d,0d,0d,0d);
    double mean = App.mean(zeroes);
    assertThat(mean, is(0));
}

@Test
public void meanWithNegatives() {
    List<Double> numbers = Arrays.asList(-1d,1d);
    double mean = App.mean(numbers);
    assertThat(mean, is(0));
}

@Test
public void stdIsCorrect() throws FileNotFoundException {
    List<Double> numbers = App.loadFromFile("data");
    double mean = App.mean(numbers);
    double std = App.std(numbers, mean);
    assertThat(std, is(2.8722813232690143));
}

@Test
public void zeroStd() {
    List<Double> zeroes = Arrays.asList(0d,0d,0d,0d);
    double std = App.std(zeroes, 0);
    assertThat(std, is(0));
}

@Test
public void normalizeIsCorrect() throws FileNotFoundException {
    List<Double> numbers = App.loadFromFile("data");
    double mean = App.mean(numbers);
    double std = App.std(numbers, mean);
    List<Double> normalized = App.normalize(numbers, mean, std);
    assertThat(normalized, contains(
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
    ));
}

@Test
public void writeToFileIsCorrect() throws FileNotFoundException {
    List<Double> data = Arrays.asList(1d,2d,3d,4d);
    App.writeToFile(data);
    List<Double> written = App.loadFromFile("output");
    assertThat(written, is(data));
}
```
