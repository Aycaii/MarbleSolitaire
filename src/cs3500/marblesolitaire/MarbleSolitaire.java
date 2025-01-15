package cs3500.marblesolitaire;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import java.io.InputStreamReader;
import java.util.Scanner;

public final class MarbleSolitaire {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Choose game type: english, european, or triangular.");
    String gameType = scanner.next().toLowerCase();

    int size = -1;
    int row = -1;
    int col = -1;

    System.out.println("Do you want to specify board size? (yes/no)");
    if (scanner.next().equalsIgnoreCase("yes")) {
      System.out.println("Enter the board size:");
      size = scanner.nextInt();
    }

    System.out.println("Do you want to specify the initial hole position? (yes/no)");
    if (scanner.next().equalsIgnoreCase("yes")) {
      System.out.println("Enter the initial hole row position:");
      row = scanner.nextInt();
      System.out.println("Enter the initial hole column position:");
      col = scanner.nextInt();
    }

    MarbleSolitaireModel model = null;
    MarbleSolitaireView view = null;

    switch (gameType) {
      case "english":
        if (size > 0 && row >= 0 && col >= 0) {
          model = new EnglishSolitaireModel(size, row, col);
        } else if (size > 0) {
          model = new EnglishSolitaireModel(size);
        } else if (row >= 0 && col >= 0) {
          model = new EnglishSolitaireModel(row, col);
        } else {
          model = new EnglishSolitaireModel();
        }
        view = new MarbleSolitaireTextView(model);
        break;

      case "european":
        if (size > 0 && row >= 0 && col >= 0) {
          model = new EuropeanSolitaireModel(size, row, col);
        } else if (size > 0) {
          model = new EuropeanSolitaireModel(size);
        } else if (row >= 0 && col >= 0) {
          model = new EuropeanSolitaireModel(row, col);
        } else {
          model = new EuropeanSolitaireModel();
        }
        view = new MarbleSolitaireTextView(model);
        break;

      case "triangular":
        if (size > 0 && row >= 0 && col >= 0) {
          model = new TriangleSolitaireModel(size, row, col);
        } else if (size > 0) {
          model = new TriangleSolitaireModel(size);
        } else if (row >= 0 && col >= 0) {
          model = new TriangleSolitaireModel(row, col);
        } else {
          model = new TriangleSolitaireModel();
        }
        view = new TriangleSolitaireTextView(model);
        break;

      default:
        System.err.println("Invalid game type. Please choose from: english, european, or triangular.");
        System.exit(1);
    }

    MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(model, view, new InputStreamReader(System.in));
    controller.playGame();
  }
}
