package fm.knight.chesster;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class Piece {

  int posx, posy;
  Paint p;
  Bitmap b;
        
  public Piece(Resources r, int posx, int posy) {
    p = new Paint(Paint.ANTI_ALIAS_FLAG);
    b = BitmapFactory.decodeResource(r, R.drawable.chess_piece_black_king);
          
    this.posx = posx;
    this.posy = posy;
  }

}
