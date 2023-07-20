package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.client.gui.GuiGraphics;

public class ColorPainter extends Painter {
	private final Color color;
	private final Color borderColor;
	private final int borderWidth;

	public ColorPainter(Color color) {
		this(color, null, 1);
	}

	public ColorPainter(Color color, Color borderColor) {
		this(color, borderColor, 1);
	}

	public ColorPainter(Color color, Color borderColor, int borderWidth) {
		this.color = color;
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
	}

	@Override
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
		if (borderColor != null) graphics.fill(x - borderWidth, y - borderWidth, x + width + borderWidth, y + height + borderWidth, color.toInt());
		graphics.fill(x, y, x + width, y + height, color.toInt());
	}

	@Override
	public ColorPainter copy() {
		return new ColorPainter(color, borderColor, borderWidth);
	}
}
