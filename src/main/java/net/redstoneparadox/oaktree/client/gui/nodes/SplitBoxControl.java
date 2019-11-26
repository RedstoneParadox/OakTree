package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ControlDirection;

/**
 * A SplitBoxNode encapsulates two {@link BoxControl} instances,
 * maintaining a percentage-based split between them. The
 * width or height of the first box takes up the specified
 * percentage of the total width while the second box takes
 * up the remainder.
 *
 * The {@link BoxControl#setMargin(float)},
 * {@link BoxControl#setMargin(ControlDirection, float)}, and
 * {@link BoxControl#setChild(Control)} are encapsulated by the
 * SplitBoxNode as well, so there is no need to access
 * them directly.
 */
public class SplitBoxControl extends Control<SplitBoxControl> {

    private final BoxControl first = new BoxControl();
    private final BoxControl second = new BoxControl();

    public float splitPercent = 50.0f;

    public boolean vertical = false;

    public SplitBoxControl() {
        first.expand = true;
        second.expand = true;
    }

    /**
     * Sets the percentage of the split. A split of 50%, for example,
     * will mean that both sides take up half of the space.
     *
     * @param percent The percent of the split. Values are in pixels.
     * @return The node itself.
     */
    public SplitBoxControl setSplitPercent(float percent) {
        if (percent <= 0.0f) {
            percent = 0.0f;
        }
        else if (percent >= 100.0f) {
            percent = 100.0f;
        }
        splitPercent = percent;
        return this;
    }

    /**
     * Sets whether or not this node is oriented vertically. If
     * oriented vertically, the split will be horizontal.
     *
     * @param vertical If the SplitBoxContainer should be
     *                 oriented vertically.
     * @return The node itself.
     */
    public SplitBoxControl setVertical(boolean vertical) {
        this.vertical = vertical;
        return this;
    }

    /**
     * Sets the first child of this node. The first child
     * will be drawn on the left side of the node or the
     * top side if the node is oriented vertically.
     *
     * @param child The first child.
     * @return The node itself.
     */
    public SplitBoxControl setFirstChild(Control child) {
        first.setChild(child);
        return this;
    }

    /**
     * Sets the second child of this node. The second
     * child will be drawn on the right side of the
     * node or the top side if the node is oriented
     * vertically.
     *
     * @param child The second child.
     * @return The node itself.
     */
    public SplitBoxControl setSecondChild(Control child) {
        second.setChild(child);
        return this;
    }

    /**
     * Sets all 4 internal margins of the first box
     * (left or top).
     *
     * @param margin The size of the margin in pixels.
     * @return The node itself
     */
    public SplitBoxControl setFirstMargin(float margin) {
        first.setMargin(margin);
        return this;
    }

    /**
     * Sets one of the 4 internal margins of the first
     * box based on the passed direction.
     *
     * @param direction The direction of the corresponding
     *                  margin.
     * @param margin The size of the margin in pixels.
     * @return The node itself.
     */
    public SplitBoxControl setFirstMargin(ControlDirection direction, float margin) {
        first.setMargin(direction, margin);
        return this;
    }

    /**
     * Sets all 4 internal margins of the second box
     * (left or top).
     *
     * @param margin The size of the margin in pixels.
     * @return The node itself.
     */
    public SplitBoxControl setSecondMargin(float margin) {
        second.setMargin(margin);
        return this;
    }

    /**
     * Sets one of the 4 internal margins of the second
     * box based on the passed direction.
     *
     * @param direction The direction of the corresponding
     *                  margin.
     * @param margin The size of the margin in pixels.
     * @return The node itself.
     */
    public SplitBoxControl setSecondMargin(ControlDirection direction, float margin) {
        second.setMargin(direction, margin);
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
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);

        first.draw(mouseX, mouseY, deltaTime, gui);
        second.draw(mouseX, mouseY, deltaTime, gui);
    }
}
