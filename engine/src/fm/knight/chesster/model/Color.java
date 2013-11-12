package fm.knight.chesster.model;


public enum Color {
  BLACK(-1), WHITE(1);

  private final int direction;

  private Color(int direction) {
    this.direction = direction;
  }

  public int getDirection() {
    return direction;
  }

  public Color flip() {
    return this == WHITE ? BLACK : WHITE;
  }
}
