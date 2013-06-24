/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import engine.core.Scheduler;
import engine.core.SchedulerEvent;
import engine.core.TextureManager;
import engine.display.DisplayObject;
import engine.display.Image;
import engine.display.Sprite;
import engine.easing.Tween;
import engine.easing.interpolators.Interpolators;
import game.GameMain;
import game.states.MenuState;
import game.states.play.buildings.City;
import game.states.play.fallingObjects.FallingObject;
import game.states.play.fallingObjects.Meteor;
import java.util.ArrayList;
import net.java.games.input.Component.Identifier;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class PlayState extends Sprite {

    static private PlayState instance;
    private boolean shaking = false;
    public float shakeAmplitude = 10f;
    private float shakingPhaseX = 0f;
    private float shakingPhaseY = 0f;
    private RocketLauncher rocketLaunchers[] = new RocketLauncher[]{
        new RocketLauncher(),
        new RocketLauncher(),
        new RocketLauncher()
    };
    private City cities[] = new City[]{
        new City(),
        new City(),
        new City(),
        new City(),
        new City(),
        new City()
    };
    private Pointer pointer;
    private Scheduler attackScheduler;
    private long fireTime = 0;

    public PlayState() {
        instance = this;

        addChildAt(new Sky(), 0, 0);

        addChildAt(rocketLaunchers[0], 50, 768 - 64);
        addChildAt(rocketLaunchers[1], 512, 768 - 64);
        addChildAt(rocketLaunchers[2], 974, 768 - 64);

        addChildAt(cities[0], 140, 768);
        addChildAt(cities[1], 270, 768);
        addChildAt(cities[2], 400, 768);

        addChildAt(cities[3], 600, 768);
        addChildAt(cities[4], 730, 768);
        addChildAt(cities[5], 860, 768);


        SchedulerEvent startShake = new SchedulerEvent(1000, false) {
            @Override
            public void execute() {
                shaking = true;
                shakeAmplitude = 0f;

                Resources.getInstance().sndRumble.playAsSoundEffect(1, 1, true);

                Tween tw = new Tween(1000) {
                    @Override
                    protected void updateTarget(float phase) {
                        shakeAmplitude = interpolator.interpolate(0, 10, phase);
                    }

                    @Override
                    protected void onFinish() {
                        super.onFinish();

                        Resources.getInstance().sndMusic.playAsMusic(1, 1, true);

                        Tween endShakeTween = new Tween(2000, 2000, Interpolators.LINEAR) {
                            @Override
                            protected void updateTarget(float phase) {
                                shakeAmplitude = interpolator.interpolate(10, 0f, phase);
                            }

                            @Override
                            protected void onFinish() {
                                super.onFinish();
                                shaking = false;
                                startGame();

                            }
                        };

                        updateables.add(endShakeTween);
                    }
                };

                updateables.add(tw);
            }
        };

        Scheduler sc = new Scheduler();
        sc.addEvent(startShake);
        updateables.add(sc);

        pointer = new Pointer();
        addChildAt(pointer, 512, 768 / 2);
    }

    public void shake(long time) {
        if (!shaking) {
            shaking = true;

            Tween finishShakeTween = new Tween(time) {
                @Override
                protected void updateTarget(float phase) {
                    super.updateTarget(phase);
                    shakeAmplitude = interpolator.interpolate(5f, 0, phase);
                }

                @Override
                protected void onFinish() {
                    super.onFinish();
                    shaking = false;
                }
            };
            updateables.add(finishShakeTween);
        }
    }

    public Pointer getPointer() {
        return pointer;
    }

    private City getTargetForMeteor() {
        ArrayList<City> availCities = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            if (!cities[i].isDead()) {
                availCities.add(cities[i]);
            }
        }

        if (availCities.isEmpty()) {
            return null;
        } else {
            return availCities.get((int) (Math.random() * availCities.size()));
        }
    }

    public void startGame() {
        SchedulerEvent ev = new SchedulerEvent(1000, true) {
            @Override
            public void execute() {
                City c = getTargetForMeteor();

                if (c != null) {
                    Meteor fo = new Meteor();
                    addChildAt(fo, (float) Math.random() * 1024, 0);
                    fo.setTarget(new Vector2f(c.getPosition()));
                    fo.setObjectToKill(c);
                } else {
                    done = true;
                    onGameOver();
                }
            }
        };

        attackScheduler = new Scheduler();
        attackScheduler.addEvent(ev);
        updateables.add(attackScheduler);
    }

    public void onGameOver() {
        System.out.println("Game Over");

        for (int i = 0; i < FallingObject.allObjects.size(); i++) {
            FallingObject.allObjects.get(i).hit(1000);
        }

        Image gameOver = new Image(TextureManager.getTexture("game/gameData/sprites/gameover.png"));
        addChildAt(gameOver, 0, 256);
    }

    public void fire() {
        ArrayList<RocketLauncher> readyLaunchers = new ArrayList<>();
        RocketLauncher launcherToFire;

        for (int i = 0; i < rocketLaunchers.length; i++) {
            if (rocketLaunchers[i].isReady()) {
                readyLaunchers.add(rocketLaunchers[i]);
            }
        }

        if (readyLaunchers.size() > 0) {
            int minL = 1024;
            int minInd = 0;

            for (int i = 0; i < readyLaunchers.size(); i++) {
                int curL = (int) Math.abs(pointer.getPosition().x - readyLaunchers.get(i).getPosition().x);
                if (curL < minL) {
                    minInd = i;
                    minL = curL;
                }
            }
            launcherToFire = readyLaunchers.get(minInd);
            launcherToFire.fire(new Vector2f(pointer.getPosition()));
        }
    }

    @Override
    public void onKeyDown(int key) {
        super.onKeyDown(key);
        if (key == Keyboard.KEY_SPACE) {
            fire();
        }

        if (key == Keyboard.KEY_ESCAPE) {
            pause();
        }
    }

    @Override
    public void onGamePadButton(Identifier id, float data) {
        super.onGamePadButton(id, data);

        if (id.getName().equals("0")) {
            if (fireTime == 0) {
                fire();
                fireTime = 250;
            }
        }

        if (id.getName().equals("1")) {
            pause();
        }
    }

    public void pause() {
        if (Resources.getInstance().sndMusic.isPlaying()) {
            Resources.getInstance().sndMusic.stop();
        }
        GameMain.getInstance().setState(MenuState.getInstance());
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (fireTime > 0) {
            fireTime -= deltaTime;
            if (fireTime < 0) {
                fireTime = 0;
            }
        }

        if (shaking) {
            position.x = (float) (Math.sin(shakingPhaseX) * shakeAmplitude);
            position.y = (float) (Math.cos(shakingPhaseY) * shakeAmplitude);

            shakingPhaseX += (float) (Math.random() * (Math.PI / 2));
            shakingPhaseY += (float) (Math.random() * (Math.PI / 2));
        }
    }

    @Override
    public void onAdded(DisplayObject toParent) {
        super.onAdded(toParent);
        Resources.getInstance().sndMenu.stop();
    }

    public static PlayState getInstance() {
        if (instance == null) {
            return new PlayState();
        } else {
            return instance;
        }
    }
}
