package fm.knight.chesster.model;

import fm.knight.chesster.model.piece.Piece;
import com.google.common.base.Objects;

public class Move {

  private final Coordinate fromCoordinate;
  private final Coordinate toCoordinate;

  public Move(Coordinate fromCoordinate, Coordinate toCoordinate) {
    this.toCoordinate = toCoordinate;
    this.fromCoordinate = fromCoordinate;
  }

  public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
    this(new Coordinate(fromRow, fromColumn),new Coordinate(toRow, toColumn)); 
  }

  public Coordinate getFromCoordinate() {
    return fromCoordinate;
  }

  public Coordinate getToCoordinate() {
    return toCoordinate;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else if (!(obj instanceof Move)){
      return false;
    }
    Move otherMove = (Move)obj;
    return Objects.equal(this.toCoordinate,otherMove.toCoordinate) &&
      Objects.equal(this.fromCoordinate, otherMove.fromCoordinate);
  }

  @Override
  public int hashCode()
  {
    return Objects.hashCode(toCoordinate,fromCoordinate);
  }

  @Override
  public String toString() {
    return fromCoordinate.toString()+"->"+toCoordinate.toString();
  }
}
