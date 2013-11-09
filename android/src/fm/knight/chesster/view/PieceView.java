package fm.knight.chesster.view;
import fm.knight.chesster.R;

import fm.knight.chesster.model.piece.Piece;
import fm.knight.chesster.model.piece.Pawn;
import fm.knight.chesster.model.piece.Knight;
import fm.knight.chesster.model.piece.Rook;
import fm.knight.chesster.model.piece.Bishop;
import fm.knight.chesster.model.piece.Queen;
import fm.knight.chesster.model.piece.King;
import android.graphics.Bitmap;

public enum PieceView {
  PAWN(R.drawable.chess_piece_black_pawn, R.drawable.chess_piece_white_pawn, "P"),
  ROOK(R.drawable.chess_piece_black_rook, R.drawable.chess_piece_white_rook, "R"),
  KNIGHT(R.drawable.chess_piece_black_knight, R.drawable.chess_piece_white_knight, "N"),
  BISHOP(R.drawable.chess_piece_black_bishop, R.drawable.chess_piece_white_bishop, "B"),
  QUEEN(R.drawable.chess_piece_black_queen, R.drawable.chess_piece_white_queen, "Q"),
  KING(R.drawable.chess_piece_black_king, R.drawable.chess_piece_white_king, "K");

  private final int whiteResourceId;
  private final int blackResourceId;
  private final String code;

  PieceView(int blackResourceId, int whiteResourceId, String code) {
    this.blackResourceId=blackResourceId;
    this.whiteResourceId=whiteResourceId;
    this.code = code;
  }


  String getCode() {
    return code;
  }

  public static int getBitmapCode(Piece piece) {
    PieceView pieceView;
    if (piece instanceof Pawn) {
      pieceView = PAWN;
    } else if (piece instanceof Rook) {
      pieceView = ROOK;
    } else if (piece instanceof Knight) {
      pieceView = KNIGHT;
    } else if (piece instanceof Bishop) {
      pieceView = BISHOP;
    } else if (piece instanceof Queen) {
      pieceView = QUEEN;
    } else if (piece instanceof King) {
      pieceView = KING;
    } else {
      throw new RuntimeException();
    }
    switch(piece.getColor()) {
    case WHITE:
      return pieceView.whiteResourceId;
    case BLACK:
      return pieceView.blackResourceId;
    default:
      throw new RuntimeException();
    }
  }
}
