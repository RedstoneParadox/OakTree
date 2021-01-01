package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.hooks.MouseHooks;
import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;


public abstract class InteractiveControl<C extends InteractiveControl<C>> extends Control<C> {
	protected @Nullable Control<?> tooltip = null;
	protected boolean lockTooltipPos = false;
	protected int tooltipOffsetX = 8;
	protected int tooltipOffsetY = -10;
	protected boolean isMouseWithin = false;
	protected boolean leftMouseHeld = false;
	protected boolean leftMouseClicked = false;
	protected boolean rightMouseHeld = false;
	protected boolean rightMouseClicked = false;

	public void setTooltip(@Nullable Control<?> tooltip) {
		this.tooltip = tooltip;
	}

	public @Nullable Control<?> getTooltip() {
		return tooltip;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public C tooltip(@Nullable Control<?> tooltip) {
		this.tooltip = tooltip;
		return (C) this;
	}

	@ApiStatus.Internal
	public boolean isMouseWithin() {
		return isMouseWithin;
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		if (tooltip != null) tooltip.setup(client, gui);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		MouseHooks mouse = getMouse();

		if (mouse.leftButton()) {
			if (leftMouseHeld) {
				leftMouseClicked = false;
			}
			else {
				leftMouseClicked = true;
				leftMouseHeld = true;
			}
		}
		else {
			leftMouseClicked = false;
			leftMouseHeld = false;
		}

		if (mouse.rightButton()) {
			if (rightMouseHeld) {
				rightMouseClicked = false;
			} else {
				rightMouseClicked = true;
				rightMouseHeld = true;
			}
		}

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

		leftMouseClicked = false;
		rightMouseClicked = false;
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

	protected MouseHooks getMouse() {
		return (MouseHooks) MinecraftClient.getInstance().mouse;
	}
}
