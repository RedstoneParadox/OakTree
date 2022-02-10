package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.painter.Painter;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class ButtonControl extends InteractiveControl implements MouseButtonListener {
	protected boolean toggleable = false;
	protected @NotNull Consumer<ControlGui> onClick = (gui) -> {};
	protected @NotNull Consumer<ControlGui> whileHeld = (gui) -> {};
	protected @NotNull Consumer<ControlGui> onRelease = (gui) -> {};
	private boolean mouseClicked = false;
	private boolean mouseHeld = false;
	private boolean buttonHeld = false;

	public ButtonControl() {
		this.id = "button";
	}

	public void setToggleable(boolean toggleable) {
		this.toggleable = toggleable;
	}

	public boolean isToggleable() {
		return toggleable;
	}

	public void onClick(@NotNull Consumer<ControlGui> onClick) {
		this.onClick = onClick;
	}

	public void whileHeld(@NotNull Consumer<ControlGui> whileHeld) {
		this.onClick = whileHeld;
	}

	public void onRelease(@NotNull Consumer<ControlGui> onRelease) {
		this.onRelease = onRelease;
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
						onClick.accept(gui);
					}
					else {
						onRelease.accept(gui);
					}
				}
			}

			if (buttonHeld) {
				whileHeld.accept(gui);
			}
		}
		else if (isMouseWithin) {
			if (mouseHeld) {
				if (!buttonHeld) {
					buttonHeld = true;
					onClick.accept(gui);
				}

				whileHeld.accept(gui);
			} else {
				buttonHeld = false;
				onRelease.accept(gui);
			}
		} else if (buttonHeld) {
			buttonHeld = false;
			onRelease.accept(gui);
		}


		if (buttonHeld) {
			currentStyle = getPainter(gui.getTheme(), "held");
		}
		else if (isMouseWithin) {
			currentStyle = getPainter(gui.getTheme(), "hover");
		}
		if (currentStyle.blank) {
			currentStyle = getPainter(gui.getTheme(), "base");
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
