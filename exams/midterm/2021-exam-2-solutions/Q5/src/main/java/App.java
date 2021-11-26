import model.CredentialDatabase;
import model.SangriaDatabase;
import model.SecureSangriaDatabase;
import presenter.SangriaPresenter;
import view.SangriaSwingView;
import view.View;

/**
 * Main application entry point
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit everything in this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class App {

    public static void main(String[] args) {
        View view = new SangriaSwingView();
        CredentialDatabase database = new SecureSangriaDatabase(new SangriaDatabase());
        new SangriaPresenter(view, database);

        view.startApplication();
    }

}