package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.client.util.math.MatrixStack;

public class ColorPainter extends Painter {
	private Color color;
	private Color borderColor;
	private int borderWidth;

	public ColorPainter(Color color, Color borderColor, int borderWidth) {
		this.color = color;
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
	}

	public ColorPainter(Color color) {
		this(color, null, 1);
	}

	@Override
	public void draw(MatrixStack matrices, int x, int y, int width, int height) {
		if (borderColor != null) RenderHelper.drawRectangle(matrices,x - borderWidth, y - borderWidth, width + 2 * borderWidth, height + 2* borderWidth, color);
		RenderHelper.drawRectangle(matrices, x, y, width, height, color);
	}

	@Override
	public ColorPainter copy() {
		return new ColorPainter(color, borderColor, borderWidth);
	}
}
