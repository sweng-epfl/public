package ch.epfl.sweng.dp3.solutions.ex5.view;

/** Interface to be implemented by possible views implementing the game. */
public interface View {

  /** Creates the view of the game. */
  void createView();

  /** Shows the first player. */
  void showFirstPlayerChoices();

  /** Hides the first player. */
  void hideFirstPlayerChoices();

  /** Shows the play button which is pressed after players have chosen their gestures. */
  void showPlayButton();

  /** Hides play button. */
  void hidePlayButton();

  /** Show start button that starts a game. */
  void showStartButton();

  /** Hides start button. */
  void hideStartButton();

  /** Shows the possible types of the game. */
  void showGameType();

  /** Hides the types of the game. */
  void hideGameType();

  /** Show the new game button. */
  void showNewGameButton();

  /** Hides the new game button. */
  void hideNewGameButton();

  /**
   * Shows the result of the game.
   *
   * @param result of the game containing information on whether there was a victory, a lose or a
   *     draw.
   */
  void showGameResult(String result);

  /** Hides the result of the game. */
  void hideGameResult();
}
