package fm.knight.chesster.view;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.util.Log;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import android.opengl.GLSurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;


/*
 * Responsibilities
 * *****************
 * 1. Load vertex and shader programs from asset files
 * 2. Provide model transformations
 * 3. provide default vertex/shader programs
 * 4. Provide default frustum and camera settings
 * 5. Compile, link, and create a shader program

 * Abstract features
 * ******************
 * 1. allow derived shader programs
 * 2. allow derived draw
 * 3. allow derived figures
 * 4. allow derived frustum/viewing volume settings
 * 5. Act as a base class, if needed, for abstracting textures
 */
public abstract class AbstractRenderer   implements GLSurfaceView.Renderer {
  public static String TAG = "Chesster." + AbstractRenderer.class.getSimpleName();

  // Class level or global variables are usually
  // unhealthy. Try to minimize them!!

  // The target matrix that holds the end result
  // of all model transformations
  private float[] currentModelMatrix = new float[16];

  // A matrix that is a result of setting the camera/eye
  private float[] viewMatrix = new float[16];

  // A matrix that is the result of setting the frustum
  protected float[] projectionMatrix = new float[16];

  // A matrix that is a multiple of current model, view,
  // and projection matrices.
  private float[] productMatrix = new float[16];

  // GLSL program object with both the shaders compiled,
  // linked, and attached.
  private int shaderProgram;

  // A handle for the uniform variable identifying the MVP matrix
  private int productMatrixHandle;

  // An attribute handle in the vertex shader
  // for passing the vertex arrays.
  private int positionAttributeHandle;

  // Name of the default vertex shader
  // source code file in the asset directory.
  private final String vertexShaderFilename;

  // Name of the default fragment shader
  // source code file in the asset directory.
  private final String fragmentShaderFileName;

  private final Context context;
    
  /*
   * This class relies on virtual methods to specialize.
   * Doesn't use construction arguments for specialization.
   */
  public AbstractRenderer(
      Context context,
      String vertexShaderFilename,
      String fragmentShaderFileName) {
    this.context = context;
    this.vertexShaderFilename = vertexShaderFilename;
    this.fragmentShaderFileName = fragmentShaderFileName;
    initializeMatrices();
  }

  protected void initializeMatrices() {
    // Set the model matrix to identity
    // Subsequent scaling, rotation, etc will update this
    // in a stateful manner. So starting state matters.
    Matrix.setIdentityM(this.currentModelMatrix, 0);

    // Although we use this matrix only once,
    // it is good to start with a known state.
    Matrix.setIdentityM(productMatrix, 0);
  }

  // overridables


  // Override this method to specify your
  // your own frustum or the dimensions of a viewing volume.
  protected FrustumDimensions getFrustumDimensions() {
    // Get default dimensions in this base class
    return FrustumDimensions.getDefault();
  }

  // Override this method to continue the onDrawframe callback
  // from the renderer.
  protected abstract void draw(
      GL10 gl,
      int positionHandle);

  // Override this to implement preDraw
  // useful for derived classes to specialize pre-draws
  protected void preDraw(
      GL10 gl,
      int positionHandle) {// none for this class
  }

  // Override this method if you want to provide
  // a different vertex shader program.
  protected String getVertexShaderCodeString() {
    String vertexShader = "uniform mat4 uMVPMatrix;\n" + "attribute vec4 aPosition;\n"
        + "void main() {\n" + "  gl_Position = uMVPMatrix * aPosition;\n" + "}\n";

    return vertexShader;
  }

  // Override this method if you want to provide
  // a different vertex shader program.
  // In a derived method call getStringFromAssetFile(filename)
  // to read as string to be returned from here.
  protected String getFragmentShaderCodeString() {
    String fragmentShader = "void main() {\n" + "  gl_FragColor = vec4(0.5, 0.25, 0.5, 1.0);\n"
        + "}\n";

    return fragmentShader;
  }

  // end overridables

  @Override
  public void onSurfaceCreated(
      GL10 gl,
      EGLConfig eglConfig) {
    prepareSurface(gl, eglConfig);
  }

  // Based on width and height of the window set the
  // viewport and the frustum.
  @Override
  public void onSurfaceChanged(
      GL10 gl,
      int w,
      int h) {
    Log.d(TAG, "surface changed. Setting matrix frustum: projection matrix");
    GLES20.glViewport(0, 0, w, h);
    float ratio = (float) w / h;
    FrustumDimensions fd = this.getFrustumDimensions();

    // Matrix.setIdentityM(projectionMatrix, 0);
    Matrix.frustumM(projectionMatrix, 0, ratio * fd.bottom, ratio * fd.top, fd.bottom, fd.top
        ,
        fd.near, fd.far);
  }

  // 1. Set your camera. You can place this method while creating
  // the surface or changing the surface. Or you can choose to
  // vary it during the draw method.
  // 2. Do some basic drawing methods like clear the palletee
  // 3. Use the program of choice
  @Override
  public void onDrawFrame(
      GL10 gl) {
    //    Log.d(TAG, "set look at matrix: view matrix");
    // Matrix.setIdentityM(viewMatrix, 0);
    Matrix.setLookAtM(viewMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    //    Log.d(TAG, "base drawframe");
    GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

    GLES20.glUseProgram(shaderProgram);
    checkGlError("glUseProgram");

    // Allow a derived class to setup drawing
    // for further down the chain.
    // the default doesn't do anything.
    preDraw(gl, this.positionAttributeHandle);

    // Real abstract method
    draw(gl, this.positionAttributeHandle);
  }

  /**
   * Access to the android {@link Context}.
   */
  protected Resources getResources() {
    return context.getResources();
  }

  /**
   * 1. Create the GLSL program object by passing vertex
   * and shader code. Derived classes can supply their own shader
   * programs.
   * 2. Get vertex position hanndle
   * 3. get the uniform mvp matrix handle
   */
  protected void prepareSurface(
      GL10 gl,
      EGLConfig eglConfig) {
    Log.d(TAG, "preparing surface");
    shaderProgram = createProgram(this.getVertexShaderCodeString()
        ,
        this.getFragmentShaderCodeString());
    if (shaderProgram == 0) {
      return;
    }
    Log.d(TAG, "Getting position handle:aPosition");
    // positionAttributeHandle = GLES20.glGetAttribLocation(shaderProgram,
    // "aPosition");
    positionAttributeHandle = getAttributeHandle("aPosition", "Getting Position Handle");

    Log.d(TAG, "Getting matrix handle:uMVPMatrix");
    // productMatrixHandle = GLES20.glGetUniformLocation(shaderProgram,
    // "uMVPMatrix");
    productMatrixHandle = getUniformHandle("uMVPMatrix", "Getting MVP uniform matrix handle");
  }

  /*
   * 1. Load vertex shader
   * 2. load fragment shader
   * 3. create program
   * 4. attach shaders
   * 5. link program and return it
   */
  private int createProgram(
      String vertexSource,
      String fragmentSource) {
    int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);

    if (vertexShader == 0) {
      return 0;
    }
    Log.d(TAG, "vertex shader created");
    int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);

    if (pixelShader == 0) {
      return 0;
    }
    Log.d(TAG, "fragment shader created");
    int program = GLES20.glCreateProgram();

    if (program != 0) {
      Log.d(TAG, "program created");
      GLES20.glAttachShader(program, vertexShader);
      checkGlError("glAttachShader");
      GLES20.glAttachShader(program, pixelShader);
      checkGlError("glAttachShader");
      GLES20.glLinkProgram(program);
      int[] linkStatus = new int[1];

      GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
      if (linkStatus[0] != GLES20.GL_TRUE) {
        Log.e(TAG, "Could not link program: ");
        Log.e(TAG, GLES20.glGetProgramInfoLog(program));
        GLES20.glDeleteProgram(program);
        program = 0;
      }
    }
    return program;
  }

  /*
   * Load a given type of shader and check for any errors
   */
  private int loadShader(
      int shaderType,
      String source) {
    int shader = GLES20.glCreateShader(shaderType);

    if (shader != 0) {
      GLES20.glShaderSource(shader, source);
      GLES20.glCompileShader(shader);
      int[] compiled = new int[1];

      GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
      if (compiled[0] == 0) {
        Log.e(TAG, "Could not compile shader " + shaderType + ":");
        Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
        GLES20.glDeleteShader(shader);
        shader = 0;
      }
    }
    return shader;
  }

  // Use this method to check and log GL errors
  protected void checkGlError(
      String op) {
    int error;

    while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
      Log.e(TAG, op + ": glError " + error);
      throw new RuntimeException(op + ": glError " + error);
    }
  }

  /*
   * The following three methods update the currentModelMatrix
   * with the given model transformation.
   * These are stateful accumulative methods.
   */
  public void trnslate(
      float x,
      float y,
      float z) {
    float[] tempModelMatrix = new float[16];

    Matrix.setIdentityM(tempModelMatrix, 0);
    Matrix.translateM(tempModelMatrix, 0, x, y, z);
    Matrix.multiplyMM(this.currentModelMatrix, 0, tempModelMatrix, 0, this.currentModelMatrix, 0);
  }

  public void rotate(
      float angle,
      float x,
      float y,
      float z) {
    float[] tempModelMatrix = new float[16];

    Matrix.setIdentityM(tempModelMatrix, 0);
    Matrix.rotateM(tempModelMatrix, 0, angle, x, y, z);
    Matrix.multiplyMM(this.currentModelMatrix, 0, tempModelMatrix, 0, this.currentModelMatrix, 0);
  }

  public void scale(
      float xFactor,
      float yFactor,
      float zFactor) {
    float[] tempModelMatrix = new float[16];

    Matrix.setIdentityM(tempModelMatrix, 0);
    Matrix.scaleM(tempModelMatrix, 0, xFactor, yFactor, zFactor);
    Matrix.multiplyMM(this.currentModelMatrix, 0, tempModelMatrix, 0, this.currentModelMatrix, 0);
  }

  /*
   * Calculaute the final model view matrix
   * 1. Order of matrix multiplication is important
   * 2. MVPmatrix = proj * view * model;
   * 3. Setup the MVP matrix in the vertex shader memory
   */
  protected void setupMatrices() {
    float[] tempModelMatrix = new float[16];

    Matrix.setIdentityM(tempModelMatrix, 0);

    // translate the model combo next
    Matrix.multiplyMM(productMatrix, 0, // matrix and offset
        currentModelMatrix, 0, tempModelMatrix, 0);

    // translate eye coordinates first
    Matrix.multiplyMM(productMatrix, 0, this.viewMatrix, 0, productMatrix, 0);

    // Project it: screen coordinates
    Matrix.multiplyMM(productMatrix, 0, projectionMatrix, 0, productMatrix, 0);

    // Set the vertex uniform handler representing the MVP matrix
    GLES20.glUniformMatrix4fv(productMatrixHandle, // uniform handle
        1, // number of uniforms. 1 if it is not
        // an array
        false, // transpose: must be false
        productMatrix, // client matrix memory
        // pointer
        0); // offset
  }

  // Use this method if your intent is to return
  // a default vertex shader.
  public String getDefaultVertexShaderCodeString() {
    return this.getStringFromAssetFile(vertexShaderFilename);
  }

  // Use this method if your intent is to return
  // a default fragment shader.
  public String getDefaultFragmentShaderCodeString() {
    return this.getStringFromAssetFile(fragmentShaderFileName);
  }

  // How to to read a text file from an asset
  // directory. In this approach you will need to
  // create your application object and provide a static
  // variable to create the context.
  // See MyApplication implementation to see how this works.
  // Or see http://androidbook.com/item/4224
  public String getStringFromAssetFile(
      String filename) {
    if (context == null) {
      throw new RuntimeException("Sorry your app context is null");
    }
    try {
      AssetManager am = context.getAssets();
      InputStream is = am.open(filename);
      String s = convertStreamToString(is);

      is.close();
      return s;
    } catch (IOException x) {
      throw new RuntimeException("Sorry not able to read filename:" + filename, x);
    }
  }

  // Converting a file stream to a string
  // Optimize as you see fit. This may not be an efficient read
  private String convertStreamToString(
      InputStream is)
    throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int i = is.read();

    while (i != -1) {
      baos.write(i);
      i = is.read();
    }
    return baos.toString();
  }

  // Use this if you need to use a program object
  // directly. Make sure you call this after the
  // surface is created and prepared in this base class.
  // otherwise it will be null.
  public int getGLSProgramObjectReference() {
    return this.shaderProgram;
  }

  public int getAttributeHandle(
      String GLSLAttributeName,
      String comment) {
    String logComment = comment + ":" + GLSLAttributeName;

    // Get texture handle
    Log.d(TAG, comment);
    int attributeHandle = GLES20.glGetAttribLocation(shaderProgram, GLSLAttributeName);

    checkGlError(logComment);
    if (attributeHandle == -1) {
      throw new RuntimeException(logComment);
    }
    return attributeHandle;
  }

  public int getUniformHandle(
      String GLSLUniformName,
      String comment) {
    String logComment = comment + ":" + GLSLUniformName;

    // Get texture handle
    Log.d(TAG, comment);
    int uniformHandle = GLES20.glGetUniformLocation(shaderProgram, GLSLUniformName);

    checkGlError(logComment);
    if (uniformHandle == -1) {
      throw new RuntimeException(logComment);
    }
    return uniformHandle;
  }
}
