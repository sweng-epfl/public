import static spark.Spark.get;

public class SingleThreadedServer {

    public static void main(String[] args) {
        get("/loop", (req, res) -> {
            int i = 0;
            while (i++ < Integer.MAX_VALUE) {
            }
            System.out.println("Done");
            return true;
        });
    }
}
