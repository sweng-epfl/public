
package ch.epfl.sweng.contracts;

import com.google.java.contract.Requires;
import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;

@Invariant("d != 0")
public class Fraction {
    private int n;
    private int d;

    @Requires("d != 0")
    public Fraction(int n, int d) {
        this.n = n;
        this.d = d;
    }

    public int toInt() {
        return n/d;
    }
}
