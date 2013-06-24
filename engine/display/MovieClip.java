/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.display;

import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class MovieClip extends Image {

    protected long frameInterval;
    protected long frameTime;
    protected int currentFrame = 0;
    protected Texture textures[];

    public MovieClip(Texture textures[], long fps) {
        super(textures[0]);

        this.textures = textures;
        frameInterval = (1000 / fps);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (frameTime >= frameInterval) {
            currentFrame++;
            if (currentFrame >= textures.length) {
                currentFrame = 0;
            }
            currentTexture = textures[currentFrame];
            frameTime = 0;
        } else {
            frameTime += deltaTime;
        }
    }
}
