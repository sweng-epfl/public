
package ch.epfl.sweng.contracts;

import com.google.java.contract.Requires;
import com.google.java.contract.Ensures;

/**
 * @author cbr@touk.pl
 */
public class RecursiveFibonacci {
    @Requires("0 <= n")
    @Ensures("0 <= result")
    public int apply(int n) {
        if (0 == n || 1 == n) { // The bug was here (missing case for 1)
            return n;
        } else {
            return apply(n - 1) + apply(n - 2);
        }
    }
}
