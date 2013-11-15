package fm.knight.chesster.event;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


public class EventBus {
  private final Multimap<Class,
          AsyncListener> listeners = HashMultimap.create();

  public synchronized <EVENT extends Event> void registerListener(
          Class<EVENT> eventClass,
          AsyncListener<EVENT> listener) {
    listeners.put(eventClass, listener);
  }

  public synchronized <EVENT extends Event> void unregisterListener(
          Class<EVENT> eventClass,
          AsyncListener<EVENT> listener) {
    listeners.remove(eventClass, listener);
  }

  public synchronized <EVENT extends Event> void fireEvent(
          Class<EVENT> eventClass,
          EVENT event) {
    for (AsyncListener listener : listeners.get(eventClass)) {
      AsyncListener<EVENT> myListener = (AsyncListener<EVENT>) listener;

      myListener.postEvent(event);
    }
  }
}
