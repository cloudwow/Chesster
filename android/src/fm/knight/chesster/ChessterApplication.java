package fm.knight.chesster;

import fm.knight.chesster.event.EventBus;

import android.app.Application;

public class ChessterApplication extends Application {

  private EventBus eventBus = new EventBus();

  public EventBus getEventBus() {
    return eventBus;
  }

}
