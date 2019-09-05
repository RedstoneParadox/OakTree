package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;

public class DraggableNode extends InteractiveNode<DraggableNode> {

    private NodeFunction<DraggableNode> onClick = (gui, node) -> {};
    private NodeFunction<DraggableNode> whileHeld = (gui, node) -> {};
    private NodeFunction<DraggableNode> onRelease = (((gui, node) -> {}));

    private boolean held = false;

    public DraggableNode onClick(NodeFunction<DraggableNode> listener) {
        onClick = listener;
        return this;
    }

    public DraggableNode whileHeld(NodeFunction<DraggableNode> listener) {
        whileHeld = listener;
        return this;
    }

    public DraggableNode onRelease(NodeFunction<DraggableNode> listener) {
        onRelease = listener;
        return this;
    }

    private float clampPosition(float lowest, float target, float highest) {
        if (target <= lowest) return lowest;
        else if (target >= highest) return highest;
        return target;
    }
}
