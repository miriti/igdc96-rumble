/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play.buildings;

import engine.core.TextureManager;
import engine.display.Image;
import game.states.play.Explosion;
import game.states.play.PlayObject;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class City extends PlayObject {

    public City() {
        Image img = new Image(TextureManager.getTexture("game/gameData/sprites/city.png"));
        addChildAt(img, -img.getWidth() / 2, -img.getHeight());
    }

    @Override
    protected void onDeath() {
        Explosion e = new Explosion(250);
        parent.addChildAt(e, position.x, position.y);
        super.onDeath();
    }
}
