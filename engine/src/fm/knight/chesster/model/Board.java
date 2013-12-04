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
  private Coordinate blackKingCoordinate;
  private Coordinate whiteKingCoordinate;

  private Coordinate enPassantAttackCoordinate;
  private Coordinate enPassantTakeCoordinate;

  private boolean blackCanCastleKingSide;
  private boolean blackCanCastleQueenSide;
  private boolean whiteCanCastleKingSide;
  private boolean whiteCanCastleQueenSide;

  private Color whoseTurn;
  private CastlingState castlingState;
  public Board(
               String fen) {

    String[] fields = fen.split(" ");
    String piecesFen=fields[0];
    String colorFen=fields[1];
    String castlingFen = fields[2];
    String enPassantTargetFen = fields[3];

    String halfMoveClockFen = (fields.length>4) ? fields[4] : "0";
    String fullMoveNumberFen = (fields.length>5) ? fields[5] : "1";

    int row = 7;

    for (String rowFen : piecesFen.split("/")) {
      int column = 0;

      for (char c : rowFen.toCharArray()) {
        if (Character.isDigit(c)) {
          column += Character.getNumericValue(c);
        } else {
          Piece piece = null;

          switch (c) {
          case 'p':
            piece = new Pawn(Color.BLACK);
            break;

          case 'P':
            piece = new Pawn(Color.WHITE);
            break;

          case 'r':
            piece = new Rook(Color.BLACK);
            break;

          case 'R':
            piece = new Rook(Color.WHITE);
            break;
          case 'b':
            piece = new Bishop(Color.BLACK);
            break;

          case 'B':
            piece = new Bishop(Color.WHITE);
            break;
          case 'n':
            piece = new Knight(Color.BLACK);
            break;

          case 'N':
            piece = new Knight(Color.WHITE);
            break;
          case 'q':
            piece = new Queen(Color.BLACK);
            break;

          case 'Q':
            piece = new Queen(Color.WHITE);
            break;
          case 'k':
            piece = new King(Color.BLACK);
            blackKingCoordinate = new Coordinate(row, column);
            break;

          case 'K':
            piece = new King(Color.WHITE);
            whiteKingCoordinate = new Coordinate(row, column);
            break;
          }
          pieces[row][column] = piece;
          column++;
        }
      }
      row--;
    }
    whoseTurn = colorFen.equals("w") ? Color.WHITE : Color.BLACK;

    castlingState = new CastlingState(castlingFen);
  }

  public Board() {
    this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    blackKingCoordinate = new Coordinate(7, 4);
    whiteKingCoordinate = new Coordinate(0, 4);

  }

  public MoveRecord makeMove(
                             Move move) {

    final Piece takenPiece;

    if (move.getToCoordinate().equals(enPassantAttackCoordinate)) {
      takenPiece = getPieceAt(enPassantTakeCoordinate);
    } else {
      takenPiece = getPieceAt(move.getToCoordinate());
    }

    Piece movingPiece = getPieceAt(move.getFromCoordinate());

    setPieceAt(move.getToCoordinate(), movingPiece);
    setPieceAt(move.getFromCoordinate(), null);
    if (movingPiece.isKing()) {
      if(move.getFromCoordinate().getColumn()-move.getToCoordinate().getColumn()>1) {
        // kingside castle
        if (move.getFromCoordinate().getColumn() != 4) {
          throw new RuntimeException("WTF castling from odd coordinate");
        }
        //move the rook
        setPieceAt(move.getToCoordinate().add(0,-1), getPieceAt(move.getFromCoordinate().getRow(), 7));
        setPieceAt(move.getFromCoordinate().getRow(), 7, null);

      }

      if(move.getFromCoordinate().getColumn()-move.getToCoordinate().getColumn()<-1) {
        // queenside castle
        if (move.getFromCoordinate().getColumn() != 4) {
          throw new RuntimeException("WTF castling from odd coordinate");
        }
        // move the rook
        setPieceAt(move.getToCoordinate().add(0,1), getPieceAt(move.getFromCoordinate().getRow(), 0));
        setPieceAt(move.getFromCoordinate().getRow(), 0, null);

      }
    }

    enPassantAttackCoordinate = null;
    enPassantTakeCoordinate = null;
    if (movingPiece.isPawn()) {
      int distance = move.getFromCoordinate().getRow() - move.getToCoordinate().getRow();

      if (Math.abs(distance) == 2) {
        // if pawn moved 2 then it can be enpassanted
        enPassantAttackCoordinate = move.getFromCoordinate().add(distance / 2, 0);
        enPassantTakeCoordinate = move.getToCoordinate();
      }

    }
    CastlingState newCastlingState = castlingState;
    if(movingPiece.isKing()) {
      if(movingPiece.getColor()==Color.BLACK) {
        newCastlingState = castlingState.withoutBlack();
      } else {
        newCastlingState = castlingState.withoutWhite();
      }
    }

    MoveRecord result = new MoveRecord(move, takenPiece, enPassantAttackCoordinate
        ,
        enPassantTakeCoordinate, castlingState);

    castlingState = newCastlingState;
    if (movingPiece.isKing()) {
      switch (movingPiece.getColor()) {
      case WHITE:
        whiteKingCoordinate = move.getToCoordinate();
        break;

      case BLACK:
        blackKingCoordinate = move.getToCoordinate();
        break;
      }
    }
    moveStack.push(result);
    whoseTurn = whoseTurn.flip();
    return result;
  }

  public MoveRecord undo() {
    Preconditions.checkState(moveStack.size() > 0);
    MoveRecord result = moveStack.pop();

    enPassantAttackCoordinate = result.getPreviousEnPassantAttackCoordinate();
    enPassantTakeCoordinate = result.getPreviousEnPassantTakeCoordinate();
    castlingState = result.getPreviousCastlingState();
    Piece movingPiece = getPieceAt(result.getToCoordinate());

    setPieceAt(result.getFromCoordinate(), movingPiece);
    if (result.getToCoordinate().equals(enPassantAttackCoordinate)) {
      setPieceAt(enPassantTakeCoordinate, result.getTakenPiece());
    } else {
      setPieceAt(result.getToCoordinate(), result.getTakenPiece());
    }

    if (movingPiece.isKing()) {
      switch (movingPiece.getColor()) {
      case WHITE:
        whiteKingCoordinate = result.getFromCoordinate();
        break;

      case BLACK:
        blackKingCoordinate = result.getFromCoordinate();
        break;
      }
    }
    whoseTurn = whoseTurn.flip();
    return result;
  }

  public Color getWhoseTurn() {
    return whoseTurn;
  }
  public int getMoveDepth() {
    return moveStack.size();
  }

  public CastlingState getCastlingState() {
    return castlingState;
  }

  public Piece getPieceAt(
                          Coordinate c) {
    if (c.isOnBoard()) {
      return pieces[c.getRow()][c.getColumn()];
    } else {
      return null;
    }
  }

  public boolean isEmptyAt(
                           int row,
                           int column) {
    return pieces[row][column] == null;
  }

  public boolean hasColorAt(
                            int row,
                            int column,
                            Color color) {
    return pieces[row][column] != null && pieces[row][column].getColor() == color;
  }

  public Color getColorAt(
                          int row,
                          int column) {
    return pieces[row][column] == null ? null : pieces[row][column].getColor();
  }

  public Piece getPieceAt(
                          int row,
                          int column) {
    if (row > 7 || row < 0 || column > 7 || column < 0) {
      return null;
    }

    return pieces[row][column];
  }

  void setPieceAt(
                  Coordinate c,
                  Piece p) {
    pieces[c.getRow()][c.getColumn()] = p;
  }

  void setPieceAt(
                  int row, int column,
                  Piece p) {
    pieces[row][column] = p;
  }

  public boolean isInCheck(
                           Color color) {

    final Coordinate kingCoordinate;

    ;
    switch (color) {
    case WHITE:
      kingCoordinate = whiteKingCoordinate;
      break;

    case BLACK:
      kingCoordinate = blackKingCoordinate;
      break;

    default:
      throw new RuntimeException("wtf");
    }
    return isAttackedBy(kingCoordinate, color.flip());
  }
  public boolean isAttackedBy(Coordinate coordinate, Color attackerColor) {

    if (
        findDiagonalAttacker(coordinate, 1, 1, attackerColor)
        || findDiagonalAttacker(coordinate, 1, -1, attackerColor)
        || findDiagonalAttacker(coordinate, -1, 1, attackerColor)
        || findDiagonalAttacker(coordinate, -1, -1, attackerColor)
        || findFileAttacker(coordinate, 1, 0, attackerColor)
        || findFileAttacker(coordinate, 1, 0, attackerColor)
        || findFileAttacker(coordinate, 0, 1, attackerColor)
        || findFileAttacker(coordinate, 0, -1, attackerColor)
        || hasKnightOf(coordinate.add(1, 2), attackerColor)
        || hasKnightOf(coordinate.add(1, -2), attackerColor)
        || hasKnightOf(coordinate.add(-1, 2), attackerColor)
        || hasKnightOf(coordinate.add(-1, -2), attackerColor)
        || hasKnightOf(coordinate.add(2, 1), attackerColor)
        || hasKnightOf(coordinate.add(2, -1), attackerColor)
        || hasKnightOf(coordinate.add(-2, 1), attackerColor)
        || hasKnightOf(coordinate.add(-2, -1), attackerColor)
        || hasPawnOf(coordinate.add(attackerColor.getDirection()*-1, -1), attackerColor)
        || hasPawnOf(coordinate.add(attackerColor.getDirection()*-1, 1), attackerColor)) {

      //            System.out.println(this.toString() + "\n\n");

      return true;
    }
    return false;

  }

  boolean hasKnightOf(
                      Coordinate coordinate,
                      Color color) {
    Piece piece = getPieceAt(coordinate);

    return piece != null && piece.isKnightOf(color);
  }
  boolean hasPawnOf(
                    Coordinate coordinate,
                    Color color) {
    Piece piece = getPieceAt(coordinate);

    return piece != null && piece.isPawnOf(color);
  }

  boolean findDiagonalAttacker(
                               Coordinate attackedCoordinate,
                               int rowDelta,
                               int columnDelta,
                               Color attackerColor) {

    int attackerRow = attackedCoordinate.getRow();
    int attackerColumn = attackedCoordinate.getColumn();

    while (true) {
      attackerRow += rowDelta;
      attackerColumn += columnDelta;
      if (attackerRow > 7 || attackerRow < 0 || attackerColumn > 7 || attackerColumn < 0) {
        return false;
      }
      Piece attackerPiece = getPieceAt(attackerRow, attackerColumn);

      if (attackerPiece != null) {
        if (attackerPiece.getColor() != attackerColor) {
          return false;
        }
        if (attackerPiece.isQueenOrBishop()) {

          /*
            System.out.println(
            "" + attackerColor + " king at " + attackedCoordinate + " is being attacked by a "
            + attackerPiece.getClass().getSimpleName() + " at " + attackerRow + "," + attackerColumn);
            System.out.println(this.toString() + "\n\n");
          */
          return true;
        } else {
          return false;
        }
      }
    }
  }

  boolean findFileAttacker(
                           Coordinate attackedCoordinate,
                           int rowDelta,
                           int columnDelta,
                           Color attackerColor) {

    Coordinate attackerCoordinate = attackedCoordinate.add(0,0);

    while (true) {
      attackerCoordinate = attackerCoordinate.add(rowDelta, columnDelta);

      if (!attackerCoordinate.isOnBoard()){
        return false;
      }
      Piece attackerPiece = getPieceAt(attackerCoordinate);

      if (attackerPiece != null) {
        if (attackerPiece.getColor() != attackerColor) {
          return false;
        }
        if (attackerPiece.isQueenOrRook()) {
          /*
            System.out.println(
            "" + attackerColor.flip() + " king at " + attackedCoordinate + " is being attacked by a "
            + attackerPiece.getClass().getSimpleName() + " at " + attackerCoordinate.toString());
            System.out.println(this.toString() + "\n\n");
          */
          return true;
        } else {
          return false;
        }

      }
    }
  }

  @Override
  public boolean equals(
                        Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else if (!(obj instanceof Board)) {
      return false;
    }
    Board other = (Board) obj;

    return Arrays.deepEquals(this.pieces, other.pieces)
      && Arrays.deepEquals(this.moveStack.toArray(), other.moveStack.toArray());
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(pieces) + Arrays.deepHashCode(moveStack.toArray());
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (int y = 7; y >= 0; y--) {
      for (int x = 0; x < 8; x++) {
        Piece p = pieces[y][x];

        if (p == null) {
          result.append(".");
        } else {
          result.append(p.getCode());
        }
        result.append(" ");
      }
      result.append("\n");
    }
    if (moveStack.size() > 0) {
      result.append(moveStack.peekFirst().getMove().toString());
    }

    return result.toString();
  }
}
