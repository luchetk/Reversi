package cs3500.reversi.view;

import cs3500.reversi.model.Board;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.PlayerColor;

/**
 * Represents the textual view of a reversi model, where
 * a game is represented as a hexagonal grid and empty spots are
 * represented with an _, black tiles are represented with an X,
 * and white tiles are represented with an O.
 */
public class ReversiTextView implements TextView {
  private final ReadonlyReversiModel model;


  /**
   * Constructor for this ReversiTextView.
   * @param model     The model for this text view.
   */
  public ReversiTextView(ReversiModel model) {
    this.model = model;
  }

  /**
   * Produces the string based view of the text view's model.
   * The view is a hexagonal grid with
   * @return the text view of the board and empty spots are
   *        represented with an _, black tiles are represented with an X,
   *         and white tiles are represented with an O.
   */

  // this should be used to produce a visualization of the board
  public String toString() {
    Board board = this.model.getBoard();
    int size = board.getSize();
    StringBuilder b = new StringBuilder();
    int row = Math.floorDiv(size, 2);
    int colB = 0;
    int colU = row;
    for (int r = -row; r <= row; r++) {
      int numSpaces = (((2 * size) - ((colU - colB) * 2)) / 2) + 1;
      b.append(this.getNumSpaces(numSpaces));
      for (int q = colB; q <= colU; q++) {
        b.append(this.getSymbol(this.model.getContentsOfCell(q, r)));
        if (q != colU) {
          b.append(" ");
        }
      }
      b.append(this.getNumSpaces(numSpaces));
      if (colB == -row) {
        colU = colU - 1;
      } else if (colB <= 0 && colB >= -row) {
        colB = colB - 1;
      }
      if (r != row) {
        b.append("\n");
      }
    }
    return b.toString();
  }

  /**
   * Return a string with the specified number of spaces.
   * @param num the number of spaces the string should have.
   * @return the string with the specified number of spaces.
   */
  private String getNumSpaces(int num) {
    return " ".repeat(Math.max(0, num));
  }

  /**
   * Add the symbol for the text view representation of a tile, where
   * an empty tile is "_", a black tile is "X", and a white player is "O".
   * @param c the color of the player
   * @return the symbol of the player
   */
  private String getSymbol(PlayerColor c) {
    if (c == PlayerColor.BLACK) {
      return "X";
    } else if (c == PlayerColor.WHITE) {
      return "O";
    } else {
      return "_";
    }
  }
}
