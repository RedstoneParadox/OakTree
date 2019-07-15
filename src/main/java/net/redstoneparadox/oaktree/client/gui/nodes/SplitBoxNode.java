package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SplitBoxNode extends Node {

    public final BoxNode left = new BoxNode();
    public final BoxNode right = new BoxNode();

    public float splitPercent = 50.0f;

    public boolean rotated = false;

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

    public SplitBoxNode addLeftChild(Node child) {
        left.addChild(child);
        return this;
    }

    public SplitBoxNode addRightChild(Node child) {
        right.addChild(child);
        return this;
    }

    @Override
    public void preDraw(OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight);

        float actualX = x + offsetX;
        float actualY = y + offsetY;

        if (rotated) {
            throw new NotImplementedException();
        }
        else {
            float leftWidth = (splitPercent/100.0f) * width;
            float rightWidth = width - leftWidth;
            float rightX = leftWidth + actualX;

            left.preDraw(gui, actualX, actualY, leftWidth, height);
            right.preDraw(gui, rightX, actualY, rightWidth, height);
        }
    }

    @Override
    public void draw(int int_1, int int_2, float float_1, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {

        if (rotated) {
            throw new NotImplementedException();
        }
        else {
            float leftWidth = (splitPercent/100.0f) * width;
            float rightWidth = width - leftWidth;

            left.draw(int_1, int_2, float_1, gui, offsetX, offsetY, leftWidth, height);
            right.draw(int_1, int_2, float_1, gui, offsetX, offsetY, rightWidth, height);
        }
    }
}
