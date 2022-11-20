import java.util.Scanner;

/**
 * Entry point of the application.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public class App {
    public static void main(String[] args) {
        var weatherService = new WeatherService();
        var scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a city name (e.g., Lausanne) to get its weather.");
            System.out.println("Enter 'override' to override the weather predictions.");
            System.out.println("Enter 'exit' to exit the program.");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            } else if (input.startsWith("override")) {
                weatherService.overrideWeatherPredictions();
            } else {
                System.out.println(weatherService.getWeather(input));
            }
        }
    }
}
