package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;

public class SplitBoxNode extends Node<SplitBoxNode> {

    public final BoxNode first = new BoxNode();
    public final BoxNode second = new BoxNode();

    public float splitPercent = 50.0f;

    public boolean vertical = false;

    public SplitBoxNode() {
        first.expand = true;
        second.expand = true;
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

    public SplitBoxNode setFirstChild(Node child) {
        first.setChild(child);
        return this;
    }

    public SplitBoxNode setSecondChild(Node child) {
        second.setChild(child);
        return this;
    }

    public SplitBoxNode setFirstMargin(float value) {
        first.setMargin(value);
        return this;
    }

    public SplitBoxNode setFirstMargin(NodeDirection direction, float margin) {
        first.setMargin(direction, margin);
        return this;
    }

    public SplitBoxNode setSecondMargin(float value) {
        second.setMargin(value);
        return this;
    }

    public SplitBoxNode setSecondMargin(NodeDirection direction, float margin) {
        second.setMargin(direction, margin);
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

        first.preDraw(mouseX, mouseY, deltaTime, gui, trueX, trueY, leftWidth, leftHeight);
        second.preDraw(mouseX, mouseY, deltaTime, gui, rightX, rightY, rightWidth, rightHeight);
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);

        first.draw(mouseX, mouseY, deltaTime, gui);
        second.draw(mouseX, mouseY, deltaTime, gui);
    }
}
