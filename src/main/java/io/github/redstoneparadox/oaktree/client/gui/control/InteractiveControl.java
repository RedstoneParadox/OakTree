package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public abstract class InteractiveControl<C extends InteractiveControl<C>> extends Control<C> {

    protected boolean isMouseWithin = false;

    protected ScreenVec relativeMousePosition(int mouseX, int mouseY) {
        return new ScreenVec(mouseX - trueX, mouseY - trueY);
    }

    /**
     * INTERNAL API!!! DO NOT CALL!!!
     * @param isMouseWithin [DATA EXPUNGED]
     */
    @Deprecated
    public void setMouseWithin(boolean isMouseWithin) {
        this.isMouseWithin = isMouseWithin;
    }
}
