package cs3500.reversi.model;

import cs3500.reversi.player.PlayerActions;
import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.view.BoardView;

/**
 * Represents any possible notifications the model can produce during the game
 * of reversi, where the model is able to notify the player when it's their turn,
 * when the game is over, and when they have passed.
 */
public class FeaturesHandlerModel implements FeaturesModel {
  private ReversiModel model;
  private final BoardView view;
  private final PlayerColor color;
  private final PlayerActions player;

  /**
   * The constructor for the feature handler for the model.
   * @param model   The model for this feature handler.
   * @param view    The view for this feature handler.
   * @param color   The player color for this feature handler.
   */
  public FeaturesHandlerModel(ReversiModel model, BoardView view, PlayerColor color,
                              PlayerActions player) {
    this.setModel(model);
    this.view = view;
    this.color = color;
    this.player = player;
  }

  /**
   * Determines if this features handler's player color is the current player for
   * this feature handler's model. If it is, displays that it is this feature handler's
   * view's turn and allows the player to click. Then it checks to see if the player
   * has any valid moves. If it is not the given player's turn and the game is not
   * over, displays that it is not the players turn and does not allow the player
   * to click on their view.
   */
  @Override
  public void checkTurn() {
    if (model.curPlayer() == this.color) {
      this.view.displayOutput(color + ", It's your turn!");
      this.view.canClick(true);
      this.view.updateBoard();
      this.checkPass();
      if (model.curPlayer() == this.color) {
        this.player.makeMove();
      }
    }
    else if (!model.isGameOver()) {
      this.view.displayOutput("It's not your turn!");
      this.view.canClick(false);
      this.view.updateBoard();
    }
  }

  /**
   * Determines if this features handler's player has any valid moves.
   * If the game is not over and there are no more valid moves, forces the player
   * to pass.
   */
  private void checkPass() {
    if (!this.model.hasValidMove(this.color) && !model.isGameOver()) {
      this.view.displayOutput("You automatically passed!");
      this.model.pass(this.color);
      this.view.canClick(false);
      this.view.updateBoard();
    }
    else {
      this.checkGameOver();
    }
  }

  /**
   * Checks if this features handler's model's game is over. If it is, displays
   * the appropriate output to the view with the correct winner.
   */
  public void checkGameOver() {
    if (model.isGameOver()) {
      if (this.model.getStatus() == GameState.TIE) {
        this.view.displayOutput("You tied!");
      }
      if (this.model.getStatus() == GameState.B_WON) {
        this.view.displayOutput("Black Won!");
      }
      if (this.model.getStatus() == GameState.W_WON) {
        this.view.displayOutput("White Won!");
      }
      this.view.canClick(false);
      this.view.updateBoard();
    }
  }

  /**
   * Adds this features handler as a feature to the given model.
   * @param m   A model to add this features handler to.
   */
  private void setModel(ReversiModel m) {
    this.model = m;
    this.model.addFeatures(this);
  }
}
