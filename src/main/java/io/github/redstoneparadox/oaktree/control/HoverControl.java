package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.style.ControlStyle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HoverControl extends InteractiveControl<HoverControl> {
	protected @NotNull BiConsumer<ControlGui, HoverControl> onMouseEnter = (gui, node) -> {};
	protected @NotNull BiConsumer<ControlGui, HoverControl> mouseExit = (gui, node) -> {};
	protected @NotNull BiConsumer<ControlGui, HoverControl> whileHovered = (gui, node) -> {};

	private boolean mouseCurrentlyWithin = false;

	public HoverControl() {
		this.id = "hover";
	}

	public void setHoverStyle(ControlStyle hoverStyle) {
		internalTheme.add("self", "hover", hoverStyle);
	}

	public ControlStyle getHoverStyle() {
		return internalTheme.get("self/hover");
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public HoverControl hoverStyle(ControlStyle style) {
		internalTheme.add("self", "hover", style);
		return this;
	}

	public void onMouseEnter(@NotNull Consumer<ControlGui> onMouseEnter) {
		this.onMouseEnter = ((controlGui, cControl) -> onMouseEnter.accept(controlGui));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public HoverControl onMouseEnter(@NotNull BiConsumer<ControlGui, HoverControl> listener) {
		onMouseEnter = listener;
		return this;
	}

	public void onMouseExit(@NotNull Consumer<ControlGui> onMouseExit) {
		this.onMouseEnter = ((controlGui, cControl) -> onMouseExit.accept(controlGui));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public HoverControl onMouseExit(@NotNull BiConsumer<ControlGui, HoverControl> listener) {
		mouseExit = listener;
		return this;
	}

	public void whileHovered(@NotNull Consumer<ControlGui> whileHovered) {
		this.onMouseEnter = ((controlGui, cControl) -> whileHovered.accept(controlGui));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public HoverControl whileMouseHovers(@NotNull BiConsumer<ControlGui, HoverControl> listener) {
		whileHovered = listener;
		return this;
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		if (!mouseCurrentlyWithin && isMouseWithin) {
			mouseCurrentlyWithin = true;
			onMouseEnter.accept(gui, this);
		}
		else if (mouseCurrentlyWithin && !isMouseWithin) {
			mouseCurrentlyWithin = false;
			mouseExit.accept(gui, this);
		}

		if (mouseCurrentlyWithin) {
			whileHovered.accept(gui, this);
			currentStyle = getStyle(gui.getTheme(), "hover");
		}

		if (currentStyle.blank) {
			currentStyle = getStyle(gui.getTheme(), "base");
		}
	}
}
