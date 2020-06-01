package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public abstract class InteractiveControl<C extends InteractiveControl<C>> extends Control<C> {
    public @Nullable Control<?> tooltip = null;
    public boolean lockTooltipPos = false;

    protected boolean isMouseWithin = false;

    @Override
    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);


    }

    protected ScreenVec relativeMousePosition(int mouseX, int mouseY) {
        return new ScreenVec(mouseX - trueX, mouseY - trueY);
    }
    
    @ApiStatus.Internal
    public void setMouseWithin(boolean isMouseWithin) {
        this.isMouseWithin = isMouseWithin;
    }
}
