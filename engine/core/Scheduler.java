/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core;

import engine.interfaces.Updateable;
import java.util.ArrayList;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Scheduler implements Updateable {

    private ArrayList<SchedulerEvent> events = new ArrayList<>();
    private long timePassed;

    public Scheduler addEvent(SchedulerEvent e) {
        events.add(e);
        return this;
    }

    @Override
    public void update(long deltaTime) {
        timePassed += deltaTime;
        for (int i = events.size() - 1; i >= 0; i--) {
            SchedulerEvent e = events.get(i);
            if (e.isDone()) {
                events.remove(e);
            } else {
                if (timePassed - e.execTime >= e.getTime()) {
                    e.execute();
                    if (e.isRepeat()) {
                        e.execTime = timePassed;
                    } else {
                        events.remove(e);
                    }
                }
            }
        }
    }

    @Override
    public boolean finished() {
        return false;
    }
}
