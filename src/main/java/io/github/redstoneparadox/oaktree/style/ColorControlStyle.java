package io.github.redstoneparadox.oaktree.style;

import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.client.util.math.MatrixStack;

public class ColorControlStyle extends ControlStyle {
	private Color color;
	private Color borderColor;
	private int borderWidth;

	public ColorControlStyle(Color color, Color borderColor, int borderWidth) {
		this.color = color;
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
	}

	public ColorControlStyle(Color color) {
		this(color, null, 1);
	}

	@Override
	public void draw(MatrixStack matrices, int x, int y, int width, int height) {
		if (borderColor != null) RenderHelper.drawRectangle(matrices,x - borderWidth, y - borderWidth, width + 2 * borderWidth, height + 2* borderWidth, color);
		RenderHelper.drawRectangle(matrices, x, y, width, height, color);
	}

	@Override
	public ColorControlStyle copy() {
		return new ColorControlStyle(color, borderColor, borderWidth);
	}
}
