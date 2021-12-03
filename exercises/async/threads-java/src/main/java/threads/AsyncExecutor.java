package threads;

import internal.HeavyComputation;

public interface AsyncExecutor {
    /**
     * Execute a heavy computation and other task in parallel
     *
     * @param comp   the heavy computation
     * @param params the parameters of the heavy computation
     * @return the result of the heavy computation
     */
    HeavyComputation.ComputationResult execute(HeavyComputation comp, String[] params);
}
