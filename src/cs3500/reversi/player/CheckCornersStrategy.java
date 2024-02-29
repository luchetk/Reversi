package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import cs3500.reversi.model.HexCoordinates;
import cs3500.reversi.model.ReadonlyReversiModel;

/**
 * Strategy which prioritizes moving in corners.
 */
public class CheckCornersStrategy implements Strategy {
  private final ReadonlyReversiModel model;

  /**
   * Constructor for CheckCornersStrategy.
   *
   * @param model The model for this strategy.
   */
  public CheckCornersStrategy(ReadonlyReversiModel model) {
    this.model = model;
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


  /**
   * Determines the hexagonal axial coordinate of the most strategic move on the
   * board for this strategy.
   * @param color   The color of the tile of the player using this strategy.
   * @return The most strategic move on the board for this strategy.
   */
  @Override
  public Optional<HexCoordinates> chooseMove(PlayerColor color) {
    isColorEmpty(color);
    HexCoordinates bestMove = null;
    int maxCaptured = 0;
    int size = Math.floorDiv(this.model.getSizeOfBoard(), 2);
    ArrayList<Integer> coordsToCheck = new ArrayList<>(Arrays.asList(-size, 0, size));
    for (int i = 0; i < coordsToCheck.size(); i++) {
      int q = coordsToCheck.get(i);
      for (int r : coordsToCheck) {
        if (q != r) {
          int numCaptured = model.numTilesCaptured(q, r, color);
          if (numCaptured > maxCaptured || (numCaptured == maxCaptured && (bestMove == null
                  || (r < bestMove.getR() || (r == bestMove.getR() && q < bestMove.getQ()))))
                  && model.isValidMove(q, r, color)) {
            maxCaptured = numCaptured;
            bestMove = new HexCoordinates(q, r);
          }
        }
      }
    }
    if (bestMove == null) {
      Strategy s = new CapturePieceStrategy(model);
      return s.chooseMove(color);
    }
    return Optional.of(bestMove);
  }
}


