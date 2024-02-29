import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.Board;
import cs3500.reversi.model.GameState;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.ReversiModelStandard;
import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.view.ReversiTextView;
import cs3500.reversi.view.TextView;

/**
 * represents examples for the Reversi game.
 */
public class ExampleReversi {
  private ReversiModel m1;
  private TextView tv1;

  @Before
  public void init() {
    // initializing a model with a board of size five
    m1 = new ReversiModelStandard(5);
    tv1 = new ReversiTextView(m1);
  }

  // how to make a move on the board
  @Test
  public void testMakeMove() {
    m1.placeTile(PlayerColor.BLACK, -1, -1);
    Board b1 = m1.getBoard();
    Assert.assertEquals(PlayerColor.BLACK, m1.getContentsOfCell(-1, -1));
    Assert.assertEquals("    _ _ _    \n" +
            "   X X X _   \n" +
            "  _ X _ O _  \n" +
            "   _ O X _   \n" +
            "    _ _ _    ", tv1.toString());
  }

  // making an invalid move on the board
  @Test
  public void testInvalidMakeMove() {
    Assert.assertThrows(IllegalStateException.class, () -> m1.placeTile(PlayerColor.BLACK, -2, 2));
  }

  // passing a turn
  // should not affect the board
  @Test
  public void testPassingAMove() {
    m1.pass(PlayerColor.BLACK);
    Assert.assertEquals("    _ _ _    \n" +
            "   _ O X _   \n" +
            "  _ X _ O _  \n" +
            "   _ O X _   \n" +
            "    _ _ _    ", tv1.toString());
  }

  // getting the status of the board
  @Test
  public void testGetStatus() {
    Assert.assertEquals(GameState.PLAYING, m1.getStatus());
  }

  // getting the board of the model
  @Test
  public void testGetBoard() {
    Board b1 = m1.getBoard();
    Assert.assertEquals(b1, m1.getBoard());
  }
}