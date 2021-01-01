package io.github.redstoneparadox.oaktree.style;

import io.github.redstoneparadox.oaktree.ControlGui;
import net.minecraft.client.MinecraftClient;

public class NinePatchControlStyle extends TextureControlStyle {
	int firstWidth = 1;
	int secondWidth = 1;
	int thirdWidth = 1;

	int firstHeight = 1;
	int secondHeight = 1;
	int thirdHeight = 1;

	public NinePatchControlStyle(String path) {
		super(path);
	}

	public NinePatchControlStyle heights(int firstHeight, int secondHeight, int thirdHeight) {
		this.firstHeight = firstHeight;
		this.secondHeight = secondHeight;
		this.thirdHeight = thirdHeight;
		return this;
	}

	public NinePatchControlStyle widths(int firstWidth, int secondWidth, int thirdWidth) {
		this.firstWidth = firstWidth;
		this.secondWidth = secondWidth;
		this.thirdWidth = thirdWidth;
		return this;
	}

	@Override
	public NinePatchControlStyle copy() {
		NinePatchControlStyle copy = (NinePatchControlStyle) super.copy();

		copy.heights(firstHeight, secondHeight, thirdHeight);
		copy.widths(firstWidth, secondWidth, thirdWidth);

		return copy;
	}

	@Override
	public void draw(int x, int y, int width, int height, ControlGui gui) {
		int fullSecondWidth = width - firstWidth - thirdWidth;
		int fullSecondHeight = height - firstHeight - thirdHeight;

		float secondX = x + firstWidth;
		float secondY = y + firstHeight;
		int secondLeft = drawLeft + firstWidth;
		int secondTop = drawTop + firstHeight;

		float thirdX = x + firstWidth + fullSecondWidth;
		float thirdY = y + firstHeight + fullSecondHeight;
		int thirdLeft = drawLeft + firstWidth + secondHeight;
		int thirdTop = drawTop + firstHeight + secondHeight;

		// Top left
		drawTexture(x, y, drawLeft, drawTop, firstWidth, firstHeight);
		// Top Middle
		drawTiled(secondX, y, secondLeft, drawTop, secondWidth, firstHeight, fullSecondWidth, firstHeight);
		// Top Right
		drawTexture(thirdX, y, thirdLeft, drawTop, thirdWidth, firstHeight);
		// Center Left
		drawTiled(x, secondY, drawLeft, secondTop, firstWidth, secondHeight, firstWidth, fullSecondHeight);
		// Center
		drawTiled(secondX, secondY, secondLeft, secondTop, secondWidth, secondHeight, fullSecondWidth, fullSecondHeight);
		// Center Right
		drawTiled(thirdX, secondY, thirdLeft, secondTop, thirdWidth, secondHeight, thirdWidth, fullSecondHeight);
		// Bottom left
		drawTexture(x, thirdY, drawLeft, thirdTop, firstWidth, thirdHeight);
		// Bottom Middle
		drawTiled(secondX, thirdY, secondLeft, thirdTop, secondWidth, thirdHeight, fullSecondWidth, thirdHeight);
		// Bottom Right
		drawTexture(thirdX, thirdY, thirdLeft, thirdTop, thirdWidth, thirdHeight);
	}
}
