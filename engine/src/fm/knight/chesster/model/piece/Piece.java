package fm.knight.chesster.model.piece;

import fm.knight.chesster.model.Color;

public class Piece {

  private final Color color;
  private final String code;
  public Piece(Color color, String code) {
    this.color = color;
    this.code =code;
  }

  public Color getColor() {
    return color;
  }

  public String getCode() {
    return code;
  }
}
