package ch.epfl.sweng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Main {
    /**
     * Single required argument is name of file containing list of friends.
     * 
     * Each line's format is: 
     * <code>Name1 Name2</code> 
     * which indicates that Name1 is a friend of Name2.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: NameBook <filename>");
        }

        try {
            List<String[]> friends = readFriends(new FileInputStream(args[0]));
            NameBook nb = new NameBook(friends);
            System.out.println(nb.printFriendships());
        } catch (Exception e) {
            // Not much to do except quit.
            System.out.println("Unhandled exception: " + e.toString());
        }
    }

    /** Reads friends pairs from the given stream and returns a list of friend relationships. */
    public static List<String[]> readFriends(InputStream stream) throws IOException {
        // The reader will automatically close the stream passed as argument to this method
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            ArrayList<String[]> friends = new ArrayList<>();

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] words = inputLine.trim().split("\\s");
                if (words.length != 2) {
                    System.out.println("Bad input line: " + inputLine);
                } else {
                    friends.add(words);
                }
            }
            
            return friends;
        }
    }
}
