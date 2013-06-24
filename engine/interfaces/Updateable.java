/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.interfaces;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public interface Updateable {
    void update(long deltaTime);
    boolean finished();
}
