package fm.knight.chesster.view;
import fm.knight.chesster.R;

import fm.knight.chesster.model.piece.Piece;
import android.graphics.Bitmap;

public enum PieceView {
  PAWN(R.drawable.chess_piece_black_pawn, R.drawable.chess_piece_white_pawn, "P"),
  ROOK(R.drawable.chess_piece_black_rook, R.drawable.chess_piece_white_rook, "R"),
  KNIGHT(R.drawable.chess_piece_black_knight, R.drawable.chess_piece_white_knight, "N"),
  BISHOP(R.drawable.chess_piece_black_bishop, R.drawable.chess_piece_white_bishop, "B"),
  QUEEN(R.drawable.chess_piece_black_queen, R.drawable.chess_piece_white_queen, "Q"),
  KING(R.drawable.chess_piece_black_king, R.drawable.chess_piece_white_king, "K");

  private final long whiteResourceId;
  private final long blackResourceId;
  private final String code;

  PieceView(long blackResourceId, long whiteResourceId, String code) {
    this.blackResourceId=blackResourceId;
    this.whiteResourceId=whiteResourceId;
    this.code = code;
  }

  Bitmap getBitmap() {
    return null;
  }

  String getCode() {
    return code;
  }

}
