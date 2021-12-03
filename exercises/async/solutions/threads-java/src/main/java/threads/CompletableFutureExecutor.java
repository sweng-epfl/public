package threads;

import internal.HeavyComputation;
import internal.SleepPrinter;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExecutor implements AsyncExecutor {

    @Override
    public HeavyComputation.ComputationResult execute(HeavyComputation comp, String[] params) {
        CompletableFuture<HeavyComputation.ComputationResult> futureCompl = CompletableFuture.supplyAsync(() -> comp.compute(params));
        SleepPrinter.printAfter("Continue executing...", 1);
        SleepPrinter.printAfter("Can do other tasks in-between", new Random().nextInt(10));
        HeavyComputation.ComputationResult result;
        try {
            result = futureCompl.get();
        } catch (InterruptedException | ExecutionException e) {
            return HeavyComputation.ComputationResult.FAILURE;
        }
        return result;
    }
}
