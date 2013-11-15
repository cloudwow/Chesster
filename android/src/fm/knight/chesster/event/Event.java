package fm.knight.chesster.event;

public abstract class Event<PAYLOAD> {
  private final Object source;
  private final PAYLOAD payload;
  protected Event (Object source, PAYLOAD payload) {
    this.source =source;
    this.payload=payload;
  }

  public Object getSource() {
    return source;
  }

  public PAYLOAD getPayload() {
    return payload;
  }
  
}
