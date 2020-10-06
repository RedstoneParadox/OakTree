package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.RenderHelper;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.math.Vector2;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;


public abstract class InteractiveControl<C extends InteractiveControl<C>> extends Control<C> {
	protected @Nullable Control<?> tooltip = null;
	protected boolean lockTooltipPos = false;
	protected int tooltipOffsetX = 8;
	protected int tooltipOffsetY = -10;

	protected boolean isMouseWithin = false;

	public C tooltip(@Nullable Control<?> tooltip) {
		this.tooltip = tooltip;
		return (C) this;
	}

	public @Nullable Control<?> getTooltip() {
		return tooltip;
	}

	@ApiStatus.Internal
	public boolean isMouseWithin() {
		return isMouseWithin;
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		if (tooltip != null) {
			tooltip.expand = false;

			Vector2 mousePos = relativeMousePosition(mouseX, mouseY);

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
		if (tooltip != null && tooltip.isVisible()) {
			RenderHelper.setzOffset(300);
			tooltip.draw(matrices, mouseX, mouseY, deltaTime, gui);
			RenderHelper.setzOffset(0);
		}
	}

	protected Vector2 relativeMousePosition(int mouseX, int mouseY) {
		return new Vector2(mouseX - trueX, mouseY - trueY);
	}

	@ApiStatus.Internal
	public void setMouseWithin(boolean isMouseWithin) {
		this.isMouseWithin = isMouseWithin;
	}
}
