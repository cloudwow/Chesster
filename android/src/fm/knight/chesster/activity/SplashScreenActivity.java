package fm.knight.chesster.activity;

import fm.knight.chesster.event.EventBus;
import fm.knight.chesster.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends BaseActivity {

  // Splash screen timer
  private static int SPLASH_TIME_OUT = 3000;

  protected void registerListeners(EventBus eventBus) {}
  protected void unregisterListeners(EventBus eventBus) {}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    new Handler().postDelayed(new Runnable() {

        /*
         * Showing splash screen with a timer. This will be useful
         * when you
         * want to show case your app logo / company
         */

        @Override
        public void run() {
          // This method will be executed once the timer is over
          // Start your app main activity
          Intent i = new Intent(SplashScreenActivity.this, LobbyActivity.class);
          startActivity(i);

          // close this activity
          finish();
          overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
      }, SPLASH_TIME_OUT);
  }

}
