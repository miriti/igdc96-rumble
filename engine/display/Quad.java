/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.display;

import engine.core.types.Color;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Quad extends Sprite {

    protected Vector2f[] texCoords = new Vector2f[]{
        new Vector2f(0, 0),
        new Vector2f(1, 0),
        new Vector2f(1, 1),
        new Vector2f(0, 1)
    };
    protected Color[] vertexColor;

    public Quad(float quadWidth, float quadHeight, Color quadColor) {
        super();
        vertexColor = new Color[]{
            quadColor,
            quadColor,
            quadColor,
            quadColor
        };
        width = quadWidth;
        height = quadHeight;
        color = quadColor;
    }

    public Color[] getVertexColor() {
        return vertexColor;
    }

    @Override
    protected void selfRender() {
        glBegin(GL_QUADS);

        glTexCoord2f(texCoords[0].x, texCoords[0].y);
        glVertex2f(0, 0);

        glTexCoord2f(texCoords[1].x, texCoords[1].y);
        glVertex2f(width, 0);

        glTexCoord2f(texCoords[2].x, texCoords[2].y);
        glVertex2f(width, height);

        glTexCoord2f(texCoords[3].x, texCoords[3].y);
        glVertex2f(0, height);

        glEnd();
    }
}
