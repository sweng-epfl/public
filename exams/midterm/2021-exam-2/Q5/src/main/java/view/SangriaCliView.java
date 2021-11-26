package view;

import model.AuthenticatedUser;
import presenter.Presenter;

import java.util.Scanner;

/**
 * View implementation using the command-line
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class SangriaCliView implements View {

    private Presenter presenter;
    private boolean isRunning = false;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void setPresenter(Presenter presenter) {
        if (presenter == null) {
            throw new IllegalArgumentException();
        }
        this.presenter = presenter;
    }

    @Override
    public void displayUser(AuthenticatedUser user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("Authenticated user: " + user.getUserName() + ", SCIPER: " + user.getSciper());
    }

    @Override
    public void displayError(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("Authentication error: " + msg);
    }

    @Override
    public void startApplication() {
        if (!isRunning) {
            isRunning = true;
            while (isRunning) {
                System.out.println("Enter username");
                String userName = this.scanner.nextLine();
                System.out.println("Enter password");
                String password = this.scanner.nextLine();
                this.presenter.authenticateUser(userName, password);
            }
        }
    }
}
