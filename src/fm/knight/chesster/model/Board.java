package fm.knight.chesster.model;

import java.util.Deque;
import com.google.common.collect.Queues;

public class Board {

  private final Piece[][] pieces;
  private final Deque<Move> moveStack = new Stacks.newStack();
  public Board() {
  }

  public void makeMove(Coordinate fromCoordinate, Coordinate toCoordinate) {
    Move result = new Move(fromCoordinate, toCoordinate, getPieceAt(toCoordinate));
    setPiece(toCoordinate,getPiece(fromCoordinate));
    setPiece(fromCoordinate,null);
    moveStack.push(move);
  }
}
