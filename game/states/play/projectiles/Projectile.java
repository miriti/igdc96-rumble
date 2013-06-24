/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play.projectiles;

import engine.core.types.Color;
import engine.display.Quad;
import game.states.play.DirectedObject;
import game.states.play.Trail;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Projectile extends DirectedObject {

    private Vector2f targetPoint;

    public Projectile() {
        initImage();
        speed = 5f;
        exhaustInterval = 100;
    }

    protected void initImage() {
        Quad tmpQuad = new Quad(10, 10, new Color(1, 1, 0));
        addChildAt(tmpQuad, -5, -5);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void onTargetReached() {
        parent.removeChild(this);
    }
}
