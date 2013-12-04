package fm.knight.chesster.model.piece;


import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Color;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.model.Move;

import java.util.List;


public class Rook extends Piece {
  public Rook(Color color) {
    super(color, "r");
  }

  @Override
  public int getValueAt(int row, int column) {
    return getValueAt(row, column, ScoreTables.RookValue, ScoreTables.whiteRookSquareTable,
        ScoreTables.blackRookSquareTable);
  }

  @Override
  public void addMoves(Board board, int row, int column, List<Move> moveList) {
    addRay(board, row, column, +1, 0, moveList);
    addRay(board, row, column, -1, 0, moveList);
    addRay(board, row, column, 0, +1, moveList);
    addRay(board, row, column, 0, -1, moveList);
  }

  public boolean isQueenOrRook() {
    return true;
  }

}
