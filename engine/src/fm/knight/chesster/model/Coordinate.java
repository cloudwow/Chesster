package fm.knight.chesster.model;

public class Coordinate {
  private final int row;
  private final int column;

  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (obj == this) {
      return true;
    } else if (!(obj instanceof Coordinate)){
      return false;
    }
    Coordinate other = (Coordinate)obj;
    return this.row == other.row && this.column == other.column;
  }

  @Override
  public int hashCode()
  {
    return (row <<  16) +column;
  }
  @Override
  public String toString() {
    return "["+String.valueOf(row) +"."+ (char)('a'+column)+"]";
      }

}
