package fm.knight.chesster.model.piece;


import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Color;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.model.Move;
import com.google.common.base.Objects;
import java.util.List;


public class Piece {

  protected final Color color;
  private final String code;

  public Piece(
      Color color,
      String code) {
    this.color = color;
    this.code = code;
  }

  public Color getColor() {
    return color;
  }

  public String getCode() {
    return color == Color.WHITE ? code.toUpperCase() : code;
  }

  public int getValueAt(
      int row,
      int column) {
    return 1;
  }

  public int getValueAt(
      int row,
      int column,
      int pieceValue,
      int[] whiteSquareTable,
      int[] blackSquareTable) {
    if (this.getColor() == Color.WHITE) {
      return whiteSquareTable[row * 8 + column] * pieceValue;
    } else {
      return blackSquareTable[row * 8 + column] * pieceValue;
    }
  }

  public void addMoves(
      Board board,
      int row,
      int column,
      List<Move> moveList) {
    tryAddMove(board, row, column, row + color.getDirection(), column, moveList);
  }

  protected boolean tryAddMove(
      Board board,
      int fromRow,
      int fromColumn,
      int toRow,
      int toColumn,
      List<Move> moveList) {
    if (toRow > 7 || toRow < 0 || toColumn > 7 || toColumn < 0) {
      return false;
    }
    if (fromRow == toRow && fromColumn == toColumn) {
      return false;
    }
    Piece targetPiece = board.getPieceAt(toRow, toColumn);

    if (targetPiece == null || targetPiece.getColor() != this.getColor()) {
      moveList.add(new Move(fromRow, fromColumn, toRow, toColumn));
      return true;
    }
    return false;
  }

  public void addRay(
      Board board,
      int row,
      int column,
      int rowDelta,
      int columnDelta,
      List<Move> moveList) {
    int toRow = row;
    int toColumn = column;

    while (true) {
      toRow += rowDelta;
      toColumn += columnDelta;
      if (toRow > 7 || toRow < 0 || toColumn > 7 || toColumn < 0) {
        break;
      }
      Color targetColor = board.getColorAt(toRow, toColumn);

      if (this.getColor() != targetColor) {
        moveList.add(new Move(row, column, toRow, toColumn));
      }

      if (targetColor != null) {
        break;
      }

    }
  }

  public boolean isKing() {
    return false;
  }

  public boolean isPawn() {
    return false;
  }

  public boolean isQueenOrBishop() {
    return false;
  }

  public boolean isQueenOrRook() {
    return false;
  }

  public boolean isKnightOf(
      Color color) {
    return false;
  }

  public boolean isPawnOf(
      Color color) {
    return false;
  }

  @Override
  public boolean equals(
      Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else if (!(obj instanceof Piece)) {
      return false;
    }
    Piece other = (Piece) obj;

    return Objects.equal(this.color, other.color) && Objects.equal(this.code, other.code);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(color, code);
  }

}
