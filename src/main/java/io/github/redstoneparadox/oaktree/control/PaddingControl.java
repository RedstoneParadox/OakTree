package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Vector2;

/**
 * A Control that has internal padding. Serves
 * as the parent for controls such as
 * {@link BoxControl} and {@link PanelControl},
 * which can all have child Controls.
 *
 * @deprecated Will be folded into {@link PanelControl}
 */
@Deprecated
public abstract class PaddingControl extends Control {
	protected int topPadding = 0;
	protected int bottomPadding = 0;
	protected int leftPadding = 0;
	protected int rightPadding = 0;

	/**
	 * Sets the padding for all 4 sides.
	 *
	 * @param padding The padding value.
	 */
	public void setFullPadding(int padding) {
		this.topPadding = padding;
		this.bottomPadding = padding;
		this.leftPadding = padding;
		this.rightPadding = padding;
	}

	/**
	 * Sets the padding for the top.
	 *
	 * @param topPadding The padding value.
	 */
	public void setTopPadding(int topPadding) {
		this.topPadding = topPadding;
	}

	public int getTopPadding() {
		return topPadding;
	}

	/**
	 * Sets the padding for the bottom.
	 *
	 * @param bottomPadding The padding value.
	 */
	public void setBottomPadding(int bottomPadding) {
		this.topPadding = bottomPadding;
	}

	public int getBottomPadding() {
		return bottomPadding;
	}

	/**
	 * Sets the padding for the left side.
	 *
	 * @param leftPadding The padding value.
	 */
	public void setLeftPadding(int leftPadding) {
		this.leftPadding = leftPadding;
	}

	public int getLeftPadding() {
		return leftPadding;
	}

	/**
	 * Sets the padding for the right side.
	 *
	 * @param rightPadding The padding value.
	 */
	public void setRightPadding(int rightPadding) {
		this.rightPadding = rightPadding;
	}

	public int getRightPadding() {
		return rightPadding;
	}

	Vector2 innerDimensions(int spaceWidth, int spaceHeight) {
		int innerWidth = spaceWidth - leftPadding - rightPadding;
		int innerHeight = spaceHeight - topPadding - bottomPadding;

		return new Vector2(innerWidth, innerHeight);
	}

	Vector2 innerPosition(int spaceX, int spaceY) {
		int innerX = spaceX + leftPadding;
		int innerY = spaceY + topPadding;

		return new Vector2(innerX, innerY);
	}
}
