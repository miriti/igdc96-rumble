/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.Image;
import engine.display.Quad;
import engine.display.Sprite;
import game.states.play.projectiles.Projectile;
import game.states.play.projectiles.Rocket;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class RocketLauncher extends Sprite {

    private Sprite barrel;
    private long fireTime = 0;
    private long ammo = 100;
    private final Image body;
    private float aimRotation = 0;

    public RocketLauncher() {
        setInteractive(false);
        body = new Image(TextureManager.getTexture("game/gameData/sprites/launcher/launcher_body.png"));
        addChildAt(body, -body.getWidth() / 2, -body.getHeight() / 2);
        barrel = new Sprite() {
            private Quad body;

            {
                body = new Image(TextureManager.getTexture("game/gameData/sprites/launcher/launcher_head.png"));
                addChildAt(body, -32, -32);
            }
        };


        addChildAt(barrel, 0, 0);
    }

    protected void produceRocket(Vector2f to) {
        Rocket p = new Rocket();
        parent.addChildAt(p, position.x, position.y - 32);
        p.setTarget(to);
    }

    public void fire(Vector2f to) {
        if (fireTime == 0) {
            produceRocket(to);
            //fireTime = 1000;
        }
    }

    public boolean isReady() {
        return (fireTime == 0) && (ammo > 0);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        Pointer p = ((PlayState) parent).getPointer();

        barrel.rotation = (float) ((float) Math.atan2(position.y - p.getPosition().y, position.x - p.getPosition().x) * (180f / Math.PI)) - 90f;

        if (fireTime > 0) {
            fireTime -= deltaTime;
            if (fireTime < 0) {
                fireTime = 0;
            }
        }
    }
}
