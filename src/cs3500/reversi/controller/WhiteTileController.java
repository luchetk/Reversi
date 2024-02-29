package cs3500.reversi.controller;

import cs3500.reversi.features.Features;
import cs3500.reversi.features.FeaturesHandler;
import cs3500.reversi.model.FeaturesHandlerModel;
import cs3500.reversi.model.FeaturesModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.PlayerActions;
import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.view.BoardView;

/**
 * Represents the controller for a white tile player in the game of reversi. The controller
 * registers itself as a listener for the features interface of the reversi view
 * and the features interface of the reversi model. The controller controls interactions
 * between a player and the model of reversi and allows playable moves on the model.
 * A player is allowed to moves: placing a tile and passing, which a human player is
 * able to accomplish by pressing the "t" key for placing a tile or a "p" key for passing.
 * Keeps track of when it is a white tile player's turn and when it is not their turn through
 * the features interfaces, and updates the view accordingly.
 */
public class WhiteTileController implements ReversiController {

  /**
   * Constructor for a white tile controller. Registers this controller as a listener
   * of the features interface for the white tile's view and registers itself as a
   * listener for the model of this controller.
   * @param model   The model for this controller.
   * @param player  The player for this controller.
   * @param view    The view for this controller.
   */
  public WhiteTileController(ReversiModel model, PlayerActions player, BoardView view) {
    Features viewFeatures = new FeaturesHandler(model, view);
    FeaturesModel modelFeatures = new FeaturesHandlerModel(model, view, PlayerColor.WHITE, player);
    view.updateBoard();
    view.display(true);
  }



}
