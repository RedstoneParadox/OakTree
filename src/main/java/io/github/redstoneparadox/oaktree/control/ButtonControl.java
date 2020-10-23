package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.style.ControlStyle;
import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;

public class ButtonControl extends InteractiveControl<ButtonControl> implements MouseButtonListener {
	protected boolean toggleable = false;
	protected @NotNull BiConsumer<ControlGui, ButtonControl> onClick = (gui, node) -> {};
	protected @NotNull BiConsumer<ControlGui, ButtonControl> whileHeld = (gui, node) -> {};
	protected @NotNull BiConsumer<ControlGui, ButtonControl> onRelease = (gui, node) -> {};
	private boolean mouseClicked = false;
	private boolean mouseHeld = false;
	private boolean buttonHeld = false;

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
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		if (toggleable) {
			if (isMouseWithin) {
				if (mouseClicked) {
					buttonHeld = !buttonHeld;

					if (buttonHeld) {
						onClick.accept(gui, this);
					}
					else {
						onRelease.accept(gui, this);
					}
				}
			}

			if (buttonHeld) {
				whileHeld.accept(gui, this);
			}
		}
		else if (isMouseWithin) {
			if (mouseHeld) {
				if (!buttonHeld) {
					buttonHeld = true;
					onClick.accept(gui, this);
				}

				whileHeld.accept(gui, this);
			} else {
				buttonHeld = false;
				onRelease.accept(gui, this);
			}
		} else if (buttonHeld) {
			buttonHeld = false;
			onRelease.accept(gui, this);
		}


		if (buttonHeld) {
			currentStyle = getStyle(gui.getTheme(), "held");
		}
		else if (isMouseWithin) {
			currentStyle = getStyle(gui.getTheme(), "hover");
		}
		if (currentStyle.blank) {
			currentStyle = getStyle(gui.getTheme(), "base");
		}
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			mouseClicked = justPressed && !released;
			mouseHeld = !released;
		}
	}
}
