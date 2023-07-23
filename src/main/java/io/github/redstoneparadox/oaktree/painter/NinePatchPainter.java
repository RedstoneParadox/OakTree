package io.github.redstoneparadox.oaktree.painter;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;

/**
 * A {@code TexturePainter} capable of drawing nine-patch
 * textures.
 */
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

	/**
	 * Sets the slice widths of the nine-patch
	 *
	 * @param leftWidth The left slice width.
	 * @param centerWidth The center slice width.
	 * @param rightWidth The right slice width.
	 */
	public void setWidths(int leftWidth, int centerWidth, int rightWidth) {
		this.leftWidth = leftWidth;
		this.centerWidth = centerWidth;
		this.rightWidth = rightWidth;
	}

	/**
	 * Sets the slice heights of the nine-patch
	 *
	 * @param topHeight The top slice height.
	 * @param centerHeight The center slice height.
	 * @param bottomHeight The bottom slice height.
	 */
	public void setHeights(int topHeight, int centerHeight, int bottomHeight) {
		this.topHeight = topHeight;
		this.centerHeight = centerHeight;
		this.bottomHeight = bottomHeight;
	}

	@Override
	public NinePatchPainter copy() {
		NinePatchPainter copy = new NinePatchPainter(texture);

		copy.setOrigin(u, v);
		copy.setTiled(tiled);
		copy.setRegionSize(regionWidth, regionHeight);
		copy.setTint(tint);
		copy.setTextureSize(textureWidth, textureHeight);

		copy.setWidths(leftWidth, centerWidth, rightWidth);
		copy.setHeights(topHeight, centerHeight, bottomHeight);

		return copy;
	}

	@Override
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
		graphics.drawNineSlicedTexture(
				texture,
				x,
				y,
				width,
				height,
				leftWidth,
				topHeight,
				rightWidth,
				bottomHeight,
				centerWidth + rightWidth + leftWidth,
				centerHeight + topHeight + bottomHeight,
				u,
				v
		);
	}
}
