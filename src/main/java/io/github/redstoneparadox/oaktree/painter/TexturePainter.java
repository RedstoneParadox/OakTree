package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TexturePainter extends Painter {
	Identifier texture;
	int drawLeft = 0;
	int drawTop = 0;
	private boolean tiled;
	private int textureWidth = 0;
	private int textureHeight = 0;
	private Color tint = Color.WHITE;
	private float fileWidth = 0;
	private float fileHeight = 0;
	private float scale = 2;

	public TexturePainter(String path) {
		this.texture = new Identifier(path);
	}
	
	public TexturePainter(Identifier texture) {
		this.texture = texture;
	}

	public TexturePainter drawOrigin(int left, int top) {
		drawLeft = left;
		drawTop = top;
		return this;
	}

	/**
	 * Sets whether or not the StyleBox
	 * should tile its texture.
	 *
	 * @param tiled The value to set.
	 * @return The StyleBox itself.
	 */
	public TexturePainter tiled(boolean tiled) {
		this.tiled = tiled;
		return this;
	}

	public TexturePainter textureSize(int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		return this;
	}

	public TexturePainter fileDimensions(float fileWidth, float fileHeight) {
		this.fileWidth = fileWidth;
		this.fileHeight = fileHeight;
		return this;
	}

	public TexturePainter tint(Color tint) {
		this.tint = tint;
		return this;
	}

	/**
	 * If texture dimensions are wonky, you
	 * may need to change the scale.
	 *
	 * @param scale The value to set.
	 * @return This
	 */
	public TexturePainter scale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public void draw(MatrixStack matrices, int x, int y, int width, int height) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

		if (!tiled) {
			int drawWidth = Math.min(width, textureWidth);
			int drawHeight = Math.min(height, textureHeight);

			drawTexture(x, y, drawLeft, drawTop, drawWidth, drawHeight);
		}
		else {
			drawTiled(x, y, drawLeft, drawTop, textureWidth, textureHeight, width, height);
		}
	}

	@Override
	public TexturePainter copy() {
		TexturePainter copy = new TexturePainter(texture);

		copy.drawOrigin(drawLeft, drawTop);
		copy.tiled(tiled);
		copy.textureSize(textureWidth, textureHeight);
		copy.tint(tint);
		copy.fileDimensions(fileWidth, fileHeight);
		copy.scale(scale);

		return copy;
	}

	void drawTiled(float x, float y, int left, int top, int drawWidth, int drawHeight, int width, int height) {
		int remainingWidth = width;
		int remainingHeight = height;

		while (remainingHeight > 0) {
			float currentX = x + (width - remainingWidth);
			float currentY = y + (height - remainingHeight);

			float minWidth = Math.min(remainingWidth, drawWidth);
			float minHeight = Math.min(remainingHeight, drawHeight);

			drawTexture(currentX, currentY, left, top, minWidth, minHeight);

			remainingWidth -= drawWidth;
			if (remainingWidth < 0) {
				remainingWidth = width;
				remainingHeight -= drawHeight;
			}
		}
	}

	void drawTexture(float x, float y, float left, float top, float width, float height) {
		RenderHelper.drawTexture(x, y, left, top, width, height, fileWidth, fileHeight, scale, texture, tint);
	}
}
