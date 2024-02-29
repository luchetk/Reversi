package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;

import cs3500.reversi.player.PlayerColor;

/**
 * A board for a game of reversi, where a board has a hexagonal shape
 * and size. The board is made up of hex coordinates where a hex coordinate
 * is made up of axial coordinates q,r and each hex coordinate is mapped to
 * a color, where a color can be one of BLACK, WHITE, or EMPTY. A board
 * cannot have an even size and have a size greater than three.
 */
public class Board {
  // maps the Axial Hex Coordinates to a PlayerColor
  // the coordinates have a q and r component
  // the PlayerColor is one of Black, White, or Empty for tiles that players have not moved to yet
  private HashMap<HexCoordinates, PlayerColor> board;
  // the size of the board which cannot be less than 5 or even
  private final int size;
  // CLASS INVARIANT: SIZE CANNOT BE EVEN OR LESS THAN OR EQUAL TO THREE
  // AS THE CONSTRUCTOR WILL ENFORCE THIS SIZE BY THROWING AN ILLEGAL ARGUMENT
  // EXCEPTION IF IT IS NOT A VALID SIZE

  /**
   * The constructor for this Board.
   *
   * @param size the size of the board
   */
  public Board(int size) {
    this.size = this.checkBoardSize(size);
    this.board = new HashMap<>();
    this.initBoard();
  }

  /**
   * Determines if the given size is odd and greater than three.
   *
   * @param size the size of a board
   * @return the given size of a board.
   * @throws IllegalArgumentException if the given size is not even
   *                                  or is less than or equal to three.
   */
  private int checkBoardSize(int size) {
    if (size % 2 == 0 || size <= 3) {
      throw new IllegalArgumentException("Invalid board size.");
    } else {
      return size;
    }
  }

  /**
   * Initializes the board for a reversi game, where a board is made of
   * coordinates in a hexagonal shape following an axial coordinate system.
   * The first hexagonal unit surrouding the (0,0) hexagon are colored in alternating
   * colors of BLACK and WHITE, while all other values at valid coordinates
   * are initialized as EMPTY.
   */
  private void initBoard() {
    // iterates over q to go through the horizontal rows
    PlayerColor prev = PlayerColor.BLACK;
    for (int q = -size + 1; q < size; q++) {
      // gets the diagonal values r1 and r2
      int r1 = Math.max(-size + 1, -size - q);
      int r2 = Math.min(size - 1, size - q - 1);
      for (int r = r1; r <= r2; r++) {
        HexCoordinates axialCoords = new HexCoordinates(q, r);
        int s = -(q + r);
        // place tiles around the center
        if (Math.abs(q) + Math.abs(s) + Math.abs(r) == 2) {
          board.put(axialCoords, prev);
          // check this so that the initialization correctly does every other around the center
          if (r != 1) {
            if (prev == PlayerColor.BLACK) {
              prev = PlayerColor.WHITE;
            } else {
              prev = PlayerColor.BLACK;
            }
          }
        } else {
          board.put(axialCoords, PlayerColor.EMPTY);
        }
      }
    }
  }

  /**
   * Returns the coordinates and their values of this board.
   *
   * @return the hashmap representation of this board.
   */
  public HashMap<HexCoordinates, PlayerColor> getBoard() {
    return new HashMap<>(this.board);
  }

  /**
   * Makes a move with the given color at the given q, r coordinates if it is being placed
   * at valid q,r coordinates and if it sandwiches a run of opposite colored
   * tiles with another tile of the same color. If it is a valid move flips all
   * oppositely colored tiles that have been sandwiched by placing a tile at the given
   * coordinates.
   *
   * @param q     q coordinate of the hex tile
   * @param r     r coordinate of the hex tile
   * @param color color of the tile being played.
   * @throws IllegalArgumentException if the color passed in is EMPTY
   */
  void makeMove(int q, int r, PlayerColor color) {
    isColorEmpty(color);
    boolean anyFlipped = false;
    if (!isValidCoordinates(q, r)) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
    // have to add this so that when we clone the model, it doesn't throw an error for the original
    boolean isNotOriginal = Math.abs(q) + Math.abs(r) + Math.abs(-q - r) != 2;
    if (!this.isValidMove(q, r, color) && isNotOriginal) {
      throw new IllegalStateException("Not a valid move for this tile.");
    } else {
      if (!isNotOriginal) {
        throw new IllegalStateException("Not a valid move for this tile.");
      }
      ArrayList<ArrayList<HexCoordinates>> moves = this.findAllMoves(q, r, color);
      for (ArrayList<HexCoordinates> move : moves) {
        if (!move.isEmpty()) {
          if (this.checkAllColors(move, color, q, r)) {
            anyFlipped = true;
          }
        }
      }
      if (!anyFlipped) {
        throw new IllegalStateException("Not a valid move for this tile.");
      }
    }
  }

  /**
   * determines if the coordinates are valid.
   *
   * @param q the q value of the coordinate
   * @param r the r value of the coordinate
   * @return true iff the coordinate is within the board
   */
  boolean isValidCoordinates(int q, int r) {
    return q >= -(size / 2)
            && q <= (size / 2)
            && r >= -(size / 2)
            && r <= (size / 2)
            && Math.abs(-q - r) + Math.abs(q) + Math.abs(r) <= (size - 1);
  }


  /**
   * Produces a list of all possible lists of coordinates on diagonals from the given
   * q, r axial coordinate which have another tile of the given color.
   *
   * @param q     The q axial hex coordinate.
   * @param r     The r axial hex coordinate.
   * @param color The player color of the tile being placed on this board.
   * @return A list of all possible lists of coordinates on diagonal from the given axial
   *          coordinate that contain a valid move, returns an empty list if no possible moves
   *          for the given player color at the given q,r coordinate.
   */
  private ArrayList<ArrayList<HexCoordinates>> findAllMoves(int q, int r, PlayerColor color) {
    ArrayList<ArrayList<HexCoordinates>> findAllMoves = new ArrayList<>();
    for (int qCoord = -1; qCoord <= 1; qCoord++) {
      for (int rCoord = -1; rCoord <= 1; rCoord++) {
        if (qCoord != rCoord) {
          findAllMoves.add(this.findPossibleMoves(q, r, color, qCoord, rCoord));
        }
      }
    }
    return findAllMoves;
  }

  /**
   * Checks if all the player colors which the given list of axial coordinates are mapped to
   * are all the same and of the opposite color of the given color, if so flips all
   * of these tiles to be the given color and places a tile at the given q,r coordinate.
   *
   * @param hex   the hexagons we are checking to flip
   * @param color the player color of the tile being placed.
   */
  private boolean checkAllColors(ArrayList<HexCoordinates> hex, PlayerColor color, int q, int r) {
    // see which color we need to flip
    PlayerColor colorToCheck = (color == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;
    boolean allSame = this.checkAllSame(hex, colorToCheck);

    if (allSame) {
      this.flipColors(hex, color);
      HexCoordinates hc = new HexCoordinates(q, r);
      this.board.replace(hc, color);
      return true;
    }
    return false;
  }


  /**
   * Determines if the given list of coordinates are mapped to the given color
   * in this board.
   *
   * @param hex   A list of hex axial coordinates.
   * @param color A player color to check what
   * @return true iff the given list of hex coordinates are all mapped to the given
   *            color in this board.
   */
  private boolean checkAllSame(ArrayList<HexCoordinates> hex, PlayerColor color) {
    for (HexCoordinates hc : hex) {
      if (this.board.get(hc) != color) {
        return false;
      }
    }
    return true;
  }

  /**
   * flip the colors of the tiles.
   *
   * @param hex   the hexagon tiles we are flipping
   * @param color the color that we are changing them to
   */
  private void flipColors(ArrayList<HexCoordinates> hex, PlayerColor color) {
    for (HexCoordinates h : hex) {
      this.board.replace(h, color);
    }
  }

  /**
   * Determines if another tile of the same color exists on the diagonal
   * given by the qIncrement and rIncrement values. If it does, returns the
   * list of all coordinates between the given q,r coordinate and the first
   * tile of the same color. If no such tile exists returns an empty list
   * of coordinates.
   *
   * @param q          the q coordinate on the axial coordinate system
   * @param r          the r coordinate on the axial coordinate system
   * @param color      the color of a tile to be placed
   * @param qIncrement the amount that we have to increment q by each time
   * @param rIncrement the amount that we have to increment r by each time
   * @return The list of coordinates between the given q,r coordinate and the first
   *            tile of the same color on the given diagonal. If no such tile exists
   *            returns an empty list.
   */
  private ArrayList<HexCoordinates> findPossibleMoves(int q, int r, PlayerColor color,
                                                      int qIncrement, int rIncrement) {
    HexCoordinates hc = new HexCoordinates(q, r);
    ArrayList<HexCoordinates> pc = new ArrayList<>();

    boolean contains = true;
    while (contains) {
      q += qIncrement;
      r += rIncrement;
      HexCoordinates coords = new HexCoordinates(q, r);
      if (this.board.get(coords) == null) {
        contains = false;
      } else if (this.board.get(coords) == color) {
        return pc;
      } else {
        pc.add(coords);
      }
    }
    return new ArrayList<>();
  }

  /**
   * Determines if a tile of the given has any possible moves left on this board.
   *
   * @param color the color for which a valid move is being checked.
   * @return true iff a tile of the given color has any valid moves left on the board.
   * @throws IllegalArgumentException when the empty color is passed in as an argument
   */
  boolean hasValidMove(PlayerColor color) {
    isColorEmpty(color);
    // iterate through the coordinates of the game
    for (int q = -size + 1; q < size; q++) {
      for (int r = Math.max(-size + 1, -size - q); r <= Math.min(size - 1, size - q - 1); r++) {
        int s = -q - r;
        // if there is at least one valid move, return true
        if (Math.abs(q) + Math.abs(r) + Math.abs(s) <= (size - 1) && isValidMove(q, r, color)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * throws an exception if the color passed in is of type EMPTY.
   * @param color of the PlayerColor passed in
   * @throws IllegalArgumentException when the empty color is passed in as an argument
   */
  private static void isColorEmpty(PlayerColor color) {
    if (color == PlayerColor.EMPTY) {
      throw new IllegalArgumentException("the empty color cannot be passed in");
    }
  }

  /**
   * determines if there is a valid move at the specific coordinate.
   * Note: this is a default method since the model needs to access it
   * @param q     the q coordinate of the tile
   * @param r     the r coordinate of the tile
   * @param color the color of the player
   * @return true iff there is a valid move at this tile
   */
  boolean isValidMove(int q, int r, PlayerColor color) {
    if (board.get(new HexCoordinates(q, r)) == PlayerColor.EMPTY) {
      ArrayList<ArrayList<HexCoordinates>> hc = this.findAllMoves(q, r, color);
      for (ArrayList<HexCoordinates> hexCoordinates : hc) {
        PlayerColor colSwitch = (color == PlayerColor.BLACK)
                ? PlayerColor.WHITE : PlayerColor.BLACK;
        if (!hexCoordinates.isEmpty()) {
          if (this.checkAllSame(hexCoordinates, colSwitch)) {
            return true;
          }
        }
      }
    }
    return false; // the tile is not empty, so not a valid move.
  }

  /**
   * returns the size of the board.
   *
   * @return the size of the board
   */
  public int getSize() {
    return this.size;
  }

  /**
   * returns the color of the tile at the coordinates.
   *
   * @param q the q coordinate of the tile
   * @param r the r coordinate of the tile
   * @return the color of the tile at the specified coordinates
   */
  public PlayerColor getColorAt(int q, int r) throws IllegalStateException {
    HexCoordinates coords = new HexCoordinates(q, r);
    if (this.board.containsKey(coords)) {
      return this.board.get(coords);
    }
    else {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
  }

  /**
   * Determines the number of tiles captured by placing a tile of the given color
   * at the given q,r axial coordinate.
   * @param q   the q coordinate position.
   * @param r   the r coordinate position.
   * @param color   The color of the tile being placed.
   * @return    The number of tiles captured by placing a tile of the given color at the given
   *            q,r coordinates.
   */
  public int numTilesCaptured(int q, int r, PlayerColor color) {
    int num = 0;
    ArrayList<ArrayList<HexCoordinates>> possibleTilesCapture = this.findAllMoves(q, r, color);
    PlayerColor colorToCheck = (color == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;
    for (ArrayList<HexCoordinates> hexCoordinates : possibleTilesCapture) {
      if (!hexCoordinates.isEmpty()) {
        num += this.checkNumSame(hexCoordinates, colorToCheck);
      }
    }
    return num;
  }


  /**
   * Determines the number of tiles of the given color in the at the given list of
   * hex coordinates.
   * @param hc    A list of hex coordinates.
   * @param color   A color of a tile.
   * @return    The number of tiles of the given color in the list of hex coordinates
   *            iff they are all of the color, if they are not all the same color,
   *            return 0.
   */
  private int checkNumSame(ArrayList<HexCoordinates> hc, PlayerColor color) {
    if (this.checkAllSame(hc, color)) {
      return hc.size();
    } else {
      return 0;
    }
  }

  /**
   * Determines the number of tiles with the given player color that are on this board.
   *
   * @param playerColor The player color that score is being calculated for.
   * @return The score of the given player color, which is the number of tile with
   *          the given player color in this board.
   * @throws IllegalArgumentException if empty is passed in as the color
   */
  int getScore(PlayerColor playerColor) {
    int score = 0;
    isColorEmpty(playerColor);
    for (HexCoordinates coords : this.board.keySet()) {
      if (this.board.get(coords) == playerColor) {
        score = score + 1;
      }
    }
    return score;
  }


  /**
   * Produces a copy of this board.
   * @return  a copy of this board.
   */
  Board getCopy() {
    Board b = new Board(this.size);
    b.setBoard(this.getBoard());
    return b;
  }

  /**
   * Sets this board to the given board.
   * @param p A board to set this board to.
   */
  private void setBoard(HashMap<HexCoordinates, PlayerColor> p) {
    this.board = p;
  }


  /**
   * Determines if this board is the same as the given object.
   * @param o   An object to compare this board to.
   * @return    True iff the given object is the same as this board.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Board) {
      Board b = (Board) o;
      for (HexCoordinates h: this.board.keySet()) {
        if (!b.board.containsKey(h) || !this.board.get(h).equals(b.board.get(h))) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * Produces a hashcode for this board.
   * @return    The integer hashcode of this board.
   */
  @Override
  public int hashCode() {
    return this.size + this.board.size();
  }
}