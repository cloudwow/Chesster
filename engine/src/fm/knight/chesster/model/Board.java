package fm.knight.chesster.model;

import fm.knight.chesster.model.piece.Piece;
import java.util.Deque;
import com.google.common.collect.Queues;
import com.google.common.base.Preconditions;

public class Board {

  private final Piece[][] pieces = new Piece[8][8];
  private final Deque<Move> moveStack = Queues.newArrayDeque();
  public Board() {
  }

  public Move makeMove(Coordinate fromCoordinate, Coordinate toCoordinate) {
    Move result = new Move(fromCoordinate, toCoordinate, getPieceAt(toCoordinate));
    setPieceAt(toCoordinate,getPieceAt(fromCoordinate));
    setPieceAt(fromCoordinate,null);
    moveStack.push(result);
    return result;
  }

  public Move undo() {
    Preconditions.checkState(moveStack.size() > 0);
    Move result = moveStack.pop();
    setPieceAt(result.getFromCoordinate(), getPieceAt(result.getToCoordinate()));
    setPieceAt(result.getToCoordinate(), result.getTakenPiece());
    return result;
  }

  public int getMoveDepth() {
    return moveStack.size();
  }

  Piece getPieceAt(Coordinate c) {
    return pieces[c.getX()][c.getY()];
  }
 
  void setPieceAt(Coordinate c, Piece p) {
    pieces[c.getX()][c.getY()]=p;
  }
}
