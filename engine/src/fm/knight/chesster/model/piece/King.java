package fm.knight.chesster.model.piece;


import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Color;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.model.Move;

import java.util.List;


public class King extends Piece {
  public King(Color color) {
    super(color, "k");
  }

  @Override
  public void addMoves(Board board, int row, int column, List<Move> moveList) {
    for (int toRow = row - 1; toRow <= row + 1; toRow++) {
      for (int toColumn = column - 1; toColumn <= column + 1; toColumn++) {
        tryAddMove(board, row, column, toRow, toColumn, moveList);
      }
    }
  }

  @Override
  public int getValueAt(int row, int column) {
    return getValueAt(row, column, ScoreTables.KingValue, ScoreTables.whiteKingMiddleGameSquareTable,
        ScoreTables.blackKingMiddleGameSquareTable);
  }

  public boolean isKing() {
    return true;
  }

}
