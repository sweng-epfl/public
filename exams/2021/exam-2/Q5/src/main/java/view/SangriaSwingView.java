package view;

import model.AuthenticatedUser;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/**
 * View implementation using Java Swing
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class SangriaSwingView implements View {

    private final JFrame frame = new JFrame();
    private final JTextArea notificationArea;
    private Presenter presenter;

    /**
     * Construct a new view using Swing
     */
    public SangriaSwingView() {
        // Set the frame properties
        frame.setTitle("Sangria Authentication");
        frame.setBounds(10, 10, 370, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create the frame elements
        JLabel userLabel = new JLabel("USERNAME");
        JLabel passwordLabel = new JLabel("PASSWORD");
        JTextField userTextField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton(new AbstractAction("LOGIN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userTextField.getText().isBlank())
                    presenter.authenticateUser(userTextField.getText().trim(), new String(passwordField.getPassword()));
            }
        });
        JButton resetButton = new JButton(new AbstractAction("RESET") {
            @Override
            public void actionPerformed(ActionEvent e) {
                userTextField.setText(null);
                passwordField.setText(null);
                notificationArea.setText(null);
            }
        });
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.addItemListener(e -> passwordField.setEchoChar(e.getStateChange() == ItemEvent.SELECTED ? (char) 0 : '*'));
        notificationArea = new JTextArea();
        notificationArea.setEditable(false);

        // Position the elements in the frame
        userLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 100, 100, 30);
        userTextField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 100, 150, 30);
        showPassword.setBounds(150, 130, 150, 30);
        loginButton.setBounds(50, 180, 100, 30);
        resetButton.setBounds(200, 180, 100, 30);
        notificationArea.setBounds(50, 230, 250, 20);

        // Add the elements in the frame
        Container container = frame.getContentPane();
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(notificationArea);
    }

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
        this.notificationArea.setText("Welcome " + user.getUserName() + " (" + user.getSciper() + ")!");
    }

    @Override
    public void displayError(String msg) {
        if (msg == null) {
            throw new IllegalArgumentException();
        }
        this.notificationArea.setText(msg);
    }

    @Override
    public void startApplication() {
        frame.setVisible(true);
    }

}
