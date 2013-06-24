/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.display;

import engine.Engine;
import engine.core.types.Color;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Button extends DisplayObject {

    protected Color hoverColor = Color.WHITE;
    protected Color normalColor = Color.WHITE;
    protected Color downColor = Color.WHITE;
    private Image upState;

    public Button(Image upState) {
        this.upState = upState;
        addChild(upState);
    }

    public void onExecute() {
    }

    public void setSelected(boolean selected) {
        if (selected) {
            upState.color = hoverColor;
        } else {
            upState.color = normalColor;
        }
    }

    public void setColors(Color normal, Color hover, Color down) {
        normalColor = normal;
        hoverColor = hover;
        downColor = down;
    }
}
