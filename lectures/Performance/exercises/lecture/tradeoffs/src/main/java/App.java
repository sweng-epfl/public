import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    // This app mimics a news reader. The user can start reading news titles from any index among the list.
    // The app will then show a batch of news titles starting at the user's chosen index.
    // However, fetching each individual news item is a little slow.

    // Exercise 1: Currently, the app fetches an entire batch at once, then displays them all.
    //             But this means waiting for the last item to be fetched before the user gets any feedback.
    //             Change the signature of `getNews` to return a `Stream<String>`,
    //             change its implementation to stream the results,
    //             and change `main` to show each result as it comes.
    // The `Stream` documentation, including methods you'll need, is available at https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html
    // You may also need specialized streams such as IntStream: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/IntStream.html

    // Exercise 2: Currently, if the user requests the same news again, they are re-fetched.
    //             Change `getNews` to add caching for individual news items.
    //             Don't worry about cache size or expiry for this exercise.

    // Exercise 3: Currently, if the user does the "expected" thing and reads news starting at 0 in order,
    //             each batch must wait for the user to request it to be loaded.
    //             Add prefetching to `main` so that once a batch is loaded, the next batch starts loading.
    // Hint: You can use `Executors.newSingleThreadExecutor().submit(...)` to run a background task.
    //       Make sure your cache still works if both the user and the prefetch task run concurrently, though!

    private static final int BATCH_SIZE = 5;

    public static void main(String[] args) {
        while(true) {
            int index = getInputIndex();

            for (var news : getNews(index, BATCH_SIZE)) {
                System.out.println(news);
            }

            System.out.println();
        }
    }

    private static List<String> getNews(int index, int count) {
        var result = new ArrayList<String>();
        for (int n = 0; n < count; n++) {
            // In a real app, this might involve a more efficient batched request, e.g., asking the server for multiple news items at once
            result.add(getNewsItem(index + n));
        }
        return result;
    }


    // Do not modify this method.
    private static String getNewsItem(int index) {
        // Pretend we're doing some lengthy load, e.g., from a website
        // In a real app, this would be, e.g., an HTTP request
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            // nothing, this can only happen if the program is terminating in which case we have nothing to do here
        }

        // generated via ChatGPT
        final String[] NEWS_TITLES = {
            "Nation’s Dogs Celebrate Annual Garbage Buffet Week",
            "Area Man Still Unsure If Neighbor’s Pet Is Dog or Really Confident Raccoon",
            "Scientists Discover Entire Ocean Just Spilled Glass of Water",
            "Study Finds 83% of Lost Socks Actually Escape to Start New Lives as Puppets",
            "Local Gym Confirms Treadmill Only Activated by Guilt",
            "New App Allows Users to Experience Full Existential Crisis in Under 30 Seconds",
            "Time Traveler From 3025 Still Can't Figure Out Remote Control",
            "Area Cat Declares Couch Entirely Off-Limits to Humans",
            "New Report Confirms Moon Has Been Watching You This Whole Time",
            "Man Who Said “Let’s Just Wing It” Now Head Surgeon",
            "Whale Becomes First Marine Lifeform to Ghost Entire Human Research Team",
            "Scientists Warn Earth’s Crust About to Flake",
            "Local Man Accidentally Joins Cult After Trying Free Samples at Mall",
            "Experts Confirm Shower Thoughts Now Account for 70% of All Human Innovation",
            "New Study Finds Most Clouds Just Freestyle Dancing in Sky",
            "Area Toddler Discovers Ancient Crayon Beneath Couch, Declares War",
            "Mysterious Hum in Office Identified as Employee’s Will to Live Fading",
            "Feral Printer Finally Captured After 3 Years Loose in Office Jungle",
            "Haunted Toaster Only Toasts Bread to Your Greatest Insecurity",
            "Dog Shocked to Discover Human Also Goes on Walks Without Permission"
        };
        return NEWS_TITLES[index % NEWS_TITLES.length];
    }

    // Do not modify this method.
    private static int getInputIndex() {
        while(true) {
            System.out.println("Index to start fetching news from (0-100), or 'exit': ");
            var input = new Scanner(System.in).nextLine();
            if (input.equals("exit")) {
                System.exit(0);
            }
            try {
                int result = Integer.parseInt(input);
                if (result >= 0 && result <= 100) {
                    return result;
                }
            } catch (NumberFormatException e) {
                // continue looping
            }
        }
    }
}
