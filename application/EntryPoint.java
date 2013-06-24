/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import engine.Engine;
import game.GameMain;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class EntryPoint {
    
    public static EntryPoint entryPoint;
    private Engine engineMain;
    
    public static void main(String[] args) {
        entryPoint = new EntryPoint();
    }
    
    public EntryPoint() {
        engineMain = new Engine(new GameMain(), 1024, 768);
        engineMain.setSceneSize(1024, 768);
        engineMain.setTitle("RUMBLE - [a game by Michael «KEFIR» Miriti special for igdc.ru contest #96 «Missile Command» 6-16 jun. 2013]");
        engineMain.start();
    }
}
