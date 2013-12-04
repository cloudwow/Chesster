package fm.knight.chesster.model.piece;


import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Color;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.model.Move;

import java.util.List;


public class Knight extends Piece {
  public Knight(Color color) {
    super(color, "n");
  }
  public boolean isKnightOf(Color color) {
    return color == getColor();
  }

  @Override
  public int getValueAt(int row, int column) {
    return getValueAt(row, column, ScoreTables.KnightValue, ScoreTables.whiteKnightSquareTable,
        ScoreTables.blackKnightSquareTable);
  }

  @Override
  public void addMoves(Board board, int row, int column, List<Move> moveList) {
    tryAddMove(board, row, column, row + 1, column + 2, moveList);
    tryAddMove(board, row, column, row + 2, column + 1, moveList);
    tryAddMove(board, row, column, row + 1, column - 2, moveList);
    tryAddMove(board, row, column, row + 2, column - 1, moveList);
    tryAddMove(board, row, column, row - 1, column + 2, moveList);
    tryAddMove(board, row, column, row - 2, column + 1, moveList);
    tryAddMove(board, row, column, row - 1, column - 2, moveList);
    tryAddMove(board, row, column, row - 2, column - 1, moveList);
  }
}
