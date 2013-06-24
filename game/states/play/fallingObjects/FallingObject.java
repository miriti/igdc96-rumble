/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play.fallingObjects;

import engine.display.DisplayObject;
import engine.display.Quad;
import game.states.play.DirectedObject;
import game.states.play.PlayObject;
import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class FallingObject extends DirectedObject {

    public static ArrayList<FallingObject> allObjects;
    private PlayObject killObject;

    public FallingObject() {
    }

    public void setObjectToKill(PlayObject object) {
        killObject = object;
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        if (allObjects == null) {
            allObjects = new ArrayList<>();
        }
        allObjects.add(this);
    }

    @Override
    public void onRemoved(DisplayObject fromParent) {
        super.onRemoved(fromParent);
        allObjects.remove(this);
    }

    @Override
    protected void onTargetReached() {
        super.onTargetReached();
        parent.removeChild(this);
        if ((killObject != null) && (!killObject.isDead())) {
            killObject.hit(100);
        }
    }
}
