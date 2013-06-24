/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class TextureManager {

    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String path) {
        if (!textures.containsKey(path)) {
            try {
                String pathElements[] = path.split("\\.");
                String extension = pathElements[pathElements.length - 1].toUpperCase();
                Texture newTexture = TextureLoader.getTexture(extension, ResourceLoader.getResourceAsStream(path));
                textures.put(path, newTexture);
            } catch (IOException ex) {
                Logger.getLogger(TextureManager.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        }

        return textures.get(path);
    }
}
