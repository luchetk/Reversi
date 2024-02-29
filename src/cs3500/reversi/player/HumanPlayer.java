package cs3500.reversi.player;

import cs3500.reversi.model.ReversiModel;

/**
 * Represents a human player for a game of reversi, where a player is able
 * to place a tile on the board or pass.
 */
public class HumanPlayer implements PlayerActions {
  private final ReversiModel model;
  private final PlayerColor color;

  /**
   * The constructor for a human player.
   * @param model   The model for this player.
   */
  public HumanPlayer(ReversiModel model) {
    this.model = model;
    color = this.model.curPlayer();
  }

  /**
   * Allows this player to pass their turn.
   */
  @Override
  public void pass() {
    this.model.pass(this.color);
  }

  /**
   * Allows this player to make a move.
   */
  @Override
  public void makeMove() {
    // does not have any functionality for a human player, as it will never be used
  }
}
