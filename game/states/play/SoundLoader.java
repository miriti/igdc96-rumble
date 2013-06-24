/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states.play;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class SoundLoader implements Runnable {

    public boolean loaded = false;
    private final PlayState _to;

    public SoundLoader(PlayState to) {
        _to = to;
    }

    @Override
    public void run() {
    }
}
