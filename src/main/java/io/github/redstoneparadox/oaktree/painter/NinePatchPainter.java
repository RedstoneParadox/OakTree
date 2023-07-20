package io.github.redstoneparadox.oaktree.painter;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;

public class NinePatchPainter extends TexturePainter {
	int firstWidth = 1;
	int secondWidth = 1;
	int thirdWidth = 1;

	int firstHeight = 1;
	int secondHeight = 1;
	int thirdHeight = 1;

	public NinePatchPainter(String path) {
		super(path);
	}

	public NinePatchPainter(Identifier path) {
		super(path);
	}

	public void setWidths(int firstWidth, int secondWidth, int thirdWidth) {
		this.firstWidth = firstWidth;
		this.secondWidth = secondWidth;
		this.thirdWidth = thirdWidth;
	}

	public void setHeights(int firstHeight, int secondHeight, int thirdHeight) {
		this.firstHeight = firstHeight;
		this.secondHeight = secondHeight;
		this.thirdHeight = thirdHeight;
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

		copy.setWidths(firstWidth, secondWidth, thirdWidth);
		copy.setHeights(firstHeight, secondHeight, thirdHeight);

		return copy;
	}

	@Override
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
		int fullSecondWidth = width - firstWidth - thirdWidth;
		int fullSecondHeight = height - firstHeight - thirdHeight;

		graphics.drawNineSlicedTexture(texture, x, y, width, height, firstWidth, firstHeight, thirdWidth, thirdHeight, fullSecondWidth, fullSecondHeight, left, top);
	}
}
