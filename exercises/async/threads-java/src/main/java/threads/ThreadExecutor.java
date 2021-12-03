package threads;

import internal.HeavyComputation;
import internal.SleepPrinter;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadExecutor implements AsyncExecutor {

    @Override
    public HeavyComputation.ComputationResult execute(HeavyComputation comp, String[] params) {
        // TODO add async computation using Thread here and return result of the computation
        SleepPrinter.printAfter("Continue executing...", 1);
        SleepPrinter.printAfter("Can do other tasks in-between", new Random().nextInt(10));
        return null;
    }
}
