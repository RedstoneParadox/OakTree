package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ControlDirection;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class AbstractListControl<T extends AbstractListControl> extends Control<T> {

    private final List<BoxControl> listBoxes = new ArrayList<>();

    private float listDimensionLength = 0.0f;
    private ControlDirection drawingDirection;

    protected AbstractListControl(ControlDirection drawingDirection) {
        this.drawingDirection = drawingDirection;
    }

    /**
     * Adds a child node at the specified index.
     *
     * @param index The index of the list.
     * @param child The child {@link Control} to add
     * @return The node itself.
     */
    public T addChild(int index, Control child) {
        if (index >= listBoxes.size()) {
            listBoxes.add(new BoxControl().setChild(child));
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
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
    }
}
