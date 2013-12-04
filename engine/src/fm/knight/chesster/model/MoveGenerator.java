package fm.knight.chesster.model;


import fm.knight.chesster.model.piece.Piece;
import com.google.common.collect.Lists;
import java.util.List;


public class MoveGenerator {
  List<Move> generateMoves(
      Board board,
      Color whoseTurn) {
    List<Move> pseudoLegalMoves = Lists.newArrayList();

    for (int row = 7; row >= 0; row--) {
      for (int column = 0; column < 8; column++) {
        Piece p = board.getPieceAt(row, column);

        if (p != null && p.getColor() == whoseTurn) {
          p.addMoves(board, row, column, pseudoLegalMoves);
        }
      }
    }
    List<Move> result = Lists.newArrayList();

    for (Move m : pseudoLegalMoves) {
      board.makeMove(m);
      if (!board.isInCheck(whoseTurn)) {
        result.add(m);
      }
      board.undo();
    }
    return result;
  }
}
