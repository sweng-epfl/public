import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser for quiz questions.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class QuizParser {
    /** Creates a new QuizParser. */
    public QuizParser() {
        // Nothing... maybe this will change in a future version of SuperQuiz?
    }

    /** Parses a question from text. (SuperQuiz developers forgot to document the format) */
    public QuizQuestion parse(String text) {
        var lines = getLines(text);
        if (lines.size() == 0) {
            throw new IllegalArgumentException();
        }

        int currentIndex = 0;

        if (!lines.get(currentIndex).startsWith("# ")) {
            throw new QuizFormatException();
        }
        String heading = lines.get(currentIndex).substring(1).trim();
        currentIndex++;

        if (lines.size() == currentIndex) {
            throw new IllegalArgumentException();
        }

        if (lines.get(currentIndex).startsWith("- ")) {
            List<String> choices = new ArrayList<>();
            while (currentIndex < lines.size() && lines.get(currentIndex).startsWith("- ")) {
                choices.add(lines.get(currentIndex).substring(1).trim());
                currentIndex++;
            }

            if (currentIndex == lines.size()) {
                throw new IllegalArgumentException();
            }

            if (lines.get(currentIndex).startsWith("> ")) {
                if (currentIndex != lines.size() - 1) {
                    throw new IllegalArgumentException();
                }

                String solution = lines.get(currentIndex).substring(1).trim();
                return new QuizQuestion(heading, choices, solution);
            }

            throw new QuizFormatException();
        }

        throw new QuizFormatException();
    }

    /** Splits the given text into lines, stripping them and ignoring empty ones. */
    private static List<String> getLines(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        // Hardcoded separator so the format doesn't change across operating systems
        var lines = text.split("\n");
        return Arrays.stream(lines)
                .map(String::strip)
                .filter(l -> l.length() > 0)
                .collect(Collectors.toList());
    }
}
