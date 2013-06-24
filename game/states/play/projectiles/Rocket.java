/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play.projectiles;

import engine.core.TextureManager;
import engine.display.Image;
import engine.display.MovieClip;
import game.states.play.Explosion;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Rocket extends Projectile {

    private Image img;

    public Rocket() {
        super();
    }

    @Override
    protected void onDeath() {
        Explosion ex = new Explosion(50);
        parent.addChildAt(ex, position.x, position.y);

        super.onDeath();
    }

    @Override
    protected void initImage() {
        Texture frames[] = new Texture[]{
            TextureManager.getTexture("game/gameData/sprites/rocket/rocket0001.png"),
            TextureManager.getTexture("game/gameData/sprites/rocket/rocket0002.png"),
            TextureManager.getTexture("game/gameData/sprites/rocket/rocket0003.png")
        };
        img = new MovieClip(frames, 24);
        addChildAt(img, -img.getWidth() / 2, -img.getHeight() / 2);
        exhausts = true;
    }

    @Override
    public void setTarget(Vector2f target) {
        super.setTarget(target);
        rotation = (float) (Math.atan2(vector.y, vector.x) * (180f / Math.PI)) + 90f;
    }

    @Override
    protected void onTargetReached() {
        parent.addChildAt(new Explosion(), position.x, position.y);
        super.onTargetReached();
    }
}
