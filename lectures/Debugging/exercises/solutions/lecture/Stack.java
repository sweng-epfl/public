import java.util.*;

public class App {
    /** A stack of integers, with a maximum size. */
    static final class IntStack {
        private int top;
        private final int[] values;

        /** Creates an empty stack with the given max size; it is forbidden to push more items than that. */
        public IntStack(int maxSize) {
            // BUGFIX: argument validation
            if (maxSize < 0) {
                throw new IllegalArgumentException("Max size must be >=0");
            }
            top = -1;
            values = new int[maxSize];

            assertInvariant();
        }

        /** Returns and pops the top of the stack, or returns null if the stack is empty. */
        public Integer pop() {
            assertInvariant();

            Integer value = top >= 0 ? values[top] : null;
            if (top > -1) {
                top--;
            }

            assertInvariant();

            return value;
        }

        /** Pushes the given value on the stack. */
        public void push(int value) {
            assertInvariant();

            // ADDED: precondition
            if (isFull()) {
                throw new IllegalStateException("Cannot push to a full stack");
            }

            top++;
            values[top] = value;

            assertInvariant();
        }

        // ADDED: method for callers to check if they can call `push`
        /** Checks whether the stack is full. */
        public boolean isFull() {
            return top == values.length - 1;
        }

        // ADDED: Invariant checking method
        private void assertInvariant() {
            if (!(-1 <= top && top < values.length)) {
                throw new AssertionError("Invariant does not hold");
            }
        }
    }

    public static void main(String[] args) {
        useStack(new IntStack(4));
    }

    // IntStack usage example
    private static void useStack(IntStack stack) {
        stack.push(1);
        stack.pop();
        stack.pop();
    }
}
