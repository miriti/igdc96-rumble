/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core;

import engine.display.DisplayObject;
import java.util.Comparator;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class DisplayObjectComparator implements Comparator<DisplayObject> {

    @Override
    public int compare(DisplayObject o1, DisplayObject o2) {
        if (o1.getPosition().z == o2.getPosition().z) {
            return 0;
        }
        return (o1.getPosition().z > o2.getPosition().z) ? 1 : -1;
    }
}
