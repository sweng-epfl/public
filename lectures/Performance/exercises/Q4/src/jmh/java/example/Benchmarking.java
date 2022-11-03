// Unfortunately, JMH does not work within the default package.
package example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;

@State(Scope.Benchmark)
public class Benchmarking {

    // State classes are used to hold the data that is used by the benchmark
    // methods.
    //
    // Here, we set up the ArrayList with a million elements.
    @State(Scope.Benchmark)
    public static class BenchmarkingState {

        public ArrayList<Integer> arrayList;

        @Setup(Level.Trial)
        public void setUp() {
            arrayList = new ArrayList<>();
            for (int n = 0; n < 1_000_000; n++) {
                arrayList.add(n);
            }
        }
    }

    @Benchmark
    // These 3 annotations keep the benchmark short, but aren't required.
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void getMiddleArrayList(BenchmarkingState state, Blackhole bh) {
        var result = state.arrayList.get(500_000);
        // The compiler cannot tell that the result of the operation is not
        // actually used, due to the "black hole" consuming it. When writing
        // benchmarks, you should always consume the result of the operation
        // you are benchmarking, to prevent the compiler from optimizing it
        // away.
        bh.consume(result);
    }
}
