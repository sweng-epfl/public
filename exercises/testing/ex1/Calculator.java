public class Calculator {
    /**
     * Adds a to b.
     */
    public long add(int a, int b) {
        return (long) a + (long) b;
    }

    /**
     * Divides a by b.
     * Throws an ArithmeticException if b is 0.
     */
    public int divide(int a, int b) {
        return a / b;
    }
}
