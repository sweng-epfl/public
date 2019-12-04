package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit or remove the signatures of the existing methods/constructors.
// You CANNOT add new constructors.
// You CAN change the implementation of the methods/constructors.
// You CAN add new methods.
// You CAN add interface implementations.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A leaderboard that counts points.
 * The points are tallied as follows:
 * - Posting a question is worth 5 points.
 * - Posting an answer is worth 1 point.
 */
public final class Leaderboard implements Observer {
    // BigInts to avoid overflows
    private final Map<User, BigInteger> scores;

    /**
     * Constructs a leaderboard for the specified forum.
     *
     * @throws IllegalArgumentException if the forum is null.
     */
    public Leaderboard(Forum forum) {
        if (forum == null) {
            throw new IllegalArgumentException("Null forum.");
        }

        scores = new HashMap<>();

        forum.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object arg) {
        Post post = (Post) arg;
        scores.putIfAbsent(post.getAuthor(), BigInteger.ZERO);

        long score;
        if (post instanceof Question) {
            score = 5;
        } else {
            score = 1;
        }

        scores.put(post.getAuthor(), scores.get(post.getAuthor()).add(BigInteger.valueOf(score)));
    }

    /**
     * Prints the leaderboard, using the following rules:
     * - Users are sorted by their point.
     * - If users have the same number of points, they are sorted by alphabetical order of names.
     * - Each user is printed as one line on the leaderboard.
     * - The line format is the following:
     * --- The line begins with a hash sign '#'
     * --- Immediately after the sign comes the user's rank.
     * ----- The user with most points has rank 1, the next user has rank 2, and so on.
     * ----- Two users with the same number of points have the same rank.
     * ------ If there are N users with the same number of points, N-1 ranks are skipped afterwards.
     * --- After the rank comes a space.
     * --- After that comes the user's name.
     * --- After the name comes a space.
     * --- After that comes the number of points.
     * - There is no line separator at the end, only between lines.
     * ===
     * [Begin example 0]
     * #1 George 9001
     * [End example 0]
     * [Begin example 1]
     * #1 George 9001
     * #2 Alice 100
     * #3 Bob 5
     * [End example 1]
     * [Begin example 2]
     * #1 George 9001
     * #2 Alice 100
     * #2 Carol 100
     * #4 Bob 5
     * [End example 2]
     */
    @Override
    public String toString() {
        int rank = 0;
        int similarCount = 1;
        BigInteger last = null;

        List<Map.Entry<User, BigInteger>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort(Leaderboard::compareEntries);

        StringBuilder result = new StringBuilder();

        for (Map.Entry<User, BigInteger> entry : sortedScores) {
            if (entry.getValue().equals(last)) {
                similarCount++;
            } else {
                rank += similarCount;
                similarCount = 1;
                last = entry.getValue();
            }

            result.append('#');
            result.append(rank);
            result.append(' ');
            result.append(entry.getKey().getName());
            result.append(' ');
            result.append(entry.getValue());
            result.append(System.lineSeparator());
        }

        return result.toString().trim();
    }

    private static int compareEntries(Map.Entry<User, BigInteger> a, Map.Entry<User, BigInteger> b) {
        int first = b.getValue().compareTo(a.getValue());
        if (first != 0) {
            return first;
        }

        return a.getKey().getName().compareTo(b.getKey().getName());
    }
}
