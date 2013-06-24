/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.types;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Color {

    public static final Color BLACK;
    public static final Color WHITE;
    public static final Color CORNFLOWER_BLUE;
    public static final Color RED;

    static {
        BLACK = new Color(0, 0, 0);
        CORNFLOWER_BLUE = new Color(100 / 255f, 149 / 255f, 237 / 255f);
        WHITE = new Color(1, 1, 1);
        RED = new Color(1, 0, 0);
    }
    public float r;
    public float g;
    public float b;
    public float a;

    public Color(float nr, float ng, float nb) {
        r = nr;
        g = ng;
        b = nb;
        a = 1;
    }

    public Color(float nr, float ng, float nb, float na) {
        r = nr;
        g = ng;
        b = nb;
        a = na;
    }

    public Color clone() {
        return new Color(r, g, b, a);
    }

    public void glColor() {
        GL11.glColor4f(r, g, b, a);
    }
}
