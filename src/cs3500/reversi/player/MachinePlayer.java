package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.model.GameState;
import cs3500.reversi.model.HexCoordinates;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents the machine player for a reversi game, where
 * a machine player makes a move based on a strategy.
 */
public class MachinePlayer implements PlayerActions {
  private final ReversiModel model;
  private final Strategy strategy;

  /**
   * The constructor for a machine player of reversi.
   * @param model   The model for this machine player.
   * @param strategy    The strategy this machine player is using to play.
   */
  public MachinePlayer(ReversiModel model, Strategy strategy) {
    this.model = model;
    this.strategy = strategy;
  }

  /**
   * Allows the player to pass.
   */
  @Override
  public void pass() {
    this.model.pass(model.curPlayer());
  }

  /**
   * Allows this player to make a move based on its strategy.
   * If strategy provides a valid move, the player will place a tile at
   * the most strategic hex coordinate, if not, the player will pass its turn.
   */
  @Override
  public void makeMove() {
    Optional<HexCoordinates> hc = strategy.chooseMove(model.curPlayer());
    if (model.getStatus() == GameState.PLAYING) {
      if (hc.isPresent() && model.getContentsOfCell(hc.get().getQ(),
              hc.get().getR()) == PlayerColor.EMPTY) {
        this.model.placeTile(model.curPlayer(), hc.get().getQ(), hc.get().getR());
      } else {
        this.model.pass(model.curPlayer());
      }
    }
    else {
      if (!this.model.isGameOver()) {
        this.model.pass(this.model.curPlayer());
      }
    }
  }
}
