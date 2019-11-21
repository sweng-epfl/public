package ch.epfl.sweng.dp3.solutions.ex5.model;

/** Contains the main functionality of comparing gestures and deciding the outcome of the game. */
public class GameRules {

  // every row of the array contains 2 elements. A row {A, B} means
  // that A wins B and that B loses from A
  // there should not be cycles of length 2, i.e. row {A, B}, row {B, A}
  // create cycle A -> B -> A. Cycles of greater length are allowed, e.g.
  // rock -> scissors -> paper -> rock
  private static Gesture[][] winning = {
    {Gesture.ROCK, Gesture.SCISSORS},
    {Gesture.SCISSORS, Gesture.PAPER},
    {Gesture.PAPER, Gesture.ROCK}
  };

  /**
   * Compares two given gestures and decides if there is a winner or not and in case of a winner who
   * it is.
   *
   * @param a gesture of first player.
   * @param b gesture of second player.
   * @return 0 if there is a draw between the two players. Or -1 in case the first player wins the
   *     second player; 1 in case second player wins the first one.
   * @throws IllegalArgumentException is thrown if one or both of the arguments are null.
   */
  public static int getWinner(Gesture a, Gesture b) throws IllegalArgumentException {

    if (a == null || b == null) {
      throw new IllegalArgumentException("none of the arguments can be null");
    }

    // we have a draw
    if (a == b) {
      return 0;
    }

    for (Gesture[] g : winning) {
      if (g[0] == a && g[1] == b) {
        return -1;
      }
    }

    return 1;
  }
}
