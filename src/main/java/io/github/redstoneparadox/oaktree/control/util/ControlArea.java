package io.github.redstoneparadox.oaktree.control.util;

import io.github.redstoneparadox.oaktree.math.Rectangle;
import io.github.redstoneparadox.oaktree.util.ClickFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ControlArea {
	private final Rectangle rect;
	private final @Nullable ClickFunction onClick;

	private ControlArea(Rectangle rect, @Nullable ClickFunction onClick) {
		this.rect = rect;
		this.onClick = onClick;
	}

	public static ControlArea regular(int x, int y, int width, int height) {
		return new ControlArea(new Rectangle(x, y, width, height), null);
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

	public boolean onClick(int mouseX, int mouseY) {
		if (mouseX < rect.x || mouseY > rect.x + rect.width || mouseY < rect.y || mouseY > rect.y + rect.width) {
			return false;
		}

		if (onClick != null) onClick.onClick(mouseX, mouseY);

		return true;
	}
}
