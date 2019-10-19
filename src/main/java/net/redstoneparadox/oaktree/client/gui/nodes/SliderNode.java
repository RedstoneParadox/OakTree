package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public class SliderNode extends InteractiveNode<SliderNode> {

    private NodeFunction<SliderNode> onDrag = (gui, node) -> {};
    private NodeDirection direction = NodeDirection.RIGHT;
    private final Node dragNode = new Node();

    public SliderNode setDragNodeStyle(StyleBox style) {
        dragNode.setDefaultStyle(style);
        return this;
    }

    public SliderNode setDragNodeSize(float width, float height) {
        dragNode.setSize(width, height);
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        ScreenVec relativeMousePos = relativeMousePosition(mouseX, mouseY);


    }
}
