package cs3500.reversi.model;

import java.util.HashMap;

import cs3500.reversi.player.PlayerColor;

/**
 * The model for a standard, 2-person game of ReversiModelStandard, where
 * a standard game has a hexagonal board and has tiles that are either black
 * or white. A player can place a valid move if their tile sandwiches tiles
 * of the opposite color with another tile of the same color, and the game
 * is played until there are no more valid moves for both players. A player
 * is not able to place a tile more than once in a row, and a player may pass
 * if there is no valid moves left for that tile color. If both colors pass
 * in one round, the game is over.
 */
public class ReversiModelStandard implements ReversiModel {
  // the board representation from the board class
  private final Board board;
  // using a map from PlayerColor to Integer to map the colors to the corresponding
  // number of times that a player has passed consecutively
  private final HashMap<PlayerColor, Integer> numTimesPassedInRow;
  // CLASS INVARIANT: EACH COLOR CANNOT HAVE A NEGATIVE VALUE FOR
  // THE INTEGER VALUE IN NUM TIMES GONE AS THE MODEL WILL ENFORCE A VALUE
  // OF ZERO OR ONE, AND WILL THROW AN ILLEGAL STATE EXCEPTION IF A TILE
  // OF THE SAME COLOR IS PLACED MORE THAN ONCE IN A ROW
  // using a map from PlayerColor to Integer to map the colors to the correspond
  // number of times that a player has played
  // this helps to keep track of players not going twice back to back
  private final HashMap<PlayerColor, Integer> numTimesGone;
  // representing the state of the game, which is one of PLAYING, TIE, W_WON, B_WON
  private GameState state;
  private final TurnComponent turnComponent;

  /**
   * The constructor for a ReversiModelStandard.
   *
   * @param size The size of the board.
   */
  public ReversiModelStandard(int size) {
    this.board = new Board(size);
    this.state = GameState.PLAYING;
    this.numTimesPassedInRow = new HashMap<>();
    this.initHM(this.numTimesPassedInRow);
    this.numTimesGone = new HashMap<>();
    this.initHM(this.numTimesGone);
    this.turnComponent = new TurnComponent();
  }

  /**
   * Adds the given feature to this model.
   * @param f   A feature to add to this model.
   */
  public void addFeatures(FeaturesModel f) {
    this.turnComponent.addFeatures(f);
  }



  /**
   * Starts this model's game by checking to determine which player has the first
   * turn.
   */
  public void startGame() {
    this.turnComponent.listenerCheckTurn();
  }

  /**
   * Initializes the given hashmap to contain black and white
   * player colors keys, and sets each value to zero.
   *
   * @param hm A HashMap from PlayerColor to Integer.
   */
  private void initHM(HashMap<PlayerColor, Integer> hm) {
    hm.put(PlayerColor.BLACK, 0);
    hm.put(PlayerColor.WHITE, 0);
  }

  /**
   * Places a tile of the given color at the given q,r axial coordinates on
   * this models board. A tile will only be placed if it is being placed
   * at valid q,r coordinates and if it sandwiches a run of opposite colored
   * tiles with another tile of the same color. Placing a tile will flip any
   * tiles which are of the opposite color between the placed tile and any tile
   * of the same color on any diagonal of the board. Placing a tile will increment
   * the number of times a tile of the given color has been placed in this round
   * and resets the opposite color's number of times placed in a row to 0. Resets
   * the number of passes in this round for this color to 0.
   *
   * @param color   the color of a tile on the board, where a color will be
   *                BLACK or WHITE
   * @param q       the q axial coordinate of the tile
   * @param r       the r axial coordinate of the tile
   * @throws IllegalStateException    if the move is not a valid move.
   * @throws IllegalStateException    if the game is not currently being played.
   * @throws IllegalArgumentException if the color is of type EMPTY
   */
  @Override
  public void placeTile(PlayerColor color, int q, int r) throws IllegalArgumentException {
    isColorIsEmpty(color);
    if (this.state == GameState.PLAYING) {
      this.checkNumTimesGoneInRow(color);
      this.board.makeMove(q, r, color);
      this.placeTimesReset(color);
      this.numTimesGone.put(color, 1);
      this.numTimesPassedInRow.put(color, 0);
    } else {
      throw new IllegalStateException("Game is over, unable to make any more moves.");
    }
    this.turnComponent.listenerCheckTurn();
  }


  /**
   * Resets the color which is not currently being played to
   * have placed zero tiles in this round.
   *
   * @param color the color of a tile.
   * @throws IllegalArgumentException if the color passed in is EMPTY
   */
  private void placeTimesReset(PlayerColor color) {
    isColorIsEmpty(color);
    if (color == PlayerColor.WHITE) {
      this.resetPlayer(PlayerColor.BLACK);
    } else {
      this.resetPlayer(PlayerColor.WHITE);
    }
  }

  /**
   * throws an exception if the color passed in is of type EMPTY.
   *
   * @param color of the PlayerColor passed in
   * @throws IllegalArgumentException when the empty color is passed in as an argument
   */
  private static void isColorIsEmpty(PlayerColor color) {
    if (color == PlayerColor.EMPTY) {
      throw new IllegalArgumentException("cannot use an empty player color here");
    }
  }


  /**
   * If the given color has no valid moves left on the board, increments
   * the number of times the player has passed in this round. Increments
   * the number of moves made in this round to ensure the given color cannot
   * go more than once in a row. Resets the other color to have no moves in
   * this round. Checks to see if both players have passed twice
   * in a row, if so, set this model's status to win or tie.
   *
   * @param color The color of a tile on the board.
   * @throws IllegalStateException    if the game is already over.
   * @throws IllegalArgumentException if the color EMPTY is passed in
   */
  public void pass(PlayerColor color) {
    // NOTE: WILL BE FORCING PLAYER TO PASS IN CONTROLLER BY CHECKING IF THE PLAYER INTERACTING WITH
    // THAT CONTROLLER HAS NO VALID MOVES AND PASSING FOR THEM AUTOMATICALLY, AS THIS WILL BE
    // A SOMETHING THAT THE PLAYER MUST DO FOR THE GAME TO CONTINUE
    isColorIsEmpty(color);
    this.turnComponent.listenerCheckGameOver();
    if (this.state == GameState.PLAYING) {
      this.checkNumTimesGoneInRow(color);
      this.placeTimesReset(color);
      this.numTimesPassedInRow.put(color, 1);
      this.numTimesGone.put(color, 1);
      this.checkNumTimesPassed();
    } else {
      throw new IllegalStateException("Game is already over.");
    }
    this.turnComponent.listenerCheckGameOver();
    this.turnComponent.listenerCheckTurn();
  }

  /**
   * Determines if the given player color has a valid move
   * on this model's board.
   *
   * @param color A player color of a tile of this model.
   * @return true iff the given color has any valid moves left of
   *         this model's board.
   * @throws IllegalArgumentException if the color EMPTY is passed in
   */
  public boolean hasValidMove(PlayerColor color) {
    isColorIsEmpty(color);
    // WILL BE USING THIS IN CONTROLLER TO DETERMINE IF AUTOMATIC PASS SHOULD BE USED OR NOT
    return this.board.hasValidMove(color);
  }

  /**
   * Updates the game state if both colors have passed in
   * one round.
   */
  // ONLY USED BLACK, WHITE AS THESE ARE THE ONLY COLORS AVAILABLE IN REVERSI
  private void checkNumTimesPassed() {
    if (this.numTimesPassedInRow.get(PlayerColor.BLACK) == 1
            && this.numTimesPassedInRow.get(PlayerColor.WHITE) == 1) {
      this.updateGameStateIfGameOver();
    }
  }

  /**
   * Resets the given color's number of times placed to zero.
   *
   * @param color The color to reset to zero.
   */
  private void resetPlayer(PlayerColor color) {
    if (this.numTimesGone.get(color) == 1) {
      this.numTimesGone.replace(color, 0);
    }
  }

  /**
   * Determines if a tile of the given color has already been placed in this round, if
   * it has, throws an exception.
   *
   * @param color The color of a tile on the board of this model.
   * @throws IllegalStateException iff there has been an attempt to place a tile of
   *                               the same color more than once in a row.
   */
  private void checkNumTimesGoneInRow(PlayerColor color) {
    if (this.numTimesGone.get(color) >= 1) {
      throw new IllegalStateException("You can only make one move at a time.");
    }
  }

  /**
   * Determines the current status of this model.
   *
   * @return the current game state which is one of PLAYING, TIE, W_WON, B_WON.
   */
  @Override
  public GameState getStatus() {
    return this.state;
  }

  /**
   * Checks to see if anyone has one the game and updates the view accordingly.
   */
  @Override
  public void checkGame() {
    this.turnComponent.listenerCheckGameOver();
  }

  /**
   * Returns a copy of the game board of this model.
   *
   * @return the game board of this model.
   */
  @Override
  public Board getBoard() {
    return this.board.getCopy();
  }

  /**
   * Creates a board of the given size in a default initial state.
   *
   * @return a board of the given size in default initial state.
   */
  @Override
  public Board defaultBoard(int size) {
    return new Board(size);
  }


  /**
   * Returns the size of the board of this reversi model.
   */
  @Override
  public int getSizeOfBoard() {
    return this.board.getSize();
  }


  /**
   * Returns the contents of a cell at a given coordinate in this reversi model.
   *
   * @return the PlayerColor at the given q,r axial coordinate in this reversi model.
   */
  @Override
  public PlayerColor getContentsOfCell(int q, int r) {
    return this.board.getColorAt(q, r);
  }


  /**
   * Returns the current score for the given player color.
   *
   * @return the total sum of tiles with the given color on the board of this reversi model.
   */
  @Override
  public int getScore(PlayerColor color) {
    return this.board.getScore(color);
  }


  /**
   * Determines whether the game is over for this reversi model.
   *
   * @return true iff the GameState for this reversi model is either PLAYING, W_WON, B_WON
   */
  @Override
  public boolean isGameOver() {
    return this.state == GameState.TIE || this.state == GameState.W_WON
            || this.state == GameState.B_WON;
  }


  /**
   * determines if placing the tile at the given coordinates is a valid move for the player.
   *
   * @param q     the row coordinate (q) at which the user is attempting to put the tile
   * @param r     the column coordinate (r) at which the user is attempting to put the tile
   * @param color the color of the current player
   * @return true iff the move is a valid move
   */
  @Override
  public boolean isValidMove(int q, int r, PlayerColor color) {
    return this.board.isValidMove(q, r, color);
  }

  /**
   * Updates the state of the game if the game is over.
   * to W_WON if there are more white tiles on the board than black
   * tiles, to a TIE if there are an equal number of tiles of white
   * and black, or B_WON if there are more black tiles than white tiles.
   */
  private void updateGameStateIfGameOver() {
    int scoreWhite = this.board.getScore(PlayerColor.WHITE);
    int scoreBlack = this.board.getScore(PlayerColor.BLACK);
    if (scoreWhite > scoreBlack) {
      this.state = GameState.W_WON;
    } else if (scoreWhite == scoreBlack) {
      this.state = GameState.TIE;
    } else {
      this.state = GameState.B_WON;
    }
  }

  /**
   * Determines if there is a HexCoordinate on this model's board at the given axial q,r
   * coordinates.
   *
   * @param q A axial q-coordinate.
   * @param r A axial r-coordinate.
   * @return true iff the given axial coordinates are a HexCoordinate within this model's board.
   */
  public boolean isValidCoordinate(int q, int r) {
    return this.board.isValidCoordinates(q, r);
  }


  /**
   * Determines the current PlayerColor which is able to place a tile on this model's board.
   *
   * @return The PlayerColor that is able to add a tile to the board in this round.
   */
  public PlayerColor curPlayer() {
    if (this.numTimesGone.get(PlayerColor.BLACK) == 0) {
      return PlayerColor.BLACK;
    } else {
      return PlayerColor.WHITE;
    }
  }


  /**
   * Determines how many tiles would be captured if the given PlayerColor places a tile
   * at the given q,r coordinates on the board.
   *
   * @param q     the q coordinate on the board.
   * @param r     the r coordinate on the board.
   * @param color The color of a tile.
   * @return the number of tiles that are captured if the given player placed a tile at the given
   *         q,r coordinate.
   */
  public int numTilesCaptured(int q, int r, PlayerColor color) {
    return this.board.numTilesCaptured(q, r, color);
  }
}
