package fm.knight.chesster.model;


import fm.knight.chesster.model.piece.Piece;
import com.google.common.collect.Lists;
import java.util.List;


public class BoardScorer {
  public int calculateScore(Board board, Color whoseTurn) {
    int result = 0;

    for (int row = 7; row >= 0; row--) {
      for (int column = 0; column < 8; column++) {
        Piece p = board.getPieceAt(row, column);

        if (p != null) {
          if (p.getColor() == whoseTurn) {
            result += p.getValueAt(row, column);
          } else {
            result -= p.getValueAt(row, column);
          }
        }
      }
    }
    return result;
  }
}
