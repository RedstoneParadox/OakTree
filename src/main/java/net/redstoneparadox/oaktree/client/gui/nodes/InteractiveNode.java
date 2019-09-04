package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public abstract class InteractiveNode<T extends InteractiveNode> extends Node<T> {

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        boolean mouseWithin = (mouseX >= trueX && mouseX <= (trueWidth + trueX)) && (mouseY >= trueY && mouseY <= (trueHeight + trueY));
        updateListeners(gui, mouseWithin, mouseX, mouseY, offsetX, offsetY, containerWidth, containerHeight);
    }

    public abstract void updateListeners(OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY, float containerX, float containerY, float containerWidth, float containerHeight);
}
