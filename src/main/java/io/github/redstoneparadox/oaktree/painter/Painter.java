package io.github.redstoneparadox.oaktree.painter;

import net.minecraft.client.util.math.MatrixStack;

/**
 * A StyleBox represents the part of the node that actually gets drawn. Multiple Nodes
 * can be drawn as a color or texture, so this class and its children were created to
 * reduce redundancy.
 */
public abstract class Painter {
	public static final Painter BLANK = new Painter(true) {
		@Override
		public Painter copy() {
			return this;
		}
	};
	
	public final boolean blank;

	private Painter(boolean blank) {
		this.blank = blank;
	}

	protected Painter() {
		this.blank = false;
	}

	public void draw(MatrixStack matrices, int x, int y, int width, int height) {

	}

	public abstract Painter copy();
}
