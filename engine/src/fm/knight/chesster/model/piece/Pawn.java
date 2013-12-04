package fm.knight.chesster.model.piece;


import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Color;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.model.Move;

import java.util.List;


public class Pawn extends Piece {

  public Pawn(
      Color color) {
    super(color, "p");
  }

  @Override
  public int getValueAt(
      int row,
      int column) {
    return getValueAt(row, column, ScoreTables.PawnValue, ScoreTables.whitePawnSquareTable
        ,
        ScoreTables.blackPawnSquareTable);
  }

  public boolean isPawn() {
    return true;
  }
  public boolean isPawnOf(Color color) {
    return color == getColor();
  }

  @Override
  public void addMoves(
      Board board,
      int row,
      int column,
      List<Move> moveList) {
    int nextRow = row + getColor().getDirection();

    if (nextRow > 7 || nextRow < 0) {
      return;
    }

    if (board.isEmptyAt(nextRow, column)) {
      moveList.add(new Move(row, column, nextRow, column));

      if (color == Color.WHITE && row == 1 && board.isEmptyAt(3, column)) {
        moveList.add(new Move(row, column, 3, column));
      }
      if (color == Color.BLACK && row == 6 && board.isEmptyAt(4, column)) {
        moveList.add(new Move(row, column, 4, column));
      }
    }

    if (column < 7 && board.hasColorAt(nextRow, column + 1, getColor().flip())) {
      moveList.add(new Move(row, column, nextRow, column + 1));
    }

    if (column > 0 && board.hasColorAt(nextRow, column - 1, getColor().flip())) {
      moveList.add(new Move(row, column, nextRow, column - 1));
    }
  }
}
