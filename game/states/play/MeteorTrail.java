/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.core.TextureManager;
import engine.display.Image;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class MeteorTrail extends Trail {

    @Override
    protected void initImage() {
        img = new Image(TextureManager.getTexture("game/gameData/sprites/flame_trail.png"));
        ttl = 4000 + (long) (Math.random() * 1000);
        changeScale = false;
    }

    @Override
    protected float getAlpha() {
        if (ttl > 2000) {
            return 1;
        } else {
            return (float) (ttl / 2000f);
        }
    }
}
