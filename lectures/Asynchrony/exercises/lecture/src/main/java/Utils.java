public final class Utils {
    /** Sleeps for the given number of milliseconds. */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new AssertionError("This should never happen in this example code", e);
        }
    }
}
