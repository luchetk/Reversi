package cs3500.reversi;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.reversi.controller.BlackTileController;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.controller.WhiteTileController;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.ReversiModelStandard;
import cs3500.reversi.player.CapturePieceStrategy;
import cs3500.reversi.player.CheckCornersStrategy;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.MachinePlayer;
import cs3500.reversi.player.MultipleStrategies;
import cs3500.reversi.player.PlayerActions;
import cs3500.reversi.view.BoardView;
import cs3500.reversi.view.ReversiFrame;


/**
 * Main runner class for a game of reversi.
 */
public final class Reversi {
  /**
   * The entry point playing a reversi game.
   * @param args The command line arguments for this game.
   */
  public static void main(String[] args) {
    ReversiModel model = new ReversiModelStandard(7);
    BoardView player1View = new ReversiFrame(model);
    BoardView player2View = new ReversiFrame(model);
    PlayerActions player1 = createPlayer(args[1], model);
    PlayerActions player2 = createPlayer(args[0], model);
    ReversiController controller1 = new WhiteTileController(model, player1, player1View);
    ReversiController controller2 = new BlackTileController(model, player2, player2View);
    model.startGame();
  }


  /**
   * Creates a player for the given model based on the given string, where a string
   * "human" creates a human player, "capture" creates a machine player which uses
   * the capture strategy, "corner" creates a machine player which uses the corner
   * strategy, and "both" creates a machine player which uses both the corner and
   * capture strategy.
   * @param playerType    The string representation of a type of player.
   * @param model   The model for a player.
   * @return    The type of player associated with the given string.
   * @throws IllegalArgumentException   if the given string is not a valid argument.
   */

  private static PlayerActions createPlayer(String playerType, ReversiModel model) {
    if (playerType.equalsIgnoreCase("human")) {
      return new HumanPlayer(model);
    } else if (playerType.equalsIgnoreCase("capture")) {
      return new MachinePlayer(model, new CapturePieceStrategy(model));
    } else if (playerType.equalsIgnoreCase("corner")) {
      return new MachinePlayer(model, new CheckCornersStrategy(model));
    } else if (playerType.equalsIgnoreCase("both")) {
      return new MachinePlayer(model, new MultipleStrategies(new ArrayList<>(Arrays.asList(
              new CapturePieceStrategy(model), new CheckCornersStrategy(model)))));
    }
    throw new IllegalArgumentException("Invalid player type");
  }
}