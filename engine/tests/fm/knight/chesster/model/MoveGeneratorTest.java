package fm.knight.chesster.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MoveGeneratorTest {
  private Board board;
  private Perft perft;
  @Before
  public void setup() {
    board = new Board();
    perft = new Perft();
  }
  
  @Test
  public void testPerft1() {
    assertEquals(20, perft.countLeaves(board, 1));
  }

  @Test
  public void testPerft2() {
    assertEquals(400, perft.countLeaves(board, 2));
  }

  @Test
  public void testPerft3() {
    assertEquals(8902, perft.countLeaves(board, 3));
  }
  @Test
  public void testPerft4() {
    assertEquals(197281, perft.countLeaves(board, 4));
  }

  @Test
  public void testPerft_Pos3_1() {
    Board board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");

    assertEquals(14, perft.countLeaves(board,1));
  }
  @Test
  public void testPerft_Pos3_2() {
    Board board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");

    assertEquals(191, perft.countLeaves(board,2));
  }

  @Test
  public void testPerftKiwipete_2() {
    Board board = new Board("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");

    assertEquals(2039, perft.countLeaves(board,2));
  }
  

  @Test
  public void testPerftKiwipete_1() {
    Board board = new Board("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");

    assertEquals(48, perft.countLeaves(board,1));
  }
  

}
