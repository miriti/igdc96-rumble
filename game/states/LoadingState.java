/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states;

import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class LoadingState extends DisplayObject {
    
    private final Image loading;
    private float phase = 1;
    
    public LoadingState() {
        loading = new Image(TextureManager.getTexture("game/gameData/sprites/loading.png"));
        addChildAt(loading, 0, 320);
    }
    
    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        loading.getColor().a = 0.5f + (float) (1 + Math.sin(phase)) * 0.25f;
        phase += Math.PI / 32;
    }
}
