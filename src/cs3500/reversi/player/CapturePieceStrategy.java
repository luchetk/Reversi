package cs3500.reversi.player;

import java.util.Optional;
import cs3500.reversi.model.HexCoordinates;
import cs3500.reversi.model.ReadonlyReversiModel;


/**
 * Represents the strategy for flipping the maximum number of tiles possible
 * in a single move.
 */
public class CapturePieceStrategy implements Strategy {
  private final ReadonlyReversiModel model;

  /**
   * Constructor for CapturePieceStrategy.
   *
   * @param model the model for this strategy
   */
  public CapturePieceStrategy(ReadonlyReversiModel model) {
    this.model = model;
  }

  /**
   * Determines the hexagonal axial coordinate of the most strategic move on the
   * board for this strategy.
   *
   * @param color The color of the tile of the player using this strategy.
   * @return The most strategic move on the board for this strategy.
   */
  public Optional<HexCoordinates> chooseMove(PlayerColor color) {
    isColorEmpty(color);
    HexCoordinates bestMove = null;
    int maxCaptured = 0;
    int size = model.getSizeOfBoard();
    for (int q = -size + 1; q < size; q++) {
      for (int r = Math.max(-size + 1, -size - q); r <= Math.min(size - 1, size - q - 1); r++) {
        if (Math.abs(q) + Math.abs(r) + Math.abs(-q - r) <= (size - 1)
                && model.getContentsOfCell(q, r) == PlayerColor.EMPTY
                && model.isValidMove(q, r, color)) {
          int numCaptured = model.numTilesCaptured(q, r, color);
          // find the maximum number of tiles captured
          // break the tie by choosing the uppermost-leftmost piece if there is a tie
          if (numCaptured > maxCaptured
                  || (numCaptured == maxCaptured && (bestMove == null
                  || (r < bestMove.getR() || (r == bestMove.getR() && q < bestMove.getQ()))))) {
            maxCaptured = numCaptured;
            bestMove = new HexCoordinates(q, r);
          }
        }
      }
    }
    if (bestMove == null) {
      return Optional.empty();
    }
    return Optional.of(bestMove);
  }

  /**
   * determines if the color is empty and throws an exception if so.
   *
   * @param color the color of the player
   */
  private static void isColorEmpty(PlayerColor color) {
    if (color == PlayerColor.EMPTY) {
      throw new IllegalArgumentException("color cannot be empty");
    }
  }
}
