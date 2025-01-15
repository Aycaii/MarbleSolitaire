import org.junit.Before;
import org.junit.Test;

import cs3500.marblesolitaire.MarbleSolitaire;
import cs3500.marblesolitaire.model.hw02.*;
import static org.junit.Assert.*;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState.SlotState;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;

public class ModelTest {

  MarbleSolitaireModel model1;
  MarbleSolitaireModel basicModel;
  MarbleSolitaireModel european;

  /**
   * Initializes the test models before each test.
   */
  @Before
  public void init() {
    model1 = new EnglishSolitaireModel(4, 5);
    basicModel = new EnglishSolitaireModel();
    european = new EuropeanSolitaireModel();
  }

  /**
   * Tests the getScore method on the models.
   */
  @Test
  public void testGetScore() {
    assertEquals(32, model1.getScore());
    assertEquals(32, basicModel.getScore());
    basicModel.move(3, 1, 3, 3);
    basicModel.move(3, 4, 3, 2);
    assertEquals(30, basicModel.getScore());
  }

  /**
   * Tests the initialize board method by checking if the respective slots
   * on an instantiated board are correct.
   */
  @Test
  public void testInitBoard() {
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, basicModel.getSlotAt(3, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, basicModel.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, basicModel.getSlotAt(0, 0));
  }


  /**
   * Tests the getBoardSize getter method.
   */
  @Test
  public void testGetBoardSize() {
    assertEquals(7, model1.getBoardSize());
    assertEquals(7, basicModel.getBoardSize());
    basicModel.move(3, 1, 3, 3);
    basicModel.move(3, 4, 3, 2);
    assertEquals(7, basicModel.getBoardSize());

    assertEquals(7, european.getBoardSize());
  }

  /**
   * Tests the getSlotAt method.
   */
  @Test
  public void testGetSlotAt() {
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, basicModel.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, basicModel.getSlotAt(3, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, basicModel.getSlotAt(3, 3));

    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model1.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(4, 4));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(4, 5));

    assertEquals(MarbleSolitaireModelState.SlotState.Marble, european.getSlotAt(1,1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, european.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, european.getSlotAt(1, 0));
  }

  /**
   * Tests the getSlotAt method with an invalid position.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetSlotAtInvalidPosition() {
    basicModel.getSlotAt(-1, -1);
  }

  /**
   * Tests the move method.
   */
  @Test
  public void testMove() {
    basicModel.move(3, 1, 3, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, basicModel.getSlotAt(3, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, basicModel.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, basicModel.getSlotAt(3, 2));

    model1.move(4, 3, 4, 5);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(4, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(4, 5));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(4, 4));
  }

  /**
   * Tests for invalid moves.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNotValidPosn() {
    basicModel.move(3, 1, 3, 4); //not valid posn
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDistanceError() {
    basicModel.move(1, 1, 1, 1); //distance error
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPosition() {
    basicModel.move(1, 1, -1, -1);  //not a valid position on board
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoMarbleJump() {
    basicModel.move(3, 2, 3, 4); //no marble to jump over
  }

  /**
   * Tests the isGameOver method on the game states.
   * The first test is the game state for a board with valid moves
   * The second test is for a game state that is completely empty except for one marble
   */
  @Test
  public void testIsGameOver() {
    assertFalse(model1.isGameOver());

    MarbleSolitaireModel model = new EnglishSolitaireModel();

    SlotState[][] gameOverBoard = {
            { SlotState.Invalid, SlotState.Invalid, SlotState.Invalid, SlotState.Empty, SlotState.Invalid, SlotState.Invalid, SlotState.Invalid },
            { SlotState.Invalid, SlotState.Invalid, SlotState.Invalid, SlotState.Empty, SlotState.Invalid, SlotState.Invalid, SlotState.Invalid },
            { SlotState.Invalid, SlotState.Invalid, SlotState.Invalid, SlotState.Empty, SlotState.Invalid, SlotState.Invalid, SlotState.Invalid },
            { SlotState.Empty, SlotState.Empty, SlotState.Empty, SlotState.Empty, SlotState.Marble, SlotState.Empty, SlotState.Empty },
            { SlotState.Invalid, SlotState.Invalid, SlotState.Invalid, SlotState.Empty, SlotState.Invalid, SlotState.Invalid, SlotState.Invalid },
            { SlotState.Invalid, SlotState.Invalid, SlotState.Invalid, SlotState.Empty, SlotState.Invalid, SlotState.Invalid, SlotState.Invalid },
            { SlotState.Invalid, SlotState.Invalid, SlotState.Invalid, SlotState.Empty, SlotState.Invalid, SlotState.Invalid, SlotState.Invalid }
    };

    model.setBoard(gameOverBoard);

    assertTrue(model.isGameOver());
    assertEquals(model.getScore(), 1);

  }

}
