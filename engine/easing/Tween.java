/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.easing;

import engine.display.DisplayObject;
import engine.easing.interpolators.Interpolatable;
import engine.easing.interpolators.Linear;
import engine.interfaces.Updateable;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Tween implements Updateable {
    protected long timeDelay = 0;
    protected long timeDelayPassed = 0;
    protected long timePassed = 0;
    protected long timeTarget = 0;
    protected Interpolatable interpolator;
    private boolean tweenFinished = false;

    public Tween(long msTime, long msDelay, Interpolatable useInterpolator) {
        timeDelay = msDelay;
        timeTarget = msTime;
        interpolator = useInterpolator;
    }

    public Tween(long msTime, Interpolatable useInterpolator) {
        timeTarget = msTime;
        interpolator = useInterpolator;
    }

    public Tween(long msTime) {
        timeTarget = msTime;
        interpolator = new Linear();
    }

    protected void updateTarget(float phase) {
    }

    @Override
    public void update(long deltaTime) {
        if (timeDelayPassed < timeDelay) {
            timeDelayPassed += deltaTime;
        } else {
            timePassed += deltaTime;
            if (timePassed > timeTarget) {
                timePassed = timeTarget;
                onFinish();
            }
            float phase = (float) timePassed / (float) timeTarget;
            updateTarget(phase);
        }
    }

    public void kill() {
        tweenFinished = true;
    }

    protected void onFinish() {
        kill();
    }

    @Override
    public boolean finished() {
        return tweenFinished;
    }
}
