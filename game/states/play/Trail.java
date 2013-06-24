/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.Image;
import engine.display.Quad;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Trail extends PlayObject {

    protected Image img;
    protected long ttl;
    protected float initW;
    protected boolean changeScale = true;

    public Trail() {
        ttl = 1800 + (long) (Math.random() * 400);
        initImage();
        initW = img.getWidth();
        addChildAt(img, -img.getWidth() / 2, -img.getHeight() / 2);
    }

    protected void initImage() {
        img = new Image(TextureManager.getTexture("game/gameData/sprites/exhaust.png"));
    }

    protected float getAlpha() {
        return img.getColor().a - 0.01f;
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        ttl -= deltaTime;

        if (changeScale) {
            scaleX += 0.02f;
            scaleY += 0.02f;
        }
        
        img.getColor().a = getAlpha();

        if (ttl <= 0) {
            if (parent != null) {
                parent.removeChild(this);
            }
        }
    }
}
