package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.RenderHelper;
import io.github.redstoneparadox.oaktree.client.geometry.ScreenPos;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
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

			ScreenPos mousePos = relativeMousePosition(mouseX, mouseY);

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

	public void drawTooltip(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		if (tooltip != null) {
			RenderHelper.setzOffset(300);
			tooltip.draw(matrices, mouseX, mouseY, deltaTime, gui);
			RenderHelper.setzOffset(0);
		}
	}

	protected ScreenPos relativeMousePosition(int mouseX, int mouseY) {
		return new ScreenPos(mouseX - trueX, mouseY - trueY);
	}

	@ApiStatus.Internal
	public void setMouseWithin(boolean isMouseWithin) {
		this.isMouseWithin = isMouseWithin;
	}
}
