package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class SplitBoxNode extends Node {

    public final BoxNode left = new BoxNode();
    public final BoxNode right = new BoxNode();

    public float splitPercent = 50.0f;

    public boolean vertical = false;

    public SplitBoxNode() {
        left.expand = true;
        right.expand = true;
    }

    public SplitBoxNode setSplitPercent(float percent) {
        if (percent <= 0.0f) {
            percent = 0.0f;
        }
        else if (percent >= 100.0f) {
            percent = 100.0f;
        }
        splitPercent = percent;
        return this;
    }

    public SplitBoxNode setVertical(boolean value) {
        vertical = value;
        return this;
    }

    public SplitBoxNode addLeftChild(Node child) {
        left.addChild(child);
        return this;
    }

    public SplitBoxNode addRightChild(Node child) {
        right.addChild(child);
        return this;
    }

    public SplitBoxNode setLeftMargin(float value) {
        left.setMargin(value);
        return this;
    }

    public SplitBoxNode setRightMargin(float value) {
        right.setMargin(value);
        return this;
    }

    @Override
    public void preDraw(OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(gui, window, offsetX, offsetY, containerWidth, containerHeight);

        float actualX = x + offsetX;
        float actualY = y + offsetY;

        if (vertical) {
            float leftHeight = (splitPercent/100.0f) * height;
            float rightHeight = height - leftHeight;
            float rightY = leftHeight + actualY;

            left.preDraw(gui, window, actualX, actualY, width, leftHeight);
            right.preDraw(gui, window, actualX, rightY, width, rightHeight);
        }
        else {
            float leftWidth = (splitPercent/100.0f) * width;
            float rightWidth = width - leftWidth;
            float rightX = leftWidth + actualX;

            left.preDraw(gui, window, actualX, actualY, leftWidth, height);
            right.preDraw(gui, window, rightX, actualY, rightWidth, height);
        }
    }

    @Override
    public void draw(int int_1, int int_2, float float_1, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(int_1, int_2, float_1, gui, offsetX, offsetY, containerWidth, containerHeight);

        float actualX = x + offsetX;
        float actualY = y + offsetY;

        if (vertical) {
            float leftHeight = (splitPercent/100.0f) * height;
            float rightHeight = height - leftHeight;
            float rightY = leftHeight + actualY;

            left.draw(int_1, int_2, float_1, gui, actualX, actualY, width, leftHeight);
            right.draw(int_1, int_2, float_1, gui, actualX, rightY, width, rightHeight);
        }
        else {
            float leftWidth = (splitPercent/100.0f) * width;
            float rightWidth = width - leftWidth;
            float rightX = leftWidth + actualX;

            left.draw(int_1, int_2, float_1, gui, actualX, actualY, leftWidth, height);
            right.draw(int_1, int_2, float_1, gui, rightX, actualY, rightWidth, height);
        }
    }
}
