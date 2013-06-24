/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states;

import engine.core.GameInput;
import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import engine.display.Sprite;
import game.GameMain;
import game.states.play.PlayState;
import net.java.games.input.Component.Identifier;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class InstructionsState extends Sprite {

    public static boolean shown = false;

    public InstructionsState() {
        Image instr = new Image(TextureManager.getTexture("game/gameData/instructions.jpg"));
        instr.setWidth(1024);
        instr.setHeight(768);
        addChild(instr);
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        shown = true;
    }

    @Override
    public void onMouseDown(int buttonIndex, int atX, int atY) {
        next();
    }

    @Override
    public void onKeyDown(int key) {
        next();
    }

    @Override
    public void onGamePadButton(Identifier id, float data) {
        switch (id.getName()) {
            case "0":
                next();
                break;
            case "1":
                GameMain.getInstance().setState(MenuState.getInstance());
                break;
        }
    }

    private void next() {
        GameMain.getInstance().setState(PlayState.getInstance());
    }
}
