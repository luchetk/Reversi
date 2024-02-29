package cs3500.reversi.features;

import javax.swing.KeyStroke;

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.view.BoardView;

/**
 * Represents all possible requests from a player of Reversi, where
 * a player has the ability to pass their turn or place a tile on the board of
 * the game.
 */
public class FeaturesHandler implements Features {
  private final ReversiModel model;
  private BoardView view;

  /**
   * The constructor for this FeaturesHandler.
   *
   * @param view The view for this FeaturesHandler.
   */
  public FeaturesHandler(ReversiModel model, BoardView view) {
    this.model = model;
    this.setView(view);
  }

  /**
   * Passes a player's turn and updates this features' view to show that the player
   * is not placing any tiles in this round.
   */
  public void passMove() {
    if (this.view.canPass()) {
      try {
        this.model.pass(this.model.curPlayer());
        this.view.displayOutput("You passed.");
        this.model.checkGame();

      } catch (IllegalArgumentException e) {
        this.view.errorMessage(e.getMessage());
      } catch (IllegalStateException e) {
        if (e.getMessage().equals("Game is already over.")) {
          this.view.errorMessage("Game is already over.");
        }
      }
      this.view.updateBoard();
    }
  }


  /**
   * Places a player's tile at the most recently highlighted coordinates in this features'
   * view and updates the view to reflect and flipped tiles.
   */
  @Override
  public void placeTile() {
    PlayerColor c = this.model.curPlayer();
    if (this.view.hasHighlightedCell() && this.model.hasValidMove(c)) {
      try {
        this.view.unHighlight();
        this.model.placeTile(c, this.view.currentQCoordinate(),
                this.view.currentRCoordinate());
        if (this.model.isValidMove(this.view.currentQCoordinate(),
                this.view.currentRCoordinate(), c)) {
          this.view.displayOutput("You placed a tile.");
        }
      } catch (IllegalArgumentException | IllegalStateException e) {
        this.view.errorMessage(e.getMessage());
      }
    }
    this.view.updateBoard();
  }

  /**
   * Adds this feature to this feature's view and updates the view to respond to a
   * player typing p by calling passMove, and respond to typing t by calling placeTile.
   *
   * @param v The view to which to add this feature.
   */
  private void setView(BoardView v) {
    this.view = v;
    this.view.addFeatures(this);
    this.view.setHotKey(KeyStroke.getKeyStroke("typed p"), "passMove");
    this.view.setHotKey(KeyStroke.getKeyStroke("typed t"), "placeTile");
  }
}