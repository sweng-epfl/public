package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit or remove the signatures of the existing methods/constructors.
// You CANNOT add new constructors.
// You CAN change the implementation of the methods/constructors.
// You CAN add new methods.
// You CAN add interface implementations.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/**
 * A leaderboard that counts points.
 * The points are tallied as follows:
 * - Posting a question is worth 5 points.
 * - Posting an answer is worth 1 point.
 */
public final class Leaderboard {
    /**
     * Constructs a leaderboard for the specified forum.
     *
     * @throws IllegalArgumentException if the forum is null.
     */
    public Leaderboard(Forum forum) {
        // TODO
        throw new UnsupportedOperationException();
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
        // TODO
        throw new UnsupportedOperationException();
    }
}
