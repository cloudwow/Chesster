package fm.knight.chesster.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SearcherTest {

  private Searcher target;
  private Board board;

  @Before
  public void setup() {
    target = new Searcher(new MoveGenerator(), new BoardScorer(), 3);
    board = new Board();
  }

  @Test
  public void testSmoke() {
    Color color = Color.WHITE;

    for (int i = 0; i < 4; i++) {
      Move result = target.findBestMove(board, color);

      assertEquals(i, board.getMoveDepth());
      assertNotNull(result);
      board.makeMove(result);
      System.out.println(board.toString());
      System.out.println("---------------------------------------------");
      color = color.flip();
    }
  }
}
