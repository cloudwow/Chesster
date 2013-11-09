package fm.knight.chesster.model.piece;

import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Color;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.model.Move;

import java.util.List;


public class Queen extends Piece {
  public Queen(Color color) {
    super(color, "q");
  }
  @Override
  public int getValueAt(int row, int column) {
    return getValueAt(row, column, ScoreTables.QueenValue, ScoreTables.whiteQueenSquareTable, ScoreTables.blackQueenSquareTable);
  }
  @Override
  public void addMoves(Board board, int row, int column, List<Move> moveList) {
    add(board,row,column,+1,+1,moveList);
    add(board,row,column,+1,-1,moveList);
    add(board,row,column,-1,+1,moveList);
    add(board,row,column,-1,-1,moveList);
    add(board,row,column,+1,0,moveList);
    add(board,row,column,-1,0,moveList);
    add(board,row,column,0,+1,moveList);
    add(board,row,column,-0,-1,moveList);
  }


  public void add(Board board, int row, int column, int rowDelta, int columnDelta, List<Move> moveList) {
    int toRow=row;
    int toColumn=column;

    do {
      toRow+=rowDelta;
      toColumn+=columnDelta;

    } while(tryAddMove(board, row, column, toRow, toColumn, moveList));

  }

}
