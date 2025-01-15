package cs3500.marblesolitaire.model.hw02;

/**
 * This class represents the English Solitaire Model.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private SlotState[][] board;
  private final int armThickness;
  private final int boardSize;
  private final int emptyRow;
  private final int emptyCol;

  /**
   * No-parameter constructor.
   */
  public EnglishSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * Constructor with row and column number.
   *
   * @param sRow the row number for the empty slot
   * @param sCol the column number for the empty slot
   */
  public EnglishSolitaireModel(int sRow, int sCol) {
    this(3, sRow, sCol);
  }

  /**
   * Constructor for arm thickness.
   *
   * @param armThickness the arm thickness of the board
   */
  public EnglishSolitaireModel(int armThickness) {
    this(armThickness, (3 * armThickness - 2) / 2, (3 * armThickness - 2) / 2);
  }

  /**
   * Constructor for arm thickness, row, and column.
   *
   * @param armThickness the arm thickness of the board
   * @param sRow the row number for the empty slot
   * @param sCol the column number for the empty slot
   * @throws IllegalArgumentException if the arm thickness is negative or even, or if the empty slot position is invalid
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) {
    if (armThickness < 1 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("arm thickness cannot be negative or even");
    }
    this.armThickness = armThickness;
    this.boardSize = 3 * armThickness - 2;

    if (!isValidEmptySlot(sRow, sCol)) {
      throw new IllegalArgumentException(String.format("invalid empty position", sRow, sCol));
    }

    this.emptyRow = sRow;
    this.emptyCol = sCol;
    this.board = new SlotState[boardSize][boardSize];
    initBoard();

  }

  /**
   * Creates the board and the slot states based on the board size.
   *
   * @return the initialized board with slot states
   */
  private void initBoard() {

    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        if (isValidPosition(r, c)) {
          board[r][c] = SlotState.Marble;
        } else {
          board[r][c] = SlotState.Invalid;
        }
      }
    }
    board[emptyRow][emptyCol] = SlotState.Empty;
  }

  /**
   * Check if a position exists on the board (otherwise it should be invalid).
   *
   * @param row the row number to check
   * @param col the column number to check
   * @return true if the position is valid, false otherwise
   */
  private boolean isValidPosition(int row, int col) {
    int min = armThickness - 1;
    int max = 2 * armThickness - 2;
    return (col >= min && col <= max) || (row >= min && row <= max);
  }

  /**
   * Check if an empty slot position is valid.
   *
   * @param row the row number to check
   * @param col the column number to check
   * @return true if the empty slot position is valid, false otherwise
   */
  private boolean isValidEmptySlot(int row, int col) {
    return isValidPosition(row, col);
  }

  /**
   * Getter method for board size.
   *
   * @return the size of the board
   */
  @Override
  public int getBoardSize() {
    return boardSize;
  }

  /**
   * Returns the slot state at the given row, column position.
   *
   * @param row the row number of the slot
   * @param col the column number of the slot
   * @return the SlotState at the given position
   * @throws IllegalArgumentException if the position is out of bounds
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
      throw new IllegalArgumentException("out of bounds");
    }
    return board[row][col];
  }

  /**
   * Keeps track of marbles left, win condition is when the score is 0.
   *
   * @return the current score (number of marbles left)
   */
  @Override
  public int getScore() {
    int score = 0;
    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        if (board[r][c] == SlotState.Marble) {
          score++;
        }
      }
    }
    return score;
  }

  /**
   * Verifies that the marble can move from its initial position to the desired row, column position.
   *
   * @param fromRow the initial row position of the marble
   * @param fromCol the initial column position of the marble
   * @param toRow the desired row position of the marble
   * @param toCol the desired column position of the marble
   * @return true if the move is valid, false otherwise
   */
  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {

    if (!isValidPosition(fromRow, fromCol) || !isValidPosition(toRow, toCol)) {
      return false;
    }

    if (getSlotAt(fromRow, fromCol) != SlotState.Marble ||
            getSlotAt(toRow, toCol) != SlotState.Empty) {
      return false;
    }

    int rowDiff = Math.abs(fromRow - toRow);
    int colDiff = Math.abs(fromCol - toCol);

    if ((rowDiff == 2 && colDiff == 0) || (rowDiff == 0 && colDiff == 2)) {
      int midRow = (fromRow + toRow) / 2;
      int midCol = (fromCol + toCol) / 2;

      return getSlotAt(midRow, midCol) == SlotState.Marble;
    }

    return false;
  }

  /**
   * Checks to see if there are no valid moves left or no marbles left.
   * Must be rewritten here because of the private boolean canMove
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    for (int r = 0; r < boardSize; r++) {
      for (int c = 0; c < boardSize; c++) {
        if (board[r][c] == SlotState.Marble) {
          if (canMove(r, c)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Checks to see if there are any possible moves available for the marble at the given position.
   *
   * @param row the row number to check
   * @param col the column number to check
   * @return true if there are possible moves available, false otherwise
   */
  private boolean canMove(int row, int col) {
    int[][] directions = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}};
    for (int[] dir : directions) {
      int newRow = row + dir[0];
      int newCol = col + dir[1];
      if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize) {
        if (isValidMove(row, col, newRow, newCol)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Moves a marble.
   *
   * @param fromRow the initial row position of the marble
   * @param fromCol the initial column position of the marble
   * @param toRow the desired row position of the marble
   * @param toCol the desired column position of the marble
   * @throws IllegalArgumentException if the move is invalid
   */
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValidMove(fromRow, fromCol, toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move");
    }
    board[fromRow][fromCol] = SlotState.Empty;
    board[toRow][toCol] = SlotState.Marble;
    board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = SlotState.Empty;
  }


  /**
   * Hypothetical method to set the board state directly for testing purposes
   *
   * @param board a board with a predefined state
   */
  public void setBoard(SlotState[][] board) {
    this.board = board;
  }
}
