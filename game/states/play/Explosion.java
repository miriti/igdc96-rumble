/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Quad;
import game.states.play.fallingObjects.FallingObject;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Explosion extends DisplayObject {
    
    public static float defaultRadius = 70;
    private float radius;
    private float currentRadius = 0;
    private Quad[] clumps;
    private int clumpsDiv = 4;
    private boolean killing = true;
    
    public Explosion(float rad) {
        super();
        init(rad);
    }
    
    public Explosion() {
        init(defaultRadius);
    }
    
    private void init(float rad) {
        radius = rad;
        
        clumps = new Quad[360 / clumpsDiv];
        
        for (int i = 0; i < 360 / clumpsDiv; i++) {
            clumps[i] = new Quad(5, 5, new Color(0xf2 / 256f, 0xcf / 256f, 0x66 / 256f));
            addChild(clumps[i]);
        }
    }
    
    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        Resources.getInstance().sndBoom.playAsSoundEffect(1, 1, false);
        PlayState.getInstance().shake(1000);
    }
    
    @Override
    public void update(long deltaTime) {
        currentRadius += 2;
        
        for (int i = 0; i < 360 / clumpsDiv; i++) {
            if (killing) {
                float r = (float) ((i * clumpsDiv) * (Math.PI / 180f));
                clumps[i].getPosition().x = (float) (Math.sin(r) * currentRadius);
                clumps[i].getPosition().y = (float) (Math.cos(r) * currentRadius);
            } else {
                clumps[i].getPosition().y += 0.2;
                clumps[i].getColor().a -= 0.05f;
                if (clumps[i].getColor().a <= 0) {
                    parent.removeChild(this);
                    return;
                }
            }
        }
        
        if ((currentRadius >= radius) && (killing)) {
            killing = false;
        }
        
        if (killing) {
            if (FallingObject.allObjects != null) {
                for (int i = FallingObject.allObjects.size() - 1; i >= 0; i--) {
                    FallingObject fo = FallingObject.allObjects.get(i);
                    Vector2f lv = new Vector2f(position.x - fo.getPosition().x, position.y - fo.getPosition().y);
                    if (Math.abs(lv.length()) <= currentRadius + fo.getRadius()) {
                        fo.hit(100);
                    }
                }
            }
        }
    }
}
