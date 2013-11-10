package fm.knight.chesster;

import fm.knight.chesster.model.Board;
import fm.knight.chesster.view.BoardView;
import fm.knight.chesster.view.BoardSurfaceView;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    //        setContentView(R.layout.main);
    setContentView(new BoardSurfaceView(this, new Board()));
  }
}
