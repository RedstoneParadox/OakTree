package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;

/**
 * BoxNode is a type of Node that can have a single child
 * node and internal draw margins.
 */
public class BoxNode extends Node<BoxNode> {

    public float topMargin = 0.0f;
    public float bottomMargin = 0.0f;
    public float leftMargin = 0.0f;
    public float rightMargin = 0.0f;

    private Node child = null;

    /**
     * Sets all 4 internal margins for this BoxNode.
     *
     * @param margin The new margin.
     * @return The node itself.
     */
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

    /**
     * Sets one of the four margins based on the passed direction.
     * A direction value is passed so that nodes such as
     * {@link SplitBoxNode} which encapsulate multiple BoxNodes
     * don't have confusing/verbose method names such as
     * `setLeftMarginOfFirst`.
     *
     * @param direction Direction representing the margin to set.
     * @param margin The size of the margin. Values are in pixels.
     * @return the node itself.
     */
    public BoxNode setMargin(NodeDirection direction, float margin) {
        switch (direction) {
            case UP:
                topMargin = margin;
                break;
            case DOWN:
                bottomMargin = margin;
                break;
            case LEFT:
                leftMargin = margin;
                break;
            case RIGHT:
                rightMargin = margin;
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
    public BoxNode setChild(Node child) {
        this.child = child;
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        float innerWidth = trueWidth - leftMargin - rightMargin;
        float innerHeight = trueHeight - topMargin - bottomMargin;

        float innerX = trueX + leftMargin;
        float innerY = trueY + topMargin;

        child.preDraw(mouseX, mouseY, deltaTime, gui, innerX, innerY, innerWidth, innerHeight);
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
