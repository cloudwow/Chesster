package fm.knight.chesster;


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

  private int boardSize, squareSide;
  private boolean isSet = true;
  private Paint color;
  private Piece piece;

  public BoardView (Context context) {
    super(context);
    setFocusable(true);
    setFocusableInTouchMode(true);
    requestFocus();
    piece = new Piece(getResources(), 0, 0);
    color = new Paint(Paint.ANTI_ALIAS_FLAG);
    color.setColor(Color.BLACK);
  }

  public float[] validateTouch(float x, float y, float width, float height) {
    for (int i = 0; i < width; i+= width/8) {
      for (int j = 0; j < height; j+= height/8) {
        if (((x > i) && (x < i + squareSide)) && ((y > j) && (y < j + squareSide))) {
          return new float[]{i, j};
        }
      }
    }
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
      piece.posx = (int) xy[0];
      piece.posy = (int) xy[1];
      invalidate();
    }
    return true;
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
    case KeyEvent.KEYCODE_DPAD_UP:
      if (validateBoundaries(piece.posx, piece.posy - squareSide)) piece.posy -= squareSide;
      break;
    case KeyEvent.KEYCODE_DPAD_DOWN:
      if (validateBoundaries(piece.posx, piece.posy + squareSide)) piece.posy += squareSide;
      break;
    case KeyEvent.KEYCODE_DPAD_LEFT:
      if (validateBoundaries(piece.posx - squareSide, piece.posy)) piece.posx -= squareSide;
      break;
    case KeyEvent.KEYCODE_DPAD_RIGHT:
      if (validateBoundaries(piece.posx + squareSide, piece.posy)) piece.posx += squareSide;
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
    drawGradientRect(canvas, boardSize, canvas.getHeight(), 0, boardSize);
    drawPiece(canvas, piece);
  }

  public void drawBoard(Canvas c) {
    for (int i = 0; i < boardSize; i += squareSide) {
      isSet = !isSet;
      for (int j = 0; j < boardSize; j += squareSide) {
        color.setColor(isSet ? Color.BLACK : Color.WHITE);
        c.drawRect(new Rect(i, j, i + squareSide, j + squareSide), color);
        isSet = !isSet;
      }
    }
  }

  public void drawGradientRect(Canvas c, int top, int bottom, int left, int right) {
    float f = 255 / Float.valueOf(bottom - top);
    for (int i = 255, j = top; j <= bottom; i -= Math.round(f), j++) {
      color.setARGB(i, 0, 0, 255);
      c.drawRect(left, j, right, j+1, color);
    }
  }

  public void drawPiece(Canvas c, Piece p) {
    c.drawBitmap(p.b,
        null,
        new RectF(p.posx, p.posy, p.posx + squareSide, p.posy + squareSide),
        p.p);
  }

}
