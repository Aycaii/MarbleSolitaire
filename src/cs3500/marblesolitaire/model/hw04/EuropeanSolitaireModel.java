package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;

/**
 * This class represents the European Solitaire Model.
 */
public class EuropeanSolitaireModel extends EnglishSolitaireModel {

  private SlotState[][] board;
  private final int armThickness;
  private final int row;
  private final int col;

  /**
   * Instantiates a default board of a european solitaire game
   */
  public EuropeanSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * Instantiates a european solitaire game with a custom board size
   * @param armThickness the size of the ends of the board
   */
  public EuropeanSolitaireModel(int armThickness) {
    this(armThickness, armThickness * 3 / 2 - 1, armThickness * 3 / 2 - 1);
  }

  /**
   * Instantiates a european solitaire game with a custom empty starting position
   * @param row the row of the empty slot
   * @param col the column of the empty slot
   */
  public EuropeanSolitaireModel(int row, int col) {
    this(3, row, col);
  }

  /**
   * Instantiates a european solitaire game with all custom features
   *
   * @param armThickness the size of the ends of the board
   * @param row row of empty slot
   * @param col col of empty slot
   */
  public EuropeanSolitaireModel(int armThickness, int row, int col) {
    super(armThickness, row, col);
    if (armThickness <= 0 || armThickness % 2 == 0 || row < 0 || col < 0 ||
            row >= armThickness * 3 - 2 || col >= armThickness * 3 - 2) {
      throw new IllegalArgumentException("Invalid board dimensions or empty cell position");
    }

    this.armThickness = armThickness;
    this.row = row;
    this.col = col;
    board = initBoard();

    initBoard();
  }

  /**
   * Creates the board and initializes the slot states based on the European Solitaire model.
   */
  private SlotState[][] initBoard() {
    int boardSize = getBoardSize();
    board = new SlotState[boardSize][boardSize];

    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        if (isValidPosition(r, c)) {
          board[r][c] = SlotState.Marble;
        } else {
          board[r][c] = SlotState.Invalid;
        }
      }
    }

    board[row][col] = SlotState.Empty;
    return board;
  }

  /**
   * Check if a position exists on the board based on the European Solitaire model.
   *
   * @param row the row number to check
   * @param col the column number to check
   * @return true if the position is valid, false otherwise
   */
  private boolean isValidPosition(int row, int col) {
    int min = armThickness - 1;
    int max = 2 * armThickness - 2;
    int size = getBoardSize();
    int mid = size / 2;
    int distanceFromCenter = Math.abs(row - mid) + Math.abs(col - mid);

    return distanceFromCenter <= size - armThickness || (col >= min && col <= max) || (row >= min && row <= max);
  }

  /**
   * Overrides the move method to apply European Solitaire rules.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
   * @throws IllegalArgumentException if the move is not valid
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
   * Validates the move for the European Solitaire board.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
   * @return true if the move is valid, false otherwise
   */
  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromRow < 0 || fromRow >= getBoardSize() || fromCol < 0 || fromCol >= getBoardSize() ||
            toRow < 0 || toRow >= getBoardSize() || toCol < 0 || toCol >= getBoardSize()) {
      return false;
    }

    if (board[fromRow][fromCol] != SlotState.Marble || board[toRow][toCol] != SlotState.Empty) {
      return false;
    }

    int midRow = (fromRow + toRow) / 2;
    int midCol = (fromCol + toCol) / 2;

    if (Math.abs(fromRow - toRow) == 2 && fromCol == toCol && board[midRow][fromCol] == SlotState.Marble) {
      return true;
    }

    if (Math.abs(fromCol - toCol) == 2 && fromRow == toRow && board[fromRow][midCol] == SlotState.Marble) {
      return true;
    }

    return Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2 && board[midRow][midCol] == SlotState.Marble;
  }

  /**
   * Gets the slot at the desired slot on the board
   * MUST be rewritten because the boardSize is different for European Solitaire Model
   *
   * @param row the row number of the slot
   * @param col the column number of the slot
   * @return either invalid, empty, or marble
   */
  @Override
  public SlotState getSlotAt(int row, int col) {
    if (row < 0 || row >= getBoardSize() || col < 0 || col >= getBoardSize()) {
      throw new IllegalArgumentException("Invalid slot position");
    }
    return board[row][col];
  }

  /**
   * Gets the size of the board
   * MUST be rewritten as the criteria for the size of the board is different than english solitaire model
   * @return board size
   */
  @Override
  public int getBoardSize() {
    return this.armThickness * 3 - 2;
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
