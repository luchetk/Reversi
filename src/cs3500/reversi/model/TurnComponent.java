package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Associates features functionality with specific model functionality. Allows a
 * model to have features which check player turns as well as check to see the
 * status of the game.
 */
public class TurnComponent {
  private final List<FeaturesModel> featuresListeners = new ArrayList<>();

  /**
   * Adds the given model feature to this turn component's list of features.
   * @param f   A model feature.
   */
  void addFeatures(FeaturesModel f) {
    this.featuresListeners.add(f);
  }

  /**
   * Checks the current player turn for this turn component's list of features.
   */
  void listenerCheckTurn() {
    for (FeaturesModel f : this.featuresListeners) {
      f.checkTurn();
    }
  }

  /**
   * Checks the status of the game for this turn component's list of features.
   */
  void listenerCheckGameOver() {
    for (FeaturesModel f : this.featuresListeners) {
      f.checkGameOver();
    }
  }

}
