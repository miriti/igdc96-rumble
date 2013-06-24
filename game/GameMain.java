/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import engine.Engine;
import engine.core.GameInput;
import engine.display.DisplayObject;
import engine.display.Sprite;
import game.states.IntroState;
import game.states.LoadingState;
import game.states.play.Resources;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameMain extends Sprite {
    
    private static GameMain instance;
    private DisplayObject currentState = null;
    private boolean loadingData = true;
    
    public GameMain() {
        super();        
        instance = this;
    }    
    
    public void setState(DisplayObject state) {
        if (currentState != null) {
            removeChild(currentState);
        }
        
        currentState = state;
        addChild(currentState);
    }
    
    @Override
    public void onAdded(DisplayObject toParent) {        
        setState(new LoadingState());
        Thread thread = new Thread(Resources.getInstance());
        thread.start();
        //Resources.getInstance().loadAll();
        Engine.gameInput = new GameInput(true, true, false);
        Mouse.setGrabbed(true);
        
    }
    
    @Override
    public void update(long deltaTime) {
        if (loadingData && Resources.getInstance().loaded) {
            loadingData = false;
            setState(new IntroState());
        }
        super.update(deltaTime);
    }
    
    public static GameMain getInstance() {
        return instance;
    }
}
