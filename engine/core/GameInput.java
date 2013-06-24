/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core;

import engine.Engine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class GameInput {

    private float joystickAxisThreshold = 0.20f;
    private Controller gamepad = null;
    private ArrayList<Float> axisX = new ArrayList<>();
    private ArrayList<Float> axisY = new ArrayList<>();
    private ArrayList<Float> axisZ = new ArrayList<>();
    private boolean isKeyboard;
    private boolean isMouse;
    protected float mouseSensivity = 15f;
    private int mouseX;
    private int mouseY;
    private boolean[] mouseButtonsDown;
    private HashMap<Identifier, Boolean> gamePadButtonsDown = new HashMap<>();

    public GameInput(boolean enableGamepad, boolean enableKeyboard, boolean enableMouse) {
        if (enableGamepad) {
            for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
                if (c.getType() == Controller.Type.GAMEPAD) {
                    System.out.println("Gamepad found: " + c.getName());
                    gamepad = c;
                    System.out.println("Components [" + c.getComponents().length + "]");
                    for (Component cm : c.getComponents()) {
                        gamePadButtonsDown.put(cm.getIdentifier(), false);
                        System.out.println(cm.getIdentifier() + ": " + cm.getName());
                    }
                    System.out.println("Rumblers [" + c.getRumblers().length + "]");
                    break;
                }
            }

        }

        isKeyboard = enableKeyboard;
        isMouse = enableMouse;

        try {
            Keyboard.create();
            Mouse.create();

            int mouseButtonCount = Mouse.getButtonCount();
            mouseButtonsDown = new boolean[mouseButtonCount];

            for (int i = 0; i < mouseButtonCount; i++) {
                mouseButtonsDown[i] = false;
            }
        } catch (LWJGLException ex) {
            Logger.getLogger(GameInput.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (isMouse) {
            Mouse.setGrabbed(true);
        }
    }

    public static boolean haveGamepad() {
        for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            if (c.getType() == Controller.Type.GAMEPAD) {
                return true;
            }
        }
        return false;
    }

    public void justRemoveThisFuckingMouse() {
        Mouse.setGrabbed(true);
    }

    private void resetAxis(boolean fill) {
        axisX.clear();
        axisY.clear();
        axisZ.clear();

        if (fill) {
            axisX.add(0.0f);
            axisX.add(0.0f);

            axisY.add(0.0f);
            axisY.add(0.0f);

            axisZ.add(0.0f);
        }
    }

    public void update(long deltaTime) {
        if (gamepad != null) {
            HashMap<Identifier, Boolean> currentGamePadButtonsDown = new HashMap<>();
            HashMap<Identifier, Float> currentGamePadButtonsData = new HashMap<>();
            resetAxis(false);
            gamepad.poll();
            for (Component c : gamepad.getComponents()) {

                String idName = c.getIdentifier().getName();
                float data = c.getPollData();

                if (Math.abs(data) < joystickAxisThreshold) {
                    data = 0.0f;
                }

                if (idName.equals("x")) {
                    axisX.add(data);
                } else {
                    if (idName.equals("y")) {
                        axisY.add(data);
                    } else {
                        if (idName.equals("z")) {
                            axisZ.add(data);
                        } else {
                            if (data > 0) {
                                currentGamePadButtonsDown.put(c.getIdentifier(), true);
                                currentGamePadButtonsData.put(c.getIdentifier(), data);
                            }
                        }
                    }
                }
            }

            Iterator it = gamePadButtonsDown.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Identifier, Boolean> next = (Map.Entry) it.next();
                boolean wasPressd = next.getValue();
                boolean nowPressd = currentGamePadButtonsDown.get(next.getKey()) == null ? false : true;

                if (wasPressd) {
                    if (!nowPressd) {
                        gamePadButtonsDown.put(next.getKey(), false);
                    }
                }

                if (!wasPressd) {
                    if (nowPressd) {
                        Engine.getInstance().onGamePadButton(next.getKey(), currentGamePadButtonsData.get(next.getKey()));
                        gamePadButtonsDown.put(next.getKey(), true);
                    }
                }
            }
        } else {
            resetAxis(true);
        }

        if (isKeyboard) {

            while (Keyboard.next()) {
                int key = Keyboard.getEventKey();
                if (Keyboard.getEventKeyState()) {
                    Engine.getInstance().onKeyDown(key);
                } else {
                    Engine.getInstance().onKeyUp(key);
                }
            }


            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
                axisX.set(0, -1.0f);
            } else {
                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
                    axisX.set(0, 1.0f);
                }
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
                axisY.set(0, -1.0f);
            } else {
                if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
                    axisY.set(0, 1.0f);
                }
            }
        }

        if (isMouse) {
            axisX.set(0, Mouse.getDX() / mouseSensivity);
            axisY.set(0, -Mouse.getDY() / mouseSensivity);
        } else {
            mouseX = (int) ((float) Mouse.getX() / (float) Engine.getInstance().getWidth() * Engine.getInstance().getSceneWidth());
            mouseY = (int) ((float) (Engine.getInstance().getHeight() - Mouse.getY()) / (float) Engine.getInstance().getHeight() * Engine.getInstance().getSceneHeight());

            int mouseButtonCount = Mouse.getButtonCount();

            for (int i = 0; i < mouseButtonCount; i++) {
                if (Mouse.isButtonDown(i)) {
                    if (!mouseButtonsDown[i]) {
                        Engine.getInstance().onMouseDown(i, mouseX, mouseY);
                    }

                    mouseButtonsDown[i] = true;
                } else {
                    if (mouseButtonsDown[i]) {
                        Engine.getInstance().onMouseUp(i, mouseX, mouseY);
                    }

                    mouseButtonsDown[i] = false;
                }
            }
        }
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public ArrayList<Float> getAxisX() {
        return axisX;
    }

    public ArrayList<Float> getAxisY() {
        return axisY;
    }

    public ArrayList<Float> getAxisZ() {
        return axisZ;
    }

    public float getJoystickAxisThreshold() {
        return joystickAxisThreshold;
    }

    public void setJoystickAxisThreshold(float joystickAxisThreshold) {
        this.joystickAxisThreshold = joystickAxisThreshold;
    }

    public float getMouseSensivity() {
        return mouseSensivity;
    }

    public void setMouseSensivity(float mouseSensivity) {
        this.mouseSensivity = mouseSensivity;
    }
}
