/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class SchedulerEvent {

    public long execTime;
    protected long time;
    protected boolean repeat;
    protected boolean done = false;

    public SchedulerEvent(long time, boolean repeat) {
        this.time = time;
        this.repeat = repeat;
    }

    public void execute() {
    }

    public long getTime() {
        return time;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isDone() {
        return done;
    }
}
