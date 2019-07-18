package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

/**
 * BoxNodes can have a child and margins.
 */
public class BoxNode extends Node {

    public float topMargin = 0.0f;
    public float bottomMargin = 0.0f;
    public float leftMargin = 0.0f;
    public float rightMargin = 0.0f;

    public Node child = null;

    public BoxNode setMargin(float margin) {
        if (margin <= 0.0f) {
            margin = 0.0f;
        }

        topMargin = margin;
        bottomMargin = margin;
        leftMargin = margin;
        rightMargin = margin;
        return this;
    }

    public BoxNode addChild(Node newChild) {
        child = newChild;
        return this;
    }

    @Override
    public void preDraw(OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(gui, window, offsetX, offsetY, containerWidth, containerHeight);
        float actualX = x + leftMargin + offsetX;
        float actualY = y + topMargin + offsetY;

        float actualWidth = width - leftMargin - rightMargin;
        float actualHeight = height - topMargin - bottomMargin;

        if (child != null) {
            child.preDraw(gui, window, actualX, actualY, actualWidth, actualHeight);
        }
    }

    @Override
    public void draw(int int_1, int int_2, float float_1, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(int_1, int_2, float_1, gui, offsetX, offsetY, containerWidth, containerHeight);
        float actualWidth = width - leftMargin - rightMargin;
        float actualHeight = height - topMargin - bottomMargin;

        float actualX = x + leftMargin + offsetX;
        float actualY = y + topMargin + offsetY;

        if (child != null) {
            child.draw(int_1, int_2, float_1, gui, actualX, actualY, actualWidth, actualHeight);
        }
    }
}
