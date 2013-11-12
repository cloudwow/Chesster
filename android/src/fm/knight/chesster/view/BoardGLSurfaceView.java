package fm.knight.chesster.view;

import fm.knight.chesster.model.piece.Piece;
import fm.knight.chesster.model.Board;
import fm.knight.chesster.R;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.opengl.GLSurfaceView;
public class BoardGLSurfaceView extends GLSurfaceView {
  private final static String TAG = "Chesster."+BoardGLSurfaceView.class.getSimpleName();
  private final Board board;
  int boardSize;
  int squareSide;
  Context context;
  Bitmap mbitmap;
  public BoardGLSurfaceView (Context context, Board board) {
    super(context);
    this.board = board;
    this.context = context;
    mbitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_dark);
    setRenderer(new BoardRenderer());
  }

  class BoardRenderer implements GLSurfaceView.Renderer {
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
      GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.7f);
    }
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
    }
    public void onDrawFrame(GL10 glUnused) {
    }
  }

}
