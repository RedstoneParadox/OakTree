package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Vector2;

/**
 * Use to set the relative offset of a {@link Control}
 * to its parent. For example, {@link Anchor#CENTER}
 * will automatically center a Control on its parent
 * regardless of the parent's size.
 */
public enum Anchor {
	TOP_LEFT,
	TOP_CENTER,
	TOP_RIGHT,
	CENTER_LEFT,
	CENTER,
	CENTER_RIGHT,
	BOTTOM_LEFT,
	BOTTOM_CENTER,
	BOTTOM_RIGHT;

	public Vector2 getOffset(int width, int height) {
		int x = 0;
		int y = 0;

		if (this == TOP_CENTER || this == CENTER || this == BOTTOM_CENTER) {
			x = width/2;
		}
		else if (this == TOP_RIGHT || this == CENTER_RIGHT || this == BOTTOM_RIGHT) {
			x = width;
		}

		if (this == CENTER_LEFT || this == CENTER || this == CENTER_RIGHT)
		{
			y = height/2;
		}
		else if (this == BOTTOM_LEFT || this == BOTTOM_CENTER || this == BOTTOM_RIGHT)
		{
			y = height;
		}

		return new Vector2(x, y);
	}
}
