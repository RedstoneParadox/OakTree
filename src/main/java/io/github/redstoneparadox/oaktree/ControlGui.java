package io.github.redstoneparadox.oaktree;

import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.control.InteractiveControl;
import io.github.redstoneparadox.oaktree.hooks.KeyboardHooks;
import io.github.redstoneparadox.oaktree.hooks.MouseHooks;
import io.github.redstoneparadox.oaktree.hooks.ScreenHooks;
import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.style.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Deprecated
public final class ControlGui {
	private final ScreenHooks screen;
	private final Control root;

	private boolean initialized = false;

	private boolean leftMouseClicked = false;
	private boolean leftMouseHeld = false;

	private boolean rightMouseClicked = false;
	private boolean rightMouseHeld = false;

	private Character lastChar = null;

	private @NotNull Theme theme = Theme.EMPTY;

	public ControlGui(Screen screen, Control root) {
		this.screen = (ScreenHooks)screen;
		this.root = root;

		((KeyboardHooks)(MinecraftClient.getInstance().keyboard)).onCharTyped(character -> this.lastChar = character);
	}

	public void init() {
		if (initialized) return;
		root.setup(screen.getClient(), this);
		initialized = true;
	}

	public void draw(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		if (!initialized || !root.isVisible()) return;

		Window window = screen.getClient().getWindow();
		MouseHooks mouse = (MouseHooks) screen.getClient().mouse;

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
			}
			else {
				rightMouseClicked = true;
				rightMouseHeld = true;
			}
		}
		else {
			rightMouseClicked = false;
			rightMouseHeld = false;
		}

		List<Control> controlList = new ArrayList<>();
		root.zIndex(controlList);
		Collections.reverse(controlList);
		InteractiveControl hovered = null;
		boolean mouseCaptured = false;
		for (Control control: controlList) {
			Vector2 truePos = control.getTruePosition();
			if (control.getArea().offset(truePos.getX(), truePos.getY()).isPointWithin(mouseX, mouseY) && !mouseCaptured) {
				mouseCaptured = true;
				if (control instanceof InteractiveControl) {
					((InteractiveControl)control).setMouseWithin(true);
					hovered = (InteractiveControl) control;
				}
			}
			else {
				if (control instanceof InteractiveControl) {
					((InteractiveControl)control).setMouseWithin(false);
				}
			}
		}

		root.preDraw(this, 0, 0, window.getScaledWidth(), window.getScaledHeight(), mouseX, mouseY);
		root.oldDraw(matrices, mouseX, mouseY, delta, this);
		if (hovered != null) hovered.drawTooltip(matrices, mouseX, mouseY, delta, this);

		screen.setSize(root.getArea().getWidth(), root.getArea().getHeight());

		lastChar = null;
	}

	public void close() {

	}

	public Optional<ScreenHandler> getScreenHandler() {
		return screen.getHandler();
	}

	public @NotNull Theme getTheme() {
		return theme;
	}

	public void applyTheme(@NotNull Theme theme) {
		this.theme = theme;
	}
}
