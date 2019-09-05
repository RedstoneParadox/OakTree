package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public abstract class InteractiveNode<T extends InteractiveNode> extends Node<T> {

    protected boolean isMouseWithin = false;

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        isMouseWithin = (mouseX >= trueX && mouseX <= (trueWidth + trueX)) && (mouseY >= trueY && mouseY <= (trueHeight + trueY));
    }

}
