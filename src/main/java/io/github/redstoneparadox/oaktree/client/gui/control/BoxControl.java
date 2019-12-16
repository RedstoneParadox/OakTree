package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;

/**
 * BoxNode is a type of Node that can have a single child
 * node and internal draw margins.
 */
public class BoxControl extends Control<BoxControl> {

    public float topPadding = 0.0f;
    public float bottomPadding = 0.0f;
    public float leftPadding = 0.0f;
    public float rightPadding = 0.0f;

    private Control child = null;

    /**
     * Sets all 4 internal margins for this BoxNode.
     *
     * @param margin The new margin.
     * @return The node itself.
     */
    public BoxControl padding(float margin) {
        if (margin <= 0.0f) {
            margin = 0.0f;
        }

        topPadding = margin;
        bottomPadding = margin;
        leftPadding = margin;
        rightPadding = margin;
        return this;
    }

    /**
     * Sets one of the four margins based on the passed direction.
     * A direction value is passed so that nodes such as
     * {@link SplitBoxControl} which encapsulate multiple BoxNodes
     * don't have confusing/verbose method names such as
     * `setLeftMarginOfFirst`.
     *
     * @param direction Direction representing the margin to set.
     * @param margin The size of the margin. Values are in pixels.
     * @return the node itself.
     */
    public BoxControl padding(ControlDirection direction, float margin) {
        switch (direction) {
            case UP:
                topPadding = margin;
                break;
            case DOWN:
                bottomPadding = margin;
                break;
            case LEFT:
                leftPadding = margin;
                break;
            case RIGHT:
                rightPadding = margin;
                break;
        }
        return this;
    }

    /**
     * Sets the child node of this node. The child node
     * will be drawn relative to this node within the box
     * created by the internal margins.
     *
     * @param child The node that is being added as a child.
     * @return The node itself.
     */
    public BoxControl child(Control child) {
        this.child = child;
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        float innerWidth = trueWidth - leftPadding - rightPadding;
        float innerHeight = trueHeight - topPadding - bottomPadding;

        float innerX = trueX + leftPadding;
        float innerY = trueY + topPadding;

        if (child != null) {
            child.preDraw(mouseX, mouseY, deltaTime, gui, innerX, innerY, innerWidth, innerHeight);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);

        if (child != null) {
            child.draw(mouseX, mouseY, deltaTime, gui);
        }
    }
}
