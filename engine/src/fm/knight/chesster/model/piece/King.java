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
    Color otherColor = getColor().flip();
    for (int toRow = row - 1; toRow <= row + 1; toRow++) {
      for (int toColumn = column - 1; toColumn <= column + 1; toColumn++) {
        tryAddMove(board, row, column, toRow, toColumn, moveList);
      }
    }
    if (board.getCastlingState().canCastleKingSide(getColor()) && board.isEmptyAt(row,5) && board.isEmptyAt(row,6)
        && !board.isAttackedBy(new Coordinate(row, 4), otherColor)
        && !board.isAttackedBy(new Coordinate(row, 5), otherColor)
        && !board.isAttackedBy(new Coordinate(row, 6), otherColor)){
      moveList.add(new Move(row,column,row, 6));
    }
    if (board.getCastlingState().canCastleQueenSide(getColor()) && board.isEmptyAt(row,3) && board.isEmptyAt(row,2)&& board.isEmptyAt(row,1)
        && !board.isAttackedBy(new Coordinate(row, 4), otherColor)
        && !board.isAttackedBy(new Coordinate(row, 3), otherColor)
        && !board.isAttackedBy(new Coordinate(row, 3), otherColor)
        ) {
      moveList.add(new Move(row,column,row, 2));
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
