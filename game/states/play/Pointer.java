/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.Engine;
import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.Image;
import engine.display.Quad;
import engine.display.Sprite;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Pointer extends Sprite {

    public Pointer() {
        Image crosshair = new Image(TextureManager.getTexture("game/gameData/sprites/crosshair.png"));
        addChildAt(crosshair, -crosshair.getWidth() / 2, -crosshair.getHeight() / 2);
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        position.x += Engine.gameInput.getAxisX().get(0) * 10f;
        position.y += Engine.gameInput.getAxisY().get(0) * 10f;

        if (position.x < 0) {
            position.x = 0;
        }

        if (position.y < 0) {
            position.y = 0;
        }

        if (position.x > Engine.getInstance().getSceneWidth()) {
            position.x = Engine.getInstance().getSceneWidth();
        }

        if (position.y > Engine.getInstance().getSceneHeight() - 100) {
            position.y = Engine.getInstance().getSceneHeight() - 100;
        }
    }
}
