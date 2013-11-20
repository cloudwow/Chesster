package fm.knight.chesster.activity;

import fm.knight.chesster.R;
import fm.knight.chesster.ChessterApplication;
import fm.knight.chesster.event.EventBus;
import android.app.Activity;
import android.content.Intent;

public abstract class BaseActivity extends Activity {

  protected abstract void registerListeners(EventBus eventBus);
  protected abstract void unregisterListeners(EventBus eventBus);

  @Override
  public void onPause() {
    super.onPause();
    unregisterListeners(getEventBus());
  }
  @Override
  protected void onResume() {
    super.onResume();
    registerListeners(getEventBus());
  }

  public ChessterApplication getChessterApplication() {
    return (ChessterApplication)getApplication();
  }

  public EventBus getEventBus() {
    return getChessterApplication().getEventBus();
  }
}
