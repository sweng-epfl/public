package ch.epfl.sweng;

import java.util.Scanner;

import ch.epfl.sweng.database.CachedDatabase;
import ch.epfl.sweng.database.Database;
import ch.epfl.sweng.database.RetryDatabase;
import ch.epfl.sweng.database.UnreliableDatabase;
import ch.epfl.sweng.mvc.AppController;
import ch.epfl.sweng.mvc.AppModel;
import ch.epfl.sweng.mvc.AppView;

/**
 * Example main file, provided for convenience.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit everything in this file
 * You CAN delete this file
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class App {
    public static void main(String[] args) {
        // Ibizon's database instance. Add the decorators here.
        Database database = new CachedDatabase(new RetryDatabase(new UnreliableDatabase()));

        // Adapts Ibizon's database to store students.
        StudentDatabaseAdapter databaseAdapter = new StudentDatabaseAdapter(database);

        // Rest of the application.
        AppModel model = new AppModel(databaseAdapter);
        AppView view = new AppView();
        AppController controller = new AppController(model, view);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter your query:");
            String command = scanner.nextLine().trim();
            if (!command.isEmpty()) {
                String result = controller.handleRequest(command);
                System.out.println(result);
            }
        }
    }
}
