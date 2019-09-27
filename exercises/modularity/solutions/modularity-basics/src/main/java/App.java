import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        compute();
    }

    public static List<Double> compute() throws FileNotFoundException {
        List<Double> numbers = loadFromFile("data");
        double mean = mean(numbers);
        double std = std(numbers, mean);
        List<Double> normalized = normalize(numbers, mean, std);
        System.out.println(normalized);
        writeToFile(normalized);
        return normalized;
    }

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

    public static void writeToFile(List<Double> numbers) {
        try {
            FileWriter fw = new FileWriter("output");
            for (double n : numbers) {
                fw.write(Double.toString(n));
                fw.write("\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Error writing output file");
        }
        System.out.println("Wrote output file.");
    }
}
