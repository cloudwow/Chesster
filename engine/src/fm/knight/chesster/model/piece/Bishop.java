package fm.knight.chesster.model.piece;


import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Color;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.model.Move;

import java.util.List;


public class Bishop extends Piece {
  public Bishop(
      Color color) {
    super(color, "b");
  }

  @Override
  public void addMoves(
      Board board,
      int row,
      int column,
      List<Move> moveList) {
    addRay(board, row, column, +1, +1, moveList);
    addRay(board, row, column, +1, -1, moveList);
    addRay(board, row, column, -1, +1, moveList);
    addRay(board, row, column, -1, -1, moveList);
  }


  @Override
  public int getValueAt(
      int row,
      int column) {
    return getValueAt(row, column, ScoreTables.BishopValue, ScoreTables.whiteBishopSquareTable
        ,
        ScoreTables.blackBishopSquareTable);
  }

  public boolean isQueenOrBishop() {
    return true;
  }

}
