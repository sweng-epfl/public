import model.ReactionService;
import model.TrendingService;
import model.auth.RemoteAuthenticationService;
import model.data.GradedBook;
import model.data.MangaDatabase;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * A simple cli app that allows the user to see the trending books and to react to them.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 * You CAN edit everything in this file <p>
 * You CAN delete this file <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! <p>
 */
public class App {
    public static void main(String[] args) {
        var authService = new RemoteAuthenticationService();
        var database = new MangaDatabase();
        var reactionService = new ReactionService(authService, database);
        var trendingService = new TrendingService(database);
        var scanner = new Scanner(System.in);
        var running = true;

        System.out.println("Commands:");
        System.out.println("    'trending' to get the trending books");
        System.out.println("    'trending <number>' to get the first <number> trending books");
        System.out.println("    'login' to login");
        System.out.println("    'logout' to logout");
        System.out.println("    'like <title>' to like the book with the given title");
        System.out.println("    'dislike <title>' to dislike the book with the given title");
        System.out.println("    'exit' to exit the program.");

        while (running) {
            System.out.print("> ");
            var input = scanner.nextLine().split(" ");

            switch (input[0]) {
                case "trending":
                    CompletableFuture<Void> trendingFuture = null;
                    if (input.length == 1) {
                        trendingFuture = trendingService.getTrending().thenAccept(App::printTrendingMedia);
                    } else {
                        trendingFuture = trendingService.getTrending(Integer.parseInt(input[1])).thenAccept(App::printTrendingMedia);
                    }

                    trendingFuture.exceptionally(throwable -> {
                        System.out.println("An error occurred while getting the trending books: " + throwable.getMessage());
                        return null;
                    }).join();

                    break;
                case "login":
                    System.out.print("Username: ");
                    var username = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();
                    authService.login(username, password)
                            .thenAccept(v -> System.out.println("Successfully logged in as " + username))
                            .exceptionally(e -> {
                                System.out.println("Failed to log in");
                                return null;
                            }).join();
                    break;
                case "logout":
                    authService.logout()
                            .thenAccept(v -> System.out.println("Successfully logged out"))
                            .exceptionally(e -> {
                                System.out.println("Failed to log out");
                                return null;
                            }).join();
                    break;

                case "like":
                    if (input.length < 2) {
                        System.out.println("Please provide a title");
                        break;
                    }

                    reactionService.like(input[1])
                            .thenAccept(v -> System.out.println("Liked: " + input[1]))
                            .exceptionally(e -> {
                                System.out.println("An error occurred while liking: " + e.getMessage());
                                return null;
                            }).join();
                    break;

                case "dislike":
                    if (input.length < 2) {
                        System.out.println("Please provide a title");
                        break;
                    }

                    reactionService.dislike(input[1])
                            .thenAccept(v -> System.out.println("Disliked: " + input[1]))
                            .exceptionally(e -> {
                                System.out.println("An error occurred while disliking: " + e.getMessage());
                                return null;
                            }).join();
                    break;

                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }

        scanner.close();
    }

    private static void printTrendingMedia(List<GradedBook> l) {
        for (int i = 0; i < l.size(); i++) {
            System.out.printf("%d: %s - Grade: %.2f / 10\n", i + 1, l.get(i).getTitle(), l.get(i).getGrade() * 1d / 100);
        }
    }
}
