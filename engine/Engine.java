/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import application.EntryPoint;
import engine.core.GameInput;
import engine.core.types.Color;
import engine.display.DisplayObject;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Engine extends DisplayObject {
    
    private static Engine instance = null;
    private DisplayObject _mainClass;
    private int windowWidth;
    private int windowHeight;
    private float sceneWidth;
    private float sceneHeight;
    public static GameInput gameInput = null;
    
    public Engine(DisplayObject mainClass, int windowWidth, int windowHeight) {
        super();
        System.out.println("Initialization...");
        instance = this;
        _mainClass = mainClass;
        try {
            width = windowWidth;
            height = windowHeight;
            this.windowWidth = windowWidth;
            this.windowHeight = windowHeight;
            sceneWidth = windowWidth;
            sceneHeight = windowHeight;
            Display.setDisplayMode(new DisplayMode(windowWidth, windowHeight));
            Display.create();
            
            initOpenGL();
        } catch (LWJGLException ex) {
            Logger.getLogger(EntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setTitle(String title) {
        Display.setTitle(title);
    }
    
    public void start() {
        addChild(_mainClass);
        
        long lastTime = new Date().getTime();
        
        while (!Display.isCloseRequested()) {
            
            long currentTime = new Date().getTime();
            long deltaTime = currentTime - lastTime;
            lastTime = currentTime;
            
            if (deltaTime == 0) {
                continue;
            }
            
            if (gameInput != null) {
                gameInput.update(deltaTime);
            }
            
            super.update(deltaTime);
            
            glClear(GL_COLOR_BUFFER_BIT);// | GL_DEPTH_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            //GLU.gluOrtho2D(0, sceneWidth, sceneHeight, 0);
            glOrtho(0, sceneWidth, sceneHeight, 0, -1000, 1000);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            
            _mainClass.render();
            
            Display.update();
            Display.sync(60);
        }
        
        AL.destroy();
        Display.destroy();
    }
    
    private void initOpenGL() {
        //glEnable(GL_DEPTH_TEST);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        
        setClearColor(Color.CORNFLOWER_BLUE);
    }
    
    public void quit() {
        System.exit(0);
    }
    
    public void setClearColor(Color color) {
        glClearColor(color.r, color.g, color.b, color.a);
    }
    
    public static Engine getInstance() {
        return instance;
    }
    
    public float getSceneWidth() {
        return sceneWidth;
    }
    
    public void setSceneWidth(float sceneWidth) {
        this.sceneWidth = sceneWidth;
    }
    
    public float getSceneHeight() {
        return sceneHeight;
    }
    
    public void setSceneHeight(float sceneHeight) {
        this.sceneHeight = sceneHeight;
    }
    
    public void setSceneSize(float sceneWidth, float sceneHeight) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }
}
