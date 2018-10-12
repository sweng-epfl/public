# Modularizing code

There are four main tasks which are being done:
- Loading the numbers
- Computing the mean
- Computing the standard deviation
- Normalizing the values

We can split this behavior into separate functions, making it much easier to test and develop. It also has the benefit of making it significantly easier to understand.

```java

public static List<Double> loadNumbers(String inputFilename) throws FileNotFoundException {
    File file = new File(inputFilename);
    Scanner scanner = new Scanner(file);
    List<Double> numbers = new ArrayList<>();
    while(scanner.hasNextDouble()) {
       double number = scanner.nextDouble();
       numbers.add(number);
    }
    return numbers;
}

public static double compute_mean(List<Double> numbers) {
    double sum = 0;
    for(double f: numbers) {
       sum += f;
    }
    return = sum / numbers.size();
}

public static double compute_std(List<Double> numbers, double mean) {
    double sumSquare = 0;
    for(double f: numbers) {
       double diff = f - mean;
       sumSquare += diff * diff;
    }
    return Math.sqrt(sumSquare / numbers.size());
}

public static List<Double> normalize(List<Double> numbers, double mean, double std) {
    List<Double> normalized = new ArrayList<>();
    for(double f: numbers) {
       normalized.add((f - mean) / std);
    }
    return normalized;
}

public static List<Double> normalizeNumbers(String inputFilename) throws FileNotFoundException {
    List<Double> numbers = loadNumbers(inputFilename);
    double mean = compute_mean(numbers);
    double std = compute_std(numbers, mean);
    return normalize(numbers, mean, std);
}
```