// Unfortunately, JMH does not work within the default package.
package example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;

// Reminder: Build and run the benchmark using `gradlew.bat jmh` on Windows or `./gradlew jmh` on macOS and Linux.

@State(Scope.Benchmark)
public class Benchmarking {
    // State classes are used to hold the data that is used by the benchmark methods.
    //
    // Here, we set up an ArrayList with ten thousand elements.
    //
    // The scope "trial" means that the same state will be used for many invocations of the method.
    // (It is possible to use per-invocation state, but this comes with a _lot_ of caveats, check out the Javadoc of `Level` if you're interested)
    @State(Scope.Benchmark)
    public static class ArrayListBenchmarkingState {
        public ArrayList<Integer> arrayList;

        @Setup(Level.Trial)
        public void setUp() {
            arrayList = new ArrayList<>();
            for (int n = 0; n < 10_000; n++) {
                arrayList.add(n);
            }
        }
    }

    // This is required to indicate JMH must run this method as a benchmark
    @Benchmark
    // These annotations keep the benchmark short so this exercise takes a short amount of time.
    // In a real benchmark outside of an exercise, you would typically not need them.
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    // JMH will pass an instance of our state that is shared across invocations (since we specified `Level.Trial`), and a "black hole" utility to defeat compiler optimizations (see last line of the method)
    public void prepend(ArrayListBenchmarkingState state, Blackhole bh) {
        var result = new ArrayList<Integer>();
        for (var item : state.arrayList) {
            result.add(0, item); // prepend the item at the front of the list
        }
        // Ensure that the compiler does not "optimize" this method by removing the operation altogether, since this method has no side effects.
        bh.consume(result);
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void append(ArrayListBenchmarkingState state, Blackhole bh) {
        var result = new ArrayList<Integer>();
        for (var item : state.arrayList) {
            result.add(item); // append the item at the back of the list
        }
        bh.consume(result);
    }
}
