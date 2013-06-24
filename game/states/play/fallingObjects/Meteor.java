/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play.fallingObjects;

import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.MovieClip;
import engine.display.Quad;
import game.states.play.Explosion;
import game.states.play.MeteorTrail;
import game.states.play.Trail;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Meteor extends FallingObject {

    private final MovieClip mov;

    public Meteor() {
        Texture frames[] = new Texture[]{
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0001.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0002.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0003.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0004.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0005.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0006.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0007.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0008.png"),
            TextureManager.getTexture("game/gameData/sprites/meteor/meteor0009.png")
        };
        mov = new MovieClip(frames, 12);
        addChildAt(mov, -32, -235);
        exhausts = true;
        exhaustInterval = 100;
        radius = 15;
    }

    @Override
    protected void onDeath() {
        Explosion e = new Explosion(50);
        parent.addChildAt(e, position.x, position.y);
        super.onDeath();
    }

    @Override
    public void setTarget(Vector2f target) {
        super.setTarget(target);
        rotation = 180 + (float) (Math.atan2(vector.y, vector.x) * (180f / Math.PI)) + 90f;
    }

    @Override
    protected Vector2f getTrailPosition() {
        Vector2f ret = new Vector2f(position);
        ret.x += -20 + (int) (Math.random() * 40);
        ret.y += -20 + (int) (Math.random() * 40);
        return ret;
    }

    @Override
    protected Trail getTrail() {
        return new MeteorTrail();
    }
}
