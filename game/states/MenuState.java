/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states;

import engine.Engine;
import engine.core.TextureManager;
import engine.core.types.Color;
import engine.display.Button;
import engine.display.DisplayObject;
import engine.display.Image;
import engine.display.Sprite;
import game.GameMain;
import game.states.play.PlayState;
import game.states.play.Resources;
import net.java.games.input.Component.Identifier;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class MenuState extends Sprite {

    private static MenuState instance;
    private Image rumbleLogo;
    private Button[] buttons;
    private long inputTime;
    private int selectedButton = 0;
    private Vector2f shake = new Vector2f();

    public MenuState() {
        instance = this;
        Image bg = new Image(TextureManager.getTexture("game/gameData/sprites/menu/bg.png"));
        bg.setWidth(1024);
        bg.setHeight(768);
        addChild(bg);

        rumbleLogo = new Image(TextureManager.getTexture("game/gameData/sprites/menu/logo.png"));
        addChild(rumbleLogo);

        //f24b0f
        Color selectedColor = new Color(0xf2 / 256f, 0x4b / 256f, 0x0f / 256f);

        Button btnPlay = new Button(new Image(TextureManager.getTexture("game/gameData/sprites/menu/play.png"))) {
            @Override
            public void onExecute() {
                if (InstructionsState.shown) {
                    GameMain.getInstance().setState(PlayState.getInstance());
                } else {
                    GameMain.getInstance().setState(new InstructionsState());
                }
            }
        };
        btnPlay.setColors(Color.WHITE, selectedColor, Color.BLACK);
        btnPlay.setSelected(true);
        addChildAt(btnPlay, 256, 300);

        Button btnQuit = new Button(new Image(TextureManager.getTexture("game/gameData/sprites/menu/quit.png"))) {
            @Override
            public void onExecute() {
                Engine.getInstance().quit();
            }
        };
        btnQuit.setColors(Color.WHITE, selectedColor, Color.BLACK);
        addChildAt(btnQuit, 256, 400);

        buttons = new Button[]{
            btnPlay, btnQuit
        };

        Image sign = new Image(TextureManager.getTexture("game/gameData/sprites/menu/sign.png"));
        addChildAt(sign, 1024 - sign.getWidth(), 768 - sign.getHeight());
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        if (!Resources.getInstance().sndMenu.isPlaying()) {
            Resources.getInstance().sndMenu.playAsMusic(1, 1, true);
        }
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        rumbleLogo.getPosition().x = (float) Math.sin(shake.x) * 1.5f;
        rumbleLogo.getPosition().y = (float) Math.sin(shake.y) * 1.5f;

        shake.x += (float) (Math.random() * (Math.PI / 2));
        shake.y += (float) (Math.random() * (Math.PI / 2));

        float aY = Engine.gameInput.getAxisY().get(0);
        if (aY != 0) {
            if (inputTime == 0) {
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setSelected(false);
                }

                if (aY > 0) {
                    selectedButton++;
                } else {
                    selectedButton--;
                }

                if (selectedButton < 0) {
                    selectedButton = buttons.length - 1;
                }
                if (selectedButton >= buttons.length) {
                    selectedButton = 0;
                }

                buttons[selectedButton].setSelected(true);

                inputTime = 300;
            } else {
                inputTime -= deltaTime;
                if (inputTime < 0) {
                    inputTime = 0;
                }
            }
        } else {
            inputTime = 0;
        }
    }

    @Override
    public void onGamePadButton(Identifier id, float data) {
        if (id.getName().equals("0")) {
            buttons[selectedButton].onExecute();
        }
    }

    @Override
    public void onKeyDown(int key) {
        super.onKeyDown(key);
        if (key == Keyboard.KEY_RETURN) {
            buttons[selectedButton].onExecute();
        }
    }

    public static MenuState getInstance() {
        if (instance == null) {
            return new MenuState();
        } else {
            return instance;
        }
    }
}
