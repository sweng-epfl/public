package internal;

import java.util.concurrent.TimeUnit;

public class HeavyComputation {

    public enum ComputationResult {
        SUCCESS, FAILURE
    }

    // ...

    /**
     * Compute results based on params
     *
     * @param params the parameters of the computation
     * @return whether the computation was successful
     */
    public ComputationResult compute(String[] params) {
        System.out.println("Compute: start with params" + String.join(",", params));
        for (int i = 0; i < 10; ++i) {
            SleepPrinter.printAfter("Compute: " + (i + 1) * 10 + "%", 1);
        }
        System.out.println("Compute: done");
        return ComputationResult.SUCCESS;
    }
}
