package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.util.Action;
import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ButtonControl extends Control implements MouseButtonListener {
	public static final PainterKey HELD = new PainterKey();
	public static final PainterKey HOVERED = new PainterKey();

	protected boolean toggleable = false;
	protected @NotNull Action onClick = () -> {};
	protected @NotNull Action whileHeld = () -> {};
	protected @NotNull Action onRelease = () -> {};
	private boolean mouseClicked = false;
	private boolean mouseHeld = false;
	private boolean buttonHeld = false;
	private final LabelControl label = new LabelControl();

	public ButtonControl() {
		this.id = "button";

		label.fitText = true;
		label.capture = false;
		label.setFontColor(Color.WHITE);
		label.setAnchor(Anchor.CENTER);
		label.setParent(this);

		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	public void setToggleable(boolean toggleable) {
		this.toggleable = toggleable;
	}

	public boolean isToggleable() {
		return toggleable;
	}

	public void setText(String text) {
		label.setText(Text.literal(text));
	}

	public void setText(Text text) {
		label.setText(text);
	}

	public Text getText() {
		return label.getText();
	}

	public void onClick(@NotNull Action onClick) {
		this.onClick = onClick;
	}

	public void whileHeld(@NotNull Action whileHeld) {
		this.onClick = whileHeld;
	}

	public void onRelease(@NotNull Action onRelease) {
		this.onRelease = onRelease;
	}

	@Override
	protected void updateTree(List<Control> orderedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(orderedControls, containerX, containerY, containerWidth, containerHeight);
		label.updateTree(orderedControls, trueArea.getX(), trueArea.getY(), trueArea.getWidth(), trueArea.getHeight());
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (toggleable) {
			if (captured) {
				if (mouseClicked) {
					buttonHeld = !buttonHeld;

					if (buttonHeld) {
						onClick.run();
					}
					else {
						onRelease.run();
					}
				}
			}

			if (buttonHeld) {
				whileHeld.run();
			}
		}
		else if (captured) {
			if (mouseHeld) {
				if (!buttonHeld) {
					buttonHeld = true;
					onClick.run();
				}

				whileHeld.run();
			} else {
				buttonHeld = false;
				onRelease.run();
			}
		} else if (buttonHeld) {
			buttonHeld = false;
			onRelease.run();
		}

		if (buttonHeld) {
			painterKey = HELD;
		}
		else if (captured) {
			painterKey = HOVERED;
		}
		else {
			painterKey = DEFAULT;
		}

		return captured;
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		ClientListeners.MOUSE_BUTTON_LISTENERS.remove(this);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			mouseClicked = justPressed && !released;
			mouseHeld = !released;
		}
	}
}
