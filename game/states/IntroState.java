/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states;

import engine.Engine;
import engine.core.GameInput;
import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Image;
import engine.display.Sprite;
import engine.easing.Tween;
import engine.easing.interpolators.Interpolators;
import engine.easing.interpolators.Linear;
import game.GameMain;
import game.states.play.Resources;
import net.java.games.input.Component.Identifier;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class IntroState extends Sprite {
    
    private final Image igdcLogo;
    Tween twnShow;
    
    public IntroState() {
        Engine.getInstance().setClearColor(Color.BLACK);
        width = Engine.getInstance().getSceneWidth();
        height = Engine.getInstance().getSceneHeight();
        igdcLogo = new Image(TextureManager.getTexture("game/gameData/igdc_logo.jpg"));
        igdcLogo.setColor(new Color(1, 1, 1, 0.0f));
        igdcLogo.getPosition().x = (Engine.getInstance().getSceneWidth() - igdcLogo.getWidth()) / 2;
        igdcLogo.getPosition().y = (Engine.getInstance().getSceneHeight() - igdcLogo.getHeight()) / 2;
        addChild(igdcLogo);
        
        twnShow = new Tween(2000) {
            @Override
            protected void updateTarget(float phase) {
                igdcLogo.getColor().a = interpolator.interpolate(0f, 1f, phase);
            }
            
            @Override
            protected void onFinish() {
                super.onFinish();
                
                Tween twnHide = new Tween(2000, 2000, Interpolators.LINEAR) {
                    @Override
                    protected void updateTarget(float phase) {
                        igdcLogo.getColor().a = interpolator.interpolate(1f, 0f, phase);
                    }
                    
                    @Override
                    protected void onFinish() {
                        super.onFinish();
                        next();
                    }
                };
                
                updateables.add(twnHide);
            }
        };
        
        updateables.add(twnShow);
    }
    
    @Override
    public void onMouseDown(int buttonIndex, int atX, int atY) {
        twnShow.kill();
        next();
    }
    
    @Override
    public void onKeyDown(int key) {
        next();
    }
    
    @Override
    public void onGamePadButton(Identifier id, float data) {
        next();
    }
    
    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        Resources.getInstance().sndMenu.playAsMusic(1, 1, true);
    }
    
    public void next() {
        GameMain.getInstance().setState(new MenuState());
    }
}
