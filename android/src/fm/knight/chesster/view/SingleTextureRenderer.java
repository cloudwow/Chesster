package fm.knight.chesster.view;


import android.content.Context;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;


public abstract class SingleTextureRenderer extends AbstractRenderer {
  public static String TAG = "Chesster." + SingleTextureRenderer.class.getSimpleName();

  // Handle to the texture attribute in the vertex shader
  private int maTextureHandle;
  // handle to the texture Sampler Uniform variable in the fragment
  // shader
  private int mu2DSamplerTexture;

  // Client assigned name of the texture
  int mTextureID;
  // default texture ImageResourceId
  final int mDefTextureImageResourceId;

  public SingleTextureRenderer(
      Context context,
      String vertexShaderFilename,
      String fragmentShaderFileName,
      int textureResourceId) {
    super(context, vertexShaderFilename, fragmentShaderFileName);
    this.mDefTextureImageResourceId = textureResourceId;
  }

  // give out the texture attribute handle
  // if needed.
  protected int getTextureHandle() {
    return maTextureHandle;
  }

  // You can prepare and load your texture here
  // so that you dont have to do this everty time
  // a surface just changed changing the height and width.
  public void onSurfaceCreated(
      GL10 gl,
      EGLConfig eglConfig) {
    // Give a chance to have the parent prepare the surface
    super.prepareSurface(gl, eglConfig);
    prepareSurfaceForTexture(gl, eglConfig);
  }

  public void prepareSurfaceForTexture(
      GL10 gl,
      EGLConfig eglConfig) {
    // Get texture attribute handle
    Log.d(TAG, "Getting texture handle:aTextureCoordinate");
    maTextureHandle = getAttributeHandle("aTextureCoordinate", "Getting Texture handle");

    // Get uniform texture handle
    Log.d(TAG, "Getting texture 2D sampler handle");
    mu2DSamplerTexture = getUniformHandle("s_2DtextureSampler", "Getting 2D sampler for texture");

    this.prepareTexture();
  }

  @Override
  protected void preDraw(
      GL10 gl,
      int positionHandle) {
    // Call the parent's method first
    super.preDraw(gl, positionHandle);

    // texture support
    // Make texture unit 0 as the active texture unit
    // This is the default as well
    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

    // Make texture target 2D and texture name mTextureId
    // as the target texture for the active texture unit
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

    // Tell the texture sampler in GLSL that the texture to
    // sample belongs to teh texture unit 0.
    // This is also the default
    GLES20.glUniform1i(mu2DSamplerTexture, 0);

  }

  // Get the texture name that is initialized
  // Make sure it is initialized before calling this
  // method.
  public int getTextureID() {
    return mTextureID;
  }

  // Ultimately this code prepares the
  // texture ID mTextureID,
  // creates it, and binds it to teh texture target 2D.
  public void prepareTexture() {
    // GLES20.glEnable(GLES20.GL_TEXTURE_2D);
    int[] textures = new int[1];

    GLES20.glGenTextures(1, textures, 0);

    mTextureID = textures[0];
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);

    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

    final BitmapFactory.Options options = new BitmapFactory.Options();

    options.inScaled = false; // No pre-scaling

    int texturImageReourceId = getTextureImageResourceId();
    // Read in the resource
    final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), texturImageReourceId, options);

    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
    bitmap.recycle();
  }

  // override this to give your own texture image resource id
  protected int getTextureImageResourceId() {
    return this.mDefTextureImageResourceId;
  }
}
