// JMH requires a non-default package
package example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;

@State(Scope.Benchmark)
public class ListBenchmark {
    // We need a 'state' object to pass to our benchmark methods; here we set up an array list with a million elements
    @State(Scope.Benchmark)
    public static class BenchmarkState {
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
    // keep the benchmark short (these 3 annotations are not required; the defaults are fine if you have some time)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void getMiddle(BenchmarkState state, Blackhole blackhole) {
        // The compiler cannot tell that the result of the 'get' call is not actually used, due to the "black hole"
        // If we just used 'get', the compiler might eliminate the call entirely since its result is not used!
        blackhole.consume(state.arrayList.get(500_000));
    }
}
