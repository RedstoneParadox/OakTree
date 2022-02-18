package io.github.redstoneparadox.oaktree.painter;

import net.minecraft.client.util.math.MatrixStack;

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
		NinePatchPainter copy = (NinePatchPainter) super.copy();

		copy.setWidths(firstWidth, secondWidth, thirdWidth);
		copy.setHeights(firstHeight, secondHeight, thirdHeight);

		return copy;
	}

	@Override
	public void draw(MatrixStack matrices, int x, int y, int width, int height) {
		int fullSecondWidth = width - firstWidth - thirdWidth;
		int fullSecondHeight = height - firstHeight - thirdHeight;

		float secondX = x + firstWidth;
		float secondY = y + firstHeight;
		int secondLeft = left + firstWidth;
		int secondTop = top + firstHeight;

		float thirdX = x + firstWidth + fullSecondWidth;
		float thirdY = y + firstHeight + fullSecondHeight;
		int thirdLeft = left + firstWidth + secondHeight;
		int thirdTop = top + firstHeight + secondHeight;

		// Top left
		drawTexture(x, y, left, top, firstWidth, firstHeight);
		// Top Middle
		drawTiled(secondX, y, secondLeft, top, secondWidth, firstHeight, fullSecondWidth, firstHeight);
		// Top Right
		drawTexture(thirdX, y, thirdLeft, top, thirdWidth, firstHeight);
		// Center Left
		drawTiled(x, secondY, left, secondTop, firstWidth, secondHeight, firstWidth, fullSecondHeight);
		// Center
		drawTiled(secondX, secondY, secondLeft, secondTop, secondWidth, secondHeight, fullSecondWidth, fullSecondHeight);
		// Center Right
		drawTiled(thirdX, secondY, thirdLeft, secondTop, thirdWidth, secondHeight, thirdWidth, fullSecondHeight);
		// Bottom left
		drawTexture(x, thirdY, left, thirdTop, firstWidth, thirdHeight);
		// Bottom Middle
		drawTiled(secondX, thirdY, secondLeft, thirdTop, secondWidth, thirdHeight, fullSecondWidth, thirdHeight);
		// Bottom Right
		drawTexture(thirdX, thirdY, thirdLeft, thirdTop, thirdWidth, thirdHeight);
	}
}
