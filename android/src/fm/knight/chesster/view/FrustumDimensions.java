package fm.knight.chesster.view;


public class FrustumDimensions {
  // These are usually set by figuring out
  // the window ration. Base class may ignore these.
  public float left;
  public float right;
   
  public float bottom;
  public float top;
   
  public float near;
  public float far;
  public FrustumDimensions(float left, float right, 
      float bottom, float top,
      float near, float far) {
    this.left = left;
    this.right = right;
    this.bottom = bottom;
    this.top = top;
    this.near = near;
    this.far = far;
  }
   
  static public FrustumDimensions getDefault() {
    return new FrustumDimensions(-1, 1, -1, 1, 3, 7);
  }

  static public FrustumDimensions getMedium() {
    return new FrustumDimensions(-1, 1, -2, 2, 3, 15);
  }
}
