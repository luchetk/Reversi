import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import cs3500.reversi.model.Board;
import cs3500.reversi.model.GameState;
import cs3500.reversi.model.HexCoordinates;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.ReversiModelStandard;
import cs3500.reversi.player.PlayerColor;
import cs3500.reversi.view.ReversiTextView;

/**
 * test class for the game.
 */
public class TestReversi {
  private ReversiModel m1;
  private ReversiModel m4;
  private ReversiModel mBasic;
  private ReversiTextView tv;
  private ReversiTextView tvBasic;
  private Board b1;
  private HexCoordinates h1;
  private HexCoordinates hBig;

  @Before
  public void init() {
    m1 = new ReversiModelStandard(11);
    m4 = new ReversiModelStandard(5);
    mBasic = new ReversiModelStandard(7);
    tv = new ReversiTextView(m1);
    tvBasic = new ReversiTextView(mBasic);
    b1 = new Board(7);
    h1 = new HexCoordinates(2, 3);
    hBig = new HexCoordinates(69, 420);
  }

  @Test
  public void testValidPlaceTile() {
    m4.placeTile(PlayerColor.BLACK, -2, 1);
    m4.placeTile(PlayerColor.WHITE, 2, -1);
    m4.placeTile(PlayerColor.BLACK, 1, -2);
    m4.placeTile(PlayerColor.WHITE, -1, -1);
    m4.placeTile(PlayerColor.BLACK, 1, 1);
    m4.placeTile(PlayerColor.WHITE, -1, 2);
    Assert.assertFalse(m4.hasValidMove(PlayerColor.BLACK));
  }

  @Test
  public void validPlaceTile() {
    Assert.assertEquals(PlayerColor.EMPTY, m1.getContentsOfCell(1, -2));
    Assert.assertEquals(PlayerColor.WHITE, m1.getContentsOfCell(0, -1));
    m1.placeTile(PlayerColor.BLACK, 1, -2);
    Assert.assertEquals(PlayerColor.BLACK, m1.getContentsOfCell(1, -2));
    Assert.assertEquals(PlayerColor.BLACK, m1.getContentsOfCell(0, -1));
    // it should change the tiles in the middle
    m1.placeTile(PlayerColor.WHITE, 1, -3);
    Assert.assertEquals(PlayerColor.WHITE, m1.getContentsOfCell(1, -2));
    // the color below should stay the same
    Assert.assertEquals(PlayerColor.BLACK, m1.getContentsOfCell(0, -1));
  }

  @Test
  public void validPlaceTileBasic() {
    Assert.assertEquals(PlayerColor.EMPTY, mBasic.getContentsOfCell(1, -2));
    Assert.assertEquals(PlayerColor.WHITE, mBasic.getContentsOfCell(0, -1));
    mBasic.placeTile(PlayerColor.BLACK, 1, -2);
    Assert.assertEquals(PlayerColor.BLACK, mBasic.getContentsOfCell(1, -2));
    Assert.assertEquals(PlayerColor.BLACK, mBasic.getContentsOfCell(0, -1));
    // it should change the tiles in the middle
    mBasic.placeTile(PlayerColor.WHITE, 1, -3);
    Assert.assertEquals(PlayerColor.WHITE, mBasic.getContentsOfCell(1, -2));
    // the color below should stay the same
    Assert.assertEquals(PlayerColor.BLACK, mBasic.getContentsOfCell(0, -1));
  }

  @Test
  public void outOfBoundsPlaceTile() {
    Assert.assertThrows(IllegalArgumentException.class, () -> m1.placeTile(PlayerColor.BLACK,
            20, 40));
    Assert.assertThrows(IllegalArgumentException.class, () -> mBasic.placeTile(PlayerColor.BLACK,
            20, 40));
  }

  @Test
  public void validPlaceTileRender() {
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());
    m1.placeTile(PlayerColor.BLACK, 1, -2);
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "    _ _ _ _ X _ _ _ _    \n" +
            "   _ _ _ _ X X _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());
    m1.placeTile(PlayerColor.WHITE, 1, -3);
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ O _ _ _ _     \n" +
            "    _ _ _ _ O _ _ _ _    \n" +
            "   _ _ _ _ X O _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
    mBasic.placeTile(PlayerColor.BLACK, -2, 1);
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ X X X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
    mBasic.placeTile(PlayerColor.WHITE, -3, 2);
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ O _ O _ _  \n" +
            "   _ O X X _ _   \n" +
            "    O _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
  }

  @Test
  public void invalidPlaceTileRender() {
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());
    Assert.assertThrows(IllegalStateException.class, () -> m1.placeTile(PlayerColor.WHITE,
            2, 0));
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());
    Assert.assertThrows(IllegalStateException.class, () -> m1.placeTile(PlayerColor.BLACK,
            -3, -1));
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());

    // this is a valid move but the one after is not
    m1.placeTile(PlayerColor.WHITE, 1, -2);
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "    _ _ _ _ O _ _ _ _    \n" +
            "   _ _ _ _ O O _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());
    Assert.assertThrows(IllegalStateException.class, () -> m1.placeTile(PlayerColor.BLACK,
            1, 1));
    Assert.assertEquals("       _ _ _ _ _ _       \n" +
            "      _ _ _ _ _ _ _      \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "    _ _ _ _ O _ _ _ _    \n" +
            "   _ _ _ _ O O _ _ _ _   \n" +
            "  _ _ _ _ X _ O _ _ _ _  \n" +
            "   _ _ _ _ O X _ _ _ _   \n" +
            "    _ _ _ _ _ _ _ _ _    \n" +
            "     _ _ _ _ _ _ _ _     \n" +
            "      _ _ _ _ _ _ _      \n" +
            "       _ _ _ _ _ _       ", tv.toString());

    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());

    Assert.assertThrows(IllegalStateException.class, () -> mBasic.placeTile(PlayerColor.BLACK,
            -2, -1));
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());

    // this is a valid move but the one after is not
    mBasic.placeTile(PlayerColor.WHITE, 1, -2);
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ O _ _    \n" +
            "   _ _ O O _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
    Assert.assertThrows(IllegalStateException.class, () -> mBasic.placeTile(PlayerColor.BLACK,
            1, 1));
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ O _ _    \n" +
            "   _ _ O O _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
  }

  @Test
  public void getSizeBoard() {
    Assert.assertEquals(11, m1.getBoard().getSize());
    Assert.assertEquals(7, mBasic.getBoard().getSize());
    Assert.assertEquals(7, b1.getSize());
  }

  @Test
  public void testIllegalSize() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new ReversiModelStandard(3));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Board(3));
    Assert.assertThrows(IllegalArgumentException.class, () -> new ReversiModelStandard(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Board(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new ReversiModelStandard(-5));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Board(-5));
    Assert.assertThrows(IllegalArgumentException.class, () -> new ReversiModelStandard(6));
    Assert.assertThrows(IllegalArgumentException.class, () -> new Board(6));
  }

  @Test
  public void isEqualHexCoordinate() {
    Assert.assertEquals(new HexCoordinates(2, 3), h1);
    Assert.assertEquals(hBig, new HexCoordinates(69, 420));
  }

  @Test
  public void isHashCodeSameCoordinate() {
    Assert.assertEquals(89, h1.hashCode());
    Assert.assertEquals(9717, hBig.hashCode());
  }

  @Test
  public void testGameStateWhenGameOver() {
    Assert.assertEquals(GameState.PLAYING, m1.getStatus());
    mBasic.placeTile(PlayerColor.BLACK, -1, -1);
    mBasic.placeTile(PlayerColor.WHITE, -1, -2);
    mBasic.placeTile(PlayerColor.BLACK, -2, -1);
    mBasic.placeTile(PlayerColor.WHITE, -3, 0);
    mBasic.placeTile(PlayerColor.BLACK, -1, 2);
    mBasic.placeTile(PlayerColor.WHITE, -1, 3);
    mBasic.placeTile(PlayerColor.BLACK, -2, 1);
    mBasic.placeTile(PlayerColor.WHITE, 2, -1);
    mBasic.placeTile(PlayerColor.BLACK, 1, -2);
    mBasic.placeTile(PlayerColor.WHITE, 1, -3);
    mBasic.placeTile(PlayerColor.BLACK, -2, 3);
    mBasic.placeTile(PlayerColor.WHITE, -3, 2);
    mBasic.placeTile(PlayerColor.BLACK, -3, 1);
    mBasic.placeTile(PlayerColor.WHITE, -3, 3);
    mBasic.placeTile(PlayerColor.BLACK, 2, -3);
    mBasic.placeTile(PlayerColor.WHITE, 3, -3);
    mBasic.placeTile(PlayerColor.BLACK, 1, 1);
    mBasic.placeTile(PlayerColor.WHITE, 1, 2);
    mBasic.placeTile(PlayerColor.BLACK, 2, 1);
    mBasic.placeTile(PlayerColor.WHITE, 3, 0);
    mBasic.placeTile(PlayerColor.BLACK, 3, -2);
    mBasic.placeTile(PlayerColor.WHITE, 3, -1);

    // no valid moves anymore
    Assert.assertFalse(mBasic.hasValidMove(PlayerColor.BLACK));
    Assert.assertFalse(mBasic.hasValidMove(PlayerColor.WHITE));
    mBasic.pass(PlayerColor.BLACK);
    mBasic.pass(PlayerColor.WHITE);
    // test to see that the state is updated
    Assert.assertEquals(GameState.W_WON, mBasic.getStatus());
  }

  @Test
  public void passUpdatesGameStateToTie() {
    // simulate the game and make both players pass
    m1.pass(PlayerColor.BLACK);
    m1.pass(PlayerColor.WHITE);
    Assert.assertEquals(GameState.TIE, m1.getStatus());
  }

  @Test
  public void passUpdatesGameStateToBWon() {
    mBasic.placeTile(PlayerColor.BLACK, 1, -2);
    mBasic.pass(PlayerColor.WHITE);
    mBasic.pass(PlayerColor.BLACK);
    Assert.assertEquals(GameState.B_WON, mBasic.getStatus());
  }

  @Test
  public void passDoesNotUpdateGameStateWhilePlayingBlack() {
    m1.pass(PlayerColor.BLACK);
    Assert.assertEquals(GameState.PLAYING, m1.getStatus());
  }

  @Test
  public void passDoesNotUpdateGameStateWhilePlayingWhite() {
    m1.pass(PlayerColor.WHITE);
    Assert.assertEquals(GameState.PLAYING, m1.getStatus());
  }

  @Test
  public void throwExceptionForPlaceTileIfWon() {
    mBasic.placeTile(PlayerColor.BLACK, 1, -2);
    mBasic.pass(PlayerColor.WHITE);
    mBasic.pass(PlayerColor.BLACK);
    Assert.assertThrows(IllegalStateException.class, () -> mBasic.placeTile(PlayerColor.WHITE,
            1, -3));
  }

  @Test
  public void getBoardInBoard() {
    HashMap<HexCoordinates, PlayerColor> temp = new HashMap<>();
    // iterates over q to go through the horizontal rows
    PlayerColor prev = PlayerColor.BLACK;
    for (int q = -6; q < 7; q++) {
      // gets the diagonal values r1 and r2
      int r1 = Math.max(-6, -7 - q);
      int r2 = Math.min(6, 6 - q);
      for (int r = r1; r <= r2; r++) {
        HexCoordinates axialCoords = new HexCoordinates(q, r);
        int s = -(q + r);
        // place tiles around the center
        if (Math.abs(q) + Math.abs(s) + Math.abs(r) == 2) {
          temp.put(axialCoords, prev);
          // check this so that the initialization correctly does every other around the center
          if (r != 1) {
            if (prev == PlayerColor.BLACK) {
              prev = PlayerColor.WHITE;
            } else {
              prev = PlayerColor.BLACK;
            }
          }
        } else {
          temp.put(axialCoords, PlayerColor.EMPTY);
        }
      }
    }
    Assert.assertEquals(temp, b1.getBoard());
  }

  @Test
  public void avoidUnintendedChangeInBoard() {
    HashMap<HexCoordinates, PlayerColor> mapBoard = b1.getBoard();
    HexCoordinates hc = new HexCoordinates(69, 420);
    mapBoard.put(hc, PlayerColor.BLACK);
    // the board itself should not change
    Assert.assertNull(b1.getBoard().get(hc));
  }

  @Test
  public void hasValidMoveTrue() {
    Assert.assertTrue(m1.hasValidMove(PlayerColor.BLACK));
    Assert.assertTrue(m1.hasValidMove(PlayerColor.WHITE));
    Assert.assertThrows(IllegalArgumentException.class, () -> m1.hasValidMove(PlayerColor.EMPTY));
    m1.placeTile(PlayerColor.BLACK, 1, -2);
    m1.placeTile(PlayerColor.WHITE, 1, -3);
    Assert.assertTrue(m1.hasValidMove(PlayerColor.BLACK));
    Assert.assertTrue(m1.hasValidMove(PlayerColor.WHITE));
    Assert.assertThrows(IllegalArgumentException.class, () -> m1.hasValidMove(PlayerColor.EMPTY));
  }

  @Test
  public void getBoardFromModel() {
    HashMap<HexCoordinates, PlayerColor> temp = new HashMap<>();
    // iterates over q to go through the horizontal rows
    PlayerColor prev = PlayerColor.BLACK;
    for (int q = -6; q < 7; q++) {
      // gets the diagonal values r1 and r2
      int r1 = Math.max(-6, -7 - q);
      int r2 = Math.min(6, 6 - q);
      for (int r = r1; r <= r2; r++) {
        HexCoordinates axialCoords = new HexCoordinates(q, r);
        int s = -(q + r);
        // place tiles around the center
        if (Math.abs(q) + Math.abs(s) + Math.abs(r) == 2) {
          temp.put(axialCoords, prev);
          // check this so that the initialization correctly does every other around the center
          if (r != 1) {
            if (prev == PlayerColor.BLACK) {
              prev = PlayerColor.WHITE;
            } else {
              prev = PlayerColor.BLACK;
            }
          }
        } else {
          temp.put(axialCoords, PlayerColor.EMPTY);
        }
      }
    }
    Assert.assertEquals(temp, mBasic.getBoard().getBoard());
  }

  @Test
  public void testMultipleMovesInRow() {
    mBasic.placeTile(PlayerColor.BLACK, -1, -1);
    Assert.assertThrows(IllegalStateException.class, () -> mBasic.placeTile(PlayerColor.BLACK,
            -2, -1));
  }

  @Test
  public void testPassingMultipleTimesInRow() {
    mBasic.pass(PlayerColor.BLACK);
    Assert.assertThrows(IllegalStateException.class, () -> mBasic.pass(PlayerColor.BLACK));
  }

  @Test
  public void checkPassWontChangeBoard() {
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
    mBasic.pass(PlayerColor.BLACK);
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
    mBasic.pass(PlayerColor.WHITE);
    Assert.assertEquals("     _ _ _ _     \n" +
            "    _ _ _ _ _    \n" +
            "   _ _ O X _ _   \n" +
            "  _ _ X _ O _ _  \n" +
            "   _ _ O X _ _   \n" +
            "    _ _ _ _ _    \n" +
            "     _ _ _ _     ", tvBasic.toString());
  }

  @Test
  public void playerCantMoveAfterPassing() {
    mBasic.pass(PlayerColor.BLACK);
    Assert.assertThrows(IllegalStateException.class, () -> mBasic.placeTile(PlayerColor.BLACK,
            1, -2));
  }

  @Test
  public void passException() {
    Assert.assertThrows(IllegalArgumentException.class, () -> mBasic.pass(PlayerColor.EMPTY));
  }

  @Test
  public void placeTileException() {
    Assert.assertThrows(IllegalArgumentException.class, () -> mBasic.placeTile(PlayerColor.EMPTY,
            1, 2));
  }

  @Test
  public void playerCanNotGoMultipleTimesInRow() {
    mBasic.placeTile(PlayerColor.BLACK, -1, -1);
    // throws exception because should not be able to place black tile during
    // the white tile turn
    Assert.assertThrows(IllegalStateException.class, () -> mBasic.placeTile(PlayerColor.BLACK,
            1, 1));
  }

  @Test
  public void hasValidMoveException() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            mBasic.hasValidMove(PlayerColor.EMPTY));
  }


  @Test
  public void testCurPlayer() {
    Assert.assertEquals(PlayerColor.BLACK, mBasic.curPlayer());
    mBasic.placeTile(PlayerColor.BLACK, -1, -1);
    Assert.assertEquals(PlayerColor.WHITE, mBasic.curPlayer());
  }

  @Test
  public void testIsValidMove() {
    Assert.assertFalse(mBasic.isValidMove(-5, -5, PlayerColor.BLACK));
    Assert.assertTrue(mBasic.isValidMove(-1, -1, PlayerColor.BLACK));
    Assert.assertTrue(mBasic.isValidMove(1, -2, PlayerColor.WHITE));
    Assert.assertFalse(mBasic.isValidMove(2, 2, PlayerColor.WHITE));
  }

  @Test
  public void testDefaultBoard() {
    Assert.assertThrows(IllegalArgumentException.class, () -> mBasic.defaultBoard(2));
    Assert.assertThrows(IllegalArgumentException.class, () -> mBasic.defaultBoard(-5));
  }

  @Test
  public void testSizeOfBoard() {
    Assert.assertEquals(7, mBasic.getSizeOfBoard());
    Assert.assertEquals(11, m1.getSizeOfBoard());
  }

  @Test
  public void testGetContentsOfCell() {
    Assert.assertEquals(PlayerColor.EMPTY, mBasic.getContentsOfCell(0,0));
    Assert.assertEquals(PlayerColor.WHITE, m1.getContentsOfCell(0,-1));
    Assert.assertEquals(PlayerColor.BLACK, m1.getContentsOfCell(1,-1));
    Assert.assertThrows(IllegalArgumentException.class, () -> mBasic.getContentsOfCell(100,
            100));
    Assert.assertThrows(IllegalArgumentException.class, () -> mBasic.getContentsOfCell(-100,
            -100));

  }

  @Test
  public void testGetScore() {
    Assert.assertEquals(3, mBasic.getScore(PlayerColor.BLACK));
    Assert.assertEquals(3, mBasic.getScore(PlayerColor.WHITE));
    mBasic.placeTile(PlayerColor.BLACK, -1, -1);
    Assert.assertEquals(5, mBasic.getScore(PlayerColor.BLACK));
  }

  @Test
  public void testIsValidCoordinate() {
    Assert.assertTrue(mBasic.isValidCoordinate(0, -2));
    Assert.assertFalse(mBasic.isValidCoordinate(-1, -3));
    Assert.assertFalse(mBasic.isValidCoordinate(-100, -100));
  }

  @Test
  public void testNumTilesCaptured() {
    Assert.assertEquals(1, mBasic.numTilesCaptured(-1, -1, PlayerColor.BLACK));
    Assert.assertEquals(0, mBasic.numTilesCaptured(0, 3, PlayerColor.WHITE));
    mBasic.placeTile(PlayerColor.BLACK, -1, -1);
    mBasic.placeTile(PlayerColor.WHITE, 1, -2);
    mBasic.placeTile(PlayerColor.BLACK, -2, 1);
    Assert.assertEquals(3, mBasic.numTilesCaptured(-3, 2, PlayerColor.WHITE));
  }
}