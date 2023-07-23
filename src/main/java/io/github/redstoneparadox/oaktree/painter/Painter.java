package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.control.Control;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.ApiStatus;

/**
 * <p>A Painter represents the part of a {@link Control}
 * that actually gets drawn. Multiple subclasses such
 * as {@link TexturePainter} exist to provide different
 * options for rendering.</p>
 *
 * <p>To apply a Painter to a control, Painters must be
 * added to a {@link Theme}; see the Theme javadoc for
 * more information.</p>
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

	@ApiStatus.Internal
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {

	}

	/**
	 * Creates a copy of this {@code Painter}
	 *
	 * @return The copy
	 */
	public abstract Painter copy();
}
