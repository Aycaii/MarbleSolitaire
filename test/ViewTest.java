import org.junit.Test;

import cs3500.marblesolitaire.view.*;
import cs3500.marblesolitaire.model.hw02.*;

import static org.junit.Assert.assertEquals;

public class ViewTest {

  // Create test models
  static MarbleSolitaireModel model = new EnglishSolitaireModel();
  static MarbleSolitaireModel model2 = new EnglishSolitaireModel(4, 5);

  // Create test views
  static MarbleSolitaireTextView testGame = new MarbleSolitaireTextView(model);
  static MarbleSolitaireTextView testOther = new MarbleSolitaireTextView(model2);

  /**
   * Main method to manually test the views.
   */
  public static void main(String[] args) {
    System.out.println(testGame.toString());

    System.out.println(model.getSlotAt(3, 3));
    model.move(3, 1, 3, 3);

    System.out.println();
    System.out.println(testGame.toString());

    System.out.println();
    model.move(3, 4, 3, 2);
    System.out.println(testGame.toString());

    System.out.println();
    System.out.println(testOther.toString());
  }

  /**
   * Test the toString method of MarbleSolitaireTextView.
   */
  @Test
  public void testToString() {
    // Test to make sure the first line is correct
    String expected =
            "    O O O    \n" +
            "    O O O    \n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "O O O O O O O\n" +
            "    O O O    \n" +
            "    O O O    ";
    assertEquals(expected, testGame.toString());

    model.move(3, 1, 3, 3);

    expected =
                    "    O O O    \n" +
                    "    O O O    \n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O    \n" +
                    "    O O O    ";

    assertEquals(expected, testGame.toString());
  }

}
