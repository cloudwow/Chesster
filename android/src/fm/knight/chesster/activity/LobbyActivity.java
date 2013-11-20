package fm.knight.chesster.activity;

import fm.knight.chesster.event.EventBus;

import fm.knight.chesster.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class LobbyActivity extends BaseActivity {
  protected void registerListeners(EventBus eventBus) {}
  protected void unregisterListeners(EventBus eventBus) {}
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lobby);
    }
 
}
