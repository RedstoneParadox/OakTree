package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListNode<T extends AbstractListNode> extends Node<T> {

    private final List<BoxNode> listBoxes = new ArrayList<>();

    private float listDimensionLength = 0.0f;
    private NodeDirection drawingDirection;

    protected AbstractListNode(NodeDirection drawingDirection) {
        this.drawingDirection = drawingDirection;
    }

    /**
     * Adds a child node at the specified index.
     *
     * @param index The index of the list.
     * @param child The child {@link Node} to add
     * @return The node itself.
     */
    public T addChild(int index, Node child) {
        if (index >= listBoxes.size()) {
            listBoxes.add(new BoxNode().setChild(child));
        }
        else {
            listBoxes.get(index).setChild(child);
        }
        return (T)this;
    }

    /**
     * Sets whether or not this node should be drawn in
     * the opposite direction.
     *
     * @param shouldReverse The value to set.
     * @return The node itself
     */
    public abstract T setReversed(boolean shouldReverse);

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
    }
}
