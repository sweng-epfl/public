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

    public static double sum(List<Double> numbers){
        double sum = 0;
        for(double f : numbers){
            sum += f;
        }
        return sum;
    }

    public static void scan(Scanner scanner, List<Double> numbers){
        while (scanner.hasNextDouble()){
            double number = scanner.nextDouble();
            numbers.add(number);
        }
    }
    public static List<Double> compute() throws FileNotFoundException {
        File file = new File("data");
        List<Double> normalized = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        List<Double> numbers = new ArrayList<>();
        scan(scanner, numbers);
        double sum = sum(numbers);
        double mean = sum / numbers.size();
        double sumSquare = 0;
        for (double f : numbers) {
            double diff = f - mean;
            sumSquare += diff * diff;
        }
        double std = Math.sqrt(sumSquare / numbers.size());
        for (double f : numbers) {
            normalized.add((f - mean) / std);
        }
        System.out.println(normalized);

        try {
            FileWriter fw = new FileWriter("output");
            for (double n : normalized) {
                fw.write(Double.toString(n));
                fw.write("\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Error writing output file");
        }
        System.out.println("Wrote output file.");
        scanner.close();
        return normalized;
    }
}
