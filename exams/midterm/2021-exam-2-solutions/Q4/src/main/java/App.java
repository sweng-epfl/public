import model.AuthResult;
import model.AuthenticatedUserAdapter;
import model.SangriaDatabase;
import model.CredentialDatabase;
import view.AuthenticatedUserView;
import view.ErrorView;

/**
 * Main application entry point
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit everything in this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class App {

    public static void main(String[] args) {
        CredentialDatabase model = new SangriaDatabase();
        AuthResult result = model.authenticate(args[0], args[1]);
        if (result.isSuccessful()) {
            var userAdapter = new AuthenticatedUserAdapter(result.getData());
            System.out.println(new AuthenticatedUserView(userAdapter));
        } else {
            System.out.println(new ErrorView(result.getException().getMessage()));
        }
    }

}