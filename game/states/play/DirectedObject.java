/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.display.DisplayObject;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class DirectedObject extends PlayObject {

    protected Vector2f initPoint = null;
    protected Vector2f vector = null;
    protected Vector2f target = null;
    protected float speed = 1;
    private int steps = 0;
    private int currentStep = 0;
    protected long exhaustInterval = 300;
    protected long exhaustTime = 0;
    protected boolean exhausts = false;

    public DirectedObject() {
        super();
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        initPoint = new Vector2f(position);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        if (vector != null) {
            if (target != null) {
                if (currentStep < steps) {
                    position.x = initPoint.x + vector.x * currentStep;
                    position.y = initPoint.y + vector.y * currentStep;
                    currentStep++;
                } else {
                    onTargetReached();
                }
            } else {
                position.x += vector.x;
                position.y += vector.y;
            }
        }

        if ((parent != null) && (exhausts)) {
            if (exhaustTime == 0) {
                Trail t = getTrail();
                Vector2f tp = getTrailPosition();
                parent.addChildAt(t, tp.x, tp.y);
                exhaustTime = exhaustInterval;
            } else {
                exhaustTime -= deltaTime;
                if (exhaustTime < 0) {
                    exhaustTime = 0;
                }
            }
        }
    }

    public Vector2f getTarget() {
        return target;
    }

    public void setTarget(Vector2f target) {
        this.target = target;
        calcVectorTo(target);
        steps = (int) Math.floor(vector.length() / speed);
        singleVector(speed);
    }

    public void calcVectorTo(Vector2f targetPoint) {
        calcVectorTo(targetPoint, false, 0);
    }

    public void calcVectorTo(Vector2f targetPoint, boolean makeSingle, float normal) {
        setVector(new Vector2f(targetPoint.x - position.x, targetPoint.y - position.y));
        if (makeSingle) {
            singleVector(normal);
        }
    }

    private void singleVector(float normal) {
        float l = vector.length();
        vector.x = (vector.x / l) * normal;
        vector.y = (vector.y / l) * normal;
    }

    public Vector2f getVector() {
        return vector;
    }

    public void setVector(Vector2f vector) {
        this.vector = vector;
    }

    protected void onTargetReached() {
    }

    protected Vector2f getTrailPosition() {
        return new Vector2f(position);
    }

    protected Trail getTrail() {
        return new Trail();
    }
}
