package ch.epfl.sweng.contracts;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.java.contract.PreconditionError;


public class SwengStackTests {
    @Test(expected = PreconditionError.class)
    public void popEmptyStack() {
        SwengStack stack = new SwengStack(5);
        stack.push(1);
        stack.pop();
        stack.pop();
    }

    @Test
    public void stackPush2Pop() {
        SwengStack stack = new SwengStack(5);
        stack.push(8);
        stack.push(34);
        assertEquals(34, stack.pop());
        assertEquals(8, stack.pop());
    }
}
