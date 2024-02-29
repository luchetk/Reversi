package cs3500.reversi.model;

import org.junit.jupiter.api.Test;

import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.view.ReversiTextView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * test class for the package-private methods.
 */
public class TestPackage {
  ReversiModel m1;
  ReversiModel mBasic;
  ReversiTextView tv;
  ReversiTextView tvBasic;
  Board b1;
  HexCoordinates h1;
  HexCoordinates hBig;

  private void init() {
    m1 = new ReversiModelStandard(11);
    mBasic = new ReversiModelStandard(7);
    tv = new ReversiTextView(m1);
    tvBasic = new ReversiTextView(mBasic);
    b1 = new Board(7);
    h1 = new HexCoordinates(2, 3);
    hBig = new HexCoordinates(69, 420);
  }

  @Test
  public void testMakeMove() {
    this.init();
    b1.makeMove(-1, -1, PlayerColor.BLACK);
    b1.makeMove(-1, -2, PlayerColor.WHITE);
    b1.makeMove(-2, -1, PlayerColor.BLACK);
    b1.makeMove(-3, 0, PlayerColor.WHITE);
    b1.makeMove(-1, 2, PlayerColor.BLACK);
    b1.makeMove(-1, 3, PlayerColor.WHITE);
    b1.makeMove(-2, 1, PlayerColor.BLACK);
    b1.makeMove(2, -1, PlayerColor.WHITE);
    b1.makeMove(1, -2, PlayerColor.BLACK);
    b1.makeMove(1, -3, PlayerColor.WHITE);
    b1.makeMove(-2, 3, PlayerColor.BLACK);
    b1.makeMove(-3, 2, PlayerColor.WHITE);
    b1.makeMove(-3, 1, PlayerColor.BLACK);
    b1.makeMove(-3, 3, PlayerColor.WHITE);
    b1.makeMove(2, -3, PlayerColor.BLACK);
    b1.makeMove(3, -3, PlayerColor.WHITE);
    b1.makeMove(1, 1, PlayerColor.BLACK);
    b1.makeMove(1, 2, PlayerColor.WHITE);
    b1.makeMove(2, 1, PlayerColor.BLACK);
    b1.makeMove(3, 0, PlayerColor.WHITE);
    b1.makeMove(3, -2, PlayerColor.BLACK);
    b1.makeMove(3, -1, PlayerColor.WHITE);
    // no valid moves anymore
    assertFalse(b1.hasValidMove(PlayerColor.BLACK));
    assertFalse(b1.hasValidMove(PlayerColor.WHITE));
  }

  @Test
  public void getScoreBoard() {
    this.init();
    assertEquals(3, b1.getScore(PlayerColor.BLACK));
    assertEquals(3, b1.getScore(PlayerColor.WHITE));
    assertThrows(IllegalArgumentException.class, () -> b1.getScore(PlayerColor.EMPTY));
    b1.makeMove(1, -2, PlayerColor.BLACK);
    assertEquals(5, b1.getScore(PlayerColor.BLACK));
    assertEquals(2, b1.getScore(PlayerColor.WHITE));
    b1.makeMove(1, -3, PlayerColor.WHITE);
    assertEquals(5, b1.getScore(PlayerColor.WHITE));
    assertEquals(3, b1.getScore(PlayerColor.BLACK));
  }

  @Test
  public void makeMoveInBoard() {
    this.init();
    assertEquals(PlayerColor.EMPTY, b1.getColorAt(1, -2));
    assertEquals(PlayerColor.WHITE, b1.getColorAt(0, -1));
    b1.makeMove(1, -2, PlayerColor.BLACK);
    assertEquals(PlayerColor.BLACK, b1.getColorAt(1, -2));
    assertEquals(PlayerColor.BLACK, b1.getColorAt(0, -1));
    // it should change the tiles in the middle
    b1.makeMove(1, -3, PlayerColor.WHITE);
    assertEquals(PlayerColor.WHITE, b1.getColorAt(1, -2));
    // the color below should stay the same
    assertEquals(PlayerColor.BLACK, b1.getColorAt(0, -1));
  }

  @Test
  public void testExceptionMakeMove() {
    this.init();
    assertThrows(IllegalStateException.class, () -> b1.makeMove(2,
            0, PlayerColor.BLACK));
    b1.makeMove(1, -2, PlayerColor.WHITE);
    assertThrows(IllegalStateException.class, () -> b1.makeMove(1,
            1, PlayerColor.BLACK));
    assertThrows(IllegalArgumentException.class, () -> b1.makeMove(40,
            100, PlayerColor.BLACK));
    assertThrows(IllegalArgumentException.class, () -> b1.makeMove(-3, -1,
            PlayerColor.WHITE));
  }

  @Test
  public void isValidMoveBoard() {
    this.init();
    assertTrue(b1.hasValidMove(PlayerColor.BLACK));
    assertTrue(m1.getBoard().hasValidMove(PlayerColor.BLACK));
  }

  @Test
  public void testHasValidMoveEmpty() {
    this.init();
    assertThrows(IllegalArgumentException.class, () -> b1.hasValidMove(PlayerColor.EMPTY));
  }

  @Test
  public void testMakeMoveEmpty() {
    this.init();
    assertThrows(IllegalArgumentException.class, () -> b1.makeMove(-1, -1,
            PlayerColor.EMPTY));
  }

  @Test
  public void testGetScoreEmpty() {
    this.init();
    assertThrows(IllegalArgumentException.class, () -> b1.getScore(PlayerColor.EMPTY));
  }


  @Test
  public void getColorBoard() {
    this.init();
    assertEquals(PlayerColor.EMPTY, b1.getColorAt(-3, 2));
    assertEquals(PlayerColor.BLACK, b1.getColorAt(-1, 0));
    assertEquals(PlayerColor.WHITE, b1.getColorAt(0, -1));
  }

  @Test
  public void testDefaultBoard() {
    this.init();
    Board b = mBasic.defaultBoard(5);
    assertEquals(PlayerColor.EMPTY, b.getColorAt(0, 0));
    assertEquals(PlayerColor.WHITE, b.getColorAt(0, -1));
    assertEquals(PlayerColor.BLACK, b.getColorAt(1, -1));
    assertEquals(PlayerColor.BLACK, b.getColorAt(-1, 0));
    assertEquals(PlayerColor.WHITE, b.getColorAt(1, 0));
    assertEquals(PlayerColor.WHITE, b.getColorAt(-1, 1));
    assertEquals(PlayerColor.BLACK, b.getColorAt(0, 1));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(0, -2));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(1, -2));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(2, -2));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(-1, -1));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(2, -1));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(-2, 0));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(2, 0));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(-2, 1));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(1, 1));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(-2, 2));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(-1, 2));
    assertEquals(PlayerColor.EMPTY, b.getColorAt(0, 2));
  }

  @Test
  public void testCheckAllSame() {
    this.init();
    assertEquals(1, b1.numTilesCaptured(-1, -1, PlayerColor.BLACK));
  }
}
