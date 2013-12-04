package fm.knight.chesster.model;

public class CastlingState {

  private final boolean blackCanCastleKingSide;
  private final boolean blackCanCastleQueenSide;
  private final boolean whiteCanCastleKingSide;
  private final boolean whiteCanCastleQueenSide;

  public CastlingState(boolean whiteCanCastleKingSide,
      boolean whiteCanCastleQueenSide, boolean blackCanCastleKingSide, boolean blackCanCastleQueenSide) {
    this.whiteCanCastleKingSide = whiteCanCastleKingSide;
    this.whiteCanCastleQueenSide = whiteCanCastleQueenSide;
    this.blackCanCastleKingSide = blackCanCastleKingSide;
    this.blackCanCastleQueenSide = blackCanCastleQueenSide;
  }

  public CastlingState (String fen) {
    blackCanCastleQueenSide = fen.contains("q");
    blackCanCastleKingSide = fen.contains("k");
    whiteCanCastleQueenSide = fen.contains("Q");
    whiteCanCastleKingSide = fen.contains("K");
  }

  public boolean canCastleKingSide(Color color) {
    if(color==Color.BLACK) {
      return blackCanCastleKingSide;
    } else {
      return whiteCanCastleKingSide;
    }
  }
  public boolean canCastleQueenSide(Color color) {
    if(color==Color.BLACK) {
      return blackCanCastleQueenSide;
    } else {
      return whiteCanCastleQueenSide;
    }
  }

  public boolean canWhiteCastleKingSide() {
    return whiteCanCastleKingSide;
  }
  public boolean canWhiteCastleQueenSide() {
    return whiteCanCastleQueenSide;
  }

  public boolean canBlackCastleKingSide() {
    return blackCanCastleKingSide;
  }
  public boolean canBlackCastleQueenSide() {
    return blackCanCastleQueenSide;
  }

  public CastlingState withoutWhite() {
    if (whiteCanCastleQueenSide || whiteCanCastleKingSide) {
      return new CastlingState(false, false, blackCanCastleKingSide, blackCanCastleQueenSide);
    } else {
      return this;
    }
  }

  public CastlingState withoutBlack() {
    if (blackCanCastleQueenSide || blackCanCastleKingSide) {
      return new CastlingState(whiteCanCastleKingSide, whiteCanCastleQueenSide, false, false);
    } else {
      return this;
    }
  }
  
  public CastlingState withoutWhiteKingSide() {
    if (whiteCanCastleKingSide) {
      return new CastlingState(false, whiteCanCastleQueenSide, blackCanCastleKingSide, blackCanCastleQueenSide);
    } else {
      return this;
    }
  }

  public CastlingState withoutWhiteQueenSide() {
    if (whiteCanCastleQueenSide) {
      return new CastlingState(whiteCanCastleKingSide, false, blackCanCastleKingSide, blackCanCastleQueenSide);
    } else {
      return this;
    }
  }

  public CastlingState withoutBlackKingSide() {
    if (blackCanCastleKingSide) {
      return new CastlingState(whiteCanCastleKingSide, whiteCanCastleQueenSide, false, whiteCanCastleQueenSide);
    } else {
      return this;
    }
  }

  public CastlingState withoutBlackQueenSide() {
    if (blackCanCastleQueenSide) {
      return new CastlingState(whiteCanCastleKingSide, whiteCanCastleQueenSide, whiteCanCastleKingSide, false);
    } else {
      return this;
    }
  }


}
