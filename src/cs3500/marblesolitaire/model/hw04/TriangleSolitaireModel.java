package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * This class represents a triangular board shaped marble solitaire game
 */
public class TriangleSolitaireModel implements MarbleSolitaireModel {

  private SlotState[][] board;
  private final int size;
  private final int emptyRow;
  private final int emptyCol;

  /**
   * Default constructor that instantiates with default parameters
   */
  public TriangleSolitaireModel() {
    this(6, 3, 1);
  }

  /**
   * Constructor with size parameter
   * Instantiates with a default empty slot
   *
   * @param size the number of marbles in the bottommost row
   */
  public TriangleSolitaireModel(int size) {
    this(size, (size / 2) + 1, (size / 2) - 1);
  }

  /**
   * Constructor with specified empty slot position
   * instantiates the default size
   *
   * @param emptyRow the row of the empty marble starting from 0
   * @param emptyCol the col of the empty marble starting from 0
   */
  public TriangleSolitaireModel(int emptyRow, int emptyCol) {
    this(6, emptyRow, emptyCol);
  }

  /**
   * Constructs a triangle solitaire with all custom parameters
   *
   * @param size the number of marbles in the bottommost row
   * @param emptyRow the row of the empty marble starting from 0
   * @param emptyCol the col of the empty marble starting from 0
   */
  public TriangleSolitaireModel(int size, int emptyRow, int emptyCol) throws IllegalArgumentException {
    if (emptyCol < 0 || emptyRow >= size || emptyCol > emptyRow) {
      throw new IllegalArgumentException("Invalid board dimensions or empty cell position");
    }

    this.size = size;
    this.emptyRow = emptyRow;
    this.emptyCol = emptyCol;
    this.board = new SlotState[size][size];
    initBoard();
  }

  /**
   * Initializes a triangle shaped solitaire board
   */
  private void initBoard() {
    board = new SlotState[size][size];
    for (int r = 0; r < size; r++) {
      for (int c = 0; c <= r; c++) {
        board[r][c] = SlotState.Marble;
      }
    }
    board[emptyRow][emptyCol] = SlotState.Empty;

  }


  /**
   * Moves the marble from one position on the board to another
   * Marble in the triangle solitaire model can only move diagonally and horizontally but they cannot move up and down
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow the row number of the position to be moved to (starts at 0)
   * @param toCol the column number of the position to be moved to (starts at 0)
   * @throws IllegalArgumentException
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move");
    }

    board[fromRow][fromCol] = SlotState.Empty;
    board[toRow][toCol] = SlotState.Marble;
    board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = SlotState.Empty;
  }

  /**
   * Checks to see if the given move is valid.
   * This had to be rewritten as the marble can now only move horizontally or diagnoally and can no longer move up or down
   * like a european solitaire model or english solitaire model
   *
   * @param fromRow the row of the marble to be moved
   * @param fromCol the col of the marble to be moved
   * @param toRow the row the marble is going to
   * @param toCol the col the marble is going to
   * @return if this move abides by the rules of the game
   */
  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromCol < 0 || toCol < 0 ||
            fromRow >= size || fromCol > fromRow || toRow >= size || toCol > toRow) {
      return false;
    }

    if (board[fromRow][fromCol] != SlotState.Marble || board[toRow][toCol] != SlotState.Empty) {
      return false;
    }

    if (Math.abs(fromCol - toCol) == 2 && Math.abs(fromRow - toRow) <= 2 &&
            board[fromRow][(fromCol + toCol) / 2] == SlotState.Marble) {
      return true;
    }

    if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2 &&
            board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] == SlotState.Marble) {
      return true;
    }

    return false;
  }


  /**
   * checks to see if the game is over depending on if there are no valid moves left
   * Different criteria from the European and EnglishSolitaireModel as the move criteria is also different
   *
   * @return if there is a valid move still on the board
   */
  @Override
  public boolean isGameOver() {
    for (int r = 0; r < size; r++) {
      for (int c = 0; c <= r; c++) {
        if (board[r][c] == SlotState.Marble) {
          if (isValidMove(r, c, r + 2, c) || isValidMove(r, c, r - 2, c) ||
                  isValidMove(r, c, r, c + 2) || isValidMove(r, c, r, c - 2) ||
                  isValidMove(r, c, r + 2, c + 2) || isValidMove(r, c, r - 2, c - 2)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Gets the size of the board
   * @return the number of marbles on the bottommost row of the triangle board
   */
  @Override
  public int getBoardSize() {
    return size;
  }

  /**
   * Gets the slot state of the given slot
   *
   * @param row the row number of the slot (starts at 0)
   * @param col the column number of the slot (starts at 0)
   * @return the state of the slot, either marble, invalid or empty
   * @throws IllegalArgumentException if the slot state is out of bounds of the instantiated array
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (col < 0 || row >= size || col > row) {
      throw new IllegalArgumentException("Invalid position");
    }
    return board[row][col];
  }

  @Override
  public void setBoard(SlotState[][] board) {
    this.board = board;
  }

  @Override
  public int getScore() {
    int score = 0;
    for (int r = 0; r < getBoardSize(); r++) {
      for (int c = 0; c < getBoardSize(); c++) {
        if (board[r][c] == SlotState.Marble) {
          score++;
        }
      }
    }
    return score;
  }


}
