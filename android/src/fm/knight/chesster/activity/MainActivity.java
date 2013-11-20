
package fm.knight.chesster.activity;

import fm.knight.chesster.event.EventBus;

import fm.knight.chesster.model.Board;
import fm.knight.chesster.view.BoardView;
import fm.knight.chesster.view.BoardGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends BaseActivity {
  BoardGLSurfaceView view;

  protected void registerListeners(EventBus eventBus) {}
  protected void unregisterListeners(EventBus eventBus) {}

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // setContentView(R.layout.main);
    view = new BoardGLSurfaceView(this, new Board());
    setContentView(view);
  }

  @Override
  public void onPause() {
    super.onPause();
    view.onPause();
  }

  @Override
  public void onResume() {
    super.onResume();
    view.onResume();
  }
}
