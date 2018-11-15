
package ch.epfl.sweng.contracts;

import java.util.List;
import com.google.java.contract.Requires;
import com.google.java.contract.Ensures;

public interface SwengVector {
    public boolean contains(int element);
    public int get(int i);
    public List<Integer> toList();

    /**
     * This postcondition consists of two clauses.
     * First one makes sure that the vector stores the element.
     * Second one makes sure the vector does not loose any
     * of the old contents.
     * The special macros 'old(x)' remembers the value of x before
     * the function invocation and uses it after the function returned.
     * this allows the condition to express the evolution
     * of a stateful object.
     *
     * This is the Java 8 syntax. It means:
     * 1. convert 'old(toList())' into a stream
     * 2. call 'this.contains' on each element of the stream
     * 3. check that all the calls return true.
     */
    @Ensures({ "contains(element)",
               "old(toList()).stream().allMatch(this::contains)"})
    public void add(int element);
}
