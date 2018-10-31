package ch.epfl.sweng.dp3.solutions.ex5.model;

import java.util.Random;

/** Holds all the information needed for one game. */
public class Model {

  private GameType type;
  private Gesture firstPlayer, secondPlayer;

  // for creating random gestures
  private Random random;

  /** Constructs the model. */
  public Model() {
    random = new Random();
  }

  /**
   * Sets the type of the given game.
   *
   * @param type of the game.
   */
  public void setGameType(GameType type) {
    this.type = type;
  }

  /**
   * Gets the given game type.
   *
   * @return type of game.
   */
  public GameType getGameType() {
    return type;
  }

  /**
   * Sets the first player of the game with the given gesture.
   *
   * @param g gesture of first player.
   */
  public void setFirstPlayer(Gesture g) {
    firstPlayer = g;
  }

  /**
   * Gets first player's gesture.
   *
   * @return gesture of first player.
   */
  public Gesture getFirstPlayer() {
    return firstPlayer;
  }

  /**
   * Sets the second player of the game with the given gesture.
   *
   * @param g gesture of second player.
   */
  public void setSecondPlayer(Gesture g) {
    secondPlayer = g;
  }

  /**
   * Gets second player's gesture.
   *
   * @return gesture of second player.
   */
  public Gesture getSecondPlayer() {
    return secondPlayer;
  }

  // returns uniformly a random gesture from all the possible gestures
  private Gesture getRandomGesture() {
    Gesture[] possibleGestures = getPossibleGestures();
    return possibleGestures[random.nextInt(possibleGestures.length)];
  }

  /**
   * "Plays" the given game.
   *
   * @throws IllegalStateException thrown in case the game is in not consistent state.
   */
  public void play() throws IllegalStateException {
    if (type == GameType.HUMAN_VS_COMPUTER) {
      setSecondPlayer(getRandomGesture());
    } else if (type == GameType.COMPUTER_VS_COMPUTER) {
      setFirstPlayer(getRandomGesture());
      setSecondPlayer(getRandomGesture());
    } else {
      throw new IllegalStateException("Given type: " + type + " is missing");
    }
  }

  /**
   * Decides the winner of a played game.
   *
   * @return 0 if there is a draw between the two players. Or -1 in case the first player wins the
   *     second player; 1 in case second player wins the first one.
   * @throws IllegalArgumentException in case players do not exist, i.e. game has not been played.
   */
  public int getWinner() throws IllegalArgumentException {
    int winner;

    try {
      winner = GameRules.getWinner(firstPlayer, secondPlayer);
    } catch (IllegalArgumentException e) {
      throw e;
    }

    return winner;
  }

  /**
   * Get all the available gestures of a player.
   *
   * @return all possible gestures.
   */
  public Gesture[] getPossibleGestures() {
    return Gesture.values();
  }

  /**
   * Get all available game types.
   *
   * @return all game types.
   */
  public GameType[] getGameTypes() {
    return GameType.values();
  }
}
