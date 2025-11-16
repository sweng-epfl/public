import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    // Goal: Remove the "Project Gutenberg" header from a .txt file downloaded from their website, for personal use
    // We detect the line that contains two "***"
    // But maybe not in the most effective way...

    public static void main(String[] args) throws IOException {
        System.out.println("Hit any key to continue...");
        System.in.read();

        extract("lupin");
    }

    private static void extract(String filename) throws IOException {
        String text = read(filename + ".txt");
        String result = "";
        int stars = 0;
        int starsGroups = 0;
        boolean headerEnd = false;
        for (char c : text.toCharArray()) {
            if (headerEnd) {
                result = append(result, c);
            } else {
                stars = counter(stars, c, '*');
                if (stars == 3) {
                    starsGroups = starsGroups + 1;
                }
                if (starsGroups == 2) {
                    headerEnd = true;
                }
            }
        }
        write(filename + ".extracted.txt", result);
    }

    private static String read(String path) throws IOException {
        return Files.readString(Path.of(path));
    }

    private static void write(String path, String text) throws IOException {
        Files.writeString(Path.of(path), text);
    }

    private static String append(String s, char c) {
        return s + c;
    }

    private static int counter(int prev, char chr, char target) {
        if (chr == target) {
            return prev + 1;
        }
        return 0;
    }
}
