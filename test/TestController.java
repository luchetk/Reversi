import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.reversi.controller.BlackTileController;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.controller.WhiteTileController;
import cs3500.reversi.features.Features;
import cs3500.reversi.features.FeaturesHandler;
import cs3500.reversi.model.FeaturesHandlerModel;
import cs3500.reversi.model.GameState;
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
import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.view.ReversiTextView;
import cs3500.reversi.view.TextView;


/**
 * Contains tests for the controller and features.
 */
public class TestController {
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
    player1View = new ReversiFrame(m1);
    player2View = new ReversiFrame(m1);
    handlerView1 = new FeaturesHandler(m1, player1View);
    handlerView2 = new FeaturesHandler(m1, player2View);
    p1 = new HumanPlayer(m1);
    p2 = new MachinePlayer(m1, new CapturePieceStrategy(m1));
    handlerModel1 = new FeaturesHandlerModel(m1, player1View, PlayerColor.WHITE, p1);
    handlerModel2 = new FeaturesHandlerModel(m1, player2View, PlayerColor.BLACK, p2);
  }


  @Test
  public void testPassTwiceInRow() {
    this.handlerView1.passMove();
    Assert.assertEquals(3, this.m1.getScore(PlayerColor.BLACK));
    this.handlerView1.passMove();
    Assert.assertEquals(3, this.m1.getScore(PlayerColor.BLACK));
    Assert.assertEquals(GameState.TIE, m1.getStatus());

  }

  @Test
  public void testModelCheckTurn() {
    this.handlerModel1.checkTurn();
    Assert.assertFalse(this.player1View.canPass());
    Assert.assertEquals(3, this.m1.getScore(PlayerColor.BLACK));
    this.handlerModel2.checkTurn();
    Assert.assertEquals(5, m1.getScore(PlayerColor.BLACK));
    Assert.assertEquals(PlayerColor.WHITE, m1.curPlayer());
    //TextView v = new ReversiTextView(m1);
    //Assert.assertEquals("", v.toString());
    Assert.assertEquals(PlayerColor.BLACK, m1.getContentsOfCell(1, -2));
  }

  @Test
  public void testNextTurn() {
    Assert.assertEquals(PlayerColor.BLACK, m1.curPlayer());
    this.handlerModel2.checkTurn();
    Assert.assertEquals(PlayerColor.WHITE, m1.curPlayer());
  }

  @Test
  public void testInvalidArg() {
    this.handlerView1.placeTile();
    Assert.assertEquals(3, m1.getScore(PlayerColor.BLACK));
    Assert.assertEquals(3, m1.getScore(PlayerColor.WHITE));
    Assert.assertEquals(PlayerColor.EMPTY, m1.getContentsOfCell(0,0));
  }

  @Test
  public void testCheckPassWhenGameOver() {
    this.handlerModel1.checkGameOver();
    Assert.assertFalse(this.player1View.canPass());
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

  // for this test, we are mocking the behavior of the main method
  @Test
  public void testGameOverCaptureCapture() {
    ReversiModel model = new ReversiModelStandard(5);
    BoardView player1View = new ReversiFrame(model);
    BoardView player2View = new ReversiFrame(model);
    PlayerActions player1 = createPlayer("capture", model);
    PlayerActions player2 = createPlayer("capture", model);
    ReversiController controller1 = new WhiteTileController(model, player1, player1View);
    ReversiController controller2 = new BlackTileController(model, player2, player2View);
    model.startGame();
    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(GameState.W_WON, model.getStatus());
  }

  @Test
  public void testGameOverCaptureHuman() {
    m1 = new ReversiModelStandard(5);
    player1View = new ReversiFrame(m1);
    player2View = new ReversiFrame(m1);
    p1 = createPlayer("capture", m1);
    p2 = createPlayer("human", m1);
    ReversiController controller1 = new WhiteTileController(m1, p1, player1View);
    ReversiController controller2 = new BlackTileController(m1, p2, player2View);
    m1.startGame();
    m1.placeTile(PlayerColor.BLACK, 1, 1);
    m1.placeTile(PlayerColor.BLACK, -2, 1);
    m1.placeTile(PlayerColor.BLACK, 1, -2);
    Assert.assertTrue(m1.isGameOver());
    Assert.assertEquals(GameState.W_WON, m1.getStatus());
  }

  @Test
  public void testGameOverHumanCapture() {
    m1 = new ReversiModelStandard(5);
    player1View = new ReversiFrame(m1);
    player2View = new ReversiFrame(m1);
    p1 = createPlayer("human", m1);
    p2 = createPlayer("capture", m1);
    ReversiController controller1 = new WhiteTileController(m1, p1, player1View);
    ReversiController controller2 = new BlackTileController(m1, p2, player2View);
    m1.startGame();
    m1.placeTile(PlayerColor.WHITE, 1, 1);
    m1.placeTile(PlayerColor.WHITE, -2, 1);
    m1.placeTile(PlayerColor.WHITE, -1, -1);
    Assert.assertTrue(m1.isGameOver());
    Assert.assertEquals(GameState.B_WON, m1.getStatus());
  }

  @Test
  public void testGameOverHumanCorner() {
    m1 = new ReversiModelStandard(7);
    player1View = new ReversiFrame(m1);
    player2View = new ReversiFrame(m1);
    p1 = createPlayer("human", m1);
    p2 = createPlayer("corner", m1);
    ReversiController controller1 = new WhiteTileController(m1, p1, player1View);
    ReversiController controller2 = new BlackTileController(m1, p2, player2View);
    m1.startGame();
    m1.placeTile(PlayerColor.WHITE, 1, 1);
    m1.placeTile(PlayerColor.WHITE, 2, 1);
    m1.placeTile(PlayerColor.WHITE, -1, -1);
    m1.placeTile(PlayerColor.WHITE, 2, -1);
    m1.placeTile(PlayerColor.WHITE, -2, -1);
    m1.placeTile(PlayerColor.WHITE, -3, 2);
    m1.placeTile(PlayerColor.WHITE, 2, -3);
    m1.placeTile(PlayerColor.WHITE, -1, 2);
    m1.placeTile(PlayerColor.WHITE, 3, -2);
    Assert.assertTrue(m1.isGameOver());
    Assert.assertEquals(GameState.B_WON, m1.getStatus());
  }

  @Test
  public void testGameOverCornerHuman() {
    m1 = new ReversiModelStandard(7);
    player1View = new ReversiFrame(m1);
    player2View = new ReversiFrame(m1);
    p1 = createPlayer("corner", m1);
    p2 = createPlayer("human", m1);
    ReversiController controller1 = new WhiteTileController(m1, p1, player1View);
    ReversiController controller2 = new BlackTileController(m1, p2, player2View);
    m1.startGame();
    m1.placeTile(PlayerColor.BLACK, 1, 1);
    m1.placeTile(PlayerColor.BLACK, 1, 2);
    m1.placeTile(PlayerColor.BLACK, -1, 2);
    m1.placeTile(PlayerColor.BLACK, -1, -1);
    m1.placeTile(PlayerColor.BLACK, 2, -1);
    m1.placeTile(PlayerColor.BLACK, -3, 2);
    m1.placeTile(PlayerColor.BLACK, 2, -3);
    m1.placeTile(PlayerColor.BLACK, -1, -2);
    m1.placeTile(PlayerColor.BLACK, 3, -1);
    m1.placeTile(PlayerColor.BLACK, -2, 3);
    Assert.assertTrue(m1.isGameOver());
    Assert.assertEquals(GameState.W_WON, m1.getStatus());
  }

  @Test
  public void testGameOverHumanHuman() {
    m1 = new ReversiModelStandard(5);
    player1View = new ReversiFrame(m1);
    player2View = new ReversiFrame(m1);
    p1 = createPlayer("human", m1);
    p2 = createPlayer("human", m1);
    ReversiController controller1 = new WhiteTileController(m1, p1, player1View);
    ReversiController controller2 = new BlackTileController(m1, p2, player2View);
    m1.startGame();
    m1.placeTile(PlayerColor.WHITE, 1, 1);
    m1.placeTile(PlayerColor.BLACK, 1, -2);
    m1.placeTile(PlayerColor.WHITE, -1, -1);
    m1.placeTile(PlayerColor.BLACK, -2, 1);
    m1.placeTile(PlayerColor.WHITE, 2, -1);
    m1.placeTile(PlayerColor.BLACK, -1, 2);
    TextView t1 = new ReversiTextView(m1);
    System.out.println(t1);
    Assert.assertTrue(m1.isGameOver());
    Assert.assertEquals(GameState.W_WON, m1.getStatus());
  }
}
