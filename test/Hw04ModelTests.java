import org.junit.Before;
import org.junit.Test;

import cs3500.marblesolitaire.MarbleSolitaire;
import cs3500.marblesolitaire.model.hw02.*;
import static org.junit.Assert.*;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;

/**
 * This class tests the toString move and other functions for triangle and european solitaire models.
 */
public class Hw04ModelTests {

  MarbleSolitaireModel european;
  MarbleSolitaireModel european2;
  MarbleSolitaireModel triangular;
  MarbleSolitaireModel triangular2;

  MarbleSolitaireTextView europeanTextView;
  TriangleSolitaireTextView triangleTextView;

  @Before
  public void setup() {
    european = new EuropeanSolitaireModel();
    european2 = new EuropeanSolitaireModel(5, 4, 3);
    triangular = new TriangleSolitaireModel();
    triangular2 = new TriangleSolitaireModel(6, 0, 0);

    europeanTextView = new MarbleSolitaireTextView(european);
    triangleTextView = new TriangleSolitaireTextView(triangular2);
  }

  /**
   * Testing the slot states of a board that is set up
   */
  @Test
  public void testGetSlotAt() {
    assertEquals(SlotState.Marble, european.getSlotAt(1, 1));
    assertEquals(SlotState.Empty, european.getSlotAt(3, 3));
    assertEquals(SlotState.Invalid, european.getSlotAt(0, 0));

    assertEquals(SlotState.Empty, european2.getSlotAt(4, 3));
    assertEquals(SlotState.Marble, european2.getSlotAt(8, 3));

    assertEquals(SlotState.Marble, triangular.getSlotAt(0, 0));
    assertEquals(SlotState.Empty, triangular.getSlotAt(3, 1));
    assertEquals(SlotState.Empty, triangular2.getSlotAt(0, 0));
  }

  /**
   * Testing for invalid position exception in the triangle
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPositionTriangle() {
    triangular.getSlotAt(0, 1);
  }

  /**
   * Testing for invalid positions in the european solitaire model
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPositionEuropean() {
    european.getSlotAt(0, -1);
  }

  /**
   * To test that you cannot construct a european solitaire model that has an even arm length
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSizeConstructorEuropean() {
    european = new EuropeanSolitaireModel(6);
  }

  /**
   * To test that the empty slot cannot be placed in an invalid position
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEmptySlot() {
    european = new EuropeanSolitaireModel(8, 8);
  }

  /**
   * Testing the board state after valid moves are made
   */
  @Test
  public void testMoves() {
    assertEquals(SlotState.Empty, european.getSlotAt(3, 3));
    european.move(3, 1, 3, 3);
    assertEquals(SlotState.Marble, european.getSlotAt(3, 3));
    assertEquals(SlotState.Empty, european.getSlotAt(3, 1));

    assertEquals(SlotState.Empty, triangular.getSlotAt(3, 1));
    triangular.move(3, 3, 3, 1);
    assertEquals(SlotState.Empty, triangular.getSlotAt(3, 3));
    assertEquals(SlotState.Marble, triangular.getSlotAt(3, 1));
  }

  /**
   * Test invalid move exception for european model
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveEuropean() {
    european.move(4, 2, 4, 4);
  }

  /**
   * Test invalid move exception for triangle
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveTriangular() {
    triangular.move(3, 2, 3, 1);
  }

  @Test
  public void testTextViewEuropean() {
    String europeanExpected =
            "    O O O    \n" +
                    "  O O O O O  \n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "O O O O O O O\n" +
                    "  O O O O O  \n" +
                    "    O O O    ";

    assertEquals(europeanTextView.toString(), europeanExpected);
    european.move(3, 1, 3, 3);

    europeanExpected =
            "    O O O    \n" +
                    "  O O O O O  \n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "  O O O O O  \n" +
                    "    O O O    ";

    assertEquals(europeanTextView.toString(), europeanExpected);

    european.move(1, 1, 3, 1);

    europeanExpected =
            "    O O O    \n" +
                    "  _ O O O O  \n" +
                    "O _ O O O O O\n" +
                    "O O _ O O O O\n" +
                    "O O O O O O O\n" +
                    "  O O O O O  \n" +
                    "    O O O    ";

    assertEquals(europeanTextView.toString(), europeanExpected);
  }

  @Test
  public void testTriangularTextView() {
    System.out.println(triangleTextView.toString());

    String triangularExpected =
                    "     _\n" +
                    "    O O\n" +
                    "   O O O\n" +
                    "  O O O O\n" +
                    " O O O O O\n" +
                    "O O O O O O";

    assertEquals(triangleTextView.toString(), triangularExpected);

    triangular2.move(2, 2, 0, 0);

    triangularExpected =
                    "     O\n" +
                    "    O _\n" +
                    "   O O _\n" +
                    "  O O O O\n" +
                    " O O O O O\n" +
                    "O O O O O O";

    assertEquals(triangleTextView.toString(), triangularExpected);

    triangular2.move(4, 4, 2, 2);

    triangularExpected =
                    "     O\n" +
                    "    O _\n" +
                    "   O O O\n" +
                    "  O O O _\n" +
                    " O O O O _\n" +
                    "O O O O O O";

    assertEquals(triangleTextView.toString(), triangularExpected);

    triangular2.move(4, 2, 4, 4);

    triangularExpected =
                    "     O\n" +
                    "    O _\n" +
                    "   O O O\n" +
                    "  O O O _\n" +
                    " O O _ _ O\n" +
                    "O O O O O O";

    assertEquals(triangleTextView.toString(), triangularExpected);
  }

  @Test
  public void testIsGameOver() {

    MarbleSolitaireModel triangle = new TriangleSolitaireModel(3);
    assertFalse(triangle.isGameOver());

    triangle.move(2, 2, 2, 0);
    triangle.move(0, 0, 2, 2);

    assertTrue(triangle.isGameOver());

    MarbleSolitaireModel european = new EuropeanSolitaireModel(1);
    assertTrue(european.isGameOver());

  }
}
