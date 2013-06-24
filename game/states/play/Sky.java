/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.core.Scheduler;
import engine.core.SchedulerEvent;
import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.DisplayObject;
import engine.display.Image;
import engine.display.Quad;
import engine.display.Sprite;
import engine.easing.Tween;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Sky extends Sprite {

    private final Image redSky;
    private final Image blueSky;

    public Sky() {
        blueSky = new Image(TextureManager.getTexture("game/gameData/sprites/menu/bg.png"));
        redSky = new Image(TextureManager.getTexture("game/gameData/sprites/red_sky.png"));

        blueSky.setWidth(1044);
        blueSky.setHeight(788);

        redSky.setWidth(1044);
        redSky.setHeight(788);

        addChild(redSky);
        addChild(blueSky);

        blueSky.getPosition().x = redSky.getPosition().x = -10f;
        blueSky.getPosition().y = redSky.getPosition().y = -10f;


        updateables.add(new Scheduler().addEvent(new SchedulerEvent(1000, false) {
            @Override
            public void execute() {
                updateables.add(new Tween(5000) {
                    @Override
                    protected void updateTarget(float phase) {
                        blueSky.getColor().a = 1f - phase;
                    }
                });
            }
        }));
    }
}
