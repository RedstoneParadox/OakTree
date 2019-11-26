package redstoneparadox.oaktree.client.gui.control;

import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.ScreenVec;

public abstract class InteractiveControl<T extends InteractiveControl> extends Control<T> {

    protected boolean isMouseWithin = false;

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        isMouseWithin = (mouseX >= trueX && mouseX <= (trueWidth + trueX)) && (mouseY >= trueY && mouseY <= (trueHeight + trueY));
    }

    protected ScreenVec relativeMousePosition(float mouseX, float mouseY) {
        return new ScreenVec(mouseX - trueX, mouseY - trueY);
    }
}
