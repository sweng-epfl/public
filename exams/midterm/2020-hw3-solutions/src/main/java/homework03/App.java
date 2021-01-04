// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN edit everything in this file
// You CAN delete this file
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
package homework03;

import homework03.util.CLI;
import homework03.util.Client;
import homework03.util.Database;
import homework03.util.Server;

/**
 * Main entry-point of the app. You can use it to run your implementation.
 * <p>
 * // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * // You CAN edit everything in this file
 * // You CAN delete this file
 * // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class App {
    public static void main(String[] args) {
        Database db = new DatabaseImpl();
        Server server = new ServerImpl(db);
        Client client = new ClientImpl("123456", server);

        CLI cli = new CLIImpl(client);

        while (true) {
            cli.getQueryAndExecute();
        }
    }
}
