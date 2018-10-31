package ch.epfl.sweng.dp3.solutions.ex5.view;

import ch.epfl.sweng.dp3.solutions.ex5.controller.Controller;
import ch.epfl.sweng.dp3.solutions.ex5.model.GameType;
import ch.epfl.sweng.dp3.solutions.ex5.model.Gesture;
import ch.epfl.sweng.dp3.solutions.ex5.model.Model;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/** Corresponds to a graphical user interface of the game. */
public class GUIView implements View {

  // drop-down list of the possible game types
  private JComboBox<GameType> gameType;

  // drop down list of first player choice
  private JComboBox<Gesture> firstPlayerChoice;

  // not needed in the current implementation
  // could be used if we wanted to implement human vs human
  // private JComboBox<Gesture> secondPlayerChoice;

  private JButton startButton;
  private JButton playButton;
  private JButton newGameButton;

  private JTextArea gameResult;

  private final Model model;
  private final Controller controller;

  /**
   * Creates a graphical user interface based on the given model and controller.
   *
   * @param model model to be used for the view.
   * @param controller controller that is used by the view.
   */
  public GUIView(Model model, Controller controller) {
    this.model = model;
    this.controller = controller;
    controller.setView(this);
  }

  @Override
  public void createView() {
    JPanel panel = new JPanel(new FlowLayout());
    JFrame frame = new JFrame("Rock Paper Scissors");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(300, 180));
    frame.setResizable(false);

    GameType[] gameTypes = model.getGameTypes();
    gameType = new JComboBox<>(gameTypes);

    startButton = new JButton("Start");
    startButton.addActionListener(
        new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            controller.startGame((GameType) gameType.getSelectedItem());
          }
        });

    panel.add(gameType);
    panel.add(startButton);

    // add  (hidden) game GUI elements
    Gesture[] choices = model.getPossibleGestures();

    firstPlayerChoice = new JComboBox<Gesture>(choices);
    firstPlayerChoice.setVisible(false);

    panel.add(firstPlayerChoice);

    playButton = new JButton("Play");
    playButton.setVisible(false);
    playButton.addActionListener(
        new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            controller.setFirstPlayer((Gesture) firstPlayerChoice.getSelectedItem());
            controller.play();
          }
        });
    panel.add(playButton);

    gameResult = new JTextArea(5, 23);
    gameResult.setVisible(false);
    panel.add(gameResult);

    newGameButton = new JButton("New Game");
    newGameButton.setVisible(false);
    newGameButton.addActionListener(
        new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            controller.newGame();
          }
        });
    panel.add(newGameButton);
    //

    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.setVisible(true);
  }

  @Override
  public void showGameType() {
    gameType.setVisible(true);
  }

  @Override
  public void hideGameType() {
    gameType.setVisible(false);
  }

  @Override
  public void showFirstPlayerChoices() {
    firstPlayerChoice.setVisible(true);
  }

  @Override
  public void hideFirstPlayerChoices() {
    firstPlayerChoice.setVisible(false);
  }

  @Override
  public void showGameResult(String result) {
    gameResult.setText(result);
    gameResult.setVisible(true);
  }

  @Override
  public void hideGameResult() {
    gameResult.setVisible(false);
  }

  @Override
  public void showPlayButton() {
    playButton.setVisible(true);
  }

  @Override
  public void hidePlayButton() {
    playButton.setVisible(false);
  }

  @Override
  public void showStartButton() {
    startButton.setVisible(true);
  }

  @Override
  public void hideStartButton() {
    startButton.setVisible(false);
  }

  @Override
  public void showNewGameButton() {
    newGameButton.setVisible(true);
  }

  @Override
  public void hideNewGameButton() {
    newGameButton.setVisible(false);
  }
}
