package fm.knight.chesster.model.piece;


import fm.knight.chesster.model.Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PieceTest {

  private Piece target;

  @Before
  public void setup() {
    target = new King(Color.BLACK);
  }

  @Test
  public void testEquals() {
    Piece other = new King(Color.BLACK);

    assertEquals(other, target);
    assertEquals(other.hashCode(), target.hashCode());
    Piece otherNot = new King(Color.WHITE);

    assertFalse(otherNot.equals(target));
    assertFalse(otherNot.hashCode() == target.hashCode());
  }
  
}
