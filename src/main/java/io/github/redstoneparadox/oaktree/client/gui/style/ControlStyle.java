package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;

/**
 * A StyleBox represents the part of the node that actually gets drawn. Multiple Nodes
 * can be drawn as a color or texture, so this class and its children were created to
 * reduce redundancy.
 */
public abstract class ControlStyle {
	public static final ControlStyle BLANK = new ControlStyle(true) {
		@Override
		public ControlStyle copy() {
			return this;
		}
	};
	
	public final boolean blank;

	private ControlStyle(boolean blank) {
		this.blank = blank;
	}

	protected ControlStyle() {
		this.blank = false;
	}

	public void draw(int x, int y, int width, int height, ControlGui gui) {

	}

	public abstract ControlStyle copy();
}
