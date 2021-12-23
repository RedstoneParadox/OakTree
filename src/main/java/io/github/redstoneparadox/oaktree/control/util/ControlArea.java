package io.github.redstoneparadox.oaktree.control.util;

import io.github.redstoneparadox.oaktree.math.Rectangle;
import io.github.redstoneparadox.oaktree.util.ClickFunction;
import org.jetbrains.annotations.NotNull;

public final class ControlArea {
	private final Rectangle rect;
	private final ClickFunction onClick;

	private ControlArea(Rectangle rect, ClickFunction onClick) {
		this.rect = rect;
		this.onClick = onClick;
	}

	public static ControlArea regular(int x, int y, int width, int height) {
		return new ControlArea(new Rectangle(x, y, width, height), (mouseX, mouseY) -> {});
	}

	public static ControlArea clickable(int x, int y, int width, int height, @NotNull ClickFunction onClick) {
		return new ControlArea(new Rectangle(x, y, width, height), onClick);
	}

	public void updateArea(int x, int y, int width, int height) {
		rect.x = x;
		rect.y = y;
		rect.width = width;
		rect.height = height;
	}

	public void updateSize(int width, int height) {
		rect.width = width;
		rect.height = height;
	}

	public void updatePosition(int x, int y) {
		rect.x = x;
		rect.y = y;
	}

	public Rectangle getRect() {
		return rect;
	}

	public boolean captureMouse(int mouseX, int mouseY) {
		if (!rect.isPointWithin(mouseX, mouseY)) return false;

		onClick.onClick(mouseX, mouseY);

		return true;
	}
}
