package fm.knight.chesster.view;
import fm.knight.chesster.GameVars;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GameRenderer  implements Renderer{

  private Background background = new Background();
  
  private long loopStart = 0;
  private long loopEnd = 0;
  private long loopRunTime = 0 ;
  
  
  @Override
  public void onDrawFrame(GL10 gl) {
    // TODO Auto-generated method stub
    loopStart = System.currentTimeMillis();
    // TODO Auto-generated method stub
    try {
      if (loopRunTime < GameVars.GAME_THREAD_FPS_SLEEP){
        Thread.sleep(GameVars.GAME_THREAD_FPS_SLEEP - loopRunTime);
      }
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    
    drawBackground1(gl);

    //    movePlayer1(gl);
    
    loopEnd = System.currentTimeMillis();
    loopRunTime = ((loopEnd - loopStart));
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    // TODO Auto-generated method stub
    gl.glViewport(0, 0, width,height);
     
    gl.glMatrixMode(GL10.GL_PROJECTION);
    gl.glLoadIdentity();
    
    gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
    // TODO Auto-generated method stub
    
    gl.glEnable(GL10.GL_TEXTURE_2D);
    gl.glClearDepthf(1.0f);
    gl.glEnable(GL10.GL_DEPTH_TEST);
    gl.glDepthFunc(GL10.GL_LEQUAL);

    background.loadTexture(gl,GameVars.BACKGROUND, GameVars.context);
  }
  
  private void drawBackground1(GL10 gl){
    
    gl.glMatrixMode(GL10.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glPushMatrix();
    gl.glScalef(1f, 1f, 1f);
    //gl.glTranslatef(0f, 0f, 0f);
       
    background.draw(gl);
    gl.glPopMatrix();
    gl.glLoadIdentity();


  }
  

}
