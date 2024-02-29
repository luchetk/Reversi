import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import cs3500.reversi.model.Board;
import cs3500.reversi.model.FeaturesModel;
import cs3500.reversi.model.GameState;
import cs3500.reversi.model.HexCoordinates;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.ReversiModelStandard;
import cs3500.reversi.player.CapturePieceStrategy;
import cs3500.reversi.player.CheckCornersStrategy;
import cs3500.reversi.player.MultipleStrategies;
import cs3500.reversi.player.PlayerColor;

/**
 * contains tests for the implemented strategies.
 */
public class TestStrategy {

  /**
   * represents a mock class for the Reversi Model. This mock class has a transcript that logs
   * the coordinates that the strategy has inspected, which should be all of the hexagons on board.
   */
  public static class MockModel1 implements ReversiModel {
    public StringBuilder transcript;
    private final Board board;
    private final int size;

    /**
     * the constructor for the mock model.
     *
     * @param log  holds the transcript for the seen coordinates
     * @param size the size of the game board
     */
    public MockModel1(StringBuilder log, int size) {
      board = new Board(size);
      this.transcript = log;
      this.size = size;
    }

    @Override
    public boolean hasValidMove(PlayerColor color) {
      return false;
    }

    @Override
    public GameState getStatus() {
      return null;
    }

    @Override
    public Board getBoard() {
      return null;
    }

    @Override
    public Board defaultBoard(int size) {
      return null;
    }

    @Override
    public int getSizeOfBoard() {
      return this.size;
    }

    @Override
    public PlayerColor getContentsOfCell(int q, int r) {
      return this.board.getColorAt(q, r);
    }

    @Override
    public int getScore(PlayerColor color) {
      return 0;
    }

    @Override
    public boolean isGameOver() {
      return false;
    }

    @Override
    public boolean isValidCoordinate(int q, int r) {
      return true;
    }

    @Override
    public PlayerColor curPlayer() {
      return null;
    }

    @Override
    public int numTilesCaptured(int q, int r, PlayerColor color) {
      transcript.append(q).append(", ").append(r).append("\n");
      return 0;
    }

    @Override
    public void addFeatures(FeaturesModel f) {
      this.transcript.append("");
    }

    @Override
    public void startGame() {
      this.transcript.append("");
    }

    @Override
    public void checkGame() {
      this.transcript.append("");
    }

    @Override
    public void placeTile(PlayerColor color, int row, int col) throws IllegalArgumentException {
      this.transcript.append("");
    }

    @Override
    public void pass(PlayerColor color) {
      this.transcript.append("");
    }

    @Override
    public boolean isValidMove(int q, int r, PlayerColor color) {
      return true;
    }
  }

  /**
   * mock model for the Reversi Model to be used for testing if the strategy chooses the right
   * coordinates when it's lied to about how many tiles it can flip.
   */
  public static class MockModel2 implements ReversiModel {
    private final int size;
    private final Board board;
    private int score;

    /**
     * the constructor for the mock model.
     *
     * @param size the size of the game board
     */
    public MockModel2(int size) {
      this.size = size;
      this.board = new Board(size);
      this.score = 0;
    }

    @Override
    public boolean hasValidMove(PlayerColor color) {
      return true;
    }

    @Override
    public GameState getStatus() {
      return null;
    }

    @Override
    public Board getBoard() {
      return this.board;
    }

    @Override
    public Board defaultBoard(int size) {
      return null;
    }

    @Override
    public int getSizeOfBoard() {
      return this.size;
    }

    @Override
    public PlayerColor getContentsOfCell(int q, int r) {
      return this.board.getColorAt(q, r);
    }

    @Override
    public int getScore(PlayerColor color) {
      return this.score;
    }

    @Override
    public boolean isGameOver() {
      return false;
    }

    @Override
    public boolean isValidCoordinate(int q, int r) {
      return true;
    }

    @Override
    public PlayerColor curPlayer() {
      return null;
    }

    @Override
    public int numTilesCaptured(int q, int r, PlayerColor color) {
      if (q == -2 && r == 1) {
        return 10;
      } else {
        return this.board.numTilesCaptured(q, r, color);
      }
    }

    @Override
    public void addFeatures(FeaturesModel f) {
      this.score = 0;
    }

    @Override
    public void startGame() {
      this.score = 0;
    }

    @Override
    public void checkGame() {
      this.score = 0;
    }

    @Override
    public void placeTile(PlayerColor color, int r, int q) throws IllegalArgumentException {
      this.score = 0;
    }

    @Override
    public void pass(PlayerColor color) {
      this.score = 0;
    }

    @Override
    public boolean isValidMove(int q, int r, PlayerColor color) {
      return true;
    }
  }

  /**
   * mock model for the Reversi Model to be used for testing if the strategy correctly passes
   * when there are no available valid moves.
   */
  public static class MockModel3 implements ReversiModel {
    public StringBuilder transcript;
    private final int size;
    private final Board board;

    /**
     * the constructor for the mock model.
     *
     * @param size the size of the game board
     */
    public MockModel3(StringBuilder log, int size) {
      this.transcript = log;
      this.size = size;
      this.board = new Board(size);
    }

    @Override
    public boolean hasValidMove(PlayerColor color) {
      return true;
    }

    @Override
    public GameState getStatus() {
      return null;
    }

    @Override
    public Board getBoard() {
      return this.board;
    }

    @Override
    public Board defaultBoard(int size) {
      return null;
    }

    @Override
    public int getSizeOfBoard() {
      return this.size;
    }

    @Override
    public PlayerColor getContentsOfCell(int q, int r) {
      return this.board.getColorAt(q, r);
    }

    @Override
    public int getScore(PlayerColor color) {
      return 0;
    }

    @Override
    public boolean isGameOver() {
      return false;
    }

    @Override
    public boolean isValidCoordinate(int q, int r) {
      return true;
    }

    @Override
    public PlayerColor curPlayer() {
      return null;
    }

    @Override
    public int numTilesCaptured(int q, int r, PlayerColor color) {
      return 0;
    }


    @Override
    public void addFeatures(FeaturesModel f) {
      this.transcript.append("");
    }

    @Override
    public void startGame() {
      this.transcript.append("");
    }

    @Override
    public void checkGame() {
      this.transcript.append("");
    }

    @Override
    public void placeTile(PlayerColor color, int r, int q) throws IllegalArgumentException {
      this.transcript.append("");
    }

    @Override
    public void pass(PlayerColor color) {
      transcript.append("passed");
    }

    @Override
    public boolean isValidMove(int q, int r, PlayerColor color) {
      // no move should be valid
      return false;
    }
  }

  ReversiModel r1;
  ReversiModel r2;
  ReversiModel rActual;
  ReversiModel rActualBig;
  ReversiModel r3;
  CapturePieceStrategy s1;
  CapturePieceStrategy s2;
  CheckCornersStrategy s3;
  MultipleStrategies s4;
  CapturePieceStrategy sActual;
  CapturePieceStrategy s6Big;
  CapturePieceStrategy s7;
  StringBuilder log;

  @Before
  public void init() {
    log = new StringBuilder();
    r1 = new MockModel1(log, 7);
    r2 = new MockModel2(7);
    r3 = new MockModel3(log, 7);
    rActual = new ReversiModelStandard(7);
    rActualBig = new ReversiModelStandard(11);
    s1 = new CapturePieceStrategy(r1);
    s2 = new CapturePieceStrategy(r2);
    s3 = new CheckCornersStrategy(r1);
    s4 = new MultipleStrategies(new ArrayList<>(Arrays.asList(s1, s3)));
    sActual = new CapturePieceStrategy(rActual);
    s6Big = new CapturePieceStrategy(rActualBig);
    s7 = new CapturePieceStrategy(r3);
  }

  @Test
  public void testStrategyObservesCoordinates() {
    Assert.assertEquals("", log.toString());
    s1.chooseMove(PlayerColor.BLACK);
    Assert.assertEquals("-3, 0\n" +
            "-3, 1\n" +
            "-3, 2\n" +
            "-3, 3\n" +
            "-2, -1\n" +
            "-2, 0\n" +
            "-2, 1\n" +
            "-2, 2\n" +
            "-2, 3\n" +
            "-1, -2\n" +
            "-1, -1\n" +
            "-1, 2\n" +
            "-1, 3\n" +
            "0, -3\n" +
            "0, -2\n" +
            "0, 0\n" +
            "0, 2\n" +
            "0, 3\n" +
            "1, -3\n" +
            "1, -2\n" +
            "1, 1\n" +
            "1, 2\n" +
            "2, -3\n" +
            "2, -2\n" +
            "2, -1\n" +
            "2, 0\n" +
            "2, 1\n" +
            "3, -3\n" +
            "3, -2\n" +
            "3, -1\n" +
            "3, 0\n", log.toString());
    this.init();
    Assert.assertEquals("", log.toString());
    s1.chooseMove(PlayerColor.WHITE);
    Assert.assertEquals("-3, 0\n" +
            "-3, 1\n" +
            "-3, 2\n" +
            "-3, 3\n" +
            "-2, -1\n" +
            "-2, 0\n" +
            "-2, 1\n" +
            "-2, 2\n" +
            "-2, 3\n" +
            "-1, -2\n" +
            "-1, -1\n" +
            "-1, 2\n" +
            "-1, 3\n" +
            "0, -3\n" +
            "0, -2\n" +
            "0, 0\n" +
            "0, 2\n" +
            "0, 3\n" +
            "1, -3\n" +
            "1, -2\n" +
            "1, 1\n" +
            "1, 2\n" +
            "2, -3\n" +
            "2, -2\n" +
            "2, -1\n" +
            "2, 0\n" +
            "2, 1\n" +
            "3, -3\n" +
            "3, -2\n" +
            "3, -1\n" +
            "3, 0\n", log.toString());
    System.out.println(log.toString());
  }

  @Test
  public void testStrategyFindsRightCoordinate() {
    Optional<HexCoordinates> expectedCoordinates = Optional.of(new HexCoordinates(-2, 1));
    Optional<HexCoordinates> blackMove = s2.chooseMove(PlayerColor.BLACK);
    Optional<HexCoordinates> whiteMove = s2.chooseMove(PlayerColor.WHITE);
    Assert.assertEquals(expectedCoordinates, blackMove);
    Assert.assertEquals(expectedCoordinates, whiteMove);
  }

  @Test
  public void testStrategyCorners() {
    Assert.assertEquals("", log.toString());
    s3.chooseMove(PlayerColor.BLACK);
    Assert.assertEquals("-3, 0\n"
                    + "-3, 3\n"
                    + "0, -3\n"
                    + "0, 3\n"
                    + "3, -3\n"
                    + "3, 0\n",
            log.toString());
    this.init();
    Assert.assertEquals("", log.toString());
    s3.chooseMove(PlayerColor.WHITE);
    Assert.assertEquals("-3, 0\n"
                    + "-3, 3\n"
                    + "0, -3\n"
                    + "0, 3\n"
                    + "3, -3\n"
                    + "3, 0\n",
            log.toString());
  }

  @Test
  public void testExceptionsColorEmpty() {
    Assert.assertThrows(IllegalArgumentException.class, () -> s2.chooseMove(PlayerColor.EMPTY));
    Assert.assertThrows(IllegalArgumentException.class, () -> s3.chooseMove(PlayerColor.EMPTY));
    Assert.assertThrows(IllegalArgumentException.class, () -> s1.chooseMove(PlayerColor.EMPTY));
    Assert.assertThrows(IllegalArgumentException.class, () -> s4.chooseMove(PlayerColor.EMPTY));
  }

  @Test
  public void testMultipleStrategiesWorks() {
    Assert.assertEquals("", log.toString());
    s4.chooseMove(PlayerColor.BLACK);
    Assert.assertEquals("-3, 0\n" +
                    "-3, 1\n" +
                    "-3, 2\n" +
                    "-3, 3\n" +
                    "-2, -1\n" +
                    "-2, 0\n" +
                    "-2, 1\n" +
                    "-2, 2\n" +
                    "-2, 3\n" +
                    "-1, -2\n" +
                    "-1, -1\n" +
                    "-1, 2\n" +
                    "-1, 3\n" +
                    "0, -3\n" +
                    "0, -2\n" +
                    "0, 0\n" +
                    "0, 2\n" +
                    "0, 3\n" +
                    "1, -3\n" +
                    "1, -2\n" +
                    "1, 1\n" +
                    "1, 2\n" +
                    "2, -3\n" +
                    "2, -2\n" +
                    "2, -1\n" +
                    "2, 0\n" +
                    "2, 1\n" +
                    "3, -3\n" +
                    "3, -2\n" +
                    "3, -1\n" +
                    "3, 0\n",
            log.toString());
    this.init();
    Assert.assertEquals("", log.toString());
    s4.chooseMove(PlayerColor.WHITE);
    Assert.assertEquals("-3, 0\n" +
                    "-3, 1\n" +
                    "-3, 2\n" +
                    "-3, 3\n" +
                    "-2, -1\n" +
                    "-2, 0\n" +
                    "-2, 1\n" +
                    "-2, 2\n" +
                    "-2, 3\n" +
                    "-1, -2\n" +
                    "-1, -1\n" +
                    "-1, 2\n" +
                    "-1, 3\n" +
                    "0, -3\n" +
                    "0, -2\n" +
                    "0, 0\n" +
                    "0, 2\n" +
                    "0, 3\n" +
                    "1, -3\n" +
                    "1, -2\n" +
                    "1, 1\n" +
                    "1, 2\n" +
                    "2, -3\n" +
                    "2, -2\n" +
                    "2, -1\n" +
                    "2, 0\n" +
                    "2, 1\n" +
                    "3, -3\n" +
                    "3, -2\n" +
                    "3, -1\n" +
                    "3, 0\n",
            log.toString());
  }

  @Test
  public void testBreakTie() {
    Optional<HexCoordinates> optionalExpectedBlack = Optional.of(new HexCoordinates(1, -2));
    Assert.assertEquals(optionalExpectedBlack, sActual.chooseMove(PlayerColor.BLACK));
    Assert.assertEquals(optionalExpectedBlack, s6Big.chooseMove(PlayerColor.BLACK));

    Optional<HexCoordinates> optionalExpectedWhite = Optional.of(new HexCoordinates(1, -2));
    Assert.assertEquals(optionalExpectedWhite, sActual.chooseMove(PlayerColor.WHITE));
    Assert.assertEquals(optionalExpectedWhite, s6Big.chooseMove(PlayerColor.WHITE));
  }

  @Test
  public void testNoValidMoves() {
    Assert.assertEquals(Optional.empty(), s7.chooseMove(PlayerColor.BLACK));
    Assert.assertEquals(Optional.empty(), s7.chooseMove(PlayerColor.WHITE));
  }
}
