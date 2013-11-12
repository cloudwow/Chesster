package fm.knight.chesster.view;

import fm.knight.chesster.model.piece.Piece;
import fm.knight.chesster.model.Board;
import fm.knight.chesster.R;

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

public class BoardSurfaceView extends SurfaceView  implements SurfaceHolder.Callback {
  private final static String TAG = "Chesster."+BoardSurfaceView.class.getSimpleName();
  private final Board board;
  int boardSize;
  int squareSide;
  MyThread mythread;
  Context context;
  Bitmap darkTileBitmap;
  public BoardSurfaceView (Context context, Board board) {
    super(context);
    this.board = board;
    this.context = context;
    SurfaceHolder holder = getHolder();
    holder.addCallback(this);
    darkTileBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_dark);
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder)
  {
    mythread.setRunning(false);
    boolean retry = true;
    while(retry)
      {
        try
          {
            mythread.join();
            retry = false;
          }
        catch(Exception e)
          {
            Log.v(TAG, e.getMessage());
          }
      }
  }



  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
                             int height)
  {
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder)
  {
    mythread = new MyThread(holder, context,this);

    mythread.setRunning(true);

    mythread.start();

  }

  void doDraw(Canvas canvas)
  {
    canvas.drawColor(Color.GREEN);
    canvas.drawBitmap(darkTileBitmap, 50, 50, null);
  }

  public class MyThread extends Thread
  {
    boolean mRun;
    Canvas mcanvas;
    SurfaceHolder surfaceHolder;
    Context context;
    BoardSurfaceView msurfacePanel;

    public MyThread(SurfaceHolder sholder, Context ctx, BoardSurfaceView spanel)
    {
      surfaceHolder = sholder;
      context = ctx;
      mRun = false;
      msurfacePanel = spanel;
    }

    void setRunning(boolean bRun)
    {
      mRun = bRun;
    }

    @Override
    public void run()
    {
      super.run();
      while(mRun)
        {
          mcanvas = surfaceHolder.lockCanvas();
          if(mcanvas != null)
            {
              msurfacePanel.doDraw(mcanvas);
              surfaceHolder.unlockCanvasAndPost(mcanvas);
            }
        }
    }
  }
}
