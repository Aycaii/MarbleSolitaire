package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;

public class TriangleSolitaireTextView extends MarbleSolitaireTextView {

  private final MarbleSolitaireModelState model;

  /**
   * Constructs a Triangle Solitaire String text with the given model.
   *
   * @param model the model representing the state of the game
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model) {
    super(model);
    this.model = model;
  }

  /**
   * Constructs a TriangleSolitaireTextView with the given model and appendable.
   *
   * @param model the model representing the state of the game
   * @param out   the appendable to transmit the output to
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable out) {
    super(model, out);
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    int boardSize = model.getBoardSize();

    for (int r = 0; r < boardSize; r++) {

      for (int space = 0; space < boardSize - r - 1; space++) {
        result.append(" ");
      }

      for (int c = 0; c <= r; c++) {
        MarbleSolitaireTextView.getSlotState(result, r, c, model);
        if (c < r) {
          result.append(" ");
        }
      }
      if (r < boardSize - 1) {
        result.append("\n");
      }
    }
    return result.toString();
  }

}
