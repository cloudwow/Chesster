package fm.knight.chesster.event;


import android.os.Handler;


public abstract class AsyncListener<EVENT> {

  Handler callbackThreadHandler = new Handler();

  public AsyncListener() {
    this.callbackThreadHandler = new Handler();
  }

  public AsyncListener(
      Handler callbackThreadHandler) {
    this.callbackThreadHandler = callbackThreadHandler;
  }

  public abstract void onEvent(
      EVENT event);

  public void postEvent(
      final EVENT event) {
    callbackThreadHandler.post(new Runnable() {
      @Override
      public void run() {
        onEvent(event);
      }
    });
  }

}
