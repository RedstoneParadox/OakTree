package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.ControlStyle;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class ButtonControl extends InteractiveControl<ButtonControl> {
	protected boolean toggleable = false;
	protected @NotNull BiConsumer<ControlGui, ButtonControl> onClick = (gui, node) -> {};
	protected @NotNull BiConsumer<ControlGui, ButtonControl> whileHeld = (gui, node) -> {};
	protected @NotNull BiConsumer<ControlGui, ButtonControl> onRelease = (gui, node) -> {};

	private boolean held = false;

	public ButtonControl() {
		this.id = "button";
	}

	public ButtonControl toggleable(boolean toggleable) {
		this.toggleable = toggleable;
		return this;
	}

	public boolean isToggleable() {
		return toggleable;
	}

	public ButtonControl heldStyle(ControlStyle heldStyle) {
		internalTheme.add("self", "held", heldStyle);
		return this;
	}

	public ButtonControl hoverStyle(ControlStyle hoverStyle) {
		internalTheme.add("self", "hover", hoverStyle);
		return this;
	}

	public ButtonControl onClick(@NotNull BiConsumer<ControlGui, ButtonControl> listener) {
		onClick = listener;
		return this;
	}

	public ButtonControl whileHeld(@NotNull BiConsumer<ControlGui, ButtonControl> listener) {
		whileHeld = listener;
		return this;
	}

	public ButtonControl onRelease(@NotNull BiConsumer<ControlGui, ButtonControl> listener) {
		onRelease = listener;
		return this;
	}


	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		if (toggleable) {
			if (isMouseWithin) {
				if (leftMouseClicked) {
					held = !held;

					if (held) {
						onClick.accept(gui, this);
					}
					else {
						onRelease.accept(gui, this);
					}
				}
			}

			if (held) {
				whileHeld.accept(gui, this);
			}
		}
		else if (isMouseWithin) {
			if (leftMouseHeld) {
				if (!held) {
					held = true;
					onClick.accept(gui, this);
				}

				whileHeld.accept(gui, this);
			} else {
				held = false;
				onRelease.accept(gui, this);
			}
		} else if (held) {
			held = false;
			onRelease.accept(gui, this);
		}


		if (held) {
			currentStyle = getStyle(gui.getTheme(), "held");
		}
		else if (isMouseWithin) {
			currentStyle = getStyle(gui.getTheme(), "hover");
		}
		if (currentStyle.blank) {
			currentStyle = getStyle(gui.getTheme(), "base");
		}
	}
}
