import java.util.Scanner;

public final class App {
    public static void main(String[] args) {
        System.out.print("Hello! How many times do you want to laugh? ");

        var scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        Joker.getJokes(count);

        System.out.println("Hope you laughed! Bye!");
    }
}
