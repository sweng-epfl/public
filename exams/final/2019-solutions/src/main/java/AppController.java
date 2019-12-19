// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

final class AppController {
    private static final List<String> VIEW_CHOICES = Arrays.asList("search", "show");
    private final Directory directory;

    /**
     * Creates a new controller that uses the given directory.
     *
     * @throws IllegalArgumentException if and only if the directory is null.
     */
    AppController(Directory directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Directory cannot be null.");
        }

        this.directory = directory;
    }

    /**
     * Handles the given user input, returning the corresponding view.
     * Ignores leading and trailing spaces in the input.
     * If the input is invalid, returns null.
     * --
     * The input can either be empty, which leads to the main view, or non-empty, in which case it must be a command followed by text.
     * The command and the text are separated by one or more spaces; the text itself can contain spaces.
     * If the text is empty even though the input is non-empty, the input is invalid.
     * If the command is unknown, the input is invalid.
     *
     * @throws IllegalArgumentException if and only if the input is null.
     */
    AppView handle(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }

        String[] parts = input.trim().split(" ", 2);
        String command = parts[0];
        if (parts.length != 2) {
            if (command.isEmpty()) {
                return handleMain();
            }
            return null;
        }

        String text = parts[1].trim();
        if (command.equals("search")) {
            return handleSearch(text);
        } else if (command.equals("show")) {
            return handleShow(text);
        } else {
            return null;
        }
    }

    private AppView handleMain() {
        return new AppView("Welcome!", VIEW_CHOICES);
    }

    private AppView handleSearch(String text) {
        List<Person> results = directory.search(text);
        if (results.isEmpty()) {
            return new AppView("The search returned no results.", VIEW_CHOICES);
        }
        return new AppView(
                results.stream().map(p -> p.name).collect(Collectors.joining(System.lineSeparator())),
                VIEW_CHOICES
        );
    }

    private AppView handleShow(String text) {
        List<Person> results = directory.search(text);
        if (results.size() != 1) {
            return new AppView("The search did not return exactly one person.", VIEW_CHOICES);
        }
        Person person = results.get(0);
        StringBuilder result = new StringBuilder();
        result.append(person.name).append(System.lineSeparator());
        if (person.title != null) {
            result.append(person.title).append(System.lineSeparator());
        }
        if (person.age != null) {
            result.append(person.age).append(System.lineSeparator());
        }
        if (person.address != null) {
            result.append(person.address).append(System.lineSeparator());
        }
        return new AppView(
                result.toString(),
                VIEW_CHOICES
        );
    }
}
