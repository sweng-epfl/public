package view;

/**
 * Simple view to display an error message.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class ErrorView {

    private final String message;

    /**
     * Construct a new error view
     * @param message The error message that the view displays
     */
    public ErrorView(String message) {
        if (message == null) {
            throw new IllegalArgumentException();
        }
        this.message = message;
    }

    @Override
    public String toString() {
        return "Oops, an error occurred: " + this.message;
    }
}
