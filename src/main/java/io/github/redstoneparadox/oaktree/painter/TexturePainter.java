package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Identifier;

/**
 * A {@code Painter} that can draw a texture.
 * Can optionally be tinted.
 */
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

	/**
	 * Sets the texture coordinates to start
	 * drawing from.
	 *
	 * @param u The horizontal coordinate
	 * @param v The vertical coordinate
	 */
	public void setOrigin(int u, int v) {
		this.u = u;
		this.v = v;
	}

	public Vector2 getOrigin() {
		return new Vector2(u, v);
	}

	/**
	 * Sets whether the {@code TexturePainter}
	 * should tile its texture.
	 *
	 * @param tiled Whether the texture should
	 *              be tiled.
	 */
	public void setTiled(boolean tiled) {
		this.tiled = tiled;
	}

	public boolean isTiled() {
		return tiled;
	}

	/**
	 * Sets the size of the region on the texture
	 * that will be drawn.
	 *
	 * @param regionWidth The region width
	 * @param regionHeight The region height
	 */
	public void setRegionSize(int regionWidth, int regionHeight) {
		this.regionWidth = regionWidth;
		this.regionHeight = regionHeight;
	}

	public Vector2 getRegionSize() {
		return new Vector2(regionWidth, regionHeight);
	}

	/**
	 * Sets the size of the texture file itself.
	 * By default, this is 256x256 as that is
	 * what Minecraft uses for all GUI textures.
	 * Unless it needs to be smaller or larger, it
	 * is recommended to stick to that standard.
	 *
	 * @param textureWidth The texture file width
	 * @param textureHeight The texture file height
	 */
	public void setTextureSize(int textureWidth, int textureHeight) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	public Vector2 getTextureSize() {
		return new Vector2(textureWidth, textureHeight);
	}

	/**
	 * Sets the color to tint the texture with.
	 * By default, this is {@link Color#WHITE},
	 * which results in the texture drawing with
	 * normal colors.
	 *
	 * @param tint The tint color.
	 */
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
			// graphics.drawRepeatingTexture(texture, x, y, width, height, u, v, textureWidth, textureHeight);
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
