package io.github.redstoneparadox.oaktree.painter;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;

public class NinePatchPainter extends TexturePainter {
	int leftWidth = 1;
	int centerWidth = 1;
	int rightWidth = 1;

	int topHeight = 1;
	int centerHeight = 1;
	int bottomHeight = 1;

	public NinePatchPainter(String path) {
		super(path);
	}

	public NinePatchPainter(Identifier path) {
		super(path);
	}

	public void setWidths(int firstWidth, int secondWidth, int thirdWidth) {
		this.leftWidth = firstWidth;
		this.centerWidth = secondWidth;
		this.rightWidth = thirdWidth;
	}

	public void setHeights(int firstHeight, int secondHeight, int thirdHeight) {
		this.topHeight = firstHeight;
		this.centerHeight = secondHeight;
		this.bottomHeight = thirdHeight;
	}

	@Override
	public NinePatchPainter copy() {
		NinePatchPainter copy = new NinePatchPainter(texture);

		copy.setDrawOrigin(left, top);
		copy.setTiled(tiled);
		copy.setTextureSize(textureWidth, textureHeight);
		copy.setTint(tint);
		copy.setFileDimensions(fileWidth, fileHeight);
		copy.setScale(scale);

		copy.setWidths(leftWidth, centerWidth, rightWidth);
		copy.setHeights(topHeight, centerHeight, bottomHeight);

		return copy;
	}

	@Override
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
		graphics.drawNineSlicedTexture(texture, x, y, width, height, leftWidth, topHeight, rightWidth, bottomHeight, centerWidth, centerHeight, left, top);
	}
}
