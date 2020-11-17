import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static spark.Spark.get;

public class MultiThreadedServer {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        get("/loop", (req, res) -> {
            threadPool.execute(() -> {
                int i = 0;
                while (i++ < Integer.MAX_VALUE) {
                }
            });
            System.out.println("Done");
            return true;
        });
    }
}
