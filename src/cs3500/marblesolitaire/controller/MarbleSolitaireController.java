package cs3500.marblesolitaire.controller;

/**
 * This interface represents a controller that a user can interact with to play marble solitaire
 */
public interface MarbleSolitaireController {

  /**
   * Plays a new game of MarbleSolitaire
   *
   * @throws IllegalStateException if the controller is unable to read input
   */
  void playGame() throws IllegalStateException;
}
