package fm.knight.chesster.view;

import fm.knight.chesster.model.piece.Piece;
import fm.knight.chesster.model.Board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {

  private final Board board;
  int boardSize;
  int squareSide;
  public BoardView (Context context, Board board) {
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
    float[] xy = validateTouch(event.getX(), event.getY(), getWidth(), getWidth());
    if (xy != null) {
      // TODO
      invalidate();
    }
    return true;
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
    case KeyEvent.KEYCODE_DPAD_UP:
      break;
    case KeyEvent.KEYCODE_DPAD_DOWN:
      break;
    case KeyEvent.KEYCODE_DPAD_LEFT:
      break;
    case KeyEvent.KEYCODE_DPAD_RIGHT:
      break;
    }
    invalidate();
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    boardSize = Math.min(canvas.getWidth(), canvas.getHeight());
    squareSide = boardSize / 8;
    drawBoard(canvas);
    //    drawPiece(canvas, piece);
  }


  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

    int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
    int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);

    int newWidth, newHeight;

    if (widthSpecMode == MeasureSpec.AT_MOST)
      newWidth = 30;
    else
      newWidth = widthMeasure;

    if (heightSpecMode == MeasureSpec.AT_MOST)
      newHeight = 30;
    else
      newHeight = heightMeasure;

    setMeasuredDimension(newWidth, newHeight);
  }


  
  public void drawBoard(Canvas c) {
    Paint paint = new Paint();
    boolean isSet =false;
    for (int i = 0; i < boardSize; i += squareSide) {
      isSet = !isSet;
      for (int j = 0; j < boardSize; j += squareSide) {
        paint.setColor(isSet ? Color.BLACK : Color.WHITE);
        c.drawRect(new Rect(i, j, i + squareSide, j + squareSide), paint);
        isSet = !isSet;
      }
    }
  }


  public void drawPiece(Canvas c, Piece p) {
  }

}
