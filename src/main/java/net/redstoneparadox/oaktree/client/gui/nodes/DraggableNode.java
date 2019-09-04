package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;

public class DraggableNode extends InteractiveNode<DraggableNode> {

    private NodeFunction<DraggableNode> onClick = (gui, node) -> {};
    private NodeFunction<DraggableNode> whileHeld = (gui, node) -> {};
    private NodeFunction<DraggableNode> onRelease = (gui, node) -> {};

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

    @Override
    public void updateListeners(OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY, float containerX, float containerY, float containerWidth, float containerHeight) {
        if (mouseWithin) {
            if (gui.mouseButtonHeld("left") && !held) {
                held = true;
                onClick.invoke(gui, this);
            }
            else if (held && gui.mouseButtonHeld("left")) {
                whileHeld.invoke(gui, this);
            }
            else if (held && !gui.mouseButtonHeld("left")) {
                held = false;
                onRelease.invoke(gui, this);
            }

            if (held) {
                trueX = clampPosition(containerX, (float) (mouseX - trueX), containerX + containerWidth);
                trueY = clampPosition(containerY, (float) (mouseY - trueY), containerY + containerHeight);
            }
        }
        else if (held) {
            held = false;
            onRelease.invoke(gui, this);
        }
    }

    private float clampPosition(float lowest, float target, float highest) {
        if (target <= lowest) return lowest;
        else if (target >= highest) return highest;
        return target;
    }
}
