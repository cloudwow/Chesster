package fm.knight.chesster.model;


import fm.knight.chesster.model.piece.Piece;
import fm.knight.chesster.model.piece.King;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MoveRecordTest {

  private static final Move MOVE = new Move(new Coordinate(1, 1), new Coordinate(3, 3));
  private static final Piece BLACK_KING = new King(Color.BLACK);
  private MoveRecord target;

  @Before
  public void setup() {
    target = new MoveRecord(MOVE, BLACK_KING);
  }

  @Test
  public void testEquals() {
    Move otherMove = new Move(new Coordinate(1, 1), new Coordinate(3, 3));
    Piece otherPiece = new King(Color.BLACK);
    MoveRecord other = new MoveRecord(otherMove, otherPiece);

    assertEquals(other, target);
  }

  @Test
  public void testEquals_not() {
    Move otherMove = new Move(new Coordinate(1, 1), new Coordinate(3, 3));
    Piece otherPiece = new King(Color.WHITE);
    MoveRecord other = new MoveRecord(otherMove, otherPiece);

    assertFalse(other.equals(target));
  }

}
