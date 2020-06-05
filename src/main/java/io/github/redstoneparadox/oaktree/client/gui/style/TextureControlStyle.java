package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.RenderHelper;
import io.github.redstoneparadox.oaktree.client.gui.Color;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class TextureControlStyle extends ControlStyle {
	Identifier textureID;
	int drawLeft = 0;
	int drawTop = 0;
	private boolean tiled;
	private int textureWidth = 0;
	private int textureHeight = 0;
	private Color tint = Color.WHITE;
	private float fileWidth = 0;
	private float fileHeight = 0;
	private float scale = 2;

	public TextureControlStyle(String path) {
		textureID = new Identifier(path);
	}

	public TextureControlStyle drawOrigin(int left, int top) {
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
	public TextureControlStyle tiled(boolean tiled) {
		this.tiled = tiled;
		return this;
	}

	public TextureControlStyle textureSize(int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		return this;
	}

	public TextureControlStyle fileDimensions(float fileWidth, float fileHeight) {
		this.fileWidth = fileWidth;
		this.fileHeight = fileHeight;
		return this;
	}

	public TextureControlStyle tint(Color tint) {
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
	public TextureControlStyle scale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public void draw(int x, int y, int width, int height, ControlGui gui) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(textureID);

		if (!tiled) {
			int drawWidth = Math.min((int)width, textureWidth);
			int drawHeight = Math.min((int)height, textureHeight);

			drawTexture(x, y, drawLeft, drawTop, drawWidth, drawHeight);

			RenderHelper.drawTexture(x, y, drawLeft, drawTop, drawWidth, drawHeight, fileWidth, fileHeight, scale, textureID, tint);
		}
		else {
			drawTiled(x, y, drawLeft, drawTop, textureWidth, textureHeight, (int)width, (int)height);
		}
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
		RenderHelper.drawTexture(x, y, left, top, width, height, fileWidth, fileHeight, scale, textureID, tint);
	}
}
