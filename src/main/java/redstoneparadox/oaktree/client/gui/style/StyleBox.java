package redstoneparadox.oaktree.client.gui.style;

import redstoneparadox.oaktree.client.gui.OakTreeGUI;

/**
 * A StyleBox represents the part of the node that actually gets drawn. Multiple Nodes
 * can be drawn as a color or texture, so this class and its children were created to
 * reduce redundancy.
 */
public abstract class StyleBox {

    public abstract void draw(float x, float y, float width, float height, OakTreeGUI gui, boolean mirroredHorizontal, boolean mirroredVertical);

    public final void draw(float x, float y, float width, float height, OakTreeGUI gui) {
        draw(x, y, width, height, gui, false, false);
    }
}
