package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.client.util.math.MatrixStack;

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
	public void draw(MatrixStack matrices, int x, int y, int width, int height) {
		if (borderColor != null) RenderHelper.drawRectangle(matrices,x - borderWidth, y - borderWidth, width + 2 * borderWidth, height + 2* borderWidth, color);
		RenderHelper.drawRectangle(matrices, x, y, width, height, color);
	}

	@Override
	public ColorPainter copy() {
		return new ColorPainter(color, borderColor, borderWidth);
	}
}
