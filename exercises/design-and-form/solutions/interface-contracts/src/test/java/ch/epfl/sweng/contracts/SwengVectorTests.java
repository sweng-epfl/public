package ch.epfl.sweng.contracts;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.java.contract.PreconditionError;


public class SwengVectorTests {
    @Test
    public void addElements() {
        SwengVector v = new SwengArrayList(5);
        v.add(1);
        v.add(2);
        v.add(3);
        v.add(4);
        v.add(5);
        v.add(6);
        assert(v.contains(1));

    }

}
