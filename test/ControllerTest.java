import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class ControllerTest {
  private MarbleSolitaireController controller;
  private EnglishSolitaireModel model;
  private MarbleSolitaireTextView view;

  @Before
  public void setUp() {
    model = new EnglishSolitaireModel();
    view = new MarbleSolitaireTextView(model);
  }

  @Test
  public void testPlayGame() {
    StringReader input = new StringReader("4 2 4 4 q");
    controller = new MarbleSolitaireControllerImpl(model, view, input);

    controller.playGame();

    String expectedOutput =
            "    O O O    \n" +
            "    O O O    \n" +
            "O O O O O O O\n" +
            "O _ _ O O O O\n" +
            "O O O O O O O\n" +
            "    O O O    \n" +
            "    O O O    ";


    assertEquals(expectedOutput, view.toString());
  }

  /**
   * To test the constructor exceptions, making sure you cannot instantiate a controller with a null value
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullModel() {
    Appendable output = new StringBuilder();
    StringReader input = new StringReader("4 4 4 2");
    new MarbleSolitaireControllerImpl(null, new MarbleSolitaireTextView(new EnglishSolitaireModel(), output), input);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullView() {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    StringReader input = new StringReader("4 4 4 2");
    new MarbleSolitaireControllerImpl(model, null, input);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullInput() {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    Appendable output = new StringBuilder();
    new MarbleSolitaireControllerImpl(model, new MarbleSolitaireTextView(model, output), null);
  }

  /**
   * Checks for illegal state exception when provided an empty input
   *
   * @throws IllegalStateException
   */
  @Test (expected = IllegalStateException.class)
  public void testConstructorWithEmptyInput() {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    Appendable output = new StringBuilder();
    StringReader input = new StringReader("");
    controller = new MarbleSolitaireControllerImpl(model, new MarbleSolitaireTextView(model, output), input);
    controller.playGame();
  }

}
