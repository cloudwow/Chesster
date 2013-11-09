package fm.knight.chesster.model;

import fm.knight.chesster.model.piece.Piece;
import fm.knight.chesster.model.piece.King;
import fm.knight.chesster.model.piece.Queen;
import fm.knight.chesster.model.piece.Pawn;
import fm.knight.chesster.model.piece.Rook;
import fm.knight.chesster.model.piece.Bishop;
import fm.knight.chesster.model.piece.Knight;

import com.google.common.collect.Queues;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.Deque;

public class Board {

  private final Piece[][] pieces = new Piece[8][8];
  private final Deque<MoveRecord> moveStack = Queues.newArrayDeque();

  public Board() {
    pieces[0][0]=new Rook(Color.WHITE);
    pieces[0][1]=new Knight(Color.WHITE);
    pieces[0][2]=new Bishop(Color.WHITE);
    pieces[0][3]=new Queen(Color.WHITE);
    pieces[0][4]=new King(Color.WHITE);
    pieces[0][5]=new Bishop(Color.WHITE);
    pieces[0][6]=new Knight(Color.WHITE);
    pieces[0][7]=new Rook(Color.WHITE);
    pieces[1][0]=new Pawn(Color.WHITE);
    pieces[1][1]=new Pawn(Color.WHITE);
    pieces[1][2]=new Pawn(Color.WHITE);
    pieces[1][3]=new Pawn(Color.WHITE);
    pieces[1][4]=new Pawn(Color.WHITE);
    pieces[1][5]=new Pawn(Color.WHITE);
    pieces[1][6]=new Pawn(Color.WHITE);
    pieces[1][7]=new Pawn(Color.WHITE);


    pieces[6][0]=new Pawn(Color.BLACK);
    pieces[6][1]=new Pawn(Color.BLACK);
    pieces[6][2]=new Pawn(Color.BLACK);
    pieces[6][3]=new Pawn(Color.BLACK);
    pieces[6][4]=new Pawn(Color.BLACK);
    pieces[6][5]=new Pawn(Color.BLACK);
    pieces[6][6]=new Pawn(Color.BLACK);
    pieces[6][7]=new Pawn(Color.BLACK);

    pieces[7][0]=new Rook(Color.BLACK);
    pieces[7][1]=new Knight(Color.BLACK);
    pieces[7][2]=new Bishop(Color.BLACK);
    pieces[7][3]=new Queen(Color.BLACK);
    pieces[7][4]=new King(Color.BLACK);
    pieces[7][5]=new Bishop(Color.BLACK);
    pieces[7][6]=new Knight(Color.BLACK);
    pieces[7][7]=new Rook(Color.BLACK);

  }

  public MoveRecord makeMove(Move move) {
    MoveRecord result = new MoveRecord(move, getPieceAt(move.getToCoordinate()));
    setPieceAt(move.getToCoordinate(),getPieceAt(move.getFromCoordinate()));
    setPieceAt(move.getFromCoordinate(),null);
    moveStack.push(result);
    return result;
  }

  public MoveRecord undo() {
    Preconditions.checkState(moveStack.size() > 0);
    MoveRecord result = moveStack.pop();
    setPieceAt(result.getFromCoordinate(), getPieceAt(result.getToCoordinate()));
    setPieceAt(result.getToCoordinate(), result.getTakenPiece());
    return result;
  }

  public int getMoveDepth() {
    return moveStack.size();
  }

  public Piece getPieceAt(Coordinate c) {
    return pieces[c.getRow()][c.getColumn()];
  }

  public boolean isEmptyAt(int row, int column) {
    return pieces[row][column] == null;
  }

  public boolean hasColorAt(int row, int column, Color color) {
    return pieces[row][column] != null && pieces[row][column].getColor()==color;
  }

  public Piece getPieceAt(int row, int column) {
    return pieces[row][column];
  }

  void setPieceAt(Coordinate c, Piece p) {
    pieces[c.getRow()][c.getColumn()]=p;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else if (!(obj instanceof Board)) {
      return false;
    }
    Board other = (Board)obj;
    return Arrays.deepEquals(this.pieces, other.pieces) &&
      Arrays.deepEquals(this.moveStack.toArray(), other.moveStack.toArray()) ;
  }

  @Override
  public int hashCode()
  {
    return Arrays.deepHashCode(pieces)+Arrays.deepHashCode(moveStack.toArray());
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for(int y=7;y>=0;y--) {
      for(int x=0;x<8;x++) {
        Piece p = pieces[y][x];
        if (p==null) {
          result.append("..");
        } else {
          switch(p.getColor()) {
          case BLACK:
            result.append("b");
            break;
          case WHITE:
            result.append("w");
          }

          result.append(p.getCode());
        }
        result.append(" ");

      }
      result.append("\n");
    }
    if(moveStack.size() > 0) {
      result.append(moveStack.peekFirst().getMove().toString());
    }

    return result.toString();
  }
}
