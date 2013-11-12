package fm.knight.chesster.view;


import fm.knight.chesster.model.piece.Piece;
import fm.knight.chesster.model.Board;
import fm.knight.chesster.model.Coordinate;
import fm.knight.chesster.R;

import android.content.Context;
import android.util.Log;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;


public class BoardView extends View {

  private static final String TAG = "Chesster.BoardView";
  private final Board board;
  int mViewWidth;
  int mViewHeight;
  int mPosX[] = new int[8];
  int mPosY[] = new int[8];
  int mTileSize;
  Coordinate focusCoordinate;
  public BoardView(Context context, Board board) {
    super(context);
    setFocusable(true);
    setFocusableInTouchMode(true);
    requestFocus();
    this.board = board;
  }

  public float[] validateTouch(float x, float y, float width, float height) {
    return null;
  }

  public boolean validateBoundaries(float x, float y) {
    if ((x >= 0 && x < this.getWidth()) && (y >= 0 && y < this.getWidth())) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    boolean bRtn;

    bRtn = super.onTouchEvent(event);
    switch (event.getAction()) {
    case MotionEvent.ACTION_DOWN:
      int column = (int) event.getX() / mTileSize;
      int row = 7 - (int) event.getY() / mTileSize;

      focusCoordinate = new Coordinate(row, column);
      invalidate();
      break;

    case MotionEvent.ACTION_UP: 
      break;
    }
    return bRtn;
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mViewWidth = w;
    mViewHeight = h;
    mTileSize = Math.min(mViewWidth, mViewHeight) / 8;
    for (int i = 0; i < 8; i++) {
      mPosX[i] = i * mTileSize;
      mPosY[i] = (mTileSize * 7) - i * mTileSize;
    }

  }

  @Override
  protected void onDraw(Canvas canvas) {
    Resources r = getResources();
    BitmapDrawable tile_white_drawable = (BitmapDrawable) r.getDrawable(R.drawable.tile_white);
    Bitmap tilewhite = tile_white_drawable.getBitmap();
    BitmapDrawable tileDarkDrawable = (BitmapDrawable) r.getDrawable(R.drawable.tile_dark);
    Bitmap tileDark = tileDarkDrawable.getBitmap();

    canvas.drawColor(Color.BLACK);
    Rect drawRec = new Rect();

    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        drawRec.set(mPosX[x], mPosY[y], mPosX[x] + mTileSize, mPosY[y] + mTileSize);
        if ((x + y) % 2 == 0) {
          canvas.drawBitmap(tilewhite, null, drawRec, null);
        } else {
          canvas.drawBitmap(tileDark, null, drawRec, null);
        }
      }
    }

    // ///////////////// pieces
    Rect pieceSquare = new Rect();
    RectF focusSquare = new RectF();
    Bitmap pieceBitmap;

    for (int row = 0; row < 8; row++) {
      for (int column = 0; column < 8; column++) {
        if (!board.isEmptyAt(row, column)) {
          Piece piece = board.getPieceAt(row, column);

          pieceBitmap = BitmapFactory.decodeResource(getResources(), PieceView.getBitmapCode(piece));
          if (pieceBitmap == null) {
            Log.w(TAG, "piece bitmap is null");
          } else {
            pieceSquare.set(mPosX[column], mPosY[row], mPosX[column] + mTileSize,
                mPosY[row] + mTileSize);
            canvas.drawBitmap(pieceBitmap, null, pieceSquare, null);
          }
        }
      }
    }

    if (focusCoordinate != null) {
      Paint paint = new Paint();

      paint.setStrokeWidth(4);
      paint.setStyle(Paint.Style.STROKE);
      focusSquare.set(mPosX[focusCoordinate.getColumn()] + 1, mPosY[focusCoordinate.getRow()] + 1,
          mPosX[focusCoordinate.getColumn()] - 1 + mTileSize,
          mPosY[focusCoordinate.getRow()] - 1 + mTileSize);
      paint.setColor(0x400000ff);
      canvas.drawRoundRect(focusSquare, 3F, 3F, paint);

    }

  }

  public void setFocus(Coordinate focusCoordinate) {
    this.focusCoordinate = focusCoordinate;
  }
}
