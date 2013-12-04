package fm.knight.chesster.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MoveGeneratorTest {
  private MoveGenerator target;
  private Perft perft;
  @Before
  public void setup() {
    target = new MoveGenerator();
    perft = new Perft();
  }

  @Test
  public void testPerft1() {
    assertEquals(20, perft.countLeaves(1));
  }

  @Test
  public void testPerft2() {
    assertEquals(400, perft.countLeaves(2));
  }

  @Test
  public void testPerft3() {
    assertEquals(8902, perft.countLeaves(3));
  }
  @Test
  public void testPerft4() {
    assertEquals(197281, perft.countLeaves(4));
  }

}
