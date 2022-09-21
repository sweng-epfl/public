import model.SangriaDatabase;
import model.CredentialDatabase;
import view.AuthenticatedUserView;

/**
 * Main application entry point
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit everything in this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class App {

    public static void main(String[] args) {
        CredentialDatabase model = new SangriaDatabase();
        // We want to pass an AuthenticatedUser to the view but the database interface
        // prevents us from doing so...
        AuthenticatedUserView view = new AuthenticatedUserView(null);
        System.out.println(view);
    }

}