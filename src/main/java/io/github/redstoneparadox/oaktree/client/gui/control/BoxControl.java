package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;

/**
 * BoxControl is a type of PaddingControl that
 * can have a single child node.
 *
 * While {@link PanelControl} could also be used,
 * BoxControl is specialized for dealing with a
 * single child.
 */
public class BoxControl extends PaddingControl<BoxControl> {
    public Control child = null;

    public BoxControl() {
        this.id = "box";
    }

    @Deprecated
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

        float innerWidth = width - leftPadding - rightPadding;
        float innerHeight = height - topPadding - bottomPadding;

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
