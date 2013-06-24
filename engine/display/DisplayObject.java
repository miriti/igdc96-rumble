/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.display;

import engine.core.DisplayObjectComparator;
import engine.core.types.Color;
import engine.interfaces.Renderable;
import engine.interfaces.Updateable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import net.java.games.input.Component.Identifier;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class DisplayObject implements Renderable {

    protected ArrayList<DisplayObject> children = new ArrayList<>();
    protected ArrayList<Updateable> updateables = new ArrayList<>();
    protected Vector3f position;
    protected float width;
    protected float height;
    protected static float nextZ = 0;
    protected float pivotX = 0;
    protected float pivotY = 0;
    protected float scaleX = 1;
    protected float scaleY = 1;
    public float rotation = 0f;
    protected Color color = null;
    protected DisplayObject parent;
    private Rectangle calcRect = new Rectangle();
    private boolean interactive = true;

    public DisplayObject() {
        position = new Vector3f();
    }

    public DisplayObject(Vector3f newPosition) {
        position = newPosition;
    }

    public DisplayObject(float atX, float atY) {
        position = new Vector3f(atY, atY, 0f);
    }

    public float getPivotX() {
        return pivotX;
    }

    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    public float getPivotY() {
        return pivotY;
    }

    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public DisplayObject getParent() {
        return parent;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Rectangle getRect() {
        int minx = (int) position.x;
        int miny = (int) position.y;

        int maxx = (int) position.x + (int) width;
        int maxy = (int) position.y + (int) height;

        for (int i = 0; i < children.size(); i++) {
            DisplayObject c = children.get(i);

            if (c.getRect().getX() < minx) {
                minx = c.getRect().getX();
            }

            if (c.getRect().getY() < miny) {
                miny = c.getRect().getY();
            }

            if (c.getRect().getX() + c.getRect().getWidth() > maxx) {
                maxx = c.getRect().getX() + c.getRect().getWidth();
            }

            if (c.getRect().getY() + c.getRect().getHeight() > maxy) {
                maxy = (int) c.getRect().getY() + c.getRect().getHeight();
            }
        }

        calcRect.setBounds(minx, miny, maxx, maxy);

        return calcRect;
    }

    public void onAdded(DisplayObject toParent) {
        parent = toParent;
    }

    public void onRemoved(DisplayObject fromParent) {
        parent = null;
    }

    public void onMouseDown(int buttonIndex, int atX, int atY) {
        for (int i = 0; i < children.size(); i++) {
            DisplayObject c = children.get(i);
            if (c.getRect().contains(atX, atY)) {
                c.onMouseDown(buttonIndex, atX - (int) getPosition().x, atY - (int) getPosition().y);
            }
        }
    }

    public void onMouseUp(int buttonIndex, int atX, int atY) {
        for (int i = 0; i < children.size(); i++) {
            DisplayObject c = children.get(i);
            if (c.getRect().contains(atX, atY)) {
                c.onMouseUp(buttonIndex, atX - (int) getPosition().x, atY - (int) getPosition().y);
            }
        }
    }

    public void onKeyDown(int key) {
        for (int i = 0; i < children.size(); i++) {
            DisplayObject c = children.get(i);
            if (c.isInteractive()) {
                c.onKeyDown(key);
            }
        }
    }

    public void onKeyUp(int key) {
        for (int i = 0; i < children.size(); i++) {
            DisplayObject c = children.get(i);
            if (c.isInteractive()) {
                c.onKeyUp(key);
            }
        }
    }

    public void onGamePadButton(Identifier id, float data) {
        for (int i = 0; i < children.size(); i++) {
            DisplayObject c = children.get(i);
            if (c.isInteractive()) {
                c.onGamePadButton(id, data);
            }
        }
    }

    public DisplayObject addChild(DisplayObject child) {
        nextZ += 0.00001f;
        children.add(child);
        child.onAdded(this);
        return child;
    }

    public DisplayObject addChildAt(DisplayObject child, float atX, float atY) {
        child.getPosition().set(atX, atY, nextZ);
        addChild(child);
        if (nextZ > 1) {
            nextZ = 0;
        }
        return child;
    }

    public DisplayObject addChildAt(DisplayObject child, float atX, float atY, float atZ) {
        child.getPosition().set(atX, atY, atZ);
        addChild(child);
        return child;
    }

    public DisplayObject removeChild(DisplayObject child) {
        children.remove(child);
        child.onRemoved(this);
        return child;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public ArrayList<DisplayObject> getChildren() {
        return children;
    }

    protected void selfRender() {
    }

    protected void sortChildren() {
        Collections.sort(children, new DisplayObjectComparator());
    }

    @Override
    public void render() {

        glPushMatrix();
        glTranslatef(position.x, position.y, position.z);
        glScalef(scaleX, scaleY, 1);
        glRotatef(rotation, 0, 0, 1);

        if (color != null) {
            color.glColor();
        }

        selfRender();

        sortChildren();
        for (int i = 0; i < children.size(); i++) {
            DisplayObject current = children.get(i);
            current.render();
        }

        glPopMatrix();
    }

    @Override
    public void update(long deltaTime) {
        for (int i = updateables.size() - 1; i >= 0; i--) {
            Updateable u = updateables.get(i);
            u.update(deltaTime);
            if (u.finished()) {
                updateables.remove(u);
            }
        }

        for (int i = children.size() - 1; i >= 0; i--) {
            DisplayObject current = children.get(i);
            current.update(deltaTime);
        }
    }

    @Override
    public boolean finished() {
        return false;
    }

    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }
}
