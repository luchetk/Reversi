import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.KeyStroke;

import cs3500.reversi.features.Features;
import cs3500.reversi.features.FeaturesHandler;
import cs3500.reversi.model.FeaturesHandlerModel;
import cs3500.reversi.model.HexCoordinates;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.ReversiModelStandard;
import cs3500.reversi.player.CapturePieceStrategy;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.MachinePlayer;
import cs3500.reversi.player.PlayerActions;
import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.player.Strategy;
import cs3500.reversi.view.BoardView;
import cs3500.reversi.view.ReversiTextView;
import cs3500.reversi.view.TextView;

/**
 * Tests for placing moves through features.
 */
public class TestControllerMock {

  /**
   * A mock view for testing that the game properly works when placing tiles.
   */
  public static class MockView implements BoardView {
    HexCoordinates hc;
    Strategy s;
    ReadonlyReversiModel r;
    int temp;


    /**
     * Constructor for the mock view.
     * @param hc  a hex coordinate.
     * @param s   a strategy for placing tiles.
     * @param r   a reversi model.
     */
    public MockView(HexCoordinates hc, Strategy s, ReadonlyReversiModel r) {
      this.hc = hc;
      this.s = s;
      this.r = r;
    }

    @Override
    public void unHighlight() {
      this.temp = 0;
    }

    @Override
    public boolean canPass() {
      return true;
    }

    @Override
    public void errorMessage(String s) {
      this.temp = 0;
    }

    @Override
    public void updateBoard() {
      this.temp = 0;
    }

    @Override
    public void addFeatures(Features feature) {
      this.temp = 0;
    }

    @Override
    public void setHotKey(KeyStroke key, String featureName) {
      this.temp = 0;
    }

    @Override
    public void displayOutput(String s) {
      this.temp = 0;
    }

    public void setHC(int q, int r) {
      this.hc = new HexCoordinates(q, r);
    }

    @Override
    public int currentQCoordinate() {
      System.out.println(this.s.chooseMove(this.r.curPlayer()).get().getQ() + "" +
              this.r.curPlayer());
      return this.s.chooseMove(this.r.curPlayer()).get().getQ();
    }

    @Override
    public int currentRCoordinate() {
      return this.s.chooseMove(this.r.curPlayer()).get().getR();
    }

    @Override
    public void canClick(boolean click) {
      this.temp = 0;
    }

    @Override
    public void display(boolean show) {
      this.temp = 0;
    }

    @Override
    public boolean hasHighlightedCell() {
      return true;
    }
  }


  ReversiModel m1;
  BoardView player1View;
  BoardView player2View;
  Features handlerView1;
  Features handlerView2;
  FeaturesHandlerModel handlerModel1;
  FeaturesHandlerModel handlerModel2;
  PlayerActions p1;
  PlayerActions p2;


  @Before
  public void init() {
    m1 = new ReversiModelStandard(5);
    player1View = new MockView(new HexCoordinates(-100, -100), new CapturePieceStrategy(m1), m1);
    player2View = new MockView(new HexCoordinates(-100, -100), new CapturePieceStrategy(m1), m1);
    handlerView1 = new FeaturesHandler(m1, player1View);
    handlerView2 = new FeaturesHandler(m1, player2View);
    p1 = new HumanPlayer(m1);
    p2 = new MachinePlayer(m1, new CapturePieceStrategy(m1));
    handlerModel1 = new FeaturesHandlerModel(m1, player1View, PlayerColor.BLACK, p1);
  }

  @Test
  public void testPlacingTile() {
    this.handlerView1.placeTile();
    TextView tv = new ReversiTextView(m1);
    Assert.assertEquals(PlayerColor.BLACK, m1.getContentsOfCell(1, -2));
    Assert.assertEquals(PlayerColor.WHITE, m1.curPlayer());
    Assert.assertEquals(5, m1.getScore(PlayerColor.BLACK));
    this.handlerView1.placeTile();
    Assert.assertEquals(PlayerColor.WHITE, m1.getContentsOfCell(-1, 1));
    Assert.assertEquals(PlayerColor.BLACK, m1.curPlayer());
    Assert.assertEquals(4, m1.getScore(PlayerColor.WHITE));
  }
}
