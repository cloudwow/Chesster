package fm.knight.chesster.view;

import fm.knight.chesster.R;

import android.content.Context;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CubeRenderer extends SingleTextureRenderer {
  // A raw native buffer to hold the point coordinates
  // for the cube.
  private FloatBuffer mFVertexBuffer;
  // A raw native buffer to hold the texture coordinates
  // for the cube.
  private FloatBuffer mFTextureBuffer;
  private static final int FLOAT_SIZE_BYTES = 4;
    
  // variables to control rotation
  // if stopFlag = true stop the rotation
  private boolean stopFlag = false;
  // what is the current angle of rotation
  private float curAngle = 0;
  // At the last stop what was the angle 
  // to restart.
  private float stoppedAtAngle = 0;
    
  // What are the vertex and fragment shader
  // source code files
  private static final String VERTEX_SHADER_FILENAME = "tex_vertex_shader_1.txt";
  private static final String FRAGMENT_SHADER_FILENAME = "tex_fragment_shader_1.txt";
    
  // front-face: f1,f2,f3,f4
  // starting top-right-anti-clockwise    
  // f1(1,1) f2(-1,1) f3(-1,-1) f4(1,-1): z plane 0
    
  // back-face: b1,b2,b3,b4
  // starting bottom-right-anti-clockwise    
  // b1(1,-1) b2(1,1) b3(-1,1) b4(-1,-1) : z plane 2
  private float z = 2.0f;
  private final float[] mTriangleVerticesData = {
    // 1. front-triangles
    // f1,f2,f3
    1, 1, 0, -1, 1, 0, -1, -1, 0, // f3,f4,f1
    -1, -1, 0, 1, -1, 0, 1, 1, 0, // 2. back-triangles
    // b1,b2,b3
    1, -1, z, 1, 1, z, -1, 1, z, // b3,b4,b1
    -1, 1, z, -1, -1, z, 1, -1, z, // 3. right-triangles
    // b2,f1,f4
    1, 1, z, 1, 1, 0, 1, -1, 0, // b2,f4,b1
    1, 1, z, 1, -1, 0, 1, -1, z, // 4. left-triangles
    // b3, f2, b4
    -1, 1, z, -1, 1, 0, -1, -1, z, // b4 f2 f3
    -1, -1, z, -1, 1, 0, -1, -1, 0, // 5. top-triangles
    // b2, b3, f2
    1, 1, z, -1, 1, z, -1, 1, 0, // b2, f2, f1
    1, 1, z, -1, 1, 0, 1, 1, 0, // 6. bottom-triangles
    // b1, b4, f3
    1, -1, z, -1, -1, z, -1, -1, 0, // b1, f3, f4
    1, -1, z, -1, -1, 0, 1, -1, 0

    /*
     */          
  };

  // f1(1,1) f2(0,1) f3(0,0) f4(1,0) 
  // b1(1,0) b2(1,1) b3(0,1) b4(0,0)
  private final float[] mTextureData = {
    // 1. front-triangles
    // f1,f2,f3
    1, 0, 0, 0, 0, 1, // f3,f4,f1
    0, 1, 1, 1, 1, 0, // 2. back-triangles
    // b1,b2,b3
    1, 0, 1, 1, 0, 1, // b3,b4,b1
    0, 1, 0, 0, 1, 0, // 3. right-triangles
    // b2,f1,f4
    1, 1, 0, 1, 0, 0, // b2,f4,b1
    1, 1, 0, 0, 1, 0, // 4. left-triangles
    // b3, f2, b4
    0, 1, 1, 1, 0, 0, // b4 f2 f3
    0, 0, 1, 1, 1, 0, // 5. top-triangles
    // b2, b3, f2
    1, 1, 0, 1, 0, 0, // b2, f2, f1
    1, 1, 0, 0, 1, 0, // 6. bottom-triangles
    // b1, b4, f3
    1, 1, 0, 1, 0, 0, // b1, f3, f4
    1, 1, 0, 0, 1, 0

    /*
     */          
  };
  public CubeRenderer(Context context) {
    super(context,null,null,R.drawable.cube_texture);
    // Turn java points to native buffer points
    setupVertexBuffer();
    // Turn java texture points to native buffer texture
    // points.
    setupTextureBuffer();
  }
    
  // Convert to a native buffer
  private void setupTextureBuffer() {
    // Allocate and handle texture buffer
    ByteBuffer vbb1 = ByteBuffer.allocateDirect(mTextureData.length * FLOAT_SIZE_BYTES);

    vbb1.order(ByteOrder.nativeOrder());
    mFTextureBuffer = vbb1.asFloatBuffer();
    mFTextureBuffer.put(mTextureData);
    mFTextureBuffer.position(0);
  }

  // Convert to a native buffer
  private void setupVertexBuffer() {
    // Allocate and handle vertex buffer
    ByteBuffer vbb = ByteBuffer.allocateDirect(mTriangleVerticesData.length * FLOAT_SIZE_BYTES);

    vbb.order(ByteOrder.nativeOrder());
    mFVertexBuffer = vbb.asFloatBuffer();
    mFVertexBuffer.put(mTriangleVerticesData);
    mFVertexBuffer.position(0);
  }

  // Trasfer the vertices from the vertex buffer
  // to the shader.
  private void transferVertexPoints(
      int vertexPositionHandle) {
    GLES20.glVertexAttribPointer(vertexPositionHandle, // bound address
        // in the vertex
        // shader 
        3, // how may floats for this
        // attribute: x, y, z
        GLES20.GL_FLOAT, // what is type of
        // each attribute? 
        false, // not normalized
        0, // stride
        mFVertexBuffer); // local client
    // pointer to data
    // Check to see if this caused any errors
    checkGlError("glVertexAttribPointer maPosition");
        
    // You have to call this to enable the arrays to be 
    // used by glDrawArrays or glDrawElements.
    GLES20.glEnableVertexAttribArray(vertexPositionHandle);
    checkGlError("glEnableVertexAttribArray maPositionHandle");
  }

  // Same as above but for transfering texture attributes
  // Notice how textures and vertices use the same concept
  // of attributes and the same APIs.
  private void transferTexturePoints(
      int texturePositionHandle) {
    GLES20.glVertexAttribPointer(texturePositionHandle, 2, GLES20.GL_FLOAT, false, 0
        ,
        mFTextureBuffer);
    checkGlError("glVertexAttribPointer texture array");
    // mFVertexBuffer.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
    GLES20.glEnableVertexAttribArray(texturePositionHandle);
    checkGlError("glEnableVertexAttribArray textures");
    // this.bindTextureSamplerToTextureId();
  }
    
  // Drawing operation
  @Override
  protected void draw(
      GL10 gl,
      int positionHandle) {
    // Hide the hidden surfaces using these APIs
    GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    GLES20.glDepthFunc(GLES20.GL_LESS);
       
    // Transfer vertices to the shader
    transferVertexPoints(positionHandle);
    // Transfer texture points to the shader
    transferTexturePoints(getTextureHandle());

    // Implement rotation from 0 to 360 degrees
    // Stop when asked and restart when the stopFlag
    // is set to false.
    // Decide what the current angle to apply
    // for rotation is.
    if (stopFlag == true) {
      // stop rotation
      curAngle = stoppedAtAngle;
    } else {
      curAngle += 1.0f;
    }
    if (curAngle > 360) {
      curAngle = 0;
    }
        
    // Tell the base class to start their
    // matrices to unit matrices.
    this.initializeMatrices();
        
    // The order of these model transformations matter
    // Each model transformation is specified with 
    // respect to the last one, and not the very first.
        
    // Center the cube
    this.trnslate(0, 0, -1);
    // Rotate it around y axis
    this.rotate(curAngle, 0, -1, 0);
    // Decenter it to where ever you want
    this.trnslate(0, -2, 2);
        
    // Go ahead calculate the ModelViewMatrix as 
    // we are done with ALL of our model transformations
    this.setupMatrices();
        
    // Call glDrawArrays to use the vertices and draw
    int vertexCount = mTriangleVerticesData.length / 3;

    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, // what primitives to use
        0, // at what point to start
        vertexCount); // Starting there how many points
    // to use
    // Check if there are errors
    checkGlError("glDrawArrays");
  }
    
  // Indicate how big of a viewing volume we desire
  // This is just a simple homegrown class
  // @see FrustumDimensions
  @Override
  protected FrustumDimensions getFrustumDimensions() {
    return FrustumDimensions.getMedium();
  }
  
  // Indicate the fragment shader source code
  @Override
  protected String getFragmentShaderCodeString() {
    return this.getStringFromAssetFile(FRAGMENT_SHADER_FILENAME);
  }

  // Give out the vertex shader source code
  @Override
  protected String getVertexShaderCodeString() {
    return this.getStringFromAssetFile(VERTEX_SHADER_FILENAME);
    // return this.getDefaultVertexShaderCodeString();
  }
  
  // Stop the rotation. Called by a client
  // on a button click or other user actions.
  public void stop() {
    this.stopFlag = true;
    this.stoppedAtAngle = curAngle;
  }

  // Restart the rotation
  public void start() {
    this.stopFlag = false;
    this.curAngle = this.stoppedAtAngle;
  }
}
