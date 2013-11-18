package fm.knight.chesster;

import android.content.Context;
import android.view.Display;
import android.view.View;

public class GameVars {
  public static Display display;
  public static Context context;
  public static final int GAME_THREAD_DELAY = 3000;
  public static final int MENU_BUTTON_ALPHA = 0;
  public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
  public static final int GAME_THREAD_FPS_SLEEP = (1000/60);
  public static final int BACKGROUND = R.drawable.library;


  public boolean onExit(View v) {
    /*try
      {
      Intent bgmusic = new Intent(context, SFMusic.class);
      context.stopService(bgmusic);
      musicThread.stop();*/

    return true;
    /* }catch(Exception e){
       return false;
       }*/

  }
}
