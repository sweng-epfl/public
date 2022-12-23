import model.Book;
import model.Chapter;
import providers.manganese.ManganeseBookProvider;
import providers.manganese.MockManganeseApi;

import java.util.List;
import java.util.Scanner;

/**
 * A simple interactive command line interface to test your implementation.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit everything in this file
 * You CAN delete this file
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class App {

    private static final Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        var provider = new ManganeseBookProvider(new MockManganeseApi());
        var state = State.SEARCH;


        Book currentBook = null;
        Chapter currentChapter = null;
        int currentPage = -1;

        while (true) {
            switch (state) {
                case SEARCH -> {
                    System.out.println("Search for a book, or keep empty to exit the program:");
                    var input = inputScanner.nextLine();
                    if (input.isBlank()) {
                        return;
                    }
                    var result = provider.findBooksByTitle(input);
                    var choice = showOptions(result.stream().map(Book::title).toList());
                    if (1 <= choice && choice <= result.size()) {
                        currentBook = result.get(choice - 1);
                        state = State.LIST_CHAPTERS;
                    }
                }
                case LIST_CHAPTERS -> {
                    var chapters = currentBook.chapters();
                    var choice = showOptions(chapters.stream().map(Chapter::title).toList());
                    if (1 <= choice && choice <= chapters.size()) {
                        currentChapter = chapters.get(choice - 1);
                        state = State.SELECT_PAGE;
                    } else {
                        state = State.SEARCH;
                    }
                }
                case SELECT_PAGE -> {
                    System.out.printf("Choose a page in the range [%d, %d] or 0 to go back%n", 1, currentChapter.numberOfPages());
                    var choice = inputScanner.nextInt();
                    // nextInt does not consume the \n at the end
                    inputScanner.nextLine();
                    if (1 <= choice && choice <= currentChapter.numberOfPages()) {
                        currentPage = choice;
                        state = State.READ;
                    } else {
                        state = State.LIST_CHAPTERS;
                    }
                }
                case READ -> {
                    var content = provider.fetchPage(currentChapter, currentPage);
                    System.out.println(content);
                    System.out.println("Press p for previous page, n for next page, else go back to chapter list");
                    var choice = inputScanner.nextLine();
                    if (choice.equals("p") && currentPage > 1) {
                        currentPage -= 1;
                    } else if (choice.equals("n") && currentPage < currentChapter.numberOfPages()) {
                        currentPage += 1;
                    } else {
                        state = State.LIST_CHAPTERS;
                    }
                }
            }
        }
    }

    private static int showOptions(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, options.get(i));
        }
        System.out.println("Choose an option, or 0 to go back:");
        var result = inputScanner.nextInt();
        // nextInt does not consume the \n at the end
        inputScanner.nextLine();
        return result;
    }

    private enum State {
        SEARCH,
        LIST_CHAPTERS,
        SELECT_PAGE,
        READ
    }
}
