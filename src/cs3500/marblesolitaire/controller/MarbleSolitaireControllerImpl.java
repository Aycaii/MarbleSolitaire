package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * This class represent the implementation of the marble solitaire controller
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Readable rd;

  /**
   * Constructs a MarbleSolitaireControllerImpl with the given model, view, and readable.
   *
   * @param model representing the state of the game
   * @param view the view to transmits the output to
   * @param rd to read the input from
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view, Readable rd) throws IllegalArgumentException {
    if (model == null || view == null || rd == null) {
      throw new IllegalArgumentException("inputs cannot be null");
    }
    this.model = model;
    this.view = view;
    this.rd = rd;
  }

  /**
   * Plays a game of marble solitaire from the controller
   *
   * @throws IllegalStateException if the game state cannot render properly
   */
  @Override
  public void playGame() throws IllegalStateException {
    Scanner scanner = new Scanner(this.rd);
    try {
      while (!model.isGameOver()) {

        view.renderBoard();
        view.renderMessage("Score: " + model.getScore());


        //i modified this enter move print line here solely for clarity in the main
        //however, i have not touched the implementation of the controller at all besides this.
        System.out.println("Enter move: ");


        if (!scanner.hasNext()) {
          throw new IllegalStateException("invalid input");
        }


        String input = scanner.next();

        if (processInput(input, scanner)){
          return;
        }

      }

      view.renderMessage("Game over!");
      view.renderBoard();
      view.renderMessage("Score: " + model.getScore());

    } catch (IOException e) {
      throw new IllegalStateException("could not convert input to view", e);
    }
  }

  /**
   * Helper method for playGame()
   * Process the users input to see whether they are trying to make a move or quit the game
   *
   * @param input an instruction
   * @param scanner user input of an instruction
   * @return boolean
   * @throws IOException when there is an invalid instruction
   */

  private boolean processInput(String input, Scanner scanner) throws IOException {
    if (input.equalsIgnoreCase("q")) {
      view.renderMessage("Game quit!");
      view.renderMessage("State of game when quit:");
      view.renderBoard();
      view.renderMessage("Score: " + model.getScore());
      return true;
    }

    try {
      int fromRow = Integer.parseInt(input) - 1;
      int fromCol = getNextInt(scanner) - 1;
      int toRow = getNextInt(scanner) - 1;
      int toCol = getNextInt(scanner) - 1;

      model.move(fromRow, fromCol, toRow, toCol);
    } catch (NumberFormatException e) {
      view.renderMessage("invalid input");
    } catch (IllegalArgumentException e) {
      view.renderMessage("invalid move " + e.getMessage());
    }

    return false;
  }

  /**
   * helper method for playGame
   * Gets the next integer in the scanner which is used to read the user's desired move
   *
   * @param scanner user input of an integer
   * @return the given integer
   */
  private int getNextInt(Scanner scanner) {
    if (!scanner.hasNextInt()) {
      throw new IllegalArgumentException("expected integer");
    }
    return scanner.nextInt();
  }
}
