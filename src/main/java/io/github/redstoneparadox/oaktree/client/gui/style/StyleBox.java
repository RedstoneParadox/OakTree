package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;

/**
 * A StyleBox represents the part of the node that actually gets drawn. Multiple Nodes
 * can be drawn as a color or texture, so this class and its children were created to
 * reduce redundancy.
 */
public abstract class StyleBox {

    public abstract void draw(int x, int y, int width, int height, ControlGui gui, boolean mirroredHorizontal, boolean mirroredVertical);

    public final void draw(int x, int y, int width, int height, ControlGui gui) {
        draw(x, y, width, height, gui, false, false);
    }
}
