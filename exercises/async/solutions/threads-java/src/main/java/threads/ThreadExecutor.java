package threads;

import internal.HeavyComputation;
import internal.SleepPrinter;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadExecutor implements AsyncExecutor {

    @Override
    public HeavyComputation.ComputationResult execute(HeavyComputation comp, String[] params) {
        AtomicReference<HeavyComputation.ComputationResult> result = new AtomicReference<>();
        Thread newThread = new Thread(() -> result.set(comp.compute(params)));
        newThread.start();
        SleepPrinter.printAfter("Continue executing...", 1);
        SleepPrinter.printAfter("Can do other tasks in-between", new Random().nextInt(10));
        try {
            newThread.join();
        } catch (InterruptedException e) {
            return HeavyComputation.ComputationResult.FAILURE;
        }
        return result.get();
    }
}
