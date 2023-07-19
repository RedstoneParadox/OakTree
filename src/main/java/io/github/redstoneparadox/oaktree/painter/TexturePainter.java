package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TexturePainter extends Painter {
	protected Identifier texture;
	protected int left = 0;
	protected int top = 0;
	protected boolean tiled;
	protected int textureWidth = 0;
	protected int textureHeight = 0;
	protected Color tint = Color.WHITE;
	protected int fileWidth = 256;
	protected int fileHeight = 256;
	protected float scale = 1;

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
	public void draw(GuiGraphics graphics, MatrixStack matrices, int x, int y, int width, int height) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

		if (!tiled) {
			int drawWidth = Math.min(width, textureWidth);
			int drawHeight = Math.min(height, textureHeight);

			graphics.drawTexture(texture, x, y, drawWidth, drawHeight, left, top, textureWidth, textureHeight, fileWidth, fileHeight);
		}
		else {
			graphics.drawRepeatingTexture(texture, x, y, width, height, left, top, fileWidth, fileHeight);
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
}
