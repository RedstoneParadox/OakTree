package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TexturePainter extends Painter {
	Identifier texture;
	int left = 0;
	int top = 0;
	private boolean tiled;
	private int textureWidth = 0;
	private int textureHeight = 0;
	private Color tint = Color.WHITE;
	private int fileWidth = 256;
	private int fileHeight = 256;
	private float scale = 2;

	public TexturePainter(String path) {
		this.texture = new Identifier(path);
	}
	
	public TexturePainter(Identifier texture) {
		this.texture = texture;
	}

	public void setDrawOrigin(int left, int top) {
		this.left = left;
		this.top = top;
	}

	public Vector2 getDrawOrigin() {
		return new Vector2(left, top);
	}

	/**
	 * Sets whether or not the StyleBox
	 * should tile its texture.
	 *
	 * @param tiled The value to set.
	 */
	public void setTiled(boolean tiled) {
		this.tiled = tiled;
	}

	public boolean isTiled() {
		return tiled;
	}

	public void setTextureSize(int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	public Vector2 getTextureSize() {
		return new Vector2(textureWidth, textureHeight);
	}

	public void setFileDimensions(int fileWidth, int fileHeight) {
		this.fileWidth = fileWidth;
		this.fileHeight = fileHeight;
	}

	public Vector2 getFileDimensions() {
		return new Vector2(fileWidth, fileHeight);
	}

	public void setTint(Color tint) {
		this.tint = tint;
	}

	public Color getTint() {
		return tint;
	}

	/**
	 * If texture dimensions are wonky, you
	 * may need to change the scale.
	 *
	 * @param scale The value to set.
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}

	@Override
	public void draw(MatrixStack matrices, int x, int y, int width, int height) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

		if (!tiled) {
			int drawWidth = Math.min(width, textureWidth);
			int drawHeight = Math.min(height, textureHeight);

			drawTexture(x, y, left, top, drawWidth, drawHeight);
		}
		else {
			drawTiled(x, y, left, top, textureWidth, textureHeight, width, height);
		}
	}

	@Override
	public TexturePainter copy() {
		TexturePainter copy = new TexturePainter(texture);

		copy.setDrawOrigin(left, top);
		copy.setTiled(tiled);
		copy.setTextureSize(textureWidth, textureHeight);
		copy.setTint(tint);
		copy.setFileDimensions(fileWidth, fileHeight);
		copy.setScale(scale);

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
