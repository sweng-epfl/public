package threads;

import internal.HeavyComputation;
import internal.SleepPrinter;

import java.util.Random;
import java.util.concurrent.*;

public class FutureTaskExecutor implements AsyncExecutor {

    @Override
    public HeavyComputation.ComputationResult execute(HeavyComputation comp, String[] params) {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future<HeavyComputation.ComputationResult> futureResult = threadpool.submit(() -> comp.compute(params));
        SleepPrinter.printAfter("Continue executing...", 1);
        SleepPrinter.printAfter("Can do other tasks in-between", new Random().nextInt(10));
        HeavyComputation.ComputationResult result;
        try {
            result = futureResult.get();
        } catch (InterruptedException | ExecutionException e) {
            return HeavyComputation.ComputationResult.FAILURE;
        }
        threadpool.shutdown();
        return result;
    }
}
