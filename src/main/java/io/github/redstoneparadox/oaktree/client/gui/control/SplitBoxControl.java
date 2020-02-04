package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;

import java.awt.*;

@Deprecated
public class SplitBoxControl extends Control<SplitBoxControl> {

    private final BoxControl first = new BoxControl();
    private final BoxControl second = new BoxControl();

    public float splitPercent = 50.0f;

    public boolean vertical = false;

    public SplitBoxControl() {
        first.expand = true;
        second.expand = true;
    }

    @Deprecated
    public SplitBoxControl splitPercent(float percent) {
        if (percent <= 0.0f) {
            percent = 0.0f;
        }
        else if (percent >= 100.0f) {
            percent = 100.0f;
        }
        splitPercent = percent;
        return this;
    }

    @Deprecated
    public SplitBoxControl setVertical(boolean vertical) {
        this.vertical = vertical;
        return this;
    }

    @Deprecated
    public SplitBoxControl firstChild(Control child) {
        first.child(child);
        return this;
    }

    public Control getFirstChild() {
        return first.child;
    }

    @Deprecated
    public SplitBoxControl secondChild(Control child) {
        second.child(child);
        return this;
    }

    public Control getSecondChild() {
        return second.child;
    }

    @Deprecated
    public SplitBoxControl setFirstMargin(float margin) {
        first.padding(margin);
        return this;
    }

    @Deprecated
    public SplitBoxControl setFirstMargin(ControlDirection direction, float margin) {
        first.padding(direction, margin);
        return this;
    }

    @Deprecated
    public SplitBoxControl setSecondMargin(float margin) {
        second.padding(margin);
        return this;
    }

    @Deprecated
    public SplitBoxControl setSecondMargin(ControlDirection direction, float margin) {
        second.padding(direction, margin);
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        float rightX = 0.0f;
        float rightY = 0.0f;
        float leftWidth = 0.0f;
        float rightWidth = 0.0f;
        float leftHeight = 0.0f;
        float rightHeight = 0.0f;

        if (vertical) {
            leftWidth = width;
            leftHeight = (splitPercent/100.0f) * height;
            rightWidth = width;
            rightHeight = height - leftHeight;
            rightX = trueX;
            rightY = leftHeight + trueY;
        }
        else {
            leftWidth = (splitPercent/100.0f) * width;
            leftHeight = height;
            rightWidth = width - leftWidth;
            rightHeight = height;
            rightX = leftWidth + trueX;
            rightY = trueY;
        }

        first.preDraw(mouseX, mouseY, deltaTime, gui, trueX, trueY, leftWidth, leftHeight);
        second.preDraw(mouseX, mouseY, deltaTime, gui, rightX, rightY, rightWidth, rightHeight);
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);

        first.draw(mouseX, mouseY, deltaTime, gui);
        second.draw(mouseX, mouseY, deltaTime, gui);
    }
}
