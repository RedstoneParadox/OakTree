package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.control.Control;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A Painter represents the part of the {@link Control}
 * that actually gets drawn.
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
