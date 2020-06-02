package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;


public abstract class InteractiveControl<C extends InteractiveControl<C>> extends Control<C> {
    public @Nullable Control<?> tooltip = null;
    public boolean lockTooltipPos = false;
    public int tooltipOffsetX = 8;
    public int tooltipOffsetY = -10;

    protected boolean isMouseWithin = false;

    public C tooltip(@Nullable Control<?> tooltip) {
        this.tooltip = tooltip;
        return (C) this;
    }

    @Override
    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
        if (!visible) return;
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

        if (tooltip != null) {
            tooltip.expand = false;

            ScreenVec mousePos = relativeMousePosition(mouseX, mouseY);

            if (isMouseWithin) {
                if (!tooltip.visible || !lockTooltipPos) {
                    tooltip.position(mousePos.x + tooltipOffsetX, mousePos.y + tooltipOffsetY);
                    tooltip.visible = true;
                }
            }
            else {
                tooltip.visible = false;
            }

            tooltip.preDraw(gui, trueX, trueY, 0, 0, mouseX, mouseY);
        }
    }

    @Override
    public void postDraw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        if (tooltip != null) tooltip.draw(matrices, mouseX, mouseY, deltaTime, gui);
    }

    protected ScreenVec relativeMousePosition(int mouseX, int mouseY) {
        return new ScreenVec(mouseX - trueX, mouseY - trueY);
    }

    @ApiStatus.Internal
    public void setMouseWithin(boolean isMouseWithin) {
        this.isMouseWithin = isMouseWithin;
    }
}
