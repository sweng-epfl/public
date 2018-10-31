package ch.epfl.sweng.dp3.ex5.view;

import ch.epfl.sweng.dp3.ex5.controller.Controller;
import ch.epfl.sweng.dp3.ex5.model.GameType;
import ch.epfl.sweng.dp3.ex5.model.Model;
import java.util.InputMismatchException;
import java.util.Scanner;

/** Corresponds to a text-based user interface of the game. */
public class TextView implements View {

  private final Model model = null;
  private final Controller controller = null;

  /**
   * Creates a text-based user interface based on the given model and controller.
   *
   * @param model model to be used for the view.
   * @param controller controller that is used by the view.
   */
  public TextView(Model model, Controller controller) {
    // TODO
  }

  @Override
  public void createView() {
    System.out.println("\n\n=== Rock-paper-scissors ====");

    GameType[] gameTypes = model.getGameTypes();
    for (int i = 0; i < gameTypes.length; ++i) {
      System.out.println(i + ": " + gameTypes[i]);
    }

    while (true) {

      System.out.print("Select a game: ");
      Scanner sc = new Scanner(System.in);

      try {
        int gameType = sc.nextInt();
        controller.startGame(gameTypes[gameType]);

        break; // everything is fine
      } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
        String errorMsg = "Game type should be a number from 0 to " + (gameTypes.length - 1) + "\n";

        System.err.println(errorMsg);
      }
    }
  }

  @Override
  public void showGameType() {}

  @Override
  public void hideGameType() {}

  @Override
  public void showFirstPlayerChoices() {}

  @Override
  public void hideFirstPlayerChoices() {}

  @Override
  public void showGameResult(String result) {
    System.out.println(result);
  }

  @Override
  public void hideGameResult() {}

  @Override
  public void showPlayButton() {
    if (model.getGameType() == GameType.HUMAN_VS_COMPUTER) {

      int possibleGesturesNumber = model.getPossibleGestures().length;

      for (int i = 0; i < possibleGesturesNumber; ++i) {
        System.out.println(i + ": " + model.getPossibleGestures()[i]);
      }

      while (true) {
        System.out.print("Select gesture: ");
        Scanner sc = new Scanner(System.in);

        try {
          int g = sc.nextInt();
          controller.setFirstPlayer(model.getPossibleGestures()[g]);

          break; // everything is fine
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
          String errorMsg =
              "Gesture should be a number from 0 to " + (possibleGesturesNumber - 1) + "\n";
          System.err.println(errorMsg);
        }
      }
    }

    controller.play();
    System.out.println("Game played!");
    controller.newGame();
    System.out.println("Let's play a new game ...");
  }

  @Override
  public void hidePlayButton() {}

  @Override
  public void showStartButton() {}

  @Override
  public void hideStartButton() {}

  @Override
  public void showNewGameButton() {
    // create a new game
    // TODO
  }

  @Override
  public void hideNewGameButton() {}
}
