/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Resources implements Runnable {

    public Audio sndRumble;
    public Audio sndMusic;
    public Audio sndMenu;
    public Audio sndBoom;
    private static Resources instance;
    public boolean loaded = false;

    public void loadAll() {
        if (!loaded) {
            try {
                sndBoom = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("game/gameData/sfx/boom.ogg"));
                sndRumble = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("game/gameData/sfx/rumble.ogg"));
                sndMusic = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("game/gameData/music/musorgsky.ogg"));
                sndMenu = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("game/gameData/music/menu.ogg"));
                loaded = true;
            } catch (IOException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Resources getInstance() {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }

    @Override
    public void run() {
        loadAll();
    }
}
