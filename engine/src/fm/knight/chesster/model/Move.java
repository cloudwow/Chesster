package fm.knight.chesster.model;

import fm.knight.chesster.model.piece.Piece;

public class Move {

  private final Coordinate fromCoordinate;
  private final Coordinate toCoordinate;
  private final Piece takenPiece;
  public Move(Coordinate fromCoordinate, Coordinate toCoordinate, Piece takenPiece) {
    this.toCoordinate = toCoordinate;
    this.fromCoordinate = fromCoordinate;
    this.takenPiece = takenPiece;
  }

  public Coordinate getFromCoordinate() {
    return fromCoordinate;
  }

  public Coordinate getToCoordinate() {
    return toCoordinate;
  }

  public Piece getTakenPiece() {
    return takenPiece;
  }
}
