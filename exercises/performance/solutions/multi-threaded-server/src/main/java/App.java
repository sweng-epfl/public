public class App {
    private final static String wrongUsageMessage = "Wrong set of arguments.\nUsage on Unix like: './gradlew run --args=\"single\"' or './gradlew run --args=\"multi\"'\n" + 
    "Usage on Windows like: '.\\gradlew.bat run --args=\"single\"' or '.\\gradlew.bat run --args=\"multi\"'";
    private final static String singleThreadedArg = "single";
    private final static String multiThreadedArg = "multi";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(wrongUsageMessage);
            return;
        }
        var arg = args[0];
        String empty[] = {};
        switch (arg) {
        case singleThreadedArg:
            SingleThreadedServer.main(empty);
            break;
        case multiThreadedArg:
            MultiThreadedServer.main(empty);
            break;
        default:
            System.out.println(wrongUsageMessage);
            return;
        }

    }
}
