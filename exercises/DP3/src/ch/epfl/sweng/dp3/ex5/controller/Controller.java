package ch.epfl.sweng.dp3.ex5.controller;

import ch.epfl.sweng.dp3.ex5.model.GameType;
import ch.epfl.sweng.dp3.ex5.model.Gesture;
import ch.epfl.sweng.dp3.ex5.model.Model;
import ch.epfl.sweng.dp3.ex5.view.View;

/**
 * Class that takes input from the user (either GUI or text-based) and decides how the view and/or
 * the model is changed.
 */
public class Controller {

  private View view = null;
  private final Model model = null;

  /**
   * Creates the controller.
   *
   * @param model given model which the controller is going to interact with.
   */
  public Controller(Model model) {
    // TODO
  }

  /**
   * Sets the view of the controller.
   *
   * @param view given view which the controller is going to interact with.
   */
  public void setView(View view) {
    // TODO
  }

  /**
   * Starts a game based on the given type.
   *
   * @param type of the game to be be played.
   * @throws IllegalArgumentException thrown in case type of the game is not known or null.
   */
  public void startGame(GameType type) throws IllegalArgumentException {
    model.setGameType(type);

    if (type == GameType.HUMAN_VS_COMPUTER) {
      view.hideGameType();
      view.hideStartButton();

      view.showFirstPlayerChoices();
      view.showPlayButton();
    } else if (type == GameType.COMPUTER_VS_COMPUTER) {
      view.hideGameType();
      view.hideStartButton();

      view.showPlayButton();
    } else {
      throw new IllegalArgumentException("type of the game is unknown!");
    }
  }

  /** "Plays" (executes) the given game. */
  public void play() {

    model.play();
    int winner = model.getWinner();

    // create output result to be shown by the view
    String result = ">> ";
    String firstPlayer = null, secondPlayer = null;

    if (model.getGameType() == GameType.HUMAN_VS_COMPUTER) {
      result += "You played " + model.getFirstPlayer() + ",\n ";
      result += "computer played: " + model.getSecondPlayer() + ".\n\n";
      firstPlayer = "You";
      secondPlayer = "Computer";
    } else if (model.getGameType() == GameType.COMPUTER_VS_COMPUTER) {
      result += "First Computer played " + model.getFirstPlayer() + ",\n ";
      result += "other computer played: " + model.getSecondPlayer() + ".\n\n";
      firstPlayer = "First computer";
      secondPlayer = "Second computer";
    } else {
      assert false : "type of the game is invalid";
    }

    if (winner == 0) {
      result += "It's a draw.";
    } else if (winner == -1) {
      result += firstPlayer + " won.";
    } else if (winner == 1) {
      result += secondPlayer + " won.";
    }

    view.showGameResult(result);
    view.showNewGameButton();
  }

  /**
   * Sets the first player of the game.
   *
   * @param g gesture of the first player.
   */
  public void setFirstPlayer(Gesture g) {
    model.setFirstPlayer(g);
  }

  /** Creates a new game to be played. */
  public void newGame() {
    view.showStartButton();
    view.showGameType();

    view.hidePlayButton();
    view.hideGameResult();
    view.hideNewGameButton();
    view.hideFirstPlayerChoices();
  }
}
