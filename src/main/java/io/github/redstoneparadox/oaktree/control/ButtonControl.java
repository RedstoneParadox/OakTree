package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.style.ControlStyle;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ButtonControl extends InteractiveControl implements MouseButtonListener {
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

	public void setToggleable(boolean toggleable) {
		this.toggleable = toggleable;
	}

	public boolean isToggleable() {
		return toggleable;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ButtonControl toggleable(boolean toggleable) {
		this.toggleable = toggleable;
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ButtonControl heldStyle(ControlStyle heldStyle) {
		internalTheme.add("self", "held", heldStyle);
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ButtonControl hoverStyle(ControlStyle hoverStyle) {
		internalTheme.add("self", "hover", hoverStyle);
		return this;
	}

	public void onClick(@NotNull Consumer<ControlGui> onClick) {
		this.onClick = ((controlGui, cControl) -> onClick.accept(controlGui));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ButtonControl onClick(@NotNull BiConsumer<ControlGui, ButtonControl> listener) {
		onClick = listener;
		return this;
	}

	public void whileHeld(@NotNull Consumer<ControlGui> whileHeld) {
		this.onClick = ((controlGui, cControl) -> whileHeld.accept(controlGui));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ButtonControl whileHeld(@NotNull BiConsumer<ControlGui, ButtonControl> listener) {
		whileHeld = listener;
		return this;
	}

	public void onRelease(@NotNull Consumer<ControlGui> onRelease) {
		this.onRelease = ((controlGui, cControl) -> onRelease.accept(controlGui));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
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
