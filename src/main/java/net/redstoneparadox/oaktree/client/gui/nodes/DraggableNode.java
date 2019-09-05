package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

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

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        if (isMouseWithin) {
            ScreenVec mousePos = relativeMousePosition(mouseX, mouseY);

            if (isMouseWithin) {
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
                    x = clampPosition(mousePos.x, trueWidth);
                    y = clampPosition(mousePos.y, trueHeight);
                }
            }
            else if (held) {
                held = false;
                onRelease.invoke(gui, this);
            }
        }
    }

    private float clampPosition(float target, float highest) {
        if ((int)target <= 0) return 0;
        else if ((int)target >= (int)highest) return highest;
        return target;
    }
}
