package threads;

import internal.HeavyComputation;
import internal.SleepPrinter;

import java.util.Random;

public class CompletableFutureExecutor implements AsyncExecutor {

    @Override
    public HeavyComputation.ComputationResult execute(HeavyComputation comp, String[] params) {
        // TODO add async computation using CompletableFuture here and return result of the computation
        SleepPrinter.printAfter("Continue executing...", 1);
        SleepPrinter.printAfter("Can do other tasks in-between", new Random().nextInt(10));
        return null;
    }
}
