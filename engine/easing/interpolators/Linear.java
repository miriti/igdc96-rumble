/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.easing.interpolators;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Linear implements Interpolatable {

    @Override
    public float interpolate(float a, float b, float p) {
        return a + (b - a) * p;
    }
}
