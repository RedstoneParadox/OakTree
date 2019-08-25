package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class SplitBoxNode extends Node<SplitBoxNode> {

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

    public SplitBoxNode setLeftChild(Node child) {
        left.setChild(child);
        return this;
    }

    public SplitBoxNode setRightChild(Node child) {
        right.setChild(child);
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
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        float rightX = 0.0f;
        float rightY = 0.0f;
        float leftWidth = 0.0f;
        float rightWidth = 0.0f;
        float leftHeight = 0.0f;
        float rightHeight = 0.0f;

        if (vertical) {
            leftWidth = trueWidth;
            leftHeight = (splitPercent/100.0f) * trueHeight;
            rightWidth = trueWidth;
            rightHeight = trueHeight - leftHeight;
            rightX = trueX;
            rightY = leftHeight + trueY;
        }
        else {
            leftWidth = (splitPercent/100.0f) * trueWidth;
            leftHeight = trueHeight;
            rightWidth = trueWidth - leftWidth;
            rightHeight = trueHeight;
            rightX = leftWidth + trueX;
            rightY = trueY;
        }

        left.preDraw(mouseX, mouseY, deltaTime, gui, trueX, trueY, leftWidth, leftHeight);
        right.preDraw(mouseX, mouseY, deltaTime, gui, rightX, rightY, rightWidth, rightHeight);
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);

        left.draw(mouseX, mouseY, deltaTime, gui);
        right.draw(mouseX, mouseY, deltaTime, gui);
    }
}
