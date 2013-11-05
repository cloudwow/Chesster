package fm.knight.chesster.model;

public class Move {

 private final Position fromPosition;
 private final Position toPosition;
	
    public Move(Position fromPosition, Position toPosition) {
	this.toPosition = toPosition;
	this.fromPosition = fromPosition;	
  }

    public Position getFromPosition() {
	return fromPosition;
    }

    public Position getToPosition() {
	return toPosition;
    }
}
