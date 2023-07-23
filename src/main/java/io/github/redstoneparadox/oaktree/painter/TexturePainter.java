package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;


public class TexturePainter extends Painter {
	protected Identifier texture;
	protected int u = 0;
	protected int v = 0;
	protected boolean tiled;
	protected int regionWidth = 0;
	protected int regionHeight = 0;
	protected Color tint = Color.WHITE;
	protected int textureWidth = 256;
	protected int textureHeight = 256;

	public TexturePainter(String path) {
		this.texture = new Identifier(path);
	}
	
	public TexturePainter(Identifier texture) {
		this.texture = texture;
	}

	public void setOrigin(int u, int v) {
		this.u = u;
		this.v = v;
	}

	public Vector2 getOrigin() {
		return new Vector2(u, v);
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

	public void setRegionSize(int textureWidth, int textureHeight) {
		this.regionWidth = textureWidth;
		this.regionHeight = textureHeight;
	}

	public Vector2 getRegionSize() {
		return new Vector2(regionWidth, regionHeight);
	}

	public void setTextureSize(int fileWidth, int fileHeight) {
		this.textureWidth = fileWidth;
		this.textureHeight = fileHeight;
	}

	public Vector2 getTextureSize() {
		return new Vector2(textureWidth, textureHeight);
	}

	public void setTint(Color tint) {
		this.tint = tint;
	}

	public Color getTint() {
		return tint;
	}

	@Override
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

		if (!tiled) {
			int drawWidth = Math.min(width, regionWidth);
			int drawHeight = Math.min(height, regionHeight);

			graphics.drawTexture(texture, x, y, drawWidth, drawHeight, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
		}
		else {
			graphics.drawRepeatingTexture(texture, x, y, width, height, u, v, textureWidth, textureHeight);
		}
	}

	@Override
	public TexturePainter copy() {
		TexturePainter copy = new TexturePainter(texture);

		copy.setOrigin(u, v);
		copy.setTiled(tiled);
		copy.setRegionSize(regionWidth, regionHeight);
		copy.setTint(tint);
		copy.setTextureSize(textureWidth, textureHeight);

		return copy;
	}
}
