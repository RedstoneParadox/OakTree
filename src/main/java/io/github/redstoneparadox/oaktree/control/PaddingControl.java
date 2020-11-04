package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Vector2;
import org.jetbrains.annotations.ApiStatus;

public abstract class PaddingControl<C extends PaddingControl<C>> extends Control<C> {
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public C padding(int padding) {
		this.topPadding = padding;
		this.bottomPadding = padding;
		this.leftPadding = padding;
		this.rightPadding = padding;
		return (C) this;
	}

	/**
	 * Sets the padding for the top.
	 *
	 * @param topPadding The padding value.
	 * @return The control itself.
	 */
	public void setTopPadding(int topPadding) {
		this.topPadding = topPadding;
	}

	public int getTopPadding() {
		return topPadding;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public C topPadding(int topPadding) {
		this.topPadding = topPadding;
		return (C) this;
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public C bottomPadding(int bottomPadding) {
		this.bottomPadding = bottomPadding;
		return (C) this;
	}

	/**
	 * Sets the padding for the left side.
	 *
	 * @param leftPadding The padding value.
	 * @return The control itself.
	 */
	public void setLeftPadding(int leftPadding) {
		this.leftPadding = leftPadding;
	}

	public int getLeftPadding() {
		return leftPadding;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public C leftPadding(int leftPadding) {
		this.leftPadding = leftPadding;
		return (C) this;
	}

	/**
	 * Sets the padding for the right side.
	 *
	 * @param rightPadding The padding value.
	 * @return The control itself.
	 */
	public void setRightPadding(int rightPadding) {
		this.rightPadding = rightPadding;
	}

	public int getRightPadding() {
		return rightPadding;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public C rightPadding(int rightPadding) {
		this.rightPadding = rightPadding;
		return (C) this;
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
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
