/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.display.DisplayObject;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class PlayObject extends DisplayObject {

    protected float health = 100;
    protected float healthMax = 100;
    protected boolean dead = false;
    protected float radius = 20;

    public PlayObject() {
        setInteractive(false);
    }

    protected void onDeath() {
        parent.removeChild(this);
    }

    public boolean isDead() {
        return dead;
    }

    public float getRadius() {
        return radius;
    }

    public void hit(float pow) {
        if (!dead) {
            health -= pow;
            if (health <= 0) {
                dead = true;
                onDeath();
            }
        }
    }
}
