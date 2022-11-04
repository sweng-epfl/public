
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that represents an abstraction of a Joke.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public class Joke {
    private String content;
    private JokeType type;

    public Joke(String content, JokeType type) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Joke cannot be null or blank");
        }
        if (!isFunny(content)) {
            throw new IllegalArgumentException("Joke is not funny");
        }
        if (type == null) {
            throw new IllegalArgumentException("Joke type cannot be null");
        }

        this.content = content.strip();
        this.type = type;
    }

    private static boolean isFunny(String text) {
        List<String> lines = getLines(text);
        if (lines.size() > 2) {
            return false;
        }
        if (averageWordsPerLine(lines) > 5) {
            return false;
        }


        return true;
    }

    public String getContent() {
        return this.content;
    }

    /**
     * Returns a new Joke object that has an adapted content text.
     * Adapting gets done by replacing certain words, according to the provided map.
     *
     * @param replace a map that provides the words that should be replaced and their replacement.
     * @return a joke with an adapted content.
     */
    public Joke adapt(Map<String, String> replace) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : this.content.split(" ")) {
            if (replace.containsKey(word)) {
                stringBuilder.append(replace.get(word));
            } else {
                stringBuilder.append(word);
            }
            stringBuilder.append(" ");
        }

        return new Joke(stringBuilder.toString().strip(), this.type);
    }

    private static int averageWordsPerLine(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            sum += line.split(" ").length;
        }
        return sum / lines.size();
    }

    private static List<String> getLines(String text) {
        var lines = text.split("\n");
        return Arrays.stream(lines)
                .map(String::strip)
                .filter(l -> l.length() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        switch (type) {
            case OneLiner: {
                return this.content.replace('\n', ' ');
            }
            case Observational: {
                return "Have you ever noticed, " + this.content;
            }
            default: {
                return this.content;
            }
        }
    }

}