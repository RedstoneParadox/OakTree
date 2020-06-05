package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.ControlStyle;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;

import java.util.function.BiConsumer;

public class ButtonControl extends InteractiveControl<ButtonControl> {

	public BiConsumer<ControlGui, ButtonControl> onClick = (gui, node) -> {};
	public BiConsumer<ControlGui, ButtonControl> whileHeld = (gui, node) -> {};
	public BiConsumer<ControlGui, ButtonControl> onRelease = (gui, node) -> {};

	public boolean toggleable = false;

	private boolean held = false;
	private boolean hovered = false;

	public ControlStyle heldStyle = null;
	public ControlStyle hoverStyle = null;

	public ButtonControl() {
		this.id = "button";
	}

	public ButtonControl toggleable(boolean toggleable) {
		this.toggleable = toggleable;
		return this;
	}

	public ButtonControl heldStyle(ControlStyle heldStyle) {
		this.heldStyle = heldStyle;
		internalTheme.add("self", "held", heldStyle);
		return this;
	}

	public ButtonControl hoverStyle(ControlStyle hoverStyle) {
		this.hoverStyle = hoverStyle;
		internalTheme.add("self", "hover", hoverStyle);
		return this;
	}

	public ButtonControl onClick(BiConsumer<ControlGui, ButtonControl> listener) {
		onClick = listener;
		return this;
	}

	public ButtonControl whileHeld(BiConsumer<ControlGui, ButtonControl> listener) {
		whileHeld = listener;
		return this;
	}

	public ButtonControl onRelease(BiConsumer<ControlGui, ButtonControl> listener) {
		onRelease = listener;
		return this;
	}


	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
		if (toggleable) {
			if (isMouseWithin) {
				if (gui.mouseButtonJustClicked("left")) {
					held = !held;

					if (held) {
						whileHeld.accept(gui, this);
					}
					else {
						onRelease.accept(gui, this);
					}
				}
				else if (gui.mouseButtonHeld("left") && held) {
					whileHeld.accept(gui, this);
				}
				else if (!held){
					hovered = true;
				}
			}
		}
		else {
			if (isMouseWithin) {
				if (gui.mouseButtonHeld("left") && !held) {
					held = true;
					onClick.accept(gui, this);
				}
				else if (held && gui.mouseButtonHeld("left")) {
					whileHeld.accept(gui, this);
				}
				else if (held && !gui.mouseButtonHeld("left")) {
					held = false;
					onRelease.accept(gui, this);
				}
				else if (!held) {
					hovered = true;
				}
			}
			else if (held) {
				held = false;
				onRelease.accept(gui, this);
			}
		}


		if (held && heldStyle != null) {
			currentStyle = heldStyle;
		}

		if (hovered && hoverStyle != null) {
			currentStyle = hoverStyle;
		}

		hovered = false;
	}

	@Override
	void applyTheme(Theme theme) {
		super.applyTheme(theme);
		heldStyle = getStyle(theme, "held");
		hoverStyle = getStyle(theme, "hover");
	}
}
