package fm.knight.chesster.model;


import fm.knight.chesster.model.piece.Piece;
import com.google.common.base.Objects;


public class MoveRecord {

  private final Move move;
  private final Piece takenPiece;
  private final Coordinate previousEnPassantAttackCoordinate;
  private final Coordinate previousEnPassantTakeCoordinate;
  private final CastlingState previousCastlingState;


  public MoveRecord(Move move, Piece takenPiece, Coordinate previousEnPassantAttackCoordinate, Coordinate previousEnPassantTakeCoordinate, CastlingState previousCastlingState) {
    this.move = move;
    this.takenPiece = takenPiece;
    this.previousEnPassantAttackCoordinate = previousEnPassantAttackCoordinate;
    this.previousEnPassantTakeCoordinate = previousEnPassantTakeCoordinate;
    this.previousCastlingState = previousCastlingState;
  }

  public Move getMove() {
    return move;
  }

  public Coordinate getFromCoordinate() {
    return move.getFromCoordinate();
  }

  public Coordinate getToCoordinate() {
    return move.getToCoordinate();
  }

  public Piece getTakenPiece() {
    return takenPiece;
  }

  public Coordinate getPreviousEnPassantAttackCoordinate() {
    return previousEnPassantAttackCoordinate;
  }

  public Coordinate getPreviousEnPassantTakeCoordinate() {
    return previousEnPassantTakeCoordinate;
  }

  public CastlingState getPreviousCastlingState() {
    return previousCastlingState;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else if (!(obj instanceof MoveRecord)) {
      return false;
    }
    MoveRecord otherMoveRecord = (MoveRecord) obj;

    return Objects.equal(this.move, otherMoveRecord.move)
      && Objects.equal(this.takenPiece, otherMoveRecord.takenPiece);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(move, takenPiece);
  }

}
