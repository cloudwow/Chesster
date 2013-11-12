package fm.knight.chesster;


import fm.knight.chesster.model.Board;
import fm.knight.chesster.view.BoardView;
import fm.knight.chesster.view.BoardGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {
  BoardGLSurfaceView view;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // setContentView(R.layout.main);
    view = new BoardGLSurfaceView(this, new Board());
    setContentView(view);
  }

  @Override
  protected void onPause() {
    super.onPause();
    view.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    view.onResume();
  }
}
