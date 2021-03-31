package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.style.ControlStyle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HoverControl extends InteractiveControl {
	protected @NotNull Consumer<ControlGui> onMouseEnter = (gui) -> {};
	protected @NotNull Consumer<ControlGui> mouseExit = (gui) -> {};
	protected @NotNull Consumer<ControlGui> whileHovered = (gui) -> {};

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

	public void onMouseEnter(@NotNull Consumer<ControlGui> onMouseEnter) {
		this.onMouseEnter = onMouseEnter;
	}

	public void onMouseExit(@NotNull Consumer<ControlGui> onMouseExit) {
		this.onMouseEnter = onMouseExit;
	}

	public void whileHovered(@NotNull Consumer<ControlGui> whileHovered) {
		this.onMouseEnter = whileHovered;
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		if (!mouseCurrentlyWithin && isMouseWithin) {
			mouseCurrentlyWithin = true;
			onMouseEnter.accept(gui);
		}
		else if (mouseCurrentlyWithin && !isMouseWithin) {
			mouseCurrentlyWithin = false;
			mouseExit.accept(gui);
		}

		if (mouseCurrentlyWithin) {
			whileHovered.accept(gui);
			currentStyle = getStyle(gui.getTheme(), "hover");
		}

		if (currentStyle.blank) {
			currentStyle = getStyle(gui.getTheme(), "base");
		}
	}
}
