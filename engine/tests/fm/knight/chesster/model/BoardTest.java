package fm.knight.chesster.model;


import fm.knight.chesster.model.piece.Queen;
import fm.knight.chesster.model.piece.Piece;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BoardTest {

  private Board target;

  @Before
  public void setup() {
    target = new Board();
  }

  @Test
  public void testSmoke() {
    assertEquals(0, target.getMoveDepth());
  }

  @Test
  public void testMakeMove() {
    Move move = new Move(new Coordinate(0, 3), new Coordinate(0, 4));
    MoveRecord result = target.makeMove(move);

    assertEquals(move.getFromCoordinate(), result.getFromCoordinate());
    assertEquals(move.getToCoordinate(), result.getToCoordinate());
    assertEquals(1, target.getMoveDepth());
  }

  @Test
  public void testMakeMove_takePiece() {
    Move move = new Move(new Coordinate(0, 3), new Coordinate(7, 3));
    MoveRecord result = target.makeMove(move);

    assertEquals(move.getFromCoordinate(), result.getFromCoordinate());
    assertEquals(move.getToCoordinate(), result.getToCoordinate());
    assertEquals(new Queen(Color.BLACK), result.getTakenPiece());
    assertEquals(1, target.getMoveDepth());
  }

  @Test
  public void testUndo() {
    Move move = new Move(new Coordinate(0, 3), new Coordinate(0, 4));

    target.makeMove(move);
    target.undo();
    assertEquals(new Board(), target);
  }

  @Test
  public void testUndo_takenPiece() {
    Move move = new Move(new Coordinate(0, 3), new Coordinate(7, 3));

    target.makeMove(move);
    target.undo();
    assertEquals(new Board(), target);
  }

  @Test
  public void testGetPieceAt() {
    assertEquals(new Queen(Color.WHITE), target.getPieceAt(new Coordinate(0, 3)));
  }

  @Test
  public void testEquals() {
    Board other = new Board();

    assertEquals(other, target);
    assertEquals(other.hashCode(), target.hashCode());
    Move move = new Move(new Coordinate(0, 3), new Coordinate(0, 4));

    target.makeMove(move);
    Move otherMove = new Move(new Coordinate(0, 3), new Coordinate(0, 4));

    other.makeMove(otherMove);
    assertEquals(other, target);
    assertEquals(other.hashCode(), target.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "R N B Q K B N R \n" + "P P P P P P P P \n"
        + ". . . . . . . . \n" + ". . . . . . . . \n" + ". . . . . . . . \n"
        + ". . . . . . . . \n" + "p p p p p p p p \n" + "r n b q k b n r \n";

    assertEquals(expected, target.toString());
  }

}
