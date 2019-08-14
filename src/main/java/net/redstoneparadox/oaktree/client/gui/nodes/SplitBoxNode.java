package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

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
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, window, offsetX, offsetY, containerWidth, containerHeight);

        float actualX = x + offsetX;
        float actualY = y + offsetY;

        if (vertical) {
            float leftHeight = (splitPercent/100.0f) * height;
            float rightHeight = height - leftHeight;
            float rightY = leftHeight + actualY;

            left.preDraw(mouseX, mouseY, deltaTime, gui, window, actualX, actualY, width, leftHeight);
            right.preDraw(mouseX, mouseY, deltaTime, gui, window, actualX, rightY, width, rightHeight);
        }
        else {
            float leftWidth = (splitPercent/100.0f) * width;
            float rightWidth = width - leftWidth;
            float rightX = leftWidth + actualX;

            left.preDraw(mouseX, mouseY, deltaTime, gui, window, actualX, actualY, leftWidth, height);
            right.preDraw(mouseX, mouseY, deltaTime, gui, window, rightX, actualY, rightWidth, height);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        ScreenVec anchorOffset = anchorAlignment.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = drawAlignment.getOffset(width, height);

        float actualX = x + anchorOffset.x + offsetX - drawOffset.x;
        float actualY = y + anchorOffset.y + offsetY - drawOffset.y;

        if (vertical) {
            float leftHeight = (splitPercent/100.0f) * height;
            float rightHeight = height - leftHeight;
            float rightY = leftHeight + actualY;

            left.draw(mouseX, mouseY, deltaTime, gui, actualX, actualY, width, leftHeight);
            right.draw(mouseX, mouseY, deltaTime, gui, actualX, rightY, width, rightHeight);
        }
        else {
            float leftWidth = (splitPercent/100.0f) * width;
            float rightWidth = width - leftWidth;
            float rightX = leftWidth + actualX;

            left.draw(mouseX, mouseY, deltaTime, gui, actualX, actualY, leftWidth, height);
            right.draw(mouseX, mouseY, deltaTime, gui, rightX, actualY, rightWidth, height);
        }
    }
}
