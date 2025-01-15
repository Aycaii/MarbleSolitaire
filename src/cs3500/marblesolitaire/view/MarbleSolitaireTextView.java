package cs3500.marblesolitaire.view;

import java.io.IOException;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

public class MarbleSolitaireTextView implements MarbleSolitaireView {

  private final MarbleSolitaireModelState model;
  private final Appendable out;

  /**
   * Constructs a MarbleSolitaireTextView with the given model.
   *
   * @param model the model representing the state of the game
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState model) {
    this(model, System.out);
  }

  /**
   * Constructs a MarbleSolitaireTextView with the given model and appendable.
   *
   * @param model the model representing the state of the game
   * @param out the appendable to transmit the output to
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState model, Appendable out) throws IllegalArgumentException {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model or appendable cannot be null");
    }
    this.model = model;
    this.out = out;
  }

  /**
   * Generates a textual representation of the game board.
   *
   * @return a string representing the game board
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    int boardSize = model.getBoardSize();
    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        getSlotState(result, r, c, model);
        if (c < boardSize - 1) {
          result.append(" ");
        }
      }
      if (r < boardSize - 1) {
        result.append("\n");
      }
    }
    return result.toString();
  }

  public static void getSlotState(StringBuilder result, int r, int c, MarbleSolitaireModelState model) {
    MarbleSolitaireModelState.SlotState slotState = model.getSlotAt(r, c);
    switch (slotState) {
      case Marble:
        result.append("O");
        break;
      case Empty:
        result.append("_");
        break;
      case Invalid:
        result.append(" ");
        break;
    }
  }

  /**
   * Render the board in the format produced by the toString method above
   *
   * @throws IOException if input is invalid or there is an error converting to string
   */
  @Override
  public void renderBoard() throws IOException {
    out.append(this.toString()).append("\n");
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if there is an error converting to string
   */

  @Override
  public void renderMessage(String message) throws IOException {
    out.append(message).append("\n");
  }
}
