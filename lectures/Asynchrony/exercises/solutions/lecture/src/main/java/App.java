public class App {
    public static void main(String[] args) {
        // For this example app, we just want to wait for everything to finish, so we use `join` manually;
        // in a real application, this would be handled by a framework.
        // (or by the language itself, e.g., C# can have its "main" method return a future)
        // DO NOT USE .join() IN YOUR CODE UNLESS YOU HAVE A VERY GOOD REASON TO!

        System.out.println("--- Basics ---");

        System.out.println("- Printing today's weather");
        Basics.printTodaysWeather().join();

        System.out.println("- Uploading today's weather");
        Basics.uploadTodaysWeather().join();

        System.out.println("- Printing some weather");
        Basics.printSomeWeather().join();

        System.out.println("- Printing all weather?");
        Basics.tryPrintAllWeather().join();

        System.out.println();
        System.out.println("--- Advanced ---");

        System.out.println("- Uploading a batch of 2");
        Advanced.upload(new String[] { "one", "two" }).join();

        System.out.println("- Uploading a batch of 20");
        Advanced.upload(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                                       "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }).join();

        System.out.println("- Downloading data");
        try {
            Advanced.download().join();
        } catch (RuntimeException e) {
            // Of course, real code would have more serious error handling!
            System.out.println("Could not download");
        }

        System.out.println("- Downloading data, reliably");
        try {
            Advanced.reliableDownload().join();
        } catch (RuntimeException e) {
            System.out.println("Could not download");
        }
    }
}
