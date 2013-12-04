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
    assertEquals(Color.WHITE, target.getWhoseTurn());
  }
    
  @Test
  public void testSmoke_fen() {
    target = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");
    assertEquals(0, target.getMoveDepth());
    assertEquals(Color.BLACK, target.getWhoseTurn());
  }


    
  @Test
  public void testSmoke_fen_kiwipete() {
    target = new Board("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
    assertEquals(0, target.getMoveDepth());
    assertEquals(Color.WHITE, target.getWhoseTurn());
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
    String expected = "r n b q k b n r \n" + "p p p p p p p p \n"
      +". . . . . . . . \n" + ". . . . . . . . \n" + ". . . . . . . . \n"
      + ". . . . . . . . \n"  + "P P P P P P P P \n"+  "R N B Q K B N R \n" ;

    assertEquals(expected, target.toString());
  }

}
