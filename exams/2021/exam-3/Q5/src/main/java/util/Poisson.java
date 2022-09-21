package util;

/**
 * Utility for Poisson distributions.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class Poisson {

    /**
     * Generates a random Poisson-distributed number given a lambda parameter.
     */
    public static int generatePoissonNumber(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }

}
